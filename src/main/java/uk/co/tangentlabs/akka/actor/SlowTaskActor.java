package uk.co.tangentlabs.akka.actor;

import uk.co.tangentlabs.akka.actions.Email;
import uk.co.tangentlabs.akka.message.LogMessage;
import uk.co.tangentlabs.akka.message.LogResult;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class SlowTaskActor extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private Email email;
	
	public SlowTaskActor(Email email){
		this.email = email;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof uk.co.tangentlabs.akka.message.Error){
			
			email.log((LogMessage)message);
			
		} else {
			log.error(message.getClass().getCanonicalName());
			unhandled(message);
		}
		getSender().tell(new LogResult());
		
	}

}
