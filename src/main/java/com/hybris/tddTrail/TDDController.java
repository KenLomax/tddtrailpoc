package com.hybris.tddTrail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ManagedBean
@RestController
public class TDDController {

	@Autowired
	TrailSetupTest trailTest;

	@Value("${trailHome:'Undefined, pass in when with exucting war file: java -jar target/tddTrailSite-0.0.1-SNAPSHOT.war --trailHome=YOURTRAILHOMEDIR'}")
	private String trailHome;

	@CrossOrigin()
	@RequestMapping("/tdd")
	public ResponseEntity<String> greetings(@RequestParam(value = "test", defaultValue = "") String methodName) {
		// TrailSetupTest trailTest = new TrailSetupTest();
		try {
			Method method = trailTest.getClass().getMethod(methodName);
			method.invoke(trailTest);
		} catch (InvocationTargetException e) {
			return new ResponseEntity<String>("Fail " + e.getTargetException().getMessage(), HttpStatus.OK);
		} catch (Error | Exception e) {
			return new ResponseEntity<String>("Fail", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}

	@CrossOrigin()
	@RequestMapping({"/trailhome","/trailHome"})
	public ResponseEntity<String> trailhome() {
		return new ResponseEntity<String>(trailHome, HttpStatus.OK);
	}

}
