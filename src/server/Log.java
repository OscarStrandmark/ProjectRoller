package server;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Log {
	Logger logger;
	private FileHandler fileHandler;

	public Log(String fileName) {
		String directory = "c:\\dev\\logs\\";
		File dir = new File(directory);
		if(!dir.mkdirs()) {
			dir.getParentFile().mkdirs();
		}
		File logFile = new File(dir + fileName);
		String theLogger = directory + fileName;
		if(!logFile.exists()) {
			System.out.println("logFile no exists.");
		} else {
			System.out.println("logFile exists.");
		}
		try {
			fileHandler = new FileHandler(theLogger, true);
			System.out.println(theLogger + "<- test");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger = Logger.getLogger(theLogger);
		logger.addHandler(fileHandler);
		logger.setUseParentHandlers(false);
		CustomFormatter formatter = new CustomFormatter();
		fileHandler.setFormatter(formatter);
	}

	public Log() {
	}

	public void log(String msg) {
		logger.info(msg + System.lineSeparator());
	}

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
