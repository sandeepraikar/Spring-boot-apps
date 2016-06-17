package edu.sraikar.ws.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sraikar.ws.model.Greeting;
import edu.sraikar.ws.repository.GreetingRepository;

@Service
public class GreetingServiceImpl implements GreetingService{

	@Autowired
	private GreetingRepository greetingRepository;
	
	@Override
	public Collection<Greeting> findAll() {
		Collection<Greeting> greetings = greetingRepository.findAll();
		return greetings;
	}

	@Override
	public Greeting findOne(Long id) {
		Greeting greeting = greetingRepository.findOne(id);
		return greeting;
	}

	@Override
	public Greeting create(Greeting greeting) {
		if(greeting.getId()!=null){
			return null;
		}
		Greeting savedGreeting = greetingRepository.save(greeting);
		return savedGreeting;
	}

	@Override
	public Greeting update(Greeting greeting) {
		Greeting greetingPersisted  = greetingRepository.findOne(greeting.getId()); 
		if(greetingPersisted.getId()==null){
			return null;
		}
		Greeting updatedGreeting = greetingRepository.save(greeting);
		return updatedGreeting;
	}

	@Override
	public void remove(Long id) {
		greetingRepository.delete(id);
		
	}

}
