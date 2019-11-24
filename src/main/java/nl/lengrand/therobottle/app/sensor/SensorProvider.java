package nl.lengrand.therobottle.app.sensor;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;

@ApplicationScoped
public class SensorProvider {

    public float[] retrieveSensorData(){
        float[] sensorData = {25, 27};
        return sensorData;
    }
}
