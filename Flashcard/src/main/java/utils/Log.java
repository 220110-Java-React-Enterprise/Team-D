package utils;


import java.io.FileWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Handles logging the errors
public class Log {
    private static Log log;
    private static String path;
    private final int stackTraceSize;

    // Returns an instance of the logger for public use
    public static Log getLogger() {
        if (log == null) {
            log = new Log();
        }
        return log;
    }


    // Writes given text to file
    private void writeText(String text) {
        String filename = getFileName();
        try (Writer fileWriter = new FileWriter(filename, true)) {
            fileWriter.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // When an exception is thrown, log each line to a file
    public void write(Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getTimestamp())
                .append(" - ")
                .append(e.getMessage())
                .append("\n")
                .append(formatStackTrace(e))
                .append("\n");
        writeText(stringBuilder.toString());
    }

    private String formatStackTrace(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<stackTraceSize + 1; i++) {
            stringBuilder.append("\t")
                    .append(stackTrace[i])
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    // Builds the string that will be written in the file for a given exception
    public void write(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getTimestamp())
                .append(" - ")
                .append(text)
                .append("\n");
        write(stringBuilder.toString());
    }

    // Returns the date and time in String format
    private String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd HH:mm:ss]");
        return formatter.format(LocalDateTime.now());
    }

    // Returns the filename in String form of format year-month-day.log
    private String getFileName() {
        return path + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".log";
    }

    private Log() {
        // By default, (testing in IDE locally) this will be in C:\Users\YourName\.SmartTomcat\Flashcard\Flashcard\logs\
        path = "logs/";
        stackTraceSize = 10;
    }
}
