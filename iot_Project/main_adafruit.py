import random
import serial.tools.list_ports
import sys
import time

import livecamera
from learn import *
from uart import *
from livecamera import *
import atexit

#INSTALL THESE PACKAGE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
#keras
#tensorflow
#Pillow
#numpy
#opencv-python
#atexit
#pyserial
#adafruit-io


from Adafruit_IO import MQTTClient
AIO_FEED_IDs = ["button","lastmessage","period", "humidity", "nhietdo", "sensor_status", "devices_status"]
AIO_USERNAME = "minhtrung181"
AIO_KEY = "aio_KSDD254992xZCEQvV94LSP6QKkuO"

counter = 15
save_counter = 15
have_read = True
button_status = "0"
button_change = False
button_state = ""

def my_exit_function(client):


#this will call a function at exit(run by terminal and stop by ctrl+c),
#this publishes a message to last message feed to detect if gateway stopped
    client.publish("lastmessage","1")

def connected(client):

    #subcribe given topic


    print("Ket noi thanh cong")
    for topic in AIO_FEED_IDs:
        client.subscribe(topic)

def subscribe(client, userdata, mid, granted_qos):
    print("Subscribe thanh cong")

def disconnected(client):
    print("Ngat ket noi")
    sys.exit(1)

def message(client, feed_id, payload):
    print("Nhan du lieu " + payload + "feed id: " + feed_id)
    global counter
    global save_counter
    global have_read
    global button_state
    global button_change
    if feed_id == "button":          #button feed, ON-write 1 to uart, OFF-write 2 to uart
        button_change = True
        if payload == "ON":
            button_state = "!1_1#"
        else:
            button_state = "!1_0#"

    elif feed_id == "period":        #this feed to determine how long does uart get message from mcu in second
        if payload == "15":
            counter = 15
            save_counter = 15
        elif payload == "35":
            counter = 35
            save_counter = 35
        elif payload == "50":
            counter = 50
            save_counter = 50
        elif payload == "65":
            counter = 65
            save_counter = 65





client = MQTTClient(AIO_USERNAME, AIO_KEY)
client.on_connect = connected
client.on_disconnect = disconnected
client.on_message = message
client.on_subscribe = subscribe
client.connect()
client.loop_background()
client.publish("lastmessage", "0")
client.publish("sensor_status", "0")
client.publish("devices_status", "0")
atexit.register(my_exit_function, client)

curr_time = time.time()
while True:

    if ((time.time() - curr_time) >= 1):
        curr_time = time.time()
        counter = counter - 1                  #counter count down to 0 then
        print(counter)
    
    if counter <= 0:
    #     temp = random.randint(10, 30)
    #     client.publish("haha", temp)
    #     temp2 = random.randint(40, 60)
    #     client.publish("nhietdo", temp2)
        print("GET DATA")
        writeData("!2#")
        counter = save_counter                  #reset counter
        have_read = False
    if button_change:
        button_change = False
        writeData(button_state)
        have_read = False

    if (have_read == False):
        readSerial(client)           #THIS FUNCTION CALL FROM UART.PY

    # counter_ai = counter_ai -1
    # if counter_ai <= 0:
    #     counter_ai = 5
    #     ai_result = image_detector()
    #     print("Output: ", ai_result)
    # processData(client, "!:T:39.5##")
    # break;
    time.sleep(1)

