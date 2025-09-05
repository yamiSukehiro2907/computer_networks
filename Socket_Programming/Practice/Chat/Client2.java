package Socket_Programming.Practice.Chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        String ipAddress = "127.0.0.1";
        int port = 12345;
        try (Socket socket = new Socket()) {

            socket.connect(new InetSocketAddress(ipAddress, port));
            Scanner sc = new Scanner(System.in);

            while (true) {

                String message = sc.nextLine();
                System.out.println(port + " : " + message);
                socket.getOutputStream().write((message + "\n").getBytes());
                socket.getOutputStream().flush();

                try (Scanner scanner = new Scanner(socket.getInputStream())) {
                    String data = scanner.nextLine();
                    System.out.println(data);
                }
            }
        } catch (RuntimeException | IOException e) {

            System.out.println(e.getMessage());
            throw new RuntimeException(e);

        }
    }
}
