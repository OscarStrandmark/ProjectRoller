package server;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * This class represents a Custom Formatter for the logger
 * which displays time, date and the logged message in a clear manner.
 * @author Patrik Skuza
 *
 */
public class CustomFormatter extends Formatter {

	private final Date date = new Date(); //Date object that keeps time of when a message was logged.

	/**
	 * Returns date (and time) plus the message in a structured format for the logger.
	 */
    public synchronized String format(LogRecord record) {
        date.setTime(record.getMillis()); //Set the time for when the message was logged.
        return date + System.lineSeparator() + record.getMessage() + System.lineSeparator();
    }
}
