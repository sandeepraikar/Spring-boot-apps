package edu.sraikar.ws.service;

import java.math.BigInteger;
import java.util.Collection;

import edu.sraikar.ws.model.Greeting;

public interface GreetingService {

	Collection<Greeting> findAll();
	Greeting findOne(BigInteger id);
	Greeting create(Greeting greeting);
	Greeting update(Greeting greeting);
	boolean remove(BigInteger id);
}
