package nl.lengrand.therobottle.driver;

public class Dht11Driver {

    static {
        System.loadLibrary("dht11");
    }

    public native void sayHello();

    public native float[] getTemperatureAndHumidity();
}