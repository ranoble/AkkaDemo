package uk.co.tangentlabs.akka.actions;

import uk.co.tangentlabs.akka.message.LogMessage;

public interface LogAction {
	void log(LogMessage logMessage);
}
