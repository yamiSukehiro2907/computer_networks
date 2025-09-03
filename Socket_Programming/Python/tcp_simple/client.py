import socket 

# creating a socket
sock = socket.socket(socket.AF_INET , socket.SOCK_STREAM)

# ip Address for server
serverIp = "127.0.0.1"

port = 10000


sock.connect((serverIp , port))

sock.send("Hello Server".encode())