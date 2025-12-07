package com.example;

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