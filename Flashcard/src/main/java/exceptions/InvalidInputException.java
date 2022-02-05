package exceptions;

import javax.servlet.ServletException;

/**
 * This is a custom exception that will be caught by Catalina to produce an
 * error response that is returned to the user.
 */
public class InvalidInputException extends ServletException {
    public InvalidInputException(String message) {
        super(message);
    }
}
