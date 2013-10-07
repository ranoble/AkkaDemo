package uk.co.tangentlabs.akka.message;

import java.util.Date;

public class LogMessage {
	
	protected final String logMessage;
	protected final String caller;
	protected final Date timeStamp;
	
	public LogMessage(String caller, String logMessage){
		this.caller = caller;
		this.logMessage = logMessage;
		this.timeStamp = new Date();
	}
	
	public String getLogMessage() {
		return logMessage;
	}

	public String getCaller() {
		return caller;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}
}
