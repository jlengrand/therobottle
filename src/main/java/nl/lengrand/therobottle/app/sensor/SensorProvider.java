package nl.lengrand.therobottle.app.sensor;

import nl.lengrand.therobottle.driver.Dht11Driver;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SensorProvider {

    private Dht11Driver theDriver = new Dht11Driver();

    public float[] retrieveSensorData(){
        float [] sensorData = theDriver.getTemperatureAndHumidity();

        return sensorData;
    }
}
