JAVA_HOME=/usr/lib/jvm/java-11-openjdk-armhf
INCLUDES = -I. -I../java  -Iadafruit/Raspberry_Pi_2 -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux
HEADERS = ../java/nl/lengrand/therobottle/driver/nl_lengrand_therobottle_driver_Dht11Driver.h adafruit/common_dht_read.h adafruit/Raspberry_Pi_2/pi_2_mmio.h adafruit/Raspberry_Pi_2/pi_2_dht_read.h
FILES = adafruit/common_dht_read.c adafruit/Raspberry_Pi_2/pi_2_mmio.c adafruit/Raspberry_Pi_2/pi_2_dht_read.c Dht11Driver.c
OUT = libdht11

default: all

all: clean compileJava library copy

program: Dht11Driver.c
	gcc -Wall $(INCLUDES) $(HEADERS) $(FILES) -o $(OUT)

library: Dht11Driver.c
	gcc -shared -fPIC -o $(OUT).so $(INCLUDES) $(FILES)

copy:
	-cp $(OUT).so ../resources/native/

compileJava:
	-cd ../java/nl/lengrand/therobottle/driver;javac -h . Dht11Driver.java;cd -;

clean:
	-rm ../java/nl/lengrand/therobottle/driver/nl_lengrand_therobottle_driver_Dht11Driver.h
	-rm -f $(OUT).so
	-rm -f $(OUT)