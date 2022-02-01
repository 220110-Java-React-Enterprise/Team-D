package utils;

public class Parse {

    // Attempts to retrieve the card number (integer) from a string
    // generic-ifying this since it could be used for user ID's too
    // Expects a string representation of a number; returns an integer form
    // or returns null if it is an error or negative
    public static Integer getNumberFromString(String numberString) {
        Integer number;
        try {
            number = Integer.parseInt(numberString);
            if (number >= 0) {
                return number;
            }
        }
        catch (NumberFormatException e) {
            // TODO: Something other than a stack trace
            e.printStackTrace();
        }
        // TODO: throw some custom exception here about invalid input
        return null;
    }
}
