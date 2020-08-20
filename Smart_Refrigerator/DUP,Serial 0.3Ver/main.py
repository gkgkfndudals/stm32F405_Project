import serial
from socket import *
import threading
import time

res =""

ser = serial.Serial(
    port='COM7',
    baudrate=9600,
)


class Messenger(threading.Thread):
    def run(self):
        global res
        while True:
            ## 변수를 지정하지 않고 단순 반복을 하고 싶을 때는 _언더바를 사용한다.
            print("stm serial 대기중!!")
            time.sleep(1)
            print(threading.currentThread().getName())
            if ser.readable():
                res = ser.read()
                print("stm32으로 부터 받음: " + res.decode() + "\n")

                # if res.decode() == "4" :
                #     serverSocket.sendto("4".encode(), clientAddress)



receive = Messenger(name="Receiving Messages\n")
receive.start()
#
serverPort = 9999
serverSocket = socket(AF_INET, SOCK_DGRAM)
serverSocket.bind(('10.1.4.124', serverPort))

while True:
    print('서버 대기중!!\n')
    data, clientAddress = serverSocket.recvfrom(2048)

    print("android에서 socket 받은 data: {:s}".format(data.decode()))

    modifiedMessage = data.decode().upper()
    serverSocket.sendto(modifiedMessage.encode(), clientAddress)



    if data.decode() == "1":
        print("부저 OFF. \n")
        ser.write(b'1')  # 출력방식2
    elif data.decode() == "2":
        print("부저 ON. \n")
        ser.write(b'2')
    elif data.decode() == "3":
        print("LED 끄기. \n")
        ser.write(b'3')
