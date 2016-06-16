package edu.sraikar.ws.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.sraikar.ws.model.Greeting;

@Service
public class GreetingServiceImpl implements GreetingService{

	private static BigInteger nextId;
	private static Map<BigInteger, Greeting> greetingMap;


	static {
		Greeting g1 = new Greeting();
		g1.setText("Hello Spring Boot");
		save(g1);
		Greeting g2 = new Greeting();
		g2.setText("Hello World");

		save(g2);
		Greeting g3 = new Greeting();
		g3.setText("Namsakara Spring Boot!! ");
		save(g3);
	}

	private static Greeting save(Greeting greeting) {
		if (greetingMap == null) {
			greetingMap = new HashMap<BigInteger, Greeting>();
			nextId = BigInteger.ONE;
		}
		
		//If update...
		if(greeting.getId()!=null){
			Greeting oldGreeting = greetingMap.get(greeting.getId());
			if(oldGreeting==null){
				return null;
			}
			greetingMap.remove(greeting.getId());
			greetingMap.put(greeting.getId(), greeting);
			return greeting;
		}
		
		//If create..
		greeting.setId(nextId);
		nextId = nextId.add(BigInteger.ONE);
		greetingMap.put(greeting.getId(), greeting);
		return greeting;
	}

	private boolean delete(BigInteger id){
		if(!greetingMap.containsKey(id)){
			return false;
		}
		greetingMap.remove(id);
		return true;
	}
	
	@Override
	public Collection<Greeting> findAll() {
		Collection<Greeting> greetings = greetingMap.values();
		return greetings;
	}

	@Override
	public Greeting findOne(BigInteger id) {
		Greeting greeting = greetingMap.get(id);
		return greeting;
	}

	@Override
	public Greeting create(Greeting greeting) {
		Greeting savedGreeting = save(greeting);
		return savedGreeting;
	}

	@Override
	public Greeting update(Greeting greeting) {
		Greeting updatedGreeting = save(greeting);
		return updatedGreeting;
	}

	@Override
	public boolean remove(BigInteger id) {
		boolean deleted = delete(id);
		return deleted;
	}

}
