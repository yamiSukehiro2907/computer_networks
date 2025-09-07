package Assignments.client_server_basic_tcp;

/**
 * Server Class - Single-Threaded TCP Socket Server
 * <p>
 * This class implements a strictly single-threaded server that listens for client
 * connections on a specified port and handles ONE client at a time sequentially.
 * The server receives messages from clients and terminates the connection when "exit" is received.
 * <p>
 * Key Features:
 * - Single-threaded implementation (no Thread extension)
 * - Handles one client connection at a time
 * - Proper resource management with try-with-resources
 * - Explicit connection termination handling
 * - Consistent port usage across client-server
 *
 * @author Vimal Kumar Yadav
 * @version 1.0
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private final int PORT;
    private final String ipAddress;
    private ServerSocket serverSocket;

    /**
     * Constructor to initialize server with port and IP address
     *
     * @param PORT The port number on which server will listen (must match client port)
     * @param ipAddress The IP address to bind the server socket
     */
    public Server(int PORT, String ipAddress) {
        this.PORT = PORT;
        this.ipAddress = ipAddress;
    }

    /**
     * Initializes and starts the server socket
     * Binds the server to the specified IP address and port
     *
     * @throws IOException if server socket creation or binding fails
     */
    public void startServer() throws IOException {
        // Create server socket and bind to specified address
        this.serverSocket = new ServerSocket();
        this.serverSocket.bind(new InetSocketAddress(ipAddress, PORT));
        System.out.println("Server is now live at : " + ipAddress + ":" + PORT);
        System.out.println("Waiting for client connections...");
        System.out.println("NOTE: Server handles ONE client at a time (single-threaded)");
    }

    /**
     * Main server execution method - runs the server lifecycle
     * Handles:
     * - Server initialization
     * - Sequential client connection acceptance
     * - Message processing for each client
     * - Explicit connection termination
     */
    public void runServer() {
        try {
            // Initialize server socket
            startServer();
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
            e.printStackTrace();
            return; // Exit if server cannot start
        }

        // Main server loop - accepts and processes client connections sequentially
        while (!serverSocket.isClosed()) {
            try (
                    // Accept incoming client connection - BLOCKS until client connects
                    Socket connection = serverSocket.accept();
                    // Create input scanner for reading client messages (auto-closed)
                    Scanner inputScanner = new Scanner(connection.getInputStream())
            ) {

                System.out.println("\n=== CLIENT CONNECTED ===");
                System.out.println("Client connected from: " + connection.getInetAddress());
                System.out.println("Client port: " + connection.getPort());
                System.out.println("Ready to receive messages from this client...");

                // Message processing loop - continues until client sends "exit"
                while (!connection.isClosed() && inputScanner.hasNextLine()) {
                    String message = inputScanner.nextLine();

                    // Check for termination command
                    if (message.equalsIgnoreCase("exit")) {
                        System.out.println("Exit command received from client(" + connection.getPort() + ")");
                        System.out.println("Explicitly closing connection to client...");

                        // Explicit connection closure
                        if (!connection.isClosed()) {
                            connection.close();
                        }

                        System.out.println("Connection successfully closed to client(" + connection.getPort() + ")");
                        break; // Exit message loop
                    }

                    // Display received message with client identification
                    System.out.println("Client(" + connection.getPort() + "): " + message);
                }

                System.out.println("=== CLIENT DISCONNECTED ===");
                System.out.println("Server ready for next client connection...\n");

            } catch (IOException e) {
                // Handle connection errors (only if server is still running)
                if (!serverSocket.isClosed()) {
                    System.out.println("Error handling client connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        // Server cleanup - ensure proper resource deallocation
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server shut down successfully.");
            }
        } catch (IOException e) {
            System.out.println("Error closing the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gracefully shuts down the server by closing the server socket
     * This method can be called externally to stop the server
     */
    public void shutdown() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server shutdown initiated.");
            }
        } catch (IOException e) {
            System.out.println("Error during shutdown: " + e.getMessage());
        }
    }
}