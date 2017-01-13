package com.hybris.tddTrail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
      public static void main(String[] args) {  	  
    	 // Pass in the trailhome directory when invoking: 
    	 // java -jar target/tddTrailSite-0.0.1-SNAPSHOT.war --trailHome=~/trail
        SpringApplication.run(Application.class, args); 
    }
    
}
