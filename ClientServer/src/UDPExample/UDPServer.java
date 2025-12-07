package UDPExample;

import java.io.*;
import java.net.*;

public class UDPServer {

    public static void main(String[] args) throws IOException {
        int port = 12345;
        byte[] buffer = new byte[256];

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP Server listening on port " + port);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int receivedPort = packet.getPort();
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received: " + received + " from " + address.getHostAddress() + ":" + receivedPort);

                String response = "UDP Server received: " + received.toUpperCase();
                byte[] responseBuffer = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, address, receivedPort);
                socket.send(responsePacket);
            }
        } catch (SocketException e) {
            System.err.println("Could not create UDP socket: " + e);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
        }
    }
}