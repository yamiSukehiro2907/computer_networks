import socket 

sock = socket.socket(socket.AF_INET , socket.SOCK_STREAM)

serverIp = "127.0.0.1"
port = 10000


sock.connect((serverIp , port))

sock.send("Hello Server".encode())