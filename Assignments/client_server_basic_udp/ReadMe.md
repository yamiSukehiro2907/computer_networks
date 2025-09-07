# Objective

- Implement a connectionless client-server communication system using UDP socket programming to demonstrate the
  principles of datagram-based network communication.

# Requirements

- Server Implementation
    - Develop a UDP server application that creates a socket and binds to a specific port number
    - The server should continuously wait for incoming datagrams from clients
    - Upon receiving a datagram, the server should display the received message along with the client's address
      information
    - Implement a mechanism to stop listening when a termination message is received


- Client Implementation
    - Create a UDP client application that creates a socket for sending datagrams to the server
    - Implement an interactive user input mechanism that allows users to enter messages to be sent as datagrams to the
      server
    - Each user input should be packaged and sent as a separate datagram to the server's IP address and port
    - Continue accepting and sending user input until a termination condition is triggered


- Communication Protocol
    - The system should support continuous message exchange using discrete datagrams
    - Each message should be sent as an independent datagram (connectionless communication)
    - The communication should cease when the client sends the message "exit" to the server
    - Both client and server should handle the termination appropriately

# Constraints

- Use UDP sockets for connectionless, datagram-based communication
- The server should handle one client at a time (subsequent clients can communicate after the current client sends "
  exit")
- Maximum datagram size should be considered (implement appropriate buffer sizes)
- Note that UDP does not guarantee delivery or ordering of messages

# Deliverables

- Submit well-documented source code for both client and server programs, including:
- Clear comments explaining the UDP socket operations