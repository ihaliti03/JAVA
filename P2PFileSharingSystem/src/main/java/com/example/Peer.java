package com.example;

// Peer.java - Main peer implementation
import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import org.json.JSONArray;

public class Peer {
    private String peerID;
    private String ip;
    private int port;
    private String sharedDirectory;
    private TorrentMetadata metadata;
    private ChunkManager chunkManager;
    private PeerServer server;
    private PeerClient client;
    private List<PeerInfo> knownPeers;
    private ScheduledExecutorService scheduler;
    private boolean running;

    public Peer(String peerID, int port, String sharedDirectory) {
        this.peerID = peerID;
        this.port = port;
        this.sharedDirectory = sharedDirectory;
        this.knownPeers = Collections.synchronizedList(new ArrayList<>());
        this.scheduler = Executors.newScheduledThreadPool(5);
        this.running = false;

        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            this.ip = "127.0.0.1";
        }

        this.client = new PeerClient(peerID);
    }

    public void loadTorrent(String metadataPath) throws IOException {
        this.metadata = TorrentMetadata.loadFromFile(metadataPath);
        this.chunkManager = new ChunkManager(metadata.getFileID(), sharedDirectory, metadata);

        // DEBUG: Force recheck chunks
        System.out.println("[DEBUG] Forcing chunk recheck...");
        BitSet chunks = chunkManager.getAvailableChunks();
        for (int i = 0; i < metadata.getTotalChunks(); i++) {
            System.out.println("Chunk " + i + ": " + (chunks.get(i) ? "EXISTS" : "MISSING"));
        }

        this.server = new PeerServer(port, chunkManager);

        System.out.println("Loaded torrent: " + metadata.getFileName());
        System.out.println("Available chunks: " + chunkManager.getAvailableChunkCount() +
                "/" + metadata.getTotalChunks());
    }

    public void start() throws IOException {
        if (metadata == null) {
            throw new IllegalStateException("No torrent loaded. Call loadTorrent() first.");
        }

        running = true;

        // Start server in separate thread
        new Thread(() -> {
            try {
                server.start();
            } catch (IOException e) {
                System.err.println("Server error: " + e.getMessage());
            }
        }).start();

        // Register with tracker
        registerWithTracker();

        // Start periodic tasks
        scheduler.scheduleAtFixedRate(this::updatePeerList, 0, 30, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::downloadMissingChunks, 5, 10, TimeUnit.SECONDS);

        System.out.println("Peer " + peerID + " started on port " + port);
    }

    private void registerWithTracker() {
        JSONObject request = new JSONObject();
        request.put("action", "register");
        request.put("fileID", metadata.getFileID());
        request.put("peerID", peerID);
        request.put("ip", ip);
        request.put("port", port);

        // Add available chunks
        JSONArray chunkArray = new JSONArray();
        BitSet availableChunks = chunkManager.getAvailableChunks();
        for (int i = availableChunks.nextSetBit(0); i >= 0; i = availableChunks.nextSetBit(i + 1)) {
            chunkArray.put(i);
        }
        request.put("chunkList", chunkArray);

        String response = client.sendTrackerRequest(metadata.getTrackerIP(),
                metadata.getTrackerPort(),
                request.toString());

        if (response != null) {
            JSONObject responseJson = new JSONObject(response);
            if ("success".equals(responseJson.getString("status"))) {
                System.out.println("Successfully registered with tracker");
            } else {
                System.err.println("Failed to register with tracker: " +
                        responseJson.getString("message"));
            }
        }
    }

    private void updatePeerList() {
        JSONObject request = new JSONObject();
        request.put("action", "get_peers");
        request.put("fileID", metadata.getFileID());

        String response = client.sendTrackerRequest(metadata.getTrackerIP(),
                metadata.getTrackerPort(),
                request.toString());

        if (response != null) {
            try {
                JSONObject responseJson = new JSONObject(response);
                if ("success".equals(responseJson.getString("status"))) {
                    JSONArray peers = responseJson.getJSONArray("peers");

                    synchronized (knownPeers) {
                        knownPeers.clear();

                        for (int i = 0; i < peers.length(); i++) {
                            JSONObject peerJson = peers.getJSONObject(i);
                            String remotePeerID = peerJson.getString("peerID");

                            // Don't add ourselves
                            if (!remotePeerID.equals(peerID)) {
                                String peerIP = peerJson.getString("ip");
                                int peerPort = peerJson.getInt("port");

                                PeerInfo peerInfo = new PeerInfo(remotePeerID, peerIP, peerPort);

                                // Convert chunk array to BitSet
                                JSONArray chunks = peerJson.getJSONArray("chunks");
                                for (int j = 0; j < chunks.length(); j++) {
                                    peerInfo.setChunk(chunks.getInt(j), true);
                                }

                                knownPeers.add(peerInfo);
                            }
                        }
                    }

                    System.out.println("Updated peer list: " + knownPeers.size() + " peers");
                }
            } catch (Exception e) {
                System.err.println("Error parsing peer list: " + e.getMessage());
            }
        }
    }

    private void downloadMissingChunks() {
        if (chunkManager.isComplete()) {
            System.out.println("Download complete!");
            return;
        }

        BitSet availableChunks = chunkManager.getAvailableChunks();
        List<Integer> missingChunks = new ArrayList<>();

        for (int i = 0; i < metadata.getTotalChunks(); i++) {
            if (!availableChunks.get(i)) {
                missingChunks.add(i);
            }
        }

        if (missingChunks.isEmpty()) {
            return;
        }

        // Shuffle for random selection
        Collections.shuffle(missingChunks);

        synchronized (knownPeers) {
            for (Integer chunkIndex : missingChunks.subList(0, Math.min(3, missingChunks.size()))) {
                // Find peers who have this chunk
                List<PeerInfo> candidatePeers = new ArrayList<>();
                for (PeerInfo peer : knownPeers) {
                    if (peer.hasChunk(chunkIndex)) {
                        candidatePeers.add(peer);
                    }
                }

                if (!candidatePeers.isEmpty()) {
                    // Try to download from a random peer
                    PeerInfo selectedPeer = candidatePeers.get(
                            (int) (Math.random() * candidatePeers.size()));

                    downloadChunkFromPeer(selectedPeer, chunkIndex);
                }
            }
        }
    }

    private void downloadChunkFromPeer(PeerInfo peer, int chunkIndex) {
        try {
            System.out.println("Requesting chunk " + chunkIndex + " from " + peer);

            byte[] chunkData = client.requestChunk(peer.getIp(), peer.getPort(), chunkIndex);

            if (chunkData != null) {
                boolean saved = chunkManager.saveChunk(chunkIndex, chunkData);
                if (saved) {
                    System.out.println("Successfully downloaded chunk " + chunkIndex);

                    // Update tracker about our new chunk
                    updateTrackerChunkList();

                    // Check if download is complete
                    if (chunkManager.isComplete()) {
                        try {
                            String outputPath = sharedDirectory + "/" + metadata.getFileName();
                            chunkManager.assembleFile(outputPath);
                            System.out.println("File download completed: " + outputPath);
                        } catch (IOException e) {
                            System.err.println("Error assembling file: " + e.getMessage());
                        }
                    }
                } else {
                    System.err.println("Failed to save chunk " + chunkIndex + " (hash mismatch)");
                }
            }
        } catch (Exception e) {
            System.err.println("Error downloading chunk " + chunkIndex +
                    " from " + peer + ": " + e.getMessage());
        }
    }

    private void updateTrackerChunkList() {
        JSONObject request = new JSONObject();
        request.put("action", "register");
        request.put("fileID", metadata.getFileID());
        request.put("peerID", peerID);
        request.put("ip", ip);
        request.put("port", port);

        JSONArray chunkArray = new JSONArray();
        BitSet availableChunks = chunkManager.getAvailableChunks();
        for (int i = availableChunks.nextSetBit(0); i >= 0; i = availableChunks.nextSetBit(i + 1)) {
            chunkArray.put(i);
        }
        request.put("chunkList", chunkArray);

        client.sendTrackerRequest(metadata.getTrackerIP(),
                metadata.getTrackerPort(),
                request.toString());
    }

    public void stop() throws IOException {
        running = false;

        // Unregister from tracker
        JSONObject request = new JSONObject();
        request.put("action", "leave");
        request.put("fileID", metadata.getFileID());
        request.put("peerID", peerID);

        client.sendTrackerRequest(metadata.getTrackerIP(),
                metadata.getTrackerPort(),
                request.toString());

        // Stop components
        if (server != null) {
            server.stop();
        }

        if (scheduler != null) {
            scheduler.shutdown();
        }

        System.out.println("Peer " + peerID + " stopped");
    }

    public void printStatus() {
        if (metadata != null && chunkManager != null) {
            System.out.println("\n=== Peer Status ===");
            System.out.println("Peer ID: " + peerID);
            System.out.println("File: " + metadata.getFileName());
            System.out.println("Progress: " + chunkManager.getAvailableChunkCount() +
                    "/" + metadata.getTotalChunks() + " chunks");
            System.out.println("Known peers: " + knownPeers.size());
            System.out.println("Complete: " + chunkManager.isComplete());
            System.out.println("==================\n");
        }
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java Peer <peerID> <port> <sharedDirectory> <metadataFile>");
            System.exit(1);
        }

        String peerID = args[0];
        int port = Integer.parseInt(args[1]);
        String sharedDirectory = args[2];
        String metadataFile = args[3];

        Peer peer = new Peer(peerID, port, sharedDirectory);

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                peer.stop();
            } catch (IOException e) {
                System.err.println("Error stopping peer: " + e.getMessage());
            }
        }));

        try {
            peer.loadTorrent(metadataFile);
            peer.start();

            // Status updates and simple command interface
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Commands: status, quit");
                String command = scanner.nextLine().trim();

                switch (command.toLowerCase()) {
                    case "status":
                        peer.printStatus();
                        break;
                    case "quit":
                        peer.stop();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Unknown command: " + command);
                }
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
