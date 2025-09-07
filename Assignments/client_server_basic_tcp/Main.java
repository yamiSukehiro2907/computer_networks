package Assignments.client_server_basic_tcp;

/**
 * Main Class - Single-Threaded Server Launcher
 *
 * This class serves as the entry point for launching the TCP socket server
 * in a single-threaded manner. The server runs directly in the main thread
 * and handles one client at a time sequentially.
 *
 * IMPORTANT: This implementation does NOT use threading (no Thread.start()).
 * The server runs in the main thread to ensure strict single-threaded behavior.
 *
 * Configuration:
 * - PORT: 13245 (MUST match client port exactly)
 * - IP Address: 127.0.0.1 (localhost)
 *
 * Execution Order:
 * 1. Run this Main class to start server
 * 2. Wait for "Server is now live" message
 * 3. Run Client class to connect and communicate
 * 4. Server handles one client at a time
 *
 * @author Vimal Kumar Yadav
 * @version 1.0
 */

public class Main {

    // CRITICAL: These values MUST match the client's configuration exactly
    private static final int PORT = 13245;           // Must match client port
    private static final String IP_ADDRESS = "127.0.0.1";  // Must match client IP

    /**
     * Main method - Application entry point
     *
     * Initializes and runs the server directly in the main thread.
     * This ensures true single-threaded operation as required.
     *
     * @param args Command line arguments (not currently used)
     */
    public static void main(String[] args) {

        // Display server startup information
        System.out.println("=== Single-Threaded TCP Socket Server ===");
        System.out.println("Server Configuration:");
        System.out.println("- IP Address: " + IP_ADDRESS);
        System.out.println("- Port: " + PORT);
        System.out.println("- Connection Type: TCP");
        System.out.println("- Threading Model: Single-threaded (no Thread class)");
        System.out.println("- Client Handling: Sequential (one at a time)");
        System.out.println("==========================================\n");

        // Create server instance with specified configuration
        Server server = new Server(PORT, IP_ADDRESS);

        System.out.println("Starting server in main thread (single-threaded mode)...");

        // IMPORTANT: Call runServer() directly instead of start()
        // This ensures the server runs in the main thread, not a separate thread
        server.runServer();

        // This line will only be reached when server shuts down
        System.out.println("Server has stopped running.");
        System.out.println("Main application terminating...");
    }
}