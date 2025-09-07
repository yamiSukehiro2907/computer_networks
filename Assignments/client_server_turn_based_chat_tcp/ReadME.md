# TCP Chat Application Assignment

Note: This question will be counted as part of the Lab Assessment evaluation criteria

# Objective

- Design and implement a simple turn-based chat application using TCP socket programming that demonstrates synchronous
  client-server communication with alternating message exchange.

# Requirements

- Server Implementation
    - Develop a TCP server that creates a socket, binds to a specified port, and listens for incoming connections
    - Accept a single client connection at a time (single-threaded implementation)
    - After accepting a connection, the server should:
    - Wait to receive a message from the client
    - Display the received message
    - Allow the server operator to type and send a response back to the client
    - Continue this alternating pattern throughout the conversation
- Client Implementation
    - Create a TCP client that establishes a connection to the server using the specified IP address and port
    - After successful connection, the client should:
    - Allow the user to type and send the first message
    - Wait to receive a response from the server
    - Display the server's response
    - Allow the user to send another message
    - Maintain this alternating communication pattern
    - Communication Protocol
    - Implement a turn-based messaging system where client and server alternate sending messages
    - The client always sends the first message after connection establishment
    - Each party must wait for the other's response before sending their next message
    - The chat session terminates when the client's side user enters "exit" in the chat
    - Upon receiving "exit", the server should close the connection gracefully

# Constraints

- Single-threaded implementation only - the server handles one client at a time
- Use TCP sockets for reliable, connection-oriented communication
- Implement synchronous (blocking) send and receive operations
- No simultaneous sending/receiving - alternation required
- Maximum message length should be defined (e.g., 1024 bytes)
- Expected Program Flow
- Server starts and waits for client connection
- Client connects to server
- Client sends first message
- Server receives and displays message
- Server sends response
- Client receives and displays response
- Steps 3-6 repeat until client sends "exit"
- Both programs terminate gracefully

# Deliverables

- Submit source code for both client and server programs with:

- Clear comments explaining the turn-based logic
- Proper connection establishment and termination
- User-friendly prompts indicating when to type messages

# Evaluation Criteria

- Correct implementation of TCP socket connection
- Proper alternating message exchange mechanism
- Graceful handling of connection termination
- Code readability and documentation