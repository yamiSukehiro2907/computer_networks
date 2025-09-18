package HTTP.Server;

import HTTP.Server.Details.Method;
import HTTP.Server.Details.Request;
import HTTP.Server.Details.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    private final int PORT;
    private final String ipAddress;
    private ServerSocket serverSocket;
    private final int MAX_REQUEST_SIZE;
    private static final String RESOURCES_FOLDER = "HTTP/Server/resources";
    private static final int DEFAULT_QUEUE_SIZE = 5;

    public Server(String ipAddress, int PORT, int MAX_REQUEST_SIZE) {
        this.PORT = PORT;
        this.ipAddress = ipAddress;
        this.MAX_REQUEST_SIZE = MAX_REQUEST_SIZE;
    }

    public void run() throws InterruptedException {
        startServer();

        // Add shutdown hook for graceful server shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutting down server...");
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }));

        System.out.println("HTTP Server started on http://" + ipAddress + ":" + PORT);
        System.out.println("Serving files from '" + RESOURCES_FOLDER + "' directory");
        System.out.println("Press Ctrl+C to stop the server\n");

        while (!serverSocket.isClosed()) {
            try (Socket connection = serverSocket.accept();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                 OutputStream outputStream = connection.getOutputStream()) {

                // Log incoming connection
                String clientIP = connection.getInetAddress().getHostAddress();
                int clientPort = connection.getPort();
                System.out.println("Connection from " + clientIP + ":" + clientPort);

                // Read request with proper buffer handling
                char[] buffer = new char[MAX_REQUEST_SIZE];
                int bytesRead = bufferedReader.read(buffer, 0, MAX_REQUEST_SIZE);

                if (bytesRead == -1) {
                    continue;
                }

                String requestString = new String(buffer, 0, bytesRead);
                Request request = parseRequest(requestString);

                // Log the request
                if (request != null && request.getMethod() != null && request.getPath() != null) {
                    System.out.println("Request: " + request.getMethod() + " " + request.getPath() + " " + request.getHttpVersion());
                } else {
                    System.out.println("Request: [Malformed request]");
                }

                Response response = generateResponse(request);

                // Log the response
                if (request != null && request.getPath() != null) {
                    String fileName = request.getPath().equals("/") ? "index.html" : request.getPath().substring(1);
                    if (response.getStatusCode() == 200) {
                        System.out.println("  -> " + response.getStatusCode() + " " + response.getStatusMessage() + ": " + fileName);
                    } else {
                        System.out.println("  -> " + response.getStatusCode() + " " + response.getStatusMessage());
                    }
                } else {
                    System.out.println("  -> " + response.getStatusCode() + " " + response.getStatusMessage());
                }

                outputStream.write(response.getResponseString().getBytes(StandardCharsets.UTF_8));
                outputStream.flush();

            } catch (IOException e) {
                System.err.println("Error handling client connection: " + e.getMessage());
            }
        }
    }

    private void startServer() {
        try {
            this.serverSocket = new ServerSocket(PORT, DEFAULT_QUEUE_SIZE,
                    java.net.InetAddress.getByName(ipAddress));
            System.out.println("Server socket created and bound to " + ipAddress + ":" + PORT);
        } catch (IOException e) {
            System.err.println("Error: Creating ServerSocket: " + e.getMessage());
            System.exit(1);
        }
    }

    private Request parseRequest(String requestString) {
        if (requestString == null || requestString.trim().isEmpty()) {
            return null;
        }

        try {
            String[] lines = requestString.split("\\r?\\n");
            if (lines.length == 0) {
                return null;
            }

            String requestLine = lines[0].trim();
            if (requestLine.isEmpty()) {
                return null;
            }

            String[] requestLineParts = requestLine.split("\\s+");
            if (requestLineParts.length != 3) {
                return null;
            }

            Method method = Method.fromString(requestLineParts[0]);
            String path = requestLineParts[1];
            String httpVersion = requestLineParts[2];

            // Basic validation
            if (method == null || path == null || httpVersion == null) {
                return null;
            }

            // Validate HTTP version format
            if (!httpVersion.matches("HTTP/\\d\\.\\d")) {
                return null;
            }

            return new Request(httpVersion, method, path);
        } catch (Exception e) {
            System.err.println("Failed to parse request string: " + e.getMessage());
            return null;
        }
    }

    private Response generateResponse(Request request) {
        // Handle malformed requests
        if (request == null || request.getHttpVersion() == null ||
                request.getPath() == null || request.getMethod() == null) {
            String content = generateErrorPage(400, "Bad Request", "The request was malformed or incomplete.");
            return new Response(
                    "HTTP/1.1",
                    400,
                    "Bad Request",
                    "text/html; charset=utf-8",
                    content.length(),
                    content);
        }

        // Handle non-GET methods
        if (request.getMethod() != Method.GET) {
            String content = generateErrorPage(405, "Method Not Allowed", "Only GET requests are allowed.");
            return new Response(
                    request.getHttpVersion(),
                    405,
                    "Method Not Allowed",
                    "text/html; charset=utf-8",
                    content.length(),
                    content
            );
        }

        // Process the request path
        String requestPath = request.getPath();

        // Handle root path
        if (requestPath.equals("/")) {
            requestPath = "/index.html";
        }

        try {
            // Remove leading slash and construct file path
            String filePath = requestPath.startsWith("/") ? requestPath.substring(1) : requestPath;
            File resourcesDir = new File(RESOURCES_FOLDER);
            File requestedFile = new File(resourcesDir, filePath);

            // Security check: Ensure the file is within the resources directory
            String canonicalResourcesPath = resourcesDir.getCanonicalPath();
            String canonicalFilePath = requestedFile.getCanonicalPath();

            if (!canonicalFilePath.startsWith(canonicalResourcesPath)) {
                String content = generateErrorPage(403, "Forbidden",
                        "Access to the requested resource is forbidden.");
                return new Response(request.getHttpVersion(), 403, "Forbidden",
                        "text/html; charset=utf-8", content.length(), content);
            }

            // Check if file exists and is readable
            if (!requestedFile.exists() || !requestedFile.isFile()) {
                String content = generateErrorPage(404, "Not Found",
                        "The requested resource could not be found.");
                return new Response(request.getHttpVersion(), 404, "Not Found",
                        "text/html; charset=utf-8", content.length(), content);
            }

            if (!requestedFile.canRead()) {
                String content = generateErrorPage(403, "Forbidden",
                        "Access to the requested resource is forbidden.");
                return new Response(request.getHttpVersion(), 403, "Forbidden",
                        "text/html; charset=utf-8", content.length(), content);
            }

            // Read and serve the file
            return readFile(request, requestedFile);

        } catch (IOException e) {
            System.err.println("Error processing request: " + e.getMessage());
            String content = generateErrorPage(500, "Internal Server Error",
                    "An internal server error occurred.");
            return new Response(
                    "HTTP/1.1",
                    500,
                    "Internal Server Error",
                    "text/html; charset=utf-8",
                    content.length(),
                    content
            );
        }
    }

    private String generateErrorPage(int statusCode, String statusMessage, String description) {
        return String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>%d %s</title>
                </head>
                <body>
                    <h1>%d %s</h1>
                    <p>%s</p>
                    <hr>
                    <p><em>Simple HTTP Server</em></p>
                </body>
                </html>""", statusCode, statusMessage, statusCode, statusMessage, description);
    }

    private Response readFile(Request request, File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(file, StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        String fileContent = content.toString();
        return new Response(
                request.getHttpVersion(),
                200,
                "OK",
                "text/html; charset=utf-8",
                fileContent.getBytes(StandardCharsets.UTF_8).length,  // Use byte length for content-length
                fileContent
        );
    }

    // Main method for testing and running the server
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8080;
        int maxRequestSize = 4096;

        // Parse command line arguments
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
                if (port < 1 || port > 65535) {
                    System.err.println("Error: Port must be between 1 and 65535");
                    System.exit(1);
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid port number: " + args[0]);
                System.exit(1);
            }
        }

        if (args.length > 1) {
            host = args[1];
        }

        // Check if resources directory exists
        File resourcesDir = new File(RESOURCES_FOLDER);
        if (!resourcesDir.exists()) {
            System.err.println("Error: Resources directory '" + RESOURCES_FOLDER + "' not found!");
            System.err.println("Please create the resources directory and add HTML files.");
            System.exit(1);
        }

        Server server = new Server(host, port, maxRequestSize);
        try {
            server.run();
        } catch (InterruptedException e) {
            System.out.println("Server interrupted");
            Thread.currentThread().interrupt();
        }
    }
}