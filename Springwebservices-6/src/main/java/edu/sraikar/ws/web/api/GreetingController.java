package edu.sraikar.ws.web.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.sraikar.ws.model.Greeting;
import edu.sraikar.ws.service.GreetingService;

//The @RestController extends the standard Spring sterotype
// @Controller
@RestController
public class GreetingController {

	@Autowired
	GreetingService greetingService;

	// end point for returning all the greeting objects in JSON format
	@RequestMapping(value = "/api/greetings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greeting>> getGreetings() {
		System.out.println("getAllGreetings called!");
		Collection<Greeting> greetings = greetingService.findAll();
		return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
	}

	// end point for retrieving a single greeting object
	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> getGreeting(@PathVariable("id") Long id) {
		System.out.println("getGreeting called!");
		Greeting greeting = greetingService.findOne(id);
		if (greeting == null) {
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
	}

	// end point for creating new greeting object and saving it into dummy
	// placeholder map object
	@RequestMapping(value = "/api/greetings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {
		System.out.println("createGreeting called!");
		Greeting savedGreeting = greetingService.create(greeting);
		return new ResponseEntity<Greeting>(savedGreeting, HttpStatus.CREATED);
	}

	// end point for updating a greeting object
	@RequestMapping(value = "/api/greetings", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting) {
		System.out.println("updateGreeting called!");
		Greeting updatedGreeting = greetingService.update(greeting);
		if (updatedGreeting == null) {
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
	}

	// end point for deleting greeting resource based on the greeting id passed
	// as path variable!
	@RequestMapping(value = "/api/greetings/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") Long id) {
		System.out.println("deleteGreeting called!");
		greetingService.remove(id);
		/*
		 * if(!deleted){ return new
		 * ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR); }
		 */
		return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
	}
}
