package ph.apper.exception;

public class InvalidBlogCreationRequestException extends Exception{
    public InvalidBlogCreationRequestException(String message) {
        super(message);
    }

    public InvalidBlogCreationRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
