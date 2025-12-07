package com.example;

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
