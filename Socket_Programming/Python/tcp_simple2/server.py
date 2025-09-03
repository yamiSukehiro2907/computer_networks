import socket

ipAddress = "127.0.0.1"

port = 12345

# creating a socket for ipv4 addresses , SOCK_STREAM means it will use TCP method
sock = socket.socket(socket.AF_INET , socket.SOCK_STREAM)

sock.bind((ipAddress , port))

sock.listen() # it tells us exactly how many connections can wait in the queue

connection , address = sock.accept()

while True :
    data = connection.recv(1024)
    
    decoded_data = data.decode()
    
    if decoded_data == "End":
        break
    
    print(decoded_data)


connection.close()

sock.close()