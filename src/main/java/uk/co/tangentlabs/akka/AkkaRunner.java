package uk.co.tangentlabs.akka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import uk.co.tangentlabs.akka.actor.ConductingActor;
import uk.co.tangentlabs.akka.message.*;
import uk.co.tangentlabs.util.RandomString;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;


public class AkkaRunner {
	private static final int MESSAGES = 10000;
	Logger logger = Logger.getLogger(AkkaRunner.class.getCanonicalName());
	protected final ActorRef master;
	private ActorSystem system;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello Akka");
//		AkkaRunner runner = new AkkaRunner();
//		runner.sendMessagesLikeCrazy();
//		runner.prompt();
//		
		System.exit(0);
	}
	
	private void prompt() {
		for (;;){
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        System.out.print("Type x to exit");
	        String s;
			try {
				s = br.readLine();
				if (StringUtils.equals("x", s.trim())){
		        	master.tell(new Exit());
		        	return;
		        } else {
		        	System.err.print(s.trim());
		        }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		}
		
		
	}

	public void sendMessagesLikeCrazy(){
		ExecutorService threadRunner = Executors.newFixedThreadPool(10);
		logger.info("Running Logs");
		for (int i = 0; i < MESSAGES; i++){
			threadRunner.submit(new LogThread(master));
		}
		threadRunner.shutdown();
		logger.info("Logs should now be processed Logs");
		try {
			threadRunner.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.warning("Service Complete");
	}
	
	
	public AkkaRunner(){
		system = ActorSystem.create("Log-SuperQuick");
		
		master = system.actorOf(new Props(new UntypedActorFactory() {
			public UntypedActor create() {
				return new ConductingActor();
			}
		}), "master");
		
		system.registerOnTermination(new Runnable(){
			public void run() {
				logger.info("System shutting down");
			}
		});
		
	}
	
	public static class LogThread extends Thread{
		
		private ActorRef master;

		public LogThread(ActorRef master){
			this.master = master;
		}
		
		public void run(){
			Class[] levels = new Class[]{
					Debug.class, 
					Info.class, 
					Warn.class,
					uk.co.tangentlabs.akka.message.Error.class
					};
			Random r = new Random();
			RandomString randomCaller = new RandomString(10);
			RandomString randomMessage = new RandomString(100);
			Class c = levels[r.nextInt(levels.length)];
			Constructor construct;
			try {
				construct = c.getConstructor(new Class[]{String.class, String.class});
			
				String caller = randomCaller.nextString();
				String message = randomMessage.nextString();
				LogMessage log = (LogMessage) construct.newInstance(new Object[]{caller, message});
				master.tell(log);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
