package com.example;

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
