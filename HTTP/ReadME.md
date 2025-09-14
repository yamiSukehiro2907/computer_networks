# Build a Simple HTTP Server Using Socket Programming

## Objective

- Design and implement a basic HTTP server from scratch using low-level socket programming. This assignment will help
  you understand the fundamentals of the HTTP protocol and network programming.

## Overview

- You will create a simple HTTP server that can handle basic web requests and serve static HTML files. The server will
  listen for incoming connections, parse HTTP requests, and send appropriate responses back to clients.

## Requirements

- Server Configuration:
  The server should run on localhost (127.0.0.1) by default port should be 8080
  The server should accept command-line arguments to optionally specify:
  ```
  Port number (first argument)
  Host address (second argument)
  Example: ./server 8000 0.0.0.0
  ```
- Socket Implementation
  Use TCP sockets for communication
  The server should bind to the specified host and port
  Listen for incoming connections **(queue size of at least 5)**
  Handle one client at a time (single-threaded implementation)
  Properly close client connections after serving each request
- HTTP Request Handling
  ```
  Parse incoming HTTP requests to extract:
  Request method (GET, POST, etc.)
  Request path (e.g., /index.html, /about.html)
  HTTP version
  Only support GET requests - reject all other methods with a 405 "Method Not Allowed" response
  Handle requests up to 4096 bytes in size```

- File Serving
  Serve HTML files from a designated resources directory
  When the root path / is requested, serve index.html by default
  Map URL paths to file paths within the resources directory
  Example: /page.html → resources/page.html
  Example: / → resources/index.html
- Security
  Implement path traversal protection: Ensure that requests cannot access files outside the resources directory
  Validate that the resolved file path stays within the resources folder
  Return a 403 "Forbidden" response for any attempts to access unauthorized paths
  Example of blocked request: /../etc/passwd or /../../sensitive.txt
- HTTP Response Format
    - Implement proper HTTP/1.1 response formatting:
    - For successful requests (200 OK):
    - HTTP/1.1 200 OK
    - Content-Type: text/html; charset=utf-8
    - Content-Length: [size of content in bytes]
    - Date: [current date in GMT format]
    - Server: Simple HTTP Server
    - Connection: close

- [HTML file content]
- For error responses:
    - 404 Not Found: When the requested file doesn't exist
    - 405 Method Not Allowed: For non-GET requests
    - 403 Forbidden: For unauthorized path access attempts
    - 400 Bad Request: For malformed requests
    - 500 Internal Server Error: For server-side errors

- Error Pages
  Generate appropriate HTML error pages with:

Clear error title and code
Brief description of the error
Server identification
Example 404 response body:

```

<!DOCTYPE html>
<html>
<head>
    <title>404 Not Found</title>
</head>
<body>
    <h1>404 Not Found</h1>
    <p>The requested resource could not be served.</p>
    <hr>
    <p><em>Simple HTTP Server</em></p>
</body>
</html>
```

8. Logging
   Implement basic console logging:

Server startup message with host and port
Incoming connection details (client IP and port)
Request line for each request
Response status for each request
Server shutdown message
Example output:

HTTP Server started on http://127.0.0.1:8080
Serving files from 'resources' directory
Press Ctrl+C to stop the server

Connection from 127.0.0.1:54321
Request: GET /index.html HTTP/1.1
-> 200 OK: index.html
Connection from 127.0.0.1:54322
Request: GET /nonexistent.html HTTP/1.1
-> 404 Not Found
Testing Your Server

1. Create Test Files
   Create a resources directory with at least:

index.html - Homepage
about.html - About page
contact.html - Contact page

2. Test Cases
   Your server should correctly handle:

✅ GET / → Serves resources/index.html
✅ GET /about.html → Serves resources/about.html
✅ GET /nonexistent.html → Returns 404 error
❌ POST /index.html → Returns 405 error
❌ GET /../etc/passwd → Returns 403 error
✅ Browser access at http://localhost:8080

3. Testing Tools
   You can test your server using:

Web browsers (Chrome, Firefox, etc.)
curl command: curl -v http://localhost:8080/index.html
telnet: Connect and manually type HTTP requests
wget: wget http://localhost:8080/index.html