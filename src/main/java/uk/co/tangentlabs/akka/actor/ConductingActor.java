package uk.co.tangentlabs.akka.actor;


import java.util.HashMap;
import java.util.Map;

import uk.co.tangentlabs.akka.actions.*;
import uk.co.tangentlabs.akka.message.*;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.event.Logging.Debug;
import akka.routing.RoundRobinRouter;

public class ConductingActor extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	public static final int workers = 3;
	private final ActorRef workerRouter;
//	private final ActorRef slowTaskActor;
	private int messages;
	
	public ConductingActor(){
		
		Props actorProperties = new Props( new UntypedActorFactory() {
			public Actor create() {
				Map<Class, LogAction> logConfig = new HashMap<Class, LogAction>();
				logConfig.put(uk.co.tangentlabs.akka.message.Error.class, new Email(null));
				logConfig.put(uk.co.tangentlabs.akka.message.Warn.class, new File(new java.io.File("/tmp/akkalog.log")));
				logConfig.put(uk.co.tangentlabs.akka.message.Info.class, new Console());
				logConfig.put(uk.co.tangentlabs.akka.message.Debug.class, new Ignore());
				return new LoggingActor(logConfig);
			}}
		);
		workerRouter = this.getContext().actorOf(
				actorProperties.withRouter(new RoundRobinRouter(
						workers)), "workerRouter");
		messages = 0;
		
//		slowTaskActor = this.getContext().actorOf(new Props(new UntypedActorFactory() {
//			public UntypedActor create() {
//				return new SlowTaskActor(new Email(null));
//			}
//		}), "master");
	}
	
//	private Router getRouter

	@Override
	public void onReceive(Object message) throws Exception {
//		if (message instanceof uk.co.tangentlabs.akka.message.Error){
//			slowTaskActor.tell(message);
//		} else 
//		
		if (message instanceof LogMessage){
			workerRouter.tell(message, getSelf());
		} else if (message instanceof LogResult){
			log.info(String.format("Message: %d", messages++));
		}  else if (message instanceof Exit){
			getContext().system().shutdown();
		} else {
			unhandled(message);
		}
		
	}
}
