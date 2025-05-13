package exceptions;

public class ExceptionCpfAlreadyExists extends RuntimeException {
    public ExceptionCpfAlreadyExists(String message) {
        super(message);
    }
}
