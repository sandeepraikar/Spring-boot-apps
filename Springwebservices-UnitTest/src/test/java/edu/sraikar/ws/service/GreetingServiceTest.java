package edu.sraikar.ws.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.sraikar.ws.AbstractTest;
import edu.sraikar.ws.model.Greeting;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
@Transactional // with @Transaction - transactions are rolled back when the
				// method has completed execution
public class GreetingServiceTest extends AbstractTest {

	@Autowired
	GreetingService greetingService;

	@Before // prepare executions for the test, this method will be executed
			// prior to each test method in the class
	public void setUp() {
		greetingService.evictCache();
	}

	@After
	public void tearDown() {
		// clean up after each test method
	}

	@Test
	public void testFindAll() {
		Collection<Greeting> list = greetingService.findAll();
		Assert.assertNotNull("failure - expected not null", list);
		Assert.assertEquals("failure - expected size:2 ", 3, list.size());
	}

	@Test
	public void testFindOne() {

		Long id = new Long(1);
		Greeting entity = greetingService.findOne(id);
		Assert.assertNotNull("failure - expected not null", entity);
		Assert.assertEquals("failure - expected id attribute match", id, entity.getId());

	}

	@Test
	public void testFindOneNotFound() {

		Long id = Long.MAX_VALUE;
		Greeting entity = greetingService.findOne(id);
		Assert.assertNull("failure - expected null", entity);

	}

	@Test
	public void testCreate() {

		Greeting entity = new Greeting();
		entity.setText("test");

		Greeting createdEntity = greetingService.create(entity);
		Assert.assertNotNull("failure - expected not null", createdEntity);
		Assert.assertNotNull("failure - expected id attribute not null", createdEntity.getId());
		Assert.assertEquals("failure - expected text attribute match", "test", createdEntity.getText());

		Collection<Greeting> list = greetingService.findAll();
		Assert.assertEquals("failure - expected size", 3, list.size());

	}

	@Test
	public void testCreateWithId() {

		Exception exception = null;

		Greeting entity = new Greeting();
		entity.setId(Long.MAX_VALUE);
		entity.setText("test");

		try {
			greetingService.create(entity);
		} catch (EntityExistsException e) {
			exception = e;
		}

		Assert.assertNotNull("failure - expected exception", exception);
		Assert.assertTrue("failure - expected EntityExistsException", exception instanceof EntityExistsException);

	}

	@Test
	public void testUpdate() {

		Long id = new Long(1);

		Greeting entity = greetingService.findOne(id);
		Assert.assertNotNull("failure - expected not null", entity);

		String updatedText = entity.getText() + " test";
		entity.setText(updatedText);
		Greeting updatedEntity = greetingService.update(entity);
		Assert.assertNotNull("failure - expected not null", updatedEntity);
		Assert.assertEquals("failure - expected id attribute match", id, updatedEntity.getId());
		Assert.assertEquals("failure - expected text attribute match", updatedText, updatedEntity.getText());

	}

	@Test
	public void testUpdateNotFound() {

		Exception exception = null;

		Greeting entity = new Greeting();
		entity.setId(Long.MAX_VALUE);
		entity.setText("test");

		try {
			greetingService.update(entity);
		} catch (NoResultException e) {
			exception = e;
		}

		Assert.assertNotNull("failure - expected exception", exception);
		Assert.assertTrue("failure - expected NoResultException", exception instanceof NoResultException);

	}

	@Test
	public void testDelete() {

		Long id = new Long(1);
		Greeting entity = greetingService.findOne(id);
		Assert.assertNotNull("failure - expected not null", entity);

		greetingService.remove(id);

		Collection<Greeting> list = greetingService.findAll();
		Assert.assertEquals("failure - expected size", 1, list.size());

		Greeting deletedEntity = greetingService.findOne(id);
		Assert.assertNull("failure - expected null", deletedEntity);

	}
}
