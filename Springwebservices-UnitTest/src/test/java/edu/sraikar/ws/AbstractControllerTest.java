package edu.sraikar.ws;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sraikar.ws.web.api.BaseController;

@WebAppConfiguration //This informs spring to create a web application context instead of a standard application context
public abstract class AbstractControllerTest extends AbstractTest {

	protected MockMvc mvc; // This class is from Spring Mock packages which simulates HTTP interactions
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
	
	protected void setUp(){
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build() ;// This makes the mvc object aware of all application components
	}
	
	protected void setUp(BaseController controller){
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	protected String mapToJson(Object obj) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, clazz);

	}
}
