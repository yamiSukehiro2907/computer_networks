import socket

sock = socket.socket(socket.AF_INET , socket.SOCK_STREAM)

print("Socket created")

interface = "127.0.0.1"

port = 10000

sock.bind((interface , port))

print("Socket bound to the port: {port}" )

# no. of connection socket can provide
sock.listen()

conn, addr = sock.accept()

print(f"Got connection from client: {addr}")

# 1024 represents bytes it can recieve
data = conn.recv(1024) 

print(f"Data from client : {data.decode()}")

# to stop memory leakage in production (OS does close but after some time)
conn.close()
sock.close()