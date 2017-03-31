import RPi.GPIO as GPIO
import time
import datetime
import picamera  # new
import os

var1 = "java -jar ImageSender.jar"
var0 = "java -jar FaceRecogniser.jar"
var2 = "/home/pi/Desktop"
#"java -jar WebSocket.jar"
#var3="RASP1"

sensor = 4

GPIO.setmode(GPIO.BCM)
GPIO.setup(sensor, GPIO.IN, GPIO.PUD_DOWN)

previous_state = False
current_state = False

#imagename = "test.jpg"
cam = picamera.PiCamera()  # new
#cam.capture(var2+"/"+imagename)

while True:
    time.sleep(0.1)
    previous_state = current_state
    current_state = GPIO.input(sensor)
    if current_state != previous_state:
        new_state = "HIGH" if current_state else "LOW"
        print("GPIO pin %s is %s" % (sensor, new_state))
        if current_state:  # new
            cam.start_preview()
	    imagename=str(datetime.datetime.now().strftime("%Y-%m-%d-%H-%M-%S") )+"RASP.jpg"
	    cam.capture(var2+"/"+imagename)
	    #command = var1+" "+imagename+" "+var2+" "+var3
	    print(var0)
#	    os.system("./stop_stream.sh")
            #os.system(var0)
            #os.system(var1)
	    #os.system(var2)
	    
       
