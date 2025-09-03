package Socket_Programming.Java.udp_simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {

    private final int port;

    private final String ipAddress;

    private final DatagramSocket datagramSocket;

    public Client(String ipAddress, int port) throws SocketException {
        this.ipAddress = ipAddress;
        this.port = port;
        this.datagramSocket = new DatagramSocket();
    }

    public void sendMessage(String message) throws IOException {
        byte[] buffer = message.getBytes();

        InetAddress serverAddress = InetAddress.getByName(ipAddress);

        DatagramPacket packet = new DatagramPacket(
                buffer,
                buffer.length,
                serverAddress,
                port
        );

        datagramSocket.send(packet);
    }

    public void close() {
        datagramSocket.close();
    }
}
