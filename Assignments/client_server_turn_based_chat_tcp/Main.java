/**
 * Main Class - Turn-Based TCP Chat Server Launcher
 * <p>
 * This class serves as the entry point for launching the turn-based TCP chat server.
 * It initializes the server with proper configuration and starts the chat service.
 * <p>
 * Turn-Based Chat Protocol:
 * 1. Server starts and waits for client connection
 * 2. Client connects and sends FIRST message
 * 3. Server responds to client message
 * 4. Communication alternates: Client -> Server -> Client -> Server...
 * 5. Chat ends when client sends "exit"
 * 6. Connection closes gracefully
 * <p>
 * Configuration:
 * - PORT: 13245 (Must match client configuration exactly)
 * - IP Address: 127.0.0.1 (localhost)
 * - Message Length: Maximum 1024 bytes
 * - Threading: Single-threaded (one client at a time)
 * <p>
 * Usage Instructions:
 * 1. Run this Main class to start the server
 * 2. Wait for "Server ready for connections" message
 * 3. Run Client class in separate terminal/IDE
 * 4. Client will initiate conversation
 * 5. Take turns typing messages
 * 6. Client types "exit" to end session
 *
 * @author Vimal Kumar Yadav
 * @version 1.0
 */

package Assignments.client_server_turn_based_chat_tcp;

public class Main {

    // Configuration constants - must match client settings exactly
    private static final int PORT = 13245;
    private static final String IP_ADDRESS = "127.0.0.1";

    /**
     * Main method - Application entry point
     *
     * Initializes and starts the turn-based chat server with specified configuration.
     * The server runs in single-threaded mode, handling one client at a time.
     *
     * @param args Command line arguments (not currently used)
     */
    public static void main(String[] args) {

        // Display comprehensive server information
        System.out.println("=======================================");
        System.out.println("    TURN-BASED TCP CHAT SERVER");
        System.out.println("=======================================");
        System.out.println("Configuration:");
        System.out.println("📍 IP Address: " + IP_ADDRESS);
        System.out.println("🔌 Port: " + PORT);
        System.out.println("🔗 Protocol: TCP (reliable, connection-oriented)");
        System.out.println("👥 Threading: Single-threaded (one client at a time)");
        System.out.println("💬 Communication: Turn-based alternation");
        System.out.println("📝 Max Message Length: 1024 bytes");
        System.out.println();
        System.out.println("Chat Protocol:");
        System.out.println("1️⃣ Client connects and sends FIRST message");
        System.out.println("2️⃣ Server responds to client");
        System.out.println("3️⃣ Alternating communication continues");
        System.out.println("4️⃣ Client sends 'exit' to terminate");
        System.out.println("=======================================");
        System.out.println();

        // Create server instance with configuration
        Server server = new Server(PORT, IP_ADDRESS);

        System.out.println("🚀 Starting turn-based chat server...");
        System.out.println("📋 Instructions:");
        System.out.println("   - Server will wait for client connections");
        System.out.println("   - Client will send the first message");
        System.out.println("   - Take turns typing responses");
        System.out.println("   - Client types 'exit' to end chat");
        System.out.println();

        try {
            // Start the server - this will block until server terminates
            server.runServer();

        } catch (Exception e) {
            System.out.println("❌ Unexpected error in server execution:");
            System.out.println("   " + e.getMessage());
            e.printStackTrace();

        } finally {
            // Ensure clean shutdown
            System.out.println("\n🧹 Performing cleanup...");
            server.shutdown();
        }

        // This line is reached when server shuts down
        System.out.println("\n=======================================");
        System.out.println("    SERVER SESSION ENDED");
        System.out.println("=======================================");
        System.out.println("✅ Main application terminated successfully");
        System.out.println("🔄 To restart server, run this Main class again");
    }
}