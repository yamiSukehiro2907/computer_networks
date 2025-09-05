package Socket_Programming.Practice.Chat;

public class Main {
    public static void main(String[] args) {

        final String ipAddress = "127.0.0.1";
        final int port = 12345;

        Server server = new Server(ipAddress, port, "Group-1");

        server.start();
    }
}
