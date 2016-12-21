package com.hybris.tddTrail.server;

import java.io.File;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hybris.tddTrailTests.TrailSetupTest;
import java.lang.reflect.Method;


@RestController
public class TDDController {

    @CrossOrigin()
    @RequestMapping( "/tdd" )
    public ResponseEntity<String> greetings( @RequestParam( value="test", defaultValue="") String test) {
    	TrailSetupTest trailTest = new TrailSetupTest();  	
    	try {
    	  Method method = trailTest.getClass().getMethod("testA_HybrisInstallationIsWhereIExpectIt");
    	  method.invoke(trailTest);
    	}catch (Error e){
        	return new ResponseEntity<String>( "Fail" , HttpStatus.OK);   		
    	}catch (Exception e) {
    		return new ResponseEntity<String>( "Fail" , HttpStatus.OK);   	
    	}  	
    	return new ResponseEntity<String>( "Success" , HttpStatus.OK); 
    }

	private boolean directoryExists( String f){
		return new File( f ).exists();
	}
	private boolean fileExists( String f){
		return new File( f ).exists();
	}
	
}
