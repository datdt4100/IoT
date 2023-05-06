import unittest

from main_adafruit import client
from uart import *
#include multiple part of test. uncomment to use

class uart_test(unittest.TestCase):

    # def test1_getPort(self):
    #     #Check setup com com and hercules before test
    #     #correct case
    #     strTest = getPort()
    #     self.assertEqual(strTest, "COM9")
    #
    # def test2_getPort(self):
    #     # fail case
    #     strTest = getPort()
    #     self.assertEqual(strTest, "COM10")
    #
    # ser = serial.Serial(port="COM9", baudrate=115200)

    # def test1_processData(self):
    #     #if test this function, comment line 23 in uart.py, comment line 59 in main and uncomment line 60 and 61
    #     # correct case
    #     data = "!:T:39.5##"
    #     strTest = processData(client, data)
    #     self.assertEqual("39.5", strTest)
