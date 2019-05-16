package server;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


public class CustomFormatter extends Formatter {

    private final Date date = new Date();

    public synchronized String format(LogRecord record) {
        date.setTime(record.getMillis());
        return date + "\n" + record.getMessage() + "\n" + "\n";
    }
}