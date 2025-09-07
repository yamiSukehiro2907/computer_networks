package Assignments.client_server_basic_udp;
/**
 * UDP Server Class - Connectionless Datagram Server
 *
 * This class implements a UDP server that listens for incoming datagrams
 * from clients. UDP is connectionless, meaning each datagram is independent
 * and delivery is not guaranteed. The server handles one client session at a time.
 *
 * Key Features:
 * - Uses DatagramSocket for UDP communication
 * - Receives independent datagrams (no connection state)
 * - Handles termination when "exit" message is received
 * - Buffer size optimized for typical message sizes
 *
 * @author Your Name
 * @version 1.0
 */


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Server {
    private final int PORT;
    private final String ipAddress;
    private DatagramSocket datagramSocket;

    // Buffer size for receiving datagrams (1KB should handle most text messages)
    private static final int BUFFER_SIZE = 1024;

    /**
     * Constructor to initialize server with port and IP address
     *
     * @param PORT The port number for UDP socket binding
     * @param ipAddress The IP address to bind the server socket
     */
    public Server(int PORT, String ipAddress) {
        this.PORT = PORT;
        this.ipAddress = ipAddress;
    }

    /**
     * Main server execution method that handles the UDP server lifecycle:
     * - Creates and binds UDP socket
     * - Listens for incoming datagrams
     * - Processes messages until "exit" is received
     * - Properly closes socket when done
     */
    public void startServer() {
        try {
            createServer();
            Thread.sleep(1000); // Brief pause for server stabilization
        } catch (InterruptedException e) {
            System.out.println("Error during server startup delay: " + e.getMessage());
            return;
        }

        // Check if server creation was successful
        if (datagramSocket == null) {
            System.out.println("Server failed to start. Exiting.");
            return;
        }

        System.out.println("UDP Server listening on " + ipAddress + ":" + PORT);
        System.out.println("Waiting for datagrams from clients...");
        System.out.println("Note: UDP is connectionless - each message is independent");

        // Main server loop - continues until "exit" message is received
        boolean serverRunning = true;
        while (serverRunning && !datagramSocket.isClosed()) {
            // Create buffer for incoming datagram
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                // Receive datagram from client (blocks until packet arrives)
                System.out.println("Waiting for next datagram...");
                datagramSocket.receive(packet);

                // Extract message from received datagram
                String message = new String(packet.getData(), 0, packet.getLength());

                // Check for termination command
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Exit command received from client " + packet.getAddress());
                    System.out.println("Shutting down server...");
                    serverRunning = false; // This will exit the main loop
                    break;
                }

                // Display received message with client information
                System.out.println("Datagram received from " + packet.getAddress() +
                        ":" + packet.getPort());
                System.out.println("Client(" + packet.getPort() + "): " + message);
                System.out.println("Message size: " + packet.getLength() + " bytes");

            } catch (IOException e) {
                if (!datagramSocket.isClosed()) {
                    System.out.println("Error receiving datagram: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        // Clean shutdown - close UDP socket
        if (datagramSocket != null && !datagramSocket.isClosed()) {
            datagramSocket.close();
            System.out.println("UDP Server shut down successfully.");
        }
    }

    /**
     * Creates and initializes the UDP server socket
     * Binds the socket to the specified IP address and port
     */
    private void createServer() {
        try {
            // Create DatagramSocket bound to specific address and port
            this.datagramSocket = new DatagramSocket(new InetSocketAddress(ipAddress, PORT));
            System.out.println("UDP Socket created and bound to " + ipAddress + ":" + PORT);
        } catch (SocketException e) {
            System.out.println("Error creating Datagram Socket for Server: " + e.getMessage());
            System.out.println("Possible causes: Port already in use, insufficient permissions");
            e.printStackTrace();
            this.datagramSocket = null; // Ensure socket is null on failure
        }
    }

    /**
     * Gracefully shuts down the server by closing the datagram socket
     */
    public void shutdown() {
        if (datagramSocket != null && !datagramSocket.isClosed()) {
            datagramSocket.close();
            System.out.println("Server shutdown initiated.");
        }
    }
}