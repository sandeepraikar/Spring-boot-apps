package edu.sraikar.ws;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.sraikar.Application;


/**
 * @author Sandeep
 *
 *This is base test class
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
public abstract class AbstractTest {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
