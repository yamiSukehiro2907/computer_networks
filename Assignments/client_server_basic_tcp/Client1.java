package Assignments.client_server_basic_tcp;

/**
 * Client Class - TCP Socket Client Application
 * <p>
 * This class implements a client that connects to a TCP server using the EXACT
 * same port number as the server. The client sends messages interactively and
 * terminates on "exit" command with proper connection closure.
 * <p>
 * IMPORTANT: Port number MUST match the server's port number exactly.
 * <p>
 * Key Features:
 * - Connects to server using consistent port configuration
 * - Interactive user input mechanism
 * - Proper resource management and cleanup
 * - Explicit connection termination
 * - Comprehensive error handling with troubleshooting tips
 *
 * @author Vimal Kumar Yadav
 * @version 1.0
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {

    // CRITICAL: These values MUST match the server's configuration exactly
    private static final int PORT = 13245;           // Must match server port
    private static final String IP_ADDRESS = "127.0.0.1";  // Must match server IP

    /**
     * Main method - Entry point for client application
     * Handles the complete client lifecycle:
     * - Connection establishment with exact server parameters
     * - Message transmission loop
     * - Proper resource cleanup
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {

        // Resource declarations for proper cleanup
        Socket socket = null;
        Scanner inputScanner = null;
        PrintWriter outputWriter = null;

        // Display connection information
        System.out.println("=== TCP Socket Client ===");
        System.out.println("Connecting to server at: " + IP_ADDRESS + ":" + PORT);
        System.out.println("NOTE: Server port and client port must match exactly!");
        System.out.println("========================\n");

        try {
            // Initialize socket and attempt connection to server
            socket = new Socket();
            System.out.println("Attempting to connect to server...");

            // Establish TCP connection to server using EXACT same parameters
            socket.connect(new InetSocketAddress(IP_ADDRESS, PORT));

            System.out.println("✅ Successfully connected to server!");
            System.out.println("Connection Details:");
            System.out.println("- Server Address: " + socket.getInetAddress());
            System.out.println("- Server Port: " + socket.getPort());
            System.out.println("- Local Port: " + socket.getLocalPort());
            System.out.println("\nYou can now send messages to the server.");
            System.out.println("Type 'exit' to close connection and quit.\n");

            // Initialize input/output streams
            inputScanner = new Scanner(System.in); // For reading user input
            outputWriter = new PrintWriter(socket.getOutputStream(), true); // For sending to server

            // Main message transmission loop
            while (true) {
                System.out.print("Enter message: ");
                String message = inputScanner.nextLine();

                // Display what client is sending
                System.out.println("Client: " + message);

                // Transmit message to server
                outputWriter.println(message);

                // Check for termination command
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Exit command sent. Closing connection...");

                    // Explicit socket closure
                    if (!socket.isClosed()) {
                        socket.close();
                        System.out.println("Socket connection explicitly closed.");
                    }
                    break; // Exit the message loop
                }
            }

        } catch (IOException e) {
            // Handle connection and communication errors with detailed troubleshooting
            System.out.println("❌ Error in client communication: " + e.getMessage());
            System.out.println("\n🔍 Troubleshooting Guide:");
            System.out.println("1. Ensure server is running BEFORE starting client");
            System.out.println("2. Verify server is listening on " + IP_ADDRESS + ":" + PORT);
            System.out.println("3. Check that server port (" + PORT + ") matches client port");
            System.out.println("4. Confirm no firewall blocking the connection");
            System.out.println("5. Ensure server is not already handling another client");
            System.out.println("\n📋 Steps to resolve:");
            System.out.println("- Run Main.java first to start server");
            System.out.println("- Wait for 'Server is now live' message");
            System.out.println("- Then run this Client.java");

            e.printStackTrace();

        } finally {
            // Resource cleanup - ensures all resources are properly closed
            System.out.println("\n🧹 Cleaning up resources...");

            // Close output writer
            if (outputWriter != null) {
                outputWriter.close();
                System.out.println("✅ Output stream closed.");
            }

            // Close input scanner
            if (inputScanner != null) {
                inputScanner.close();
                System.out.println("✅ Input scanner closed.");
            }

            // Close socket connection
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                    System.out.println("✅ Socket connection closed.");
                }
            } catch (IOException e) {
                System.out.println("❌ Error closing socket: " + e.getMessage());
            }

            System.out.println("✅ Client application terminated successfully.");
        }
    }
}