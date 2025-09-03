package Socket_Programming.Java.Simple_TCP;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        try (Socket socket = new Socket();) {
            Scanner sc = new Scanner(System.in);
            socket.connect(new InetSocketAddress("127.0.0.1", 12345));

            System.out.println("Starting Client Side");
            while (true) {
                String input = sc.nextLine();
                if (Objects.equals(input, "End")) {
                    socket.getOutputStream().write((input + "\n").getBytes());
                    socket.getOutputStream().flush();
                    System.out.println("Closing Client Side...");
                    break;
                } else {
                    socket.getOutputStream().write((input + "\n").getBytes());
                    socket.getOutputStream().flush();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
