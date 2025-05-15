package exceptions;

public class ExceptionInsufficientStock extends RuntimeException {
    public ExceptionInsufficientStock(String message) {
        super(message);
    }
}
