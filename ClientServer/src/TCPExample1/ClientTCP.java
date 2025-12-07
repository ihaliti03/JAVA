package TCPExample1;

import java.io.*;
import java.net.*;

public class ClientTCP {

    public static void main(String[] args) {

        try {
            String hostname = "localhost";
            int port = 12345;

            Socket socket = new Socket(hostname, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);


            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("Server response: " + in.readLine());
            }
        } catch (IOException e) {
            System.err.println("🔥 Error: " + e.getMessage());
            System.err.println("  Possible fixes:");
            System.err.println("- Is the server running?");
            System.err.println("- Is the port correct?");
            System.err.println("- Is your internet working?");
        }

    }
}