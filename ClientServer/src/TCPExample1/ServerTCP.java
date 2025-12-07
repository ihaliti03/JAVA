package TCPExample1;

import java.io.*;
import java.net.*;

public class ServerTCP {

    public static void main(String[] args) throws IOException {
        int port = 12345;

        ServerSocket myHome = new ServerSocket(port);
        Methods methods = new Methods();

        System.out.println("Server listening on port " + port);
        while (true) {
            Socket clientSocket = myHome.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] values = inputLine.split(",");
                int n1 = Integer.parseInt(values[0]);
                int n2 = Integer.parseInt(values[1]);

                out.println("Select operation: add, sub, mul, div, mod");
                String operation = in.readLine().trim().toLowerCase();

                int result = switch (operation) {
                    case "add" -> methods.add(n1, n2);
                    case "sub" -> methods.sub(n1, n2);
                    case "mul" -> methods.mul(n1, n2);
                    case "div" -> (n2 != 0) ? methods.div(n1, n2) : 0;
                    case "mod" -> (n2 != 0) ? methods.mod(n1, n2) : 0;
                    default -> {
                        out.println("Invalid operation!");
                        yield 0;
                    }
                };
                System.out.println("Received: " + inputLine + " | Operation: " + operation);
                out.println("Result: " + result);
            }
        }
    }
}


