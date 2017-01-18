package com.hybris.tddTrail.controller;

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

import com.hybris.tddTrail.TrailSetupTest;

@ManagedBean
@RestController
public class TDDController {

	@Autowired
	TrailSetupTest trailTest;

	@Value("${trailHome:'Undefined, pass in the absolute path when running: java -jar target/tddTrailSite-0.0.1-SNAPSHOT.war --trailHome=absolutePathToYourTrailHomeFolder  --os=xnix|Windows'}")
	private String trailHome;
	
	@Value("${os:'Undefined, pass in the os when running: java -jar target/tddTrailSite-0.0.1-SNAPSHOT.war  --trailHome=absolutePathToYourTrailHomeFolder --os=xnix|windows'}")
	private String os;

	@CrossOrigin()
	@RequestMapping("/tdd")
	public ResponseEntity<String> greetings(@RequestParam(value = "test", defaultValue = "") String methodName) {
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
	@RequestMapping({"/trailsetup","/trailsetup"})
	public ResponseEntity<String> trailhome() {
		String home = trailHome;
		if (!home.endsWith("/"))
			home = home.concat("/");
		if (os.contains("Undefined"))
			return new ResponseEntity<String>(os+"|"+home, HttpStatus.OK);	
		if (os.toLowerCase().contains("indows"))
			return new ResponseEntity<String>("Windows|"+home, HttpStatus.OK);	
		return new ResponseEntity<String>("xnix|"+home, HttpStatus.OK);
	}
	

}
