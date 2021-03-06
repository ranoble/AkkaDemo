package uk.co.tangentlabs.akka.actor;

import java.util.Map;

import uk.co.tangentlabs.akka.actions.LogAction;
import uk.co.tangentlabs.akka.message.LogMessage;
import uk.co.tangentlabs.akka.message.LogResult;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class LoggingActor extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
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
			log.error(message.getClass().getCanonicalName());
			unhandled(message);
		}
		getSender().tell(new LogResult());
		
	}

}
