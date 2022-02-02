package exceptions;

import javax.servlet.ServletException;

public class InvalidContentTypeException extends ServletException {
    public InvalidContentTypeException(String message) {
        super(message);
    }
}
