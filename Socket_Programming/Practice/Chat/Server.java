package Socket_Programming.Practice.Chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class Server {

    private final Integer port;

    private final String ipAddress;

    private ServerSocket serverSocket;

    public Server(String ipAddress, int port) {
        this.port = port;
        this.ipAddress = ipAddress;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket();

            serverSocket.bind(new InetSocketAddress(ipAddress, port));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void stopServer() throws IOException {
        serverSocket.close();
    }
}
