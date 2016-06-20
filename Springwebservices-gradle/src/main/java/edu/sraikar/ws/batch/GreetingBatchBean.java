package edu.sraikar.ws.batch;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.sraikar.ws.model.Greeting;
import edu.sraikar.ws.service.GreetingService;

@Component
@Profile("batch")
public class GreetingBatchBean {

	private  Logger logger =  LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GreetingService greetingService;
	
	@Scheduled(cron= "${batch.greeting.cron}")
	public void cronJob(){
		logger.info("starting cron");
		
		//Add scheduled logic here...
		Collection<Greeting> greetings = greetingService.findAll();
		logger.info("There are {} greetings in the data store",greetings.size());
		
		logger.info("cron ended");
	}

	@Scheduled(initialDelayString="${batch.greeting.initialDelay}",fixedRateString="${batch.greeting.fixedrate}") //next cron starts after 15 seconds of the start of the previous job
	public void fixedRateJobWithInitialDelay(){
		logger.info("starting fixedRateJobWithInitialDelay");
		
		long pause=5000;
		long start = System.currentTimeMillis();
		do{
			if(start+pause<System.currentTimeMillis()){
				break;
			}
		}while(true);
		logger.info("Processing time was {} secinds",pause/1000);
		logger.info("ending fixedRateJobWithInitialDelay");
	}


	@Scheduled(initialDelayString="${batch.greeting.initialDelay}",fixedDelayString="${batch.greeting.fixedDelay}") //next cron starts after 15 seconds of the end of the previous job
	public void fixedDelayJobWithInitialDelay(){
		logger.info("starting fixedRateJobWithInitialDelay");
		
		long pause=5000;
		long start = System.currentTimeMillis();
		do{
			if(start+pause<System.currentTimeMillis()){
				break;
			}
		}while(true);
		logger.info("Processing time was {} secinds",pause/1000);
		logger.info("ending fixedRateJobWithInitialDelay");
	}
}
