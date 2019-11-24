#!/bin/sh

rm testAdafruit;
gcc -Wall -I. -Iadafruit/Raspberry_Pi_2 adafruit/common_dht_read.c adafruit/Raspberry_Pi_2/pi_2_mmio.c adafruit/Raspberry_Pi_2/pi_2_dht_read.c testAdafruit.c -o testAdafruit