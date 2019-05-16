package server;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Log {
	Logger logger;
	private FileHandler fileHandler;

	public Log(String fileName) {
		String directory = "src/server/";
		File dir = new File(directory);
		File logFile = new File(dir, fileName);
		String theLogger = directory + fileName;
		if(logFile.exists()) {
			System.out.println("logFile exists.");
		} else {
			try {
				System.out.println("logFile no exist so create new.");
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(dir.exists()) {
			System.out.println("dir exists.");
		} else {
			try {
				System.out.println("dir no exist so create new.");
				dir.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			fileHandler = new FileHandler(theLogger, true);
			System.out.println(theLogger + "<- test");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		logger.info(msg);
	}

	public void logMessage(String msg) {
		logger.info(msg);
	}

	public void logJoined(String user) {
		logger.info(user + " has joined the session.");
	}

	public void logLeft(String user) {
		logger.info(user + " has left the session.");
	}
}
