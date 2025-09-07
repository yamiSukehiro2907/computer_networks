/**
 * Main Class - UDP Server Launcher
 * <p>
 * This class serves as the entry point for launching the UDP socket server.
 * It initializes the server with specified configuration and starts the
 * datagram listening process.
 * <p>
 * UDP vs TCP Comparison:
 * - UDP: Connectionless, unreliable, faster, lower overhead
 * - TCP: Connection-oriented, reliable, slower, higher overhead
 * <p>
 * Configuration:
 * - PORT: 12345 (Must match client port exactly)
 * - IP Address: 127.0.0.1 (localhost)
 * <p>
 * Execution Order:
 * 1. Run this Main class to start UDP server
 * 2. Wait for "UDP Server listening" message
 * 3. Run Client class to send datagrams
 * 4. Server processes one client session at a time
 * 5. Send "exit" from client to terminate both client and server
 *
 * @author Your Name
 * @version 1.0
 */

package Assignments.client_server_basic_udp;

public class Main {

    // Server configuration constants - must match client configuration
    private static final int PORT = 12345;
    private static final String IP_ADDRESS = "127.0.0.1";

    /**
     * Main method - Application entry point
     *
     * Initializes and starts the UDP server with specified configuration.
     * The server runs directly in the main thread for simplicity.
     *
     * @param args Command line arguments (not currently used)
     */
    public static void main(String[] args) {

        // Display comprehensive server startup information
        System.out.println("=== UDP Socket Server Launcher ===");
        System.out.println("Protocol: UDP (User Datagram Protocol)");
        System.out.println("Communication Type: Connectionless");
        System.out.println("Reliability: Unreliable (no delivery guarantee)");
        System.out.println("Message Ordering: Not guaranteed");
        System.out.println();
        System.out.println("Server Configuration:");
        System.out.println("- IP Address: " + IP_ADDRESS);
        System.out.println("- Port: " + PORT);
        System.out.println("- Buffer Size: 1024 bytes");
        System.out.println("- Client Handling: One session at a time");
        System.out.println("===================================");
        System.out.println();

        // Create UDP server instance with specified configuration
        Server server = new Server(PORT, IP_ADDRESS);

        System.out.println("Initializing UDP server...");

        // Start the UDP server - this will block until server terminates
        try {
            server.startServer();
        } catch (Exception e) {
            System.out.println("Unexpected error in server execution: " + e.getMessage());
            e.printStackTrace();
        }

        // This line is reached when server shuts down (after receiving "exit")
        System.out.println();
        System.out.println("=== Server Session Ended ===");
        System.out.println("Main application terminating...");
        System.out.println("To restart server, run this Main class again");
    }
}