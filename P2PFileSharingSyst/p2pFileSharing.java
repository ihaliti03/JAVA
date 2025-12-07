// Message.java - Communication protocol between peers
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.List;
import java.util.ArrayList;

public class Message {
    public enum Type {
        HAVE, REQUEST, CHUNK, JOIN, LEAVE, PEER_LIST, HANDSHAKE
    }
    
    private Type type;
    private String peerID;
    private int chunkIndex;
    private byte[] chunkData;
    private List<Integer> chunkList;
    private String fileID;
    private String peerIP;
    private int peerPort;
    
    public Message(Type type) {
        this.type = type;
        this.chunkList = new ArrayList<>();
    }
    
    // Getters and setters
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
    public String getPeerID() { return peerID; }
    public void setPeerID(String peerID) { this.peerID = peerID; }
    public int getChunkIndex() { return chunkIndex; }
    public void setChunkIndex(int chunkIndex) { this.chunkIndex = chunkIndex; }
    public byte[] getChunkData() { return chunkData; }
    public void setChunkData(byte[] chunkData) { this.chunkData = chunkData; }
    public List<Integer> getChunkList() { return chunkList; }
    public void setChunkList(List<Integer> chunkList) { this.chunkList = chunkList; }
    public String getFileID() { return fileID; }
    public void setFileID(String fileID) { this.fileID = fileID; }
    public String getPeerIP() { return peerIP; }
    public void setPeerIP(String peerIP) { this.peerIP = peerIP; }
    public int getPeerPort() { return peerPort; }
    public void setPeerPort(int peerPort) { this.peerPort = peerPort; }
    
    public String toJSON() {
        JSONObject json = new JSONObject();
        json.put("type", type.toString());
        json.put("peerID", peerID);
        json.put("chunkIndex", chunkIndex);
        json.put("fileID", fileID);
        json.put("peerIP", peerIP);
        json.put("peerPort", peerPort);
        
        if (chunkList != null && !chunkList.isEmpty()) {
            JSONArray chunks = new JSONArray();
            for (Integer chunk : chunkList) {
                chunks.put(chunk);
            }
            json.put("chunkList", chunks);
        }
        
        return json.toString();
    }
    
    public static Message fromJSON(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        Message msg = new Message(Type.valueOf(json.getString("type")));
        
        if (json.has("peerID")) msg.setPeerID(json.getString("peerID"));
        if (json.has("chunkIndex")) msg.setChunkIndex(json.getInt("chunkIndex"));
        if (json.has("fileID")) msg.setFileID(json.getString("fileID"));
        if (json.has("peerIP")) msg.setPeerIP(json.getString("peerIP"));
        if (json.has("peerPort")) msg.setPeerPort(json.getInt("peerPort"));
        
        if (json.has("chunkList")) {
            JSONArray chunks = json.getJSONArray("chunkList");
            List<Integer> chunkList = new ArrayList<>();
            for (int i = 0; i < chunks.length(); i++) {
                chunkList.add(chunks.getInt(i));
            }
            msg.setChunkList(chunkList);
        }
        
        return msg;
    }
}

// TorrentMetadata.java - File metadata management
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;

public class TorrentMetadata {
    private String fileID;
    private String fileName;
    private long fileSize;
    private int chunkSize;
    private int totalChunks;
    private List<String> chunkHashes;
    private String trackerIP;
    private int trackerPort;
    
    public TorrentMetadata() {
        this.chunkHashes = new ArrayList<>();
        this.chunkSize = 1024 * 256; // 256KB chunks
    }
    
    public static TorrentMetadata createFromFile(String filePath, String trackerIP, int trackerPort) 
            throws IOException, NoSuchAlgorithmException {
        File file = new File(filePath);
        TorrentMetadata metadata = new TorrentMetadata();
        
        metadata.fileName = file.getName();
        metadata.fileSize = file.length();
        metadata.trackerIP = trackerIP;
        metadata.trackerPort = trackerPort;
        metadata.fileID = generateFileID(file.getName() + file.length());
        
        // Calculate chunks and hashes
        metadata.totalChunks = (int) Math.ceil((double) metadata.fileSize / metadata.chunkSize);
        
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[metadata.chunkSize];
            
            for (int i = 0; i < metadata.totalChunks; i++) {
                int bytesRead = raf.read(buffer);
                if (bytesRead > 0) {
                    byte[] chunkData = new byte[bytesRead];
                    System.arraycopy(buffer, 0, chunkData, 0, bytesRead);
                    
                    digest.reset();
                    byte[] hash = digest.digest(chunkData);
                    metadata.chunkHashes.add(bytesToHex(hash));
                }
            }
        }
        
