package ph.apper.exception;

public class UserNotVerifiedActive extends Exception {
    public UserNotVerifiedActive(String message) {
        super(message);
    }

    public UserNotVerifiedActive(String message, Throwable cause) {
        super(message, cause);
    }
}
