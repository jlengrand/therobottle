#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <unistd.h>
#include <jni.h>
#include "../java/nl/lengrand/therobottle/driver/nl_lengrand_therobottle_driver_Dht11Driver.h"
#include "adafruit/Raspberry_Pi_2/pi_2_dht_read.h"

JNIEXPORT void JNICALL Java_Dht11Driver_sayHello(JNIEnv *env, jobject thisObj) {
   printf("Hello JNI!\n");
   return;
}

JNIEXPORT jfloatArray JNICALL Java_Dht11Driver_getTemperatureAndHumidity(JNIEnv *env, jobject thisObj){
    float humidity = 0, temperature = 0;
    int sensor = 11; // Make those dynamic one day?
    int pin = 4; // Make those dynamic one day?
    int res = pi_2_dht_read(sensor, pin, &humidity, &temperature); // Might wanna do something with status one day

    jfloat* values = (jfloat *) malloc(2*sizeof(jfloat));
    values[0] = temperature;
    values[1] = humidity;

    jfloatArray outJNIArray = (*env)->NewFloatArray(env, 2);
    if (NULL == outJNIArray) return NULL;

    (*env)->SetFloatArrayRegion(env, outJNIArray, 0, 2, (const jfloat*)values);
    return outJNIArray;
}