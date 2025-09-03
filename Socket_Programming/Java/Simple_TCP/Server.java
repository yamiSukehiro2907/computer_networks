package Socket_Programming.Java.Simple_TCP;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket();) {

            serverSocket.bind(new InetSocketAddress("127.0.0.1", 12345));
            System.out.println("Server started at port: " + serverSocket.getLocalPort());
            Socket connection = serverSocket.accept();
            System.out.println("Client Connected");

            try (Scanner sc = new Scanner(connection.getInputStream())) {

                while (true) {
                    String data = sc.nextLine();
                    if (Objects.equals(data, "End")) {
                        System.out.println("Closing Connection" + connection.getPort());
                        break;
                    } else {
                        System.out.println(data);
                    }
                }
            }
            finally {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
