package utils;

import java.io.FileWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * This class handles the error logging.
 */
public class Log {
    private static Log log;
    private static String path;
    private final int stackTraceSize;


    /**
     * Returns an instance of the logger for public use, since it is a singleton.
     */
    public static Log getLogger() {
        if (log == null) {
            log = new Log();
        }
        return log;
    }


    /**
     * Takes String input and writes it to a file.
     * @param  text a String, the text to persist in a log file.
     */
    private void writeText(String text) {
        String filename = getFileName();
        try (Writer fileWriter = new FileWriter(filename, true)) {
            fileWriter.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * When called, this writes exceptions to a log file.
     * @param  e    an exception that is thrown.
     */
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


    /**
     * When called, this logs text to a log file - error text or notices.
     * @param  text a String of the text to persist in a log file.
     */
    public void write(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getTimestamp())
                .append(" - ")
                .append(text)
                .append("\n");
        write(stringBuilder.toString());
    }


    /**
     * Returns a formatted String to read a stack trace more easily.
     * Input is an exception that was thrown.
     * @param  e    any Exception
     */
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


    /**
     * Returns a String of the current date in a specified format.
     * @return      a String of the date
     */
    private String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd HH:mm:ss]");
        return formatter.format(LocalDateTime.now());
    }


    /**
     * Returns a String of the path and filename of the log file.
     * @return      String of the filename
     */
    private String getFileName() {
        return path + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".log";
    }

    private Log() {
        // By default, (testing in IDE locally) this will be in C:\Users\YourName\.SmartTomcat\Flashcard\Flashcard\logs\
        path = "logs/";
        stackTraceSize = 10;
    }
}
