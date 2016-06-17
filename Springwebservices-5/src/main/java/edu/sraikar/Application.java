package edu.sraikar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot application
 *
 */

@SpringBootApplication //@configuration ,@enableautoconfiguration  and  @component scan
@EnableTransactionManagement //This enables spring to enable transaction management support
public class Application 
{
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(Application.class, args);
    }
}
