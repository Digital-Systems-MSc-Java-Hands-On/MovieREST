package eu.unipi.model;

public class APIError {
    private final String message;

    public String getMessage() {
        return message;
    }

    public APIError(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "APIError{" +
                "message='" + message + '\'' +
                '}';
    }
}
