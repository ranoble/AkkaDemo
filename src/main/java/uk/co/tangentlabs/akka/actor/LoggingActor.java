package uk.co.tangentlabs.akka.actor;

import java.util.Map;

import uk.co.tangentlabs.akka.actions.LogAction;
import uk.co.tangentlabs.akka.message.LogMessage;

import akka.actor.UntypedActor;


public class LoggingActor extends UntypedActor {
	Map<Class, LogAction> actions;
	
	public LoggingActor(Map<Class, LogAction> actions){
		this.actions = actions;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (actions.containsKey(message.getClass())){
			LogAction action =  actions.get(message.getClass());
			action.log((LogMessage)message);
		} else {
			unhandled(message);
		}
		
	}

}