        return metadata;
    }
    
    private static String generateFileID(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes());
        return bytesToHex(hash).substring(0, 16);
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    
    public void saveToFile(String metadataPath) throws IOException {
        JSONObject json = new JSONObject();
        json.put("fileID", fileID);
        json.put("fileName", fileName);
        json.put("fileSize", fileSize);
        json.put("chunkSize", chunkSize);
        json.put("totalChunks", totalChunks);
        json.put("trackerIP", trackerIP);
        json.put("trackerPort", trackerPort);
        
        JSONArray hashArray = new JSONArray();
        for (String hash : chunkHashes) {
            hashArray.put(hash);
        }
        json.put("chunkHashes", hashArray);
        
        try (FileWriter writer = new FileWriter(metadataPath)) {
            writer.write(json.toString(2));
        }
    }
    
    public static TorrentMetadata loadFromFile(String metadataPath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(metadataPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        
        JSONObject json = new JSONObject(content.toString());
        TorrentMetadata metadata = new TorrentMetadata();
        
        metadata.fileID = json.getString("fileID");
        metadata.fileName = json.getString("fileName");
        metadata.fileSize = json.getLong("fileSize");
        metadata.chunkSize = json.getInt("chunkSize");
        metadata.totalChunks = json.getInt("totalChunks");
        metadata.trackerIP = json.getString("trackerIP");
        metadata.trackerPort = json.getInt("trackerPort");
        
        JSONArray hashArray = json.getJSONArray("chunkHashes");
        for (int i = 0; i < hashArray.length(); i++) {
            metadata.chunkHashes.add(hashArray.getString(i));
        }
        
        return metadata;
    }
    
    // Getters and setters
    public String getFileID() { return fileID; }
    public String getFileName() { return fileName; }
    public long getFileSize() { return fileSize; }
    public int getChunkSize() { return chunkSize; }
    public int getTotalChunks() { return totalChunks; }
    public List<String> getChunkHashes() { return chunkHashes; }
    public String getTrackerIP() { return trackerIP; }
    public int getTrackerPort() { return trackerPort; }
    public void setFileID(String fileID) { this.fileID = fileID; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setFileSize(long fileSize) { this.fileSize = fileSize; }
    public void setChunkSize(int chunkSize) { this.chunkSize = chunkSize; }
    public void setTotalChunks(int totalChunks) { this.totalChunks = totalChunks; }
    public void setTrackerIP(String trackerIP) { this.trackerIP = trackerIP; }
    public void setTrackerPort(int trackerPort) { this.trackerPort = trackerPort; }
}

// ChunkManager.java - Chunk operations
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class ChunkManager {
    private String fileID;
    private String sharedDirectory;
    private TorrentMetadata metadata;
    private BitSet availableChunks;
    
    public ChunkManager(String fileID, String sharedDirectory, TorrentMetadata metadata) {
        this.fileID = fileID;
        this.sharedDirectory = sharedDirectory;
        this.metadata = metadata;
        this.availableChunks = new BitSet(metadata.getTotalChunks());
        
        // Create directory structure
        File dir = new File(sharedDirectory + "/" + fileID);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        // Check existing chunks
        checkExistingChunks();
    }
    
    private void checkExistingChunks() {
        for (int i = 0; i < metadata.getTotalChunks(); i++) {
            File chunkFile = new File(getChunkPath(i));
            if (chunkFile.exists() && verifyChunk(i)) {
                availableChunks.set(i);
            }
        }
    }
    
    public boolean hasChunk(int chunkIndex) {
        return availableChunks.get(chunkIndex);
    }
    
    public byte[] getChunk(int chunkIndex) throws IOException {
        if (!hasChunk(chunkIndex)) {
            return null;
        }
        
        File chunkFile = new File(getChunkPath(chunkIndex));
        try (FileInputStream fis = new FileInputStream(chunkFile)) {
            byte[] data = new byte[(int) chunkFile.length()];
            fis.read(data);
            return data;
        }
    }
    
    public boolean saveChunk(int chunkIndex, byte[] data) throws IOException, NoSuchAlgorithmException {
        // Verify hash
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data);
        String hashHex = bytesToHex(hash);
        
        if (!hashHex.equals(metadata.getChunkHashes().get(chunkIndex))) {
            System.out.println("Chunk " + chunkIndex + " hash verification failed!");
            return false;
        }
        
        // Save chunk
        File chunkFile = new File(getChunkPath(chunkIndex));
        try (FileOutputStream fos = new FileOutputStream(chunkFile)) {
            fos.write(data);
        }
        
        availableChunks.set(chunkIndex);
        System.out.println("Chunk " + chunkIndex + " saved and verified.");
        return true;
    }
    
    private boolean verifyChunk(int chunkIndex) {
        try {
            byte[] data = getChunk(chunkIndex);
            if (data == null) return false;
            
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);
            String hashHex = bytesToHex(hash);
            
            return hashHex.equals(metadata.getChunkHashes().get(chunkIndex));
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isComplete() {
        return availableChunks.cardinality() == metadata.getTotalChunks();
    }
    
    public void assembleFile(String outputPath) throws IOException {
        if (!isComplete()) {
            throw new IOException("Not all chunks are available for assembly");
        }
        
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            for (int i = 0; i < metadata.getTotalChunks(); i++) {
                byte[] chunk = getChunk(i);
                fos.write(chunk);
            }
        }
        
        System.out.println("File assembled successfully: " + outputPath);
    }
    
    private String getChunkPath(int chunkIndex) {
        return sharedDirectory + "/" + fileID + "/" + chunkIndex + ".part";
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    
    public BitSet getAvailableChunks() {
        return (BitSet) availableChunks.clone();
    }
    
    public int getAvailableChunkCount() {
        return availableChunks.cardinality();
    }
}

// PeerInfo.java - Peer information storage
import java.util.BitSet;
import java.util.concurrent.ConcurrentHashMap;

public class PeerInfo {
    private String peerID;
    private String ip;
    private int port;
    private BitSet availableChunks;
    private long lastSeen;
    
    public PeerInfo(String peerID, String ip, int port) {
        this.peerID = peerID;
        this.ip = ip;
        this.port = port;
        this.availableChunks = new BitSet();
        this.lastSeen = System.currentTimeMillis();
    }
    
    // Getters and setters
    public String getPeerID() { return peerID; }
    public String getIp() { return ip; }
    public int getPort() { return port; }
    public BitSet getAvailableChunks() { return availableChunks; }
    public long getLastSeen() { return lastSeen; }
    public void setAvailableChunks(BitSet availableChunks) { this.availableChunks = availableChunks; }
    public void updateLastSeen() { this.lastSeen = System.currentTimeMillis(); }
    
    public boolean hasChunk(int chunkIndex) {
        return availableChunks.get(chunkIndex);
    }
    
    public void setChunk(int chunkIndex, boolean available) {
        availableChunks.set(chunkIndex, available);
    }
    
    @Override
    public String toString() {
        return String.format("Peer[%s:%d] - %s", ip, port, peerID);
    }
}

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

// PeerServer.java - Handles incoming requests from other peers
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PeerServer {
    private int port;
    private ServerSocket serverSocket;
    private ChunkManager chunkManager;
    private boolean running;
    private ExecutorService threadPool;
    
    public PeerServer(int port, ChunkManager chunkManager) {
        this.port = port;
        this.chunkManager = chunkManager;
        this.running = false;
        this.threadPool = Executors.newFixedThreadPool(10);
    }
    
    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        
        System.out.println("Peer server started on port " + port);
        
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                threadPool.submit(() -> handlePeerRequest(clientSocket));
            } catch (IOException e) {
                if (running) {
                    System.err.println("Error accepting peer connection: " + e.getMessage());
                }
            }
        }
    }
    
    private void handlePeerRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()) {
            
            String requestLine = in.readLine();
            if (requestLine != null) {
                Message request = Message.fromJSON(requestLine);
                handleMessage(request, out);
            }
            
        } catch (Exception e) {
            System.err.println("Error handling peer request: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing peer socket: " + e.getMessage());
            }
        }
    }
    
    private void handleMessage(Message request, OutputStream out) throws IOException {
        switch (request.getType()) {
            case REQUEST:
                handleChunkRequest(request, out);
                break;
            case HANDSHAKE:
                handleHandshake(request, out);
                break;
            default:
                System.out.println("Unknown message type: " + request.getType());
        }
    }
    
    private void handleChunkRequest(Message request, OutputStream out) throws IOException {
        int chunkIndex = request.getChunkIndex();
        
        if (chunkManager.hasChunk(chunkIndex)) {
            byte[] chunkData = chunkManager.getChunk(chunkIndex);
            
            Message response = new Message(Message.Type.CHUNK);
            response.setChunkIndex(chunkIndex);
            response.setChunkData(chunkData);
            
            // Send response header
            String header = response.toJSON() + "\n";
            out.write(header.getBytes());
            
            // Send chunk data
            out.write(chunkData);
            out.flush();
            
            System.out.println("Sent chunk " + chunkIndex + " to peer");
        } else {
            // Send error response
            Message response = new Message(Message.Type.CHUNK);
            response.setChunkIndex(-1); // Indicates error
            
            String header = response.toJSON() + "\n";
            out.write(header.getBytes());
            out.flush();
        }
    }
    
    private void handleHandshake(Message request, OutputStream out) throws IOException {
        Message response = new Message(Message.Type.HAVE);
        response.setChunkList(convertBitSetToList(chunkManager.getAvailableChunks()));
        
        String responseStr = response.toJSON() + "\n";
        out.write(responseStr.getBytes());
        out.flush();
        
        System.out.println("Sent handshake response to peer " + request.getPeerID());
    }
    
    private java.util.List<Integer> convertBitSetToList(java.util.BitSet bitSet) {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            list.add(i);
        }
        return list;
    }
    
    public void stop() throws IOException {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        if (threadPool != null) {
            threadPool.shutdown();
        }
        System.out.println("Peer server stopped");
    }
}

