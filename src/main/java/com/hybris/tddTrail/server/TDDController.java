package com.hybris.tddTrail.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hybris.tddTrailTests.TrailSetupTest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@RestController
public class TDDController {

	  @CrossOrigin()
	    @RequestMapping( "/tdd" )
	    public ResponseEntity<String> greetings( @RequestParam( value="test", defaultValue="") String methodName ) {
	    	TrailSetupTest trailTest = new TrailSetupTest();  	
	    	try {
	    	  Method method = trailTest.getClass().getMethod(methodName);
	    	  method.invoke(trailTest);
	    	}catch (InvocationTargetException e ){
	    		return new ResponseEntity<String>( "Fail "+e.getTargetException().getMessage() , HttpStatus.OK);   	
	    	}catch (Error | Exception e ){
	    		return new ResponseEntity<String>( "Fail" , HttpStatus.OK);   	
	    	}  	
	    	return new ResponseEntity<String>( "Success" , HttpStatus.OK); 
	    }
	  
	
}
