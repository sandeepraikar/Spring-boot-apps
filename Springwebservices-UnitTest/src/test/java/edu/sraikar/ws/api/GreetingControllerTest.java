package edu.sraikar.ws.api;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import edu.sraikar.ws.AbstractControllerTest;
import edu.sraikar.ws.model.Greeting;
import edu.sraikar.ws.service.GreetingService;
import junit.framework.Assert;

@Transactional
public class GreetingControllerTest extends AbstractControllerTest {

	@Autowired
	GreetingService greetingService;
	
	@Before
	public void setUp(){
		super.setUp();
		greetingService.evictCache();
	}
	
	@Test
	public void testGreeting() throws Exception{
		String uri ="/api/greetings";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected HTTP status 200 ",200,status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length()>0);
	}
	
    @Test
    public void testGetGreeting() throws Exception {

        String uri = "/api/greetings/{id}";
        Long id = new Long(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

    }

    @Test
    public void testGetGreetingNotFound() throws Exception {

        String uri = "/api/greetings/{id}";
        Long id = Long.MAX_VALUE;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

    }

    @Test
    public void testCreateGreeting() throws Exception {

        String uri = "/api/greetings";
        Greeting greeting = new Greeting();
        greeting.setText("test");
        String inputJson = super.mapToJson(greeting);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Greeting createdGreeting = super.mapFromJson(content, Greeting.class);

        Assert.assertNotNull("failure - expected greeting not null",
                createdGreeting);
        Assert.assertNotNull("failure - expected greeting.id not null",
                createdGreeting.getId());
        Assert.assertEquals("failure - expected greeting.text match", "test",
                createdGreeting.getText());

    }

    @Test
    public void testUpdateGreeting() throws Exception {

        String uri = "/api/greetings/{id}";
        Long id = new Long(1);
        Greeting greeting = greetingService.findOne(id);
        String updatedText = greeting.getText() + " test";
        greeting.setText(updatedText);
        String inputJson = super.mapToJson(greeting);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.put(uri, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Greeting updatedGreeting = super.mapFromJson(content, Greeting.class);

        Assert.assertNotNull("failure - expected greeting not null",
                updatedGreeting);
        Assert.assertEquals("failure - expected greeting.id unchanged",
                greeting.getId(), updatedGreeting.getId());
        Assert.assertEquals("failure - expected updated greeting text match",
                updatedText, updatedGreeting.getText());

    }

    @Test
    public void testDeleteGreeting() throws Exception {

        String uri = "/api/greetings/{id}";
        Long id = new Long(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 204", 204, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

        Greeting deletedGreeting = greetingService.findOne(id);

        Assert.assertNull("failure - expected greeting to be null",
                deletedGreeting);

}
}
