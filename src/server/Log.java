package server;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Class handles all the logging in the program. Every action that is to be logged
 * is saved as a .txt file on the computer hosting the server.
 * @author Patrik Skuza
 *
 */
public class Log {
	Logger logger; //The logger object for logging of actions/messages
	private FileHandler fileHandler; //FileHandler for the logger

	/**
	 * Constructor that creates a .txt file where log-messages are stored and attaches 
	 * a FileHandler to the logger object.
	 * @param fileName Name of the logfile
	 */
	public Log(String fileName) {
		String directory = "c:\\dev\\logs\\"; //The directory for where the logfile is saved
		File dir = new File(directory); //Directory "path" for the actual logfile.
		if(!dir.mkdirs()) { //If directory does not exist, create it.
			dir.getParentFile().mkdirs();
		}
		File logFile = new File(dir + fileName); //Directory and filename of the logger
		String theLogger = directory + fileName; //Directory and filename of the logger in String-format
		if(!logFile.exists()) {
			System.out.println("logFile no exists.");
		} else {
			System.out.println("logFile exists.");
		}
		try {
			fileHandler = new FileHandler(theLogger, true); //Create a FileHandler for the logger and make it appendable.
			System.out.println(theLogger + "<- test");
		} catch (SecurityException e) { //Catch security violation.
			e.printStackTrace();
		} catch (IOException e) { //Cacth I/O exception.
			e.printStackTrace();
		}
		logger = Logger.getLogger(theLogger); //Assign the created log-object.
		logger.addHandler(fileHandler); //Add the created FileHandler for the logger.
		logger.setUseParentHandlers(false); //Prevent logger from sending log-messages to its parent.
		CustomFormatter formatter = new CustomFormatter(); //Create a new instant of the CustomFormatter.
		fileHandler.setFormatter(formatter); //Set the CustomFormatter to the FileHandler.
	}

	public Log() {
	}

	public void log(String msg) {
		logger.info(msg + System.lineSeparator());
	}

	/**
	 * Method logs the acquired message in the log .txtfile
	 * @param msg Message to be logged
	 */
	public void logMessage(String msg) {
		logger.info(msg + System.lineSeparator());
	}

	public void logJoined(String user) {
		logger.info(user + " has joined the session." + System.lineSeparator());
	}

	public void logLeft(String user) {
		logger.info(user + " has left the session." + System.lineSeparator());
	}
}
