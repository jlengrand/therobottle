package nl.lengrand.therobottle.fauna;

import com.faunadb.client.FaunaClient;
import com.faunadb.client.query.Language;
import com.faunadb.client.types.Value;

import java.util.concurrent.ExecutionException;

import static com.faunadb.client.query.Language.*;

public class Connection {

    public static final String API_KEY_NAME = "ROBOTTLE_KEY";
    public final static String DB_NAME = "robottle";
    public final static String COLLECTION_NAME = "sensors";
    public final static String UPDATE_DEVICE_NAME = "robottle_00";
    public final static String INDEX_NAME = "sensor_data";
    public final static String INDEX_ALL_NAME = "all_sensors";

    private FaunaClient client;

    public Connection(){
    }

    private boolean isInit(){
        return client != null;
    }

    public void init(){
        try {
            this.client = createConnection();
        } catch (ExecutionException | InterruptedException | ConnectionException e) {
            System.out.println("Problem while initializing connection with FaunaDb");
            e.printStackTrace();
        }
    }

    private String getKey() {
        return System.getenv(API_KEY_NAME);
    }

    private String getDbKey(FaunaClient adminClient) throws InterruptedException, ExecutionException {
        Value keyResults = adminClient.query(
                CreateKey(Obj("database", Database(Language.Value(DB_NAME)), "role", Language.Value("server")))
        ).get();

        return keyResults.at("secret").to(String.class).get();
    }

    public FaunaClient createConnection() throws ExecutionException, InterruptedException, ConnectionException {
        FaunaClient adminClient = this.createAdminConnection();
        return adminClient.newSessionClient(getDbKey(adminClient));
    }

    public FaunaClient createAdminConnection() throws ConnectionException {
        if(getKey() == null) throw new ConnectionException("Local API key not found " + API_KEY_NAME );
        return FaunaClient.builder()
                .withSecret(getKey())
                .build();
    }

    public FaunaClient getClient(){
        return client;
    }
}
