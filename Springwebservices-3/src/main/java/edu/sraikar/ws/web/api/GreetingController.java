package edu.sraikar.ws.web.api;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.sraikar.ws.model.Greeting;

//The @RestController extends the standard Spring sterotype
// @Controller
@RestController
public class GreetingController {

	private static BigInteger nextId;
	private static Map<BigInteger, Greeting> greetingMap;

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

	private static boolean delete(BigInteger id){
		if(!greetingMap.containsKey(id)){
			return false;
		}
		greetingMap.remove(id);
		return true;
	}
	
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

	//end point for returning all the greeting objects in JSON format
	@RequestMapping(value = "/api/greetings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greeting>> getGreetings() {

		Collection<Greeting> greetings = greetingMap.values();

		return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
	}
	
	//end point for retrieving a single greeting object 
	@RequestMapping(value="/api/greetings/{id}", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> getGreeting(@PathVariable("id") BigInteger id ){
		Greeting greeting = greetingMap.get(id);
		if(greeting==null){
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Greeting>(greeting,HttpStatus.OK);
	}
	
	//end point for creating new greeting object and saving it into dummy placeholder map object
	@RequestMapping(value="/api/greetings",
					method=RequestMethod.POST,
					produces=MediaType.APPLICATION_JSON_VALUE,
					consumes=MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting){
		Greeting savedGreeting = save(greeting);
		return new ResponseEntity<Greeting>(savedGreeting,HttpStatus.CREATED);
	}
	
	//end point for updating a greeting object
	@RequestMapping(value="/api/greetings",
					method=RequestMethod.PUT,
					produces=MediaType.APPLICATION_JSON_VALUE,
					consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting){
		Greeting updatedGreeting = save(greeting);
		if(updatedGreeting==null){
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
	}
	
	//end point for deleting greeting resource based on the greeting id passed as path variable!
	@RequestMapping(value="/api/greetings/{id}",
					method=RequestMethod.DELETE)
	public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") BigInteger id){
		boolean deleted = delete(id);
		if(!deleted){
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
	}
}
