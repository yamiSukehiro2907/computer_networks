package Socket_Programming.Java.simple_tcp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Server server = null;
        Client[] clients = new Client[5];
        try {
            String ipAddress = "127.0.0.1";
            int port = 12345;

            server = new Server(ipAddress, port);

            System.out.println("Server started at IPV4: " + ipAddress + " , port: " + port);
            server.start();

            Thread.sleep(1000);

            String[] clientNames = {"Vimal", "Vinay", "Adnan", "Aryen", "Pathak"};


            for (int i = 0; i < 5; i++) {
                clients[i] = new Client(ipAddress, port, clientNames[i]);
                clients[i].start();
                Thread.sleep(100);
            }

            Thread.sleep(2000);

            clients[0].sendMessage("Hi guys");

            clients[1].sendMessage("Are you guys gay?");

            clients[2].sendMessage("What the fuck?");

            clients[3].sendMessage("Meri mummy hot hai...");

            clients[4].sendMessage("Main mac le rha aanh...");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                for (int i = 0; i < 5; i++) {
                    clients[i].join();
                    clients[i].close();
                }

                Thread.sleep(1000);

                if (server != null) {
                    server.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
