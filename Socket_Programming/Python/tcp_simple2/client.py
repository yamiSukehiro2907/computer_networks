import socket

ipAddress = "127.0.0.1"

port = 12345

sock = socket.socket(socket.AF_INET , socket.SOCK_STREAM)

sock.connect((ipAddress , port))

while True:
    data = input()
    
    sock.send(data.encode())
    
    if data == "End":
        break

sock.close()
