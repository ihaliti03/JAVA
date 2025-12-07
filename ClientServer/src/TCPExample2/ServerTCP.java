package TCPExample2;

import java.io.*;
import java.net.*;

public class ServerTCP {

    public static void main(String[] args) throws IOException {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)){
            //Creates a "waiting room" (TCP socket) on port 12345 for clients to connect.

            System.out.println("Server listening on port " + port);

            while (true) {
            //Listens forever (as long as the server is running)

                Socket clientSocket = serverSocket.accept();
                // accept() blocks until a client connects, then returns a new Socket for that client.

                System.out.println("New client connected: " + clientSocket.getInetAddress());


                //What Does .start() Do?
                //    Magic: Creates a new thread and calls run() in parallel.
                //    Without it: run() would execute in the main thread (no parallelism).
                //    Critical: Never call run() directly—use .start() to spawn a thread.
                new Thread(new ClientHandler(clientSocket)).start();
                // Spawns a new thread for each client (so the server can handle many clients at once).
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port + ": " + e);
            System.exit(-1);
        }
    }
}

//An interface with a single method run(). Lets you define code that can run in a separate thread.
class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            )
        {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from " + clientSocket.getInetAddress() + ": " + inputLine);
                String outputLine = "Server received: " + inputLine.toUpperCase();
                out.println(outputLine);
            }
        } catch (IOException e) {
            System.err.println("Exception caught for client " + clientSocket.getInetAddress() + ": " + e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e);
            }
            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
        }
    }
}


// Deepseek improvement suggestion

//package TCPExample2;
//
//import java.io.*;
//import java.net.*;
//import java.util.concurrent.*;
//
//public class ServerTCP {
//    private static final int PORT = 12345;
//    private static final int MAX_THREADS = 10; // Adjust based on your needs
//
//    public static void main(String[] args) {
//        // Create a thread pool (better than raw threads)
//        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
//
//        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
//            System.out.println("Server listening on port " + PORT);
//
//            while (!Thread.currentThread().isInterrupted()) {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("New client connected: " + clientSocket.getInetAddress());
//
//                // Submit task to thread pool
//                threadPool.execute(new ClientHandler(clientSocket));
//            }
//        } catch (IOException e) {
//            System.err.println("Server error: " + e.getMessage());
//        } finally {
//            threadPool.shutdown(); // Graceful shutdown
//            System.out.println("Server stopped");
//        }
//    }
//}
//
//class ClientHandler implements Runnable {
//    private final Socket clientSocket;
//
//    public ClientHandler(Socket socket) {
//        this.clientSocket = socket;
//    }
//
//    @Override
//    public void run() {
//        // Try-with-resources for auto-closeable streams
//        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
//
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println("Received from " + clientSocket.getInetAddress() + ": " + inputLine);
//                out.println("Server received: " + inputLine.toUpperCase());
//            }
//        } catch (IOException e) {
//            System.err.println("Client error: " + e.getMessage());
//        } finally {
//            try {
//                clientSocket.close();
//            } catch (IOException e) {
//                System.err.println("Failed to close socket: " + e.getMessage());
//            }
//            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
//        }
//    }
//}



//🚀 Key Improvements
//
//    Thread Pool (ExecutorService)
//
//        Replaces raw new Thread() with a managed pool of threads.
//
//        Prevents server overload (max 10 concurrent clients here).
//
//        Uses execute() instead of start().
//
//    Graceful Shutdown
//
//        threadPool.shutdown() in finally ensures clean exit.
//
//    Better Resource Handling
//
//        All resources (Socket, PrintWriter, BufferedReader) use try-with-resources.
//
//    Interruption Support
//
//        while (!Thread.currentThread().isInterrupted()) allows graceful shutdown via Ctrl+C.