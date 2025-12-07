package com.example;

// Tracker.java - Central coordinator for peers
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;
import org.json.JSONArray;

public class Tracker {
    private int port;
    private ServerSocket serverSocket;
    private Map<String, Map<String, PeerInfo>> filePeers; // fileID -> peerID -> PeerInfo
    private boolean running;

    public Tracker(int port) {
        this.port = port;
        this.filePeers = new ConcurrentHashMap<>();
        this.running = false;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;

        System.out.println("Tracker started on port " + port);

        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            } catch (IOException e) {
                if (running) {
                    System.err.println("Error accepting client: " + e.getMessage());
                }
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request = in.readLine();
            if (request != null) {
                String response = processRequest(request);
                out.println(response);
            }

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private String processRequest(String request) {
        try {
            JSONObject json = new JSONObject(request);
            String action = json.getString("action");

            switch (action) {
                case "register":
                    return handleRegister(json);
                case "get_peers":
                    return handleGetPeers(json);
                case "leave":
                    return handleLeave(json);
                default:
                    return createErrorResponse("Unknown action: " + action);
            }
        } catch (Exception e) {
            return createErrorResponse("Invalid request: " + e.getMessage());
        }
    }

    private String handleRegister(JSONObject json) {
        String fileID = json.getString("fileID");
        String peerID = json.getString("peerID");
        String ip = json.getString("ip");
        int port = json.getInt("port");

        PeerInfo peer = new PeerInfo(peerID, ip, port);

        if (json.has("chunkList")) {
            JSONArray chunks = json.getJSONArray("chunkList");
            for (int i = 0; i < chunks.length(); i++) {
                peer.setChunk(chunks.getInt(i), true);
            }
        }

        filePeers.computeIfAbsent(fileID, k -> new ConcurrentHashMap<>()).put(peerID, peer);

        System.out.println("Registered peer " + peerID + " for file " + fileID);

        JSONObject response = new JSONObject();
        response.put("status", "success");
        response.put("message", "Peer registered successfully");
        return response.toString();
    }

    private String handleGetPeers(JSONObject json) {
        String fileID = json.getString("fileID");

        Map<String, PeerInfo> peers = filePeers.get(fileID);

        JSONObject response = new JSONObject();
        if (peers != null && !peers.isEmpty()) {
            JSONArray peerArray = new JSONArray();

            for (PeerInfo peer : peers.values()) {
                JSONObject peerJson = new JSONObject();
                peerJson.put("peerID", peer.getPeerID());
                peerJson.put("ip", peer.getIp());
                peerJson.put("port", peer.getPort());

                // Convert BitSet to array
                JSONArray chunkArray = new JSONArray();
                BitSet chunks = peer.getAvailableChunks();
                for (int i = chunks.nextSetBit(0); i >= 0; i = chunks.nextSetBit(i + 1)) {
                    chunkArray.put(i);
                }
                peerJson.put("chunks", chunkArray);

                peerArray.put(peerJson);
            }

            response.put("status", "success");
            response.put("peers", peerArray);
        } else {
            response.put("status", "success");
            response.put("peers", new JSONArray());
        }

        return response.toString();
    }

    private String handleLeave(JSONObject json) {
        String fileID = json.getString("fileID");
        String peerID = json.getString("peerID");

        Map<String, PeerInfo> peers = filePeers.get(fileID);
        if (peers != null) {
            peers.remove(peerID);
            if (peers.isEmpty()) {
                filePeers.remove(fileID);
            }
        }

        System.out.println("Peer " + peerID + " left file " + fileID);

        JSONObject response = new JSONObject();
        response.put("status", "success");
        response.put("message", "Peer removed successfully");
        return response.toString();
    }

    private String createErrorResponse(String message) {
        JSONObject response = new JSONObject();
        response.put("status", "error");
        response.put("message", message);
        return response.toString();
    }

    public void stop() throws IOException {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        System.out.println("Tracker stopped");
    }

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;

        Tracker tracker = new Tracker(port);

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                tracker.stop();
            } catch (IOException e) {
                System.err.println("Error stopping tracker: " + e.getMessage());
            }
        }));

        try {
            tracker.start();
        } catch (IOException e) {
            System.err.println("Failed to start tracker: " + e.getMessage());
        }
    }
}
