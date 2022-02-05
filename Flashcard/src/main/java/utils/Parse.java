package utils;

import exceptions.InvalidInputException;

public class Parse {
    
    /**
     * Attempts to retrieve a number from a string.
     * Returns an Integer of the number sent, or a null value if it is negative or fails.
     * Input is an integer in String format.
     * @param  numberString     a String of the number to convert
     * @return      an Integer of the resulting number
     */
    public static Integer getNumberFromString(String numberString) throws InvalidInputException {
        Integer number;

        try {
            number = Integer.parseInt(numberString);
            if (number >= 0) {
                return number;
            }
        }

        catch (NumberFormatException e) {
            // TODO: Something other than a stack trace
            throw new InvalidInputException("Bad number received.  This was not a number: " + numberString);
        }
        // TODO: throw some custom exception here about invalid input
        return null;
    }
}
