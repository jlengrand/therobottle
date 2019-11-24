package nl.lengrand.therobottle.fauna.setup;

import com.faunadb.client.FaunaClient;
import com.faunadb.client.types.Value;
import fauna.Connection;
import fauna.ConnectionException;

import java.util.concurrent.ExecutionException;

import static com.faunadb.client.query.Language.*;
import static fauna.Connection.*;

/*
    Used to create the database and indexes. Should be run only once!
 */
public class FaunaDbSetup {

    public static void main(String[] args) throws ExecutionException, InterruptedException, ConnectionException {
        System.out.println("Setting up Database " + DB_NAME);

        Connection connection = new Connection();

        FaunaClient adminClient = connection.createAdminConnection();

        createDatabase(adminClient);
        Thread.sleep(1000);
        FaunaClient dbClient = connection.createConnection();

        createCollection(dbClient);
        Thread.sleep(1000);

        createIndex(dbClient);
        Thread.sleep(1000);

        createAllIndex(dbClient);
        Thread.sleep(1000);

        createInitialValue(dbClient);

        adminClient.close();
        System.out.println("Client closed");
    }

    private static String getDbKey(FaunaClient adminClient) throws InterruptedException, ExecutionException {
        Value keyResults = adminClient.query(
                CreateKey(Obj("database", Database(Language.Value(DB_NAME)), "role", Language.Value("server")))
        ).get();

        return keyResults.at("secret").to(String.class).get();
    }

    private static void createIndex(FaunaClient dbClient) throws InterruptedException, ExecutionException {
        Value indexResults = dbClient.query(
                CreateIndex(
                        Obj("name", Language.Value(INDEX_NAME),
                            "source", Collection(Language.Value(COLLECTION_NAME)),
                            "unique", Language.Value(true),
                            "terms", Arr(Obj("field", Arr(Language.Value("data"), Language.Value("name"))))
                            )
                )
        ).get();
        System.out.println("Create name index for " + DB_NAME + ":\n " + indexResults + "\n");
    }

    private static void createAllIndex(FaunaClient dbClient) throws InterruptedException, ExecutionException {
        Value indexResults = dbClient.query(
                CreateIndex(
                        Obj("name", Language.Value(INDEX_ALL_NAME),
                                "source", Collection(Language.Value(COLLECTION_NAME))
                        )
                )
        ).get();
        System.out.println("Create all index for " + DB_NAME + ":\n " + indexResults + "\n");
    }


    private static void createCollection(FaunaClient dbClient) throws InterruptedException, ExecutionException {
        Value collectionResults = dbClient.query(
                CreateCollection(
                        Obj("name", Language.Value(COLLECTION_NAME), "history_days", null)
                )
        ).get();
        System.out.println("Create Collection for " + DB_NAME + ":\n " + collectionResults + "\n");
    }

    private static void createDatabase(FaunaClient adminClient) {
        adminClient.query(
                CreateDatabase(
                        Obj("name", Language.Value(DB_NAME))
                )
        );

        System.out.println("Db created");
    }

    public static void createInitialValue(FaunaClient client) throws ExecutionException, InterruptedException {
        Value init = client.query(
                Create(
                        Collection(Language.Value(COLLECTION_NAME)),
                        Obj("data",
                                Obj("name", Language.Value(UPDATE_DEVICE_NAME))
                        )
                )
        ).get();
        System.out.println("Added initial item to collection " + COLLECTION_NAME + ":\n " + init + "\n");
    }
}
