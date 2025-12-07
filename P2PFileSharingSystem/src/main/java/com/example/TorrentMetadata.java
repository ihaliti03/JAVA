package com.example;

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