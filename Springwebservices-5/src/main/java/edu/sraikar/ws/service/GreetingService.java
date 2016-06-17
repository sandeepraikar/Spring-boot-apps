package edu.sraikar.ws.service;

import java.util.Collection;

import edu.sraikar.ws.model.Greeting;

public interface GreetingService {

	Collection<Greeting> findAll();
	Greeting findOne(Long id);
	Greeting create(Greeting greeting);
	Greeting update(Greeting greeting);
	void remove(Long id);
}
