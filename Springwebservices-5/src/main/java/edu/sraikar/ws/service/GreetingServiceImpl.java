package edu.sraikar.ws.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.sraikar.ws.model.Greeting;
import edu.sraikar.ws.repository.GreetingRepository;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) // this
																	// indicates
																	// the
																	// service
// methods exposed in
// GreetingService interface
// support existing
// transaction and will not
// start any new transaction
// if any transaction
// already exists and readOnly is set to true to indicate the methods do not
// modify or create data (applies only to findAll() and findOne())

public class GreetingServiceImpl implements GreetingService {

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
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public Greeting create(Greeting greeting) {
		if (greeting.getId() != null) {
			return null;
		}
		Greeting savedGreeting = greetingRepository.save(greeting);
		//This is dummy check to see how transaction works!!!!
		if(savedGreeting.getId()==4L){
			throw new RuntimeException();
		}
		return savedGreeting;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public Greeting update(Greeting greeting) {
		Greeting greetingPersisted = greetingRepository.findOne(greeting.getId());
		if (greetingPersisted.getId() == null) {
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
