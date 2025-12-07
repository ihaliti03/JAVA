package com.example;

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