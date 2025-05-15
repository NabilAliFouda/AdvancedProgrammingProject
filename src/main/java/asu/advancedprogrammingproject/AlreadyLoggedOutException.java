package asu.advancedprogrammingproject;

public class AlreadyLoggedOutException extends Exception {
    public AlreadyLoggedOutException(String message) {
        super(message);
    }
}
