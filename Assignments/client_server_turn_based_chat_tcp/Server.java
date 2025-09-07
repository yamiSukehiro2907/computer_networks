/**
 * Turn-Based TCP Chat Server
 * <p>
 * This server implements a turn-based chat system where:
 * 1. Client connects and sends the FIRST message
 * 2. Server receives client message and responds
 * 3. Communication alternates between client and server
 * 4. Chat terminates when client sends "exit"
 * <p>
 * Protocol Flow:
 * Client -> Server -> Client -> Server -> ... until "exit"
 *
 * @author Vimal Kumar Yadav
 * @version 1.0
 */

package Assignments.client_server_turn_based_chat_tcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private final int PORT;
    private final String ipAddress;
    private ServerSocket serverSocket;

    // Maximum message length (as per requirements)
    private static final int MAX_MESSAGE_LENGTH = 1024;

    /**
     * Constructor to initialize server with port and IP address
     *
     * @param PORT The port number for server socket binding
     * @param ipAddress The IP address to bind the server socket
     */
    public Server(int PORT, String ipAddress) {
        this.PORT = PORT;
        this.ipAddress = ipAddress;
    }

    /**
     * Main server execution method that handles the server lifecycle:
     * - Starts server and listens for connections
     * - Handles turn-based chat communication
     * - Manages connection termination
     */
    public void runServer() {
        try {
            startServer();
            Thread.sleep(1000); // Brief pause for server stabilization
        } catch (IOException | InterruptedException e) {
            System.out.println("Error starting the server: " + e.getMessage());
            return;
        }

        System.out.println("=== TURN-BASED CHAT SERVER ===");
        System.out.println("Server ready for connections...");
        System.out.println("Protocol: Client sends first, then alternating turns");
        System.out.println("===============================\n");

        // Main server loop - handles one client at a time
        while (!serverSocket.isClosed()) {
            System.out.println("Waiting for client connection...");

            try (
                    // Accept client connection
                    Socket connection = this.serverSocket.accept();
                    // Setup communication streams
                    PrintWriter messageWriter = new PrintWriter(connection.getOutputStream(), true);
                    Scanner incomingMessageScanner = new Scanner(connection.getInputStream());
                    Scanner serverInputScanner = new Scanner(System.in)
            ) {

                System.out.println("\n🎯 CLIENT CONNECTED!");
                System.out.println("Client: " + connection.getInetAddress() + ":" + connection.getPort());
                System.out.println("📝 Turn-based chat session started");
                System.out.println("⏳ Waiting for client's first message...\n");

                // Turn-based communication loop
                // CLIENT ALWAYS SENDS FIRST MESSAGE
                while (!connection.isClosed() && incomingMessageScanner.hasNextLine()) {

                    // STEP 1: Receive message from client
                    System.out.println("👂 Listening for client message...");
                    String clientMessage = incomingMessageScanner.nextLine();

                    // Check for termination command
                    if (clientMessage.equalsIgnoreCase("exit")) {
                        System.out.println("\n📤 Client sent EXIT command");
                        System.out.println("💬 Chat session ending...");
                        System.out.println("🔌 Connection will be closed");
                        break; // Exit the chat loop
                    }

                    // Display received message
                    System.out.println("📨 Client: " + clientMessage);

                    // STEP 2: Server's turn to respond
                    System.out.print("\n✍️  Your turn to respond: ");
                    String serverMessage = serverInputScanner.nextLine();

                    // Validate message length
                    if (serverMessage.length() > MAX_MESSAGE_LENGTH) {
                        serverMessage = serverMessage.substring(0, MAX_MESSAGE_LENGTH);
                        System.out.println("⚠️  Message truncated to " + MAX_MESSAGE_LENGTH + " characters");
                    }

                    // Send response to client
                    messageWriter.println(serverMessage);
                    System.out.println("📤 Server: " + serverMessage);
                    System.out.println("⏳ Waiting for client's response...\n");
                }

                System.out.println("🔚 Chat session ended with client: " + connection.getPort());
                System.out.println("👋 Client disconnected\n");

            } catch (IOException e) {
                System.out.println("❌ Error during client communication: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Server shutdown
        System.out.println("🛑 Server shutting down...");
    }

    /**
     * Initializes and starts the server socket
     * Binds the server to the specified IP address and port
     *
     * @throws IOException if server socket creation or binding fails
     */
    private void startServer() throws IOException {
        this.serverSocket = new ServerSocket();
        this.serverSocket.bind(new InetSocketAddress(ipAddress, PORT));
        System.out.println("✅ Server socket created and bound to " + ipAddress + ":" + PORT);
    }

    /**
     * Gracefully shuts down the server by closing the server socket
     */
    public void shutdown() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("🔌 Server socket closed successfully");
            }
        } catch (IOException e) {
            System.out.println("❌ Error closing server socket: " + e.getMessage());
        }
    }
}