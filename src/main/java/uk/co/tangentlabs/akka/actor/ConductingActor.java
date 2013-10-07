package uk.co.tangentlabs.akka.actor;


import akka.actor.Actor;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.routing.RoundRobinRouter;

public class ConductingActor extends UntypedActor {
	
	public static final int workers = 3;
	private final Object workerRouter;
	
	public ConductingActor(){
		Props actorProperties = new Props( new UntypedActorFactory() {
			public Actor create() {
				return null;
			}}
		);
		workerRouter = this.getContext().actorOf(
				actorProperties.withRouter(new RoundRobinRouter(
						workers)), "workerRouter");
	}
	
//	private Router getRouter

	@Override
	public void onReceive(Object arg0) throws Exception {
		
		
	}
}
