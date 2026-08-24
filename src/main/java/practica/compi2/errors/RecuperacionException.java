package practica.compi2.errors;

public class RecuperacionException extends RuntimeException {

    public RecuperacionException(String message) {
        super(message);
    }

    public RecuperacionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecuperacionException(Throwable cause) {
        super(cause);
    }

    public RecuperacionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
