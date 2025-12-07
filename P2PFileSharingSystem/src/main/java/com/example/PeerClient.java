package com.example;

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
