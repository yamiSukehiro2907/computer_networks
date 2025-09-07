/**
 * UDP Client Class - Connectionless Datagram Client
 * <p>
 * This class implements a UDP client that sends datagrams to a UDP server.
 * Each message is sent as an independent datagram - there is no connection
 * established between client and server (connectionless communication).
 * <p>
 * UDP Characteristics:
 * - No guarantee of delivery (unreliable)
 * - No guarantee of message ordering
 * - Each datagram is independent
 * - Lower overhead than TCP
 * <p>
 * Key Features:
 * - Creates DatagramSocket for sending packets
 * - Interactive user input for message creation
 * - Proper resource management and cleanup
 * - Handles "exit" termination gracefully
 *
 * @author Your Name
 * @version 1.0
 */

package Assignments.client_server_basic_udp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {

    // Server configuration - must match server settings
    private static final int PORT = 12345;
    private static final String IP_ADDRESS = "127.0.0.1";

    /**
     * Main method - Entry point for UDP client application
     *
     * Handles the complete client lifecycle:
     * - Creates UDP socket for sending datagrams
     * - Resolves server address
     * - Interactive message input loop
     * - Sends each message as independent datagram
     * - Proper resource cleanup
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {

        System.out.println("=== UDP Client Application ===");
        System.out.println("Connecting to UDP server at: " + IP_ADDRESS + ":" + PORT);
        System.out.println("Protocol: UDP (connectionless, unreliable delivery)");
        System.out.println("Each message will be sent as independent datagram");
        System.out.println("Type 'exit' to terminate client and server");
        System.out.println("===============================\n");

        // Resolve server address
        InetAddress serverAddress;
        try {
            serverAddress = InetAddress.getByName(IP_ADDRESS);
            System.out.println("Server address resolved: " + serverAddress.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Error resolving server address: " + e.getMessage());
            System.out.println("Check if IP address '" + IP_ADDRESS + "' is valid");
            return;
        }

        // Resource declarations for proper cleanup
        DatagramSocket datagramSocket = null;
        Scanner inputScanner = null;

        try {
            // Create UDP socket for sending datagrams
            datagramSocket = new DatagramSocket();
            System.out.println("UDP socket created successfully");
            System.out.println("Local port: " + datagramSocket.getLocalPort());
            System.out.println("\nReady to send messages. Enter your messages below:\n");

            // Initialize input scanner
            inputScanner = new Scanner(System.in);

            // Main message sending loop
            while (true) {
                System.out.print("Enter message: ");
                String message = inputScanner.nextLine();

                // Convert message to bytes for datagram transmission
                byte[] messageBytes = message.getBytes();

                // Create datagram packet with message data and server destination
                DatagramPacket packet = new DatagramPacket(
                        messageBytes,           // Message data as byte array
                        messageBytes.length,    // Length of message
                        serverAddress,          // Destination IP address
                        PORT                    // Destination port number
                );

                try {
                    // Send datagram to server
                    datagramSocket.send(packet);
                    System.out.println("✅ Datagram sent successfully");
                    System.out.println("   Message: '" + message + "'");
                    System.out.println("   Size: " + messageBytes.length + " bytes");
                    System.out.println("   Destination: " + serverAddress + ":" + PORT);

                } catch (IOException e) {
                    System.out.println("❌ Error sending datagram: " + e.getMessage());
                    System.out.println("   Server may not be reachable");
                    continue; // Continue with next message
                }

                // Check for termination command
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("\nExit command sent to server.");
                    System.out.println("Closing client connection...");
                    break; // Exit the message loop
                }
            }

        } catch (SocketException e) {
            System.out.println("❌ Error creating UDP socket: " + e.getMessage());
            System.out.println("Possible causes:");
            System.out.println("- Insufficient system permissions");
            System.out.println("- Network interface unavailable");
            e.printStackTrace();

        } finally {
            // Resource cleanup - ensure all resources are properly closed
            System.out.println("\n🧹 Cleaning up resources...");

            // Close input scanner
            if (inputScanner != null) {
                inputScanner.close();
                System.out.println("✅ Input scanner closed");
            }

            // Close UDP socket
            if (datagramSocket != null && !datagramSocket.isClosed()) {
                datagramSocket.close();
                System.out.println("✅ UDP socket closed");
            }

            System.out.println("✅ UDP Client terminated successfully");
            System.out.println("\nNote: UDP is 'fire-and-forget' - messages may not arrive at server");
        }
    }
}