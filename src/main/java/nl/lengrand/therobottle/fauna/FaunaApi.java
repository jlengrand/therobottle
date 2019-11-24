package nl.lengrand.therobottle.fauna;

import com.faunadb.client.query.Language;
import com.faunadb.client.types.Value;

import java.util.concurrent.ExecutionException;

import static com.faunadb.client.query.Language.*;
import static nl.lengrand.therobottle.fauna.Connection.*;

public class FaunaApi {

    private Connection connection;

    public FaunaApi(){
        this.connection = new Connection();
        connection.init();
    }

    public void add(float[] res) throws ExecutionException, InterruptedException {
        Value addDataResult = connection.getClient().query(
                Create(
                        Collection(Language.Value(COLLECTION_NAME)),
                        Obj("data",
                                Obj( "temperature", Language.Value(res[0]), "humidity", Language.Value(res[1]) )
                        )
                )
        ).get();
        System.out.println("Added sensor data to collection " + COLLECTION_NAME + ":\n " + addDataResult + "\n");
    }

    public void update(float[] res) throws ExecutionException, InterruptedException {
        Value updateDataResult =
                connection.getClient().query(
                        Update(
                                Select(Language.Value("ref"), Get(Match(Index(INDEX_NAME), Language.Value(UPDATE_DEVICE_NAME)))),
                                Obj("data",
                                        Obj(
                                                "temperature", Language.Value(res[0]),
                                                "humidity", Language.Value(res[1])
                                        )
                                )
                        )
                ).get();
        System.out.println("Updated sensor data from collection " + COLLECTION_NAME + ":\n " + updateDataResult + "\n");
    }
}