// PeerClient.java - Handles outgoing requests to other peers
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;

public class PeerClient {
    private String peerID;
    
    public PeerClient(String peerID) {
        this.peerID = peerID;
    }
    
    public byte[] requestChunk(String peerIP, int peerPort, int chunkIndex) {
        try (Socket socket = new Socket(peerIP, peerPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             InputStream in = socket.getInputStream()) {
            
            Message request = new Message(Message.Type.REQUEST);
            request.setPeerID(peerID);
            request.setChunkIndex(chunkIndex);
            
            out.println(request.toJSON());
            
            // Read response header
            BufferedReader headerReader = new BufferedReader(new InputStreamReader(in));
            String headerLine = headerReader.readLine();
            
            if (headerLine != null) {
                Message response = Message.fromJSON(headerLine);
                
                if (response.getType() == Message.Type.CHUNK && response.getChunkIndex() == chunkIndex) {
                    // Calculate chunk size (need to read remaining data)
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    
                    while ((bytesRead = in.read(buffer)) != -1) {
                        baos.write(buffer, 0, bytesRead);
                    }
                    
                    return baos.toByteArray();
                } else if (response.getChunkIndex() == -1) {
                    System.out.println("Peer doesn't have chunk " + chunkIndex);
                    return null;
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error requesting chunk from " + peerIP + ":" + peerPort + " - " + e.getMessage());
        }
        
        return null;
    }
    
    public List<Integer> handshakeWithPeer(String peerIP, int peerPort) {
        try (Socket socket = new Socket(peerIP, peerPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            Message handshake = new Message(Message.Type.HANDSHAKE);
            handshake.setPeerID(peerID);
            
            out.println(handshake.toJSON());
            
            String responseLine = in.readLine();
            if (responseLine != null) {
                Message response = Message.fromJSON(responseLine);
                if (response.getType() == Message.Type.HAVE) {
                    return response.getChunkList();
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error handshaking with " + peerIP + ":" + peerPort + " - " + e.getMessage());
        }
        
        return new ArrayList<>();
    }
    
    public String sendTrackerRequest(String trackerIP, int trackerPort, String request) {
        try (Socket socket = new Socket(trackerIP, trackerPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            out.println(request);
            return in.readLine();
            
        } catch (IOException e) {
            System.err.println("Error communicating with tracker: " + e.getMessage());
            return null;
        }
    }
}

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

// TorrentCreator.java - Utility to create torrent files
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class TorrentCreator {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java TorrentCreator <filePath> <trackerIP> <trackerPort> <outputPath>");
            System.exit(1);
        }
        
        String filePath = args[0];
        String trackerIP = args[1];
        int trackerPort = Integer.parseInt(args[2]);
        String outputPath = args[3];
        
        try {
            TorrentMetadata metadata = TorrentMetadata.createFromFile(filePath, trackerIP, trackerPort);
            metadata.saveToFile(outputPath);
            
            System.out.println("Torrent metadata created successfully:");
            System.out.println("File: " + metadata.getFileName());
            System.out.println("File ID: " + metadata.getFileID());
            System.out.println("Size: " + metadata.getFileSize() + " bytes");
            System.out.println("Chunks: " + metadata.getTotalChunks());
            System.out.println("Metadata saved to: " + outputPath);
            
        } catch (IOException | NoSuchAlgorithmException e) {
            System.err.println("Error creating torrent: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
