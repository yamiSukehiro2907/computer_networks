package Socket_Programming.Practice.Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {

        String ipAddress = "127.0.0.1";
        int port = 12345;

        try (Socket socket = new Socket()) {

            Thread.sleep(1000);

            System.out.println(socket.getLocalPort() + " is trying to join the group " + socket.getPort());

            socket.connect(new InetSocketAddress(ipAddress, port));

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            Scanner messageGetterScanner = new Scanner(socket.getInputStream());

            Scanner messageSenderScanner = new Scanner(System.in);

            new Thread(() -> {
                try {
                    while (messageGetterScanner.hasNextLine()) {
                        String incomingMessage = messageGetterScanner.nextLine();
                        System.out.println(incomingMessage);
                    }
                } catch (Exception e) {
                    System.out.println("Connection to server closed...." + e.getMessage());
                }
            }).start();

            while (messageSenderScanner.hasNextLine()) {
                String message = messageSenderScanner.nextLine();
                System.out.println(socket.getPort());
                writer.println(message);
            }

        } catch (IOException e) {
            System.out.println("An Error occurred : " + e.getMessage());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
