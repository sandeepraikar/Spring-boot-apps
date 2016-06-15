package edu.sraikar.springwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application
 *
 */

@SpringBootApplication //@configuration ,@enableautoconfiguration  and  @component scan
public class Application 
{
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(Application.class, args);
    }
}
