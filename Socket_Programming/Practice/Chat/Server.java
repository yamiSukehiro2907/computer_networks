package Socket_Programming.Practice.Chat;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public class Server extends Thread {

    private final int PORT;

    private final String ipAddress;

    private ReentrantLock lock;

    private final String groupName;

    private ConcurrentHashMap<Integer, SocketPair> connections;

    public Server(String ipAddress, int PORT, String groupName) {
        this.ipAddress = ipAddress;
        this.PORT = PORT;
        this.groupName = groupName;
        this.lock = new ReentrantLock();
        this.connections = new ConcurrentHashMap<>();
    }

    private Long lastMessageTimestamp;

    @Override
    public void run() {
        try {
            startServer();
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                Socket connection = serverSocket.accept();
                connections.put(connection.getPort(), new SocketPair(connection, new PrintWriter(connection.getOutputStream(), true)));
                System.out.println(connection.getPort() + " has joined the group.");
                new Thread(() -> {
                    try {
                        handleClient(connection);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }).start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    private void handleClient(Socket connection) {
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            while (!connection.isClosed()) {
                String message = scanner.nextLine();
                lock.lock();
                System.out.println(connection.getPort() + ": " + message);
                sendMessages(message, connection.getPort());
                lock.unlock();
            }
        } catch (Exception e) {
            System.out.println(connection.getPort() + " is now offline...");
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (IOException e) {
                System.out.println("Error in closing connection from client : " + connection.getPort());

            }
        }
    }

    private ServerSocket serverSocket;

    private void startServer() throws IOException {
        this.serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(ipAddress, PORT));
        System.out.println("Group created " + groupName);
    }

    private void close() throws IOException {
        serverSocket.close();
    }

    private void sendMessages(String message, int PORT) throws IOException {
        for (Integer port : connections.keySet()) {
            if (port != PORT) {
                SocketPair socketPair = connections.get(port);
                if (socketPair != null) {
                    try {
                        socketPair.printer.println(port + " : " + message);
                    } catch (Exception e) {
                        System.out.println("Error sending the message to the client: " + port + e.getMessage());
                        connections.remove(port);
                    }
                }
            }
        }
    }

    private static class SocketPair {
        PrintWriter printer;
        Socket connection;

        public SocketPair(Socket connection, PrintWriter printWriter) {
            this.printer = printWriter;
            this.connection = connection;
        }
    }

}