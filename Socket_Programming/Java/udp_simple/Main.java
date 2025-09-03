package Socket_Programming.Java.udp_simple;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);

        String ipAddress = "127.0.0.1";
        int port = 12345;

        Server server = new Server(ipAddress, port);

        server.start();

        Client client = new Client(ipAddress, port);

        while (true) {
            String message = sc.nextLine();
            client.sendMessage(message);
            if (message.equalsIgnoreCase("end")) {
                break;
            }
        }
    }


}
