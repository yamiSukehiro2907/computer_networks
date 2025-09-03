package Socket_Programming.Java.udp_simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server extends Thread {

    private final int port;

    private final String ipAddress;

    public Server(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    private DatagramSocket datagramSocket;

    @Override
    public void run() {
        startServer();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        byte[] buffer = new byte[1024];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                datagramSocket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            InetAddress address = packet.getAddress();
            System.out.println("Client address: " + address);
            int clientPort = packet.getPort();
            System.out.println("Client-Port: " + clientPort);

            String data = new String(packet.getData(), 0, packet.getLength());

            if (data.equalsIgnoreCase("end")) {
                break;
            }

            System.out.println(data);
        }
        datagramSocket.close();
    }

    private void startServer() {
        try {
            datagramSocket = new DatagramSocket(port, InetAddress.getByName(ipAddress));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
