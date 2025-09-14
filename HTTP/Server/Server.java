package HTTP.Server;

import HTTP.Server.Details.Method;
import HTTP.Server.Details.Request;
import HTTP.Server.Details.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    private final int PORT;
    private final String ipAddress;
    private ServerSocket serverSocket;
    private final int MAX_REQUEST_SIZE;
    private static final String RESOURCES_FOLDER = "HTTP/Server/resources";

    public Server(String ipAddress, int PORT, int MAX_REQUEST_SIZE) {
        this.PORT = PORT;
        this.ipAddress = ipAddress;
        this.MAX_REQUEST_SIZE = MAX_REQUEST_SIZE;
    }

    public void run() throws InterruptedException {
        startServer();

        Thread.sleep(1000);

        while (!serverSocket.isClosed()) {
            try (Socket connection = serverSocket.accept();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                 OutputStream outputStream = connection.getOutputStream()) {

                System.out.println("Got connection: " + connection.getPort() + " : " + connection.getInetAddress());

                char[] buffer = new char[MAX_REQUEST_SIZE];
                int bytesRead = bufferedReader.read(buffer, 0, MAX_REQUEST_SIZE);

                if (bytesRead == -1) {
                    continue;
                }

                String requestString = new String(buffer, 0, bytesRead);
                Request request = getRequest(requestString);

                if (request != null) {
                    System.out.println("Request: " + request.getRequestString());
                } else {
                    System.out.println("Request: Invalid request received");
                }

                Response response = generateResponse(request);

                System.out.println("Response: " + response.getStatusCode() + " " + response.getStatusMessage());

                outputStream.write(response.getResponseString().getBytes(StandardCharsets.UTF_8));
                outputStream.flush();

            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void startServer() {
        try {
            this.serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ipAddress, PORT));
            System.out.println("Server started on " + ipAddress + ":" + PORT);
        } catch (IOException e) {
            System.out.println("Error: Creating ServerSocket: " + e.getMessage());
        }
    }

    private Request getRequest(String requestString) {
        if (requestString == null || requestString.isEmpty()) {
            return null;
        }
        try {
            String[] lines = requestString.split("\r\n");
            String requestLine = lines[0];

            String[] requestLineParts = requestLine.split(" ");

            if (requestLineParts.length < 3) return null;

            Method method = Method.fromString(requestLineParts[0]);
            String path = requestLineParts[1];
            String httpVersion = requestLineParts[2];
            return new Request(httpVersion, method, path);
        } catch (Exception e) {
            System.out.println("Failed to parse request string: " + e.getMessage());
            return null;
        }
    }

    private Response generateResponse(Request request) {
        if (request == null || request.getHttpVersion() == null || request.getPath() == null || request.getMethod() == null) {
            String content = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>400 Bad Request</title>
                    </head>
                    <body>
                        <h1>400 Bad Request</h1>
                        <p>Wrong request format</p>
                        <hr>
                        <p><em>Simple HTTP Server</em></p>
                    </body>
                    </html>""";
            return new Response(
                    "HTTP/1.1",
                    400,
                    "BAD REQUEST",
                    "text/html",
                    content.length(),
                    content);
        }

        if (request.getMethod() != Method.GET) {
            String content =
                    """
                            <!DOCTYPE html>
                            <html>
                            <head>
                                <title>405 Method Not Allowed</title>
                            </head>
                            <body>
                                <h1>405 Method Not Allowed</h1>
                                <p>Only GET requests are allowed.</p>
                                <hr>
                                <p><em>Simple HTTP Server</em></p>
                            </body>
                            </html>""";
            return new Response(
                    request.getHttpVersion(),
                    405,
                    "METHOD NOT ALLOWED",
                    "text/html",
                    content.length(),
                    content
            );
        }

        String requestPath = request.getPath();

        if (requestPath.equals("/")) {
            requestPath = "/index.html";
        }

        if (!requestPath.startsWith("/")) {
            String content = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>403 Forbidden</title>
                    </head>
                    <body>
                        <h1>403 Forbidden</h1>
                        <p>Unauthorized Access</p>
                        <hr>
                        <p><em>Simple HTTP Server</em></p>
                    </body>
                    </html>""";
            return new Response(
                    request.getHttpVersion(),
                    403,
                    "FORBIDDEN",
                    "text/html",
                    content.length(),
                    content
            );
        }

        try {
            String filePath = requestPath.startsWith("/") ? requestPath.substring(1) : requestPath;
            File file = new File(RESOURCES_FOLDER, filePath);


            System.out.println("Looking for file: " + file.getAbsolutePath());
            System.out.println("File exists: " + file.exists());
            System.out.println("Is file: " + file.isFile());
            System.out.println("Can read: " + file.canRead());

            if (!file.exists()) {
                String content = """
                        <!DOCTYPE html>
                        <html>
                        <head>
                            <title>404 Not Found</title>
                        </head>
                        <body>
                            <h1>404 Not Found</h1>
                            <p>The requested resource could not be found.</p>
                            <hr>
                            <p><em>Simple HTTP Server</em></p>
                        </body>
                        </html>""";
                return new Response(request.getHttpVersion(), 404, "NOT FOUND", "text/html", content.length(), content);
            }

            if (file.getCanonicalPath().startsWith(new File(RESOURCES_FOLDER).getCanonicalPath())) {
                String content = """
                        <!DOCTYPE html>
                        <html>
                        <head>
                            <title>403 Forbidden</title>
                        </head>
                        <body>
                            <h1>403 Forbidden</h1>
                            <p>Directory listing not allowed.</p>
                            <hr>
                            <p><em>Simple HTTP Server</em></p>
                        </body>
                        </html>""";
                return new Response(request.getHttpVersion(), 403, "FORBIDDEN", "text/html", content.length(), content);
            }

            return readFile(request, file);

        } catch (IOException e) {
            System.out.println("Error generating Response: " + e.getMessage());
            String content =
                    """
                            <!DOCTYPE html>
                            <html>
                            <head>
                                <title>500 Internal Server Error</title>
                            </head>
                            <body>
                                <h1>500 Internal Server Error</h1>
                                <p>Server Error Occurred</p>
                                <hr>
                                <p><em>Simple HTTP Server</em></p>
                            </body>
                            </html>""";
            return new Response(
                    "HTTP/1.1",
                    500,
                    "INTERNAL SERVER ERROR",
                    "text/html",
                    content.length(),
                    content
            );
        }
    }

    private Response readFile(Request request, File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) { // Specify encoding
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return new Response(request.getHttpVersion(), 200, "OK", "text/html", content.toString().length(), content.toString());
    }
}