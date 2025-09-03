package Socket_Programming.Java.simple_tcp;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@Getter
@Setter
public class Server extends Thread {

    private final int port;

    private final String address;

    private ServerSocket serverSocket;

    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }

    private void startServer() {
        try {
            this.serverSocket = new ServerSocket();

            serverSocket.bind(new InetSocketAddress(address, port));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        startServer();
        try {
            while (!serverSocket.isClosed()) {
                try {
                    Socket connection = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            handleClient(connection);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleClient(Socket socket) throws IOException {
        Scanner scanner = null;
        try {
            scanner = new Scanner(socket.getInputStream());
            while (!socket.isClosed() && scanner.hasNextLine()) {
                String message = scanner.nextLine();
                if (message.equals("End")) {
                    break;
                }
                System.out.println(message);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (scanner != null) {
                    scanner.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void close() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }

}
