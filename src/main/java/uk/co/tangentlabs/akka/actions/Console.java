package uk.co.tangentlabs.akka.actions;

import java.text.SimpleDateFormat;

import org.joda.time.format.DateTimeFormat;

import uk.co.tangentlabs.akka.message.LogMessage;

public class Console implements LogAction {

	public Console() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void log(LogMessage logMessage) {
		System.out.println(String.format("[%s:%s]\t%s:%s", logMessage.getClass().getName(), 
				logMessage.getCaller(),
				SimpleDateFormat.getDateTimeInstance().format(logMessage.getTimeStamp()),
				logMessage.getLogMessage()));
	}


}
