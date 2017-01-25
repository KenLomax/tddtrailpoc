package com.hybris.tddTrail;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.ManagedBean;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.hybris.tddTrail.hsqldb.HsqlDBHelper;
import com.hybris.tddTrail.prettifier.helper.HelperToLoginToSuite;


@ManagedBean
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrailSetupTest {

	@Test
	// TddTrailSnippetStart testTddTrailPrerequisites
	public void testTddTrailPrerequisites() throws IOException {
		// Confirm your file system is as follows:
		// <yourTrailHome>
		//   |-- hybris-commerce-suite-6.2.0.1
		//   |-- hybris-commerce-suite-6.2.0.1.zip
		//   |-- TddTrail
		assertTrue("TddTrail and Zip need to be in the same folder",
				fileExists("../hybris-commerce-suite-6.2.0.1.zip") &&
				directoryExists("../hybris-commerce-suite-6.2.0.1") && 
				directoryExists("../TddTrail"));
	}
	// TddTrailSnippetEnd
	
	@Test
	// TddTrailSnippetStart loginAndCheckForTddTrailExtension
	public void loginAndCheckForTddTrailExtension() throws Exception {
		HelperToLoginToSuite suiteAccess = new HelperToLoginToSuite();
		suiteAccess.login();
		String content = suiteAccess.open("https://localhost:9002/platform/extensions");	
		assertTrue("Content should include tddtrail", content.contains("tddtrail"));               
	}
	//TddTrailSnippetEnd
	
	@Test
	// TddTrailSnippetStart testSuiteIsOnline
	public void testSuiteIsOnline() throws Exception {		
		HelperToLoginToSuite suiteAccess = new HelperToLoginToSuite();
		suiteAccess.login();
		String content = suiteAccess.open("https://localhost:9002");	
		assertTrue("Content should include tddtrail", content.contains("hybris Administration Console"));               
	}
	// TddTrailSnippetEnd


	@Test
	// TddTrailSnippetStart testExtensionCreateddOk
	public void testExtensionCreateddOk() {
		// If you have correctly added an extension there should be some new
		// folders and files
		assertTrue("I cannot find files expected for a new extension", directoryExists(
				"../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/tddtrail/src/com/hybris/tddtrail/jalo"));
		assertTrue("I cannot find files expected for a new extension", directoryExists(
				"../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/tddtrail/src/com/hybris/tddtrail/service"));
	}
	// TddTrailSnippetEnd

	@Test
	// TddTrailSnippetStart testCodeGeneratedOk
	public void testCodeGeneratedOk() throws IOException {	
		// If you have correctly added an extension there should be some new folders and files
		assertTrue("You should have included tddtrail in localextensions.xml", fileExistsAndContains(
				"../hybris-commerce-suite-6.2.0.1/hybris/config/localextensions.xml", "tddtrail"));
		assertTrue("Running ant should have generated some sources for tddtrail",
				directoryExists("../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/tddtrail/gensrc"));
	}
	// TddTrailSnippetEnd

	private boolean fileContains( String file, String... setOfStrings) throws IOException{
		 String content = new String(
				 Files.readAllBytes(				 
				 Paths.get(file)));	   		
		for (String s : setOfStrings){
	        if (!content.contains(s))
	        	return false;
		}
		return true;
	}
	
	@Test
	// TddTrailSnippetStart testExtensionModelOk
	public void testExtensionModelOk() throws ClassNotFoundException, IOException {	
		assertTrue( "ProductModel has been extended to support Hashtag and Band", 
				fileContains( "../hybris-commerce-suite-6.2.0.1/hybris/bin/platform/bootstrap/gensrc/de/hybris/platform/core/model/product/ProductModel.java",
				"getHashtag", "getBand",
				"setHashtag", "setBand") );
		
		assertTrue( "A new BandModel supports Code, Name, History, Tours", 
				fileContains( "../hybris-commerce-suite-6.2.0.1/hybris/bin/platform/bootstrap/gensrc/com/hybris/tddtrail/model/BandModel.java",
						"getName","getHistory","getCode", "getTours",
						"setName","setHistory","setCode", "setTours") );
		
		assertTrue( "A new ConcertModel extends VariantProductModel and supports Venue and Date", 
				fileContains( "../hybris-commerce-suite-6.2.0.1/hybris/bin/platform/bootstrap/gensrc/com/hybris/tddtrail/model/ConcertModel.java",
						"ConcertModel extends VariantProductModel",
						"getVenue","getDate",
						"setVenue","setDate") );
		
		assertTrue( "The new GeneratedBand extends GenericItem and supports Code, Name,  History, Tours", 
				fileContains( "../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/tddtrail/gensrc/com/hybris/tddtrail/jalo/GeneratedBand.java",
						"GeneratedBand extends GenericItem",
						"getName","getHistory","getCode", "getTours",
						"setName","setHistory","setCode", "setTours") );
		
		assertTrue( "The new GeneratedConcert extends VariantProduct and supports Venue, Date", 
				fileContains( "../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/tddtrail/gensrc/com/hybris/tddtrail/jalo/GeneratedConcert.java",
						"GeneratedConcert extends VariantProduct",
						"getVenue","getDate",
						"setVenue","setDate") );
	}
	// TddTrailSnippetEnd


	@Test
	// TddTrailSnippetStart testDatabaseSetup
    public void testDatabaseSetup() throws Exception {
    	HsqlDBHelper hsqldb = new HsqlDBHelper();
    	// Note test will fail if the suite is running on this DB at the same time.
    	try{        	
    		String res = hsqldb.select(  "SELECT COUNT (*) FROM BANDS");
    		assertTrue("Could not find the table BANDS", res.equals("0"));  	
    	}
    	catch(Exception e){
    		fail("HsqlDBTest failed: "+e.getMessage());
    	}
    	finally {
    		hsqldb.shutdown( );     
    	}
    }
	// TddTrailSnippetEnd
	
	private boolean directoryExists(String f) {
		System.out.println("CHECKING IF THIS FILE EXISTS " + new File(f).getPath());
		return new File(f).exists();
	}
	
	private boolean fileExists(String f) {
		return new File(f).exists();	
	}

	private boolean fileExistsAndContains(String f, String s)  {
		String content;
		try {
			content = new String(Files.readAllBytes(Paths.get(f)));
		} catch (IOException e) {
			return false;
		}
		return content.contains(s);
	}
	
	
}