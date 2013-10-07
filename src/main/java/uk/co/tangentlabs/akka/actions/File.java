package uk.co.tangentlabs.akka.actions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;

import uk.co.tangentlabs.akka.message.LogMessage;

public class File implements LogAction {

	private java.io.File file;

	public File(java.io.File file) {
		this.file = file;
	}

	@Override
	public void log(LogMessage logMessage) {
		
		try {
			FileUtils.writeStringToFile(file, String.format("[%s:%s]\t%s:%s\n", logMessage.getClass().getName(), 
					logMessage.getCaller(),
					SimpleDateFormat.getDateTimeInstance().format(logMessage.getTimeStamp()),
					logMessage.getLogMessage()), true);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}



}
