# Objective

Implement a basic client-server communication system using socket programming that demonstrates fundamental network
communication principles.

# Requirements

- Server Implementation :
    - Develop a server application that initializes and listens for incoming connections on a designated port number
    - The server should remain active and wait for client connection requests
    - Upon receiving data from a connected client, the server should display the received messages
    - Implement proper connection termination when the exit condition is met (client closes the connection)


- Client Implementation :
    - Create a client application that attempts to establish a connection with the server using the appropriate IP
      address and port number
    - Implement an interactive user input mechanism that allows users to enter messages to be transmitted to the server
    - Continue accepting and sending user input until a termination condition is triggered (client types "exit")
    - Ensure proper connection closure after sending the termination signal

# Communication Protocol

- The system should support continuous message exchange between client and server
- The communication session should terminate when the client closes the connection (Client should close the connection
  once a user types "exit")
  Constraints
- The server should handle only one client connection at a time (single-threaded implementation)
  Use TCP sockets for reliable data transmission