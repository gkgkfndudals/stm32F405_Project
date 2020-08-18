import serial
from socket import *
import threading
import time

ser = serial.Serial(
    port='COM7',
    baudrate=9600,
)


# class Messenger(threading.Thread):
#     def run(self):
#         while True:
#             ## 변수를 지정하지 않고 단순 반복을 하고 싶을 때는 _언더바를 사용한다.
#             time.sleep(2)
#             print(threading.currentThread().getName())
#             if ser.readable():
#                 res = ser.readline()
#                 print(res.decode()[:len(res) - 1])
#
#
# receive = Messenger(name="Receiving Messages")
# receive.start()
#
serverPort = 9999
serverSocket = socket(AF_INET, SOCK_DGRAM)
serverSocket.bind(('10.1.4.124', serverPort))
# print('the server is ready to receive')


while True:
    print('the server is ready to receive')
    data, clientAddress = serverSocket.recvfrom(2048)
    # print("client: {:s}/{:d}".formot(clientAddress[0], clientAddress[1]))
    print("data: {:s}".format(data.decode()))

    modifiedMessage = data.decode().upper()
    serverSocket.sendto(modifiedMessage.encode(), clientAddress)

    # data1 = "jio"
    # serverSocket.sendto(data1.encode(), clientAddress)

    if data.decode() == "1":
        print("부저 입니다.")
        ser.write(b'1')  # 출력방식2
    elif data.decode() == "2":
        print("버튼 입니다.")
        ser.write(b'2')
    elif data.decode() == "3":
        print("음표 입니다.")
        ser.write(b'3')


    #print(ser.readable())
    if ser.readable():
        res = ser.read()
        print(res.decode())

