package HTTP;

import HTTP.Server.Server;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int PORT = 8080;
        String ipAddress = "127.0.0.1";
        Server server = new Server(ipAddress, PORT, 4096);
        server.run();
    }
}
