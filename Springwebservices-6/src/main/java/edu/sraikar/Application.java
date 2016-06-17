package edu.sraikar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot application
 *
 */

@SpringBootApplication //@configuration ,@enableautoconfiguration  and  @component scan
@EnableTransactionManagement //This enables spring to enable transaction management support
@EnableCaching //This enables spring to provide cache management support and search the code base for methods annotated with cache management metadata
public class Application 
{
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(Application.class, args);
    }
    
    //This is inbuilt cachemanager and not good for production environments
    @Bean
    public CacheManager cacheManager(){
    	ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("greetings"); //accepts an array of strings which signifies the individual named caches
    	return cacheManager;
    }
}
