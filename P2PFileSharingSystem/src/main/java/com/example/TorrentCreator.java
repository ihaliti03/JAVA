package com.example;

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
