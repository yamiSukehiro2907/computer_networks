# Network Programming

A comprehensive collection of network programming implementations and data structure algorithms in Java and Python.

## 📁 Project Structure

```
├── Assignments/
│   ├── client_server_basic_tcp/     # Basic TCP client-server implementation
│   ├── client_server_basic_udp/     # Basic UDP client-server implementation
│   ├── client_server_turn_based_chat_tcp/  # Turn-based TCP chat application
│   └── ...
├── HTTP/
│   └── Server/                       # HTTP server implementation from scratch
├── Graph/
│   ├── Bellman_Ford/                # Bellman-Ford algorithm
│   ├── Dijikstra/                   # Dijkstra's shortest path
│   └── ...
└── Socket_Programming/
    ├── Java/                        # Java socket implementations
    └── Python/                      # Python socket implementations
```

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher
- Python 3.8+ (for Python implementations)
- Basic understanding of socket programming

### Running Examples

**TCP Client-Server:**
```bash
# Start server
java Assignments.client_server_basic_tcp.Main

# In another terminal, start client
java Assignments.client_server_basic_tcp.Client
```

**HTTP Server:**
```bash
java HTTP.Server.Server [port] [host]
# Example: java HTTP.Server.Server 8080 127.0.0.1
```

## 🤝 Contributing

We welcome contributions! Here's how you can help:

### Getting Started
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Contribution Guidelines
- **Code Style**: Follow existing code formatting and naming conventions
- **Documentation**: Add clear comments and update README if needed
- **Testing**: Test your implementations thoroughly before submitting
- **Commit Messages**: Write clear, descriptive commit messages
- **One Feature per PR**: Keep pull requests focused on a single feature or fix

### What to Contribute
- Bug fixes and improvements
- New socket programming examples
- Additional graph algorithms
- Performance optimizations
- Documentation improvements
- Test cases

## 📝 Code Standards

- Use meaningful variable and function names
- Include proper error handling
- Add comments for complex logic
- Follow the existing package structure
- Ensure proper resource cleanup (close sockets, streams, etc.)

## 🐛 Reporting Issues

Found a bug? Please open an issue with:
- Clear description of the problem
- Steps to reproduce
- Expected vs actual behavior
- Environment details (OS, Java version, etc.)

## 📄 License

This project is open source and available for educational purposes.

## 👨‍💻 Author

**[Vimal Kumar Yadav](https://github.com/yamiSukehiro2907)**

## 🙏 Acknowledgments

Thanks to all contributors who help improve this repository!

---

**Note**: This repository is primarily for educational purposes and learning network programming concepts.
