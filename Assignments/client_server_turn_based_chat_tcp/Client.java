/**
 * Turn-Based TCP Chat Client
 * <p>
 * This client implements the client side of a turn-based chat system where:
 * 1. Client connects to server
 * 2. Client sends the FIRST message (initiates chat)
 * 3. Client waits for server response
 * 4. Communication alternates until client sends "exit"
 * <p>
 * Protocol Flow:
 * Client sends -> Server responds -> Client sends -> Server responds -> ...
 * <p>
 * Key Features:
 * - Client always initiates conversation
 * - Synchronous (blocking) communication
 * - Turn-based alternation enforced
 * - Graceful termination with "exit" command
 *
 * @author Vimal Kumar Yadav
 * @version 1.0
 */

package Assignments.client_server_turn_based_chat_tcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    // Server connection configuration - must match server exactly
    private static final int PORT = 13245;
    private static final String IP_ADDRESS = "127.0.0.1";

    // Maximum message length (as per requirements)
    private static final int MAX_MESSAGE_LENGTH = 1024;

    /**
     * Main method - Entry point for turn-based chat client
     *
     * Handles the complete client lifecycle:
     * - Connects to chat server
     * - Initiates conversation with first message
     * - Maintains turn-based communication protocol
     * - Handles graceful termination
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {

        System.out.println("=== TURN-BASED CHAT CLIENT ===");
        System.out.println("Connecting to server: " + IP_ADDRESS + ":" + PORT);
        System.out.println("Protocol: You send first message, then alternate");
        System.out.println("Type 'exit' to end the chat session");
        System.out.println("===============================\n");

        try (
                // Establish connection to server
                Socket socket = new Socket();
        ) {
            // Connect to server
            System.out.println("🔗 Attempting to connect to server...");
            socket.connect(new InetSocketAddress(IP_ADDRESS, PORT));

            System.out.println("✅ Successfully connected to server!");
            System.out.println("📍 Connected to: " + socket.getInetAddress());
            System.out.println("🔌 Local port: " + socket.getLocalPort());
            System.out.println("\n🎯 Turn-based chat session started!");
            System.out.println("💬 You will send the FIRST message\n");

            try (
                    // Setup communication streams
                    Scanner incomingMessageScanner = new Scanner(socket.getInputStream());
                    Scanner userInputScanner = new Scanner(System.in);
                    PrintWriter messageWriter = new PrintWriter(socket.getOutputStream(), true)
            ) {

                // Turn-based communication loop
                // CLIENT ALWAYS SENDS FIRST MESSAGE
                while (!socket.isClosed()) {

                    // STEP 1: Client's turn to send message
                    System.out.print("✍️  Enter your message: ");
                    String clientMessage = userInputScanner.nextLine();

                    // Validate message length
                    if (clientMessage.length() > MAX_MESSAGE_LENGTH) {
                        clientMessage = clientMessage.substring(0, MAX_MESSAGE_LENGTH);
                        System.out.println("⚠️  Message truncated to " + MAX_MESSAGE_LENGTH + " characters");
                    }

                    // Send message to server
                    messageWriter.println(clientMessage);
                    System.out.println("📤 You: " + clientMessage);

                    // Check for exit command
                    if (clientMessage.equalsIgnoreCase("exit")) {
                        System.out.println("\n🚪 Exit command sent to server");
                        System.out.println("💬 Ending chat session...");
                        break; // Exit the chat loop
                    }

                    // STEP 2: Wait for server's response
                    System.out.println("⏳ Waiting for server's response...\n");

                    if (incomingMessageScanner.hasNextLine()) {
                        String serverMessage = incomingMessageScanner.nextLine();
                        System.out.println("📨 Server: " + serverMessage);
                        System.out.println(); // Blank line for readability
                    } else {
                        System.out.println("❌ Server disconnected unexpectedly");
                        break;
                    }
                }

                System.out.println("🔚 Chat session completed");

            } catch (IOException e) {
                System.out.println("❌ Error during communication: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("❌ Failed to connect to server: " + e.getMessage());
            System.out.println("\n🔍 Troubleshooting:");
            System.out.println("1. Ensure server is running on " + IP_ADDRESS + ":" + PORT);
            System.out.println("2. Check if server is already handling another client");
            System.out.println("3. Verify network connectivity");
            System.out.println("4. Confirm firewall is not blocking the connection");
            e.printStackTrace();
        }

        System.out.println("👋 Chat client terminated");
    }
}