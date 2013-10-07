package uk.co.tangentlabs.akka;



import java.util.logging.Logger;

import uk.co.tangentlabs.akka.actor.ConductingActor;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

public class AkkaRunner {
	Logger logger = Logger.getLogger(AkkaRunner.class.getCanonicalName());
	private ActorRef master;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello Akka");
		AkkaRunner runner = new AkkaRunner();
		runner.runActor();
		runner.sendMessagesLikeCrazy();
	}
	
	public void sendMessagesLikeCrazy(){
		
	}
	
	public void runActor(){
		ActorSystem system = ActorSystem.create("Log SuperQuick!");
		
		master = system.actorOf(new Props(new UntypedActorFactory() {
			public UntypedActor create() {
				return new ConductingActor();
			}
		}), "master");
		
		system.registerOnTermination(new Runnable(){
			public void run() {
				logger.info("System shuttong down");
			}
		});
		
	}

}
