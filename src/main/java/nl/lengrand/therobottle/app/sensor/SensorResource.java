package nl.lengrand.therobottle.app.sensor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;

@Path("/sensor")
@RequestScoped
public class SensorResource {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private final SensorProvider sensorProvider;

    @Inject
    public SensorResource(SensorProvider sensorProvider) {
        this.sensorProvider = sensorProvider;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getSensorData(){
        return createResponse(sensorProvider.retrieveSensorData());
    }

    private JsonObject createResponse(float[] sensorData) {
        return JSON.createObjectBuilder()
                .add("test", "plop")
                .add("temperature", sensorData[0])
                .add("humidity", sensorData[1])
                .build();
    }

}
