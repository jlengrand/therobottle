package nl.lengrand.therobottle.fauna;

public class ConnectionException extends Exception {
    public ConnectionException(String errorMessage) {
        super(errorMessage);
    }
}
