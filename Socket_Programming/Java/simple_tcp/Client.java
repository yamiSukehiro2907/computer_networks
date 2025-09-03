package Socket_Programming.Java.simple_tcp;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Getter
@Setter
public class Client extends Thread {

    private final int port;

    private final String address;

    private Socket socket;

    private final String clientName;

    public Client(String address, int port, String clientName) {
        this.clientName = clientName;
        this.address = address;
        this.port = port;
        this.socket = new Socket();
    }

    public void run() {
        try {
            socket.connect(new InetSocketAddress(address, port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws IOException {
        socket.close();
    }

    public void sendMessage(String message) throws IOException {
        socket.getOutputStream().write((clientName + " : " + message + "\n").getBytes());
        socket.getOutputStream().flush();
    }
}
