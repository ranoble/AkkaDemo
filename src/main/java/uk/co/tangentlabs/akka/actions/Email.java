package uk.co.tangentlabs.akka.actions;

import java.util.Map;

import uk.co.tangentlabs.akka.message.LogMessage;

public class Email implements LogAction {

	private Console console;

	public Email(Map<String, String> config) {
		console = new Console();
	}

	@Override
	public void log(LogMessage logMessage) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(500);
			console.log(logMessage);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
