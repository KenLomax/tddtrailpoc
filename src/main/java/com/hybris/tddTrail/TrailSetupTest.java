package com.hybris.tddTrail;

import static org.junit.Assert.assertTrue;

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


	// TddTrailSnippetStart testExtensionCreateddOk
	@Test
	public void testExtensionCreateddOk() {
		// If you have correctly added an extension there should be some new
		// folders and files
		assertTrue("I cannot find files expected for a new extension", directoryExists(
				"../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/tddtrail/src/com/hybris/tddtrail/jalo"));
		assertTrue("I cannot find files expected for a new extension", directoryExists(
				"../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/tddtrail/src/com/hybris/tddtrail/service"));
	}
	// TddTrailSnippetEnd

	// TddTrailSnippetStart testCodeGeneratedOk
	@Test
	public void testCodeGeneratedOk() throws IOException {	
		// If you have correctly added an extension there should be some new
		// foldes and files
		assertTrue("You should have included tddtrail in localextensions.xml", fileExistsAndContains(
				"../hybris-commerce-suite-6.2.0.1/hybris/config/localextensions.xml", "tddtrail"));
		assertTrue("Running ant should have generated some sources for tddtrail",
				directoryExists("../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/tddtrail/gensrc"));
	}
	// TddTrailSnippetEnd

	// TddTrailSnippetStart testExtensionModelOk
	@Test
	public void testExtensionModelOk() {
		assertTrue(
				"File hybris-commerce-suite-6.2.0.1/hybris/bin/custom/training/gensrc/org/tddtrail/jalo/GeneratedBand.java does not exist. Have you done ABC?", 
				fileExists(
						"../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/tddtrail/gensrc/com/hybris/tddtrail/jalo/GeneratedBand.java"));
		assertTrue(fileExists(
				"../hybris-commerce-suite-6.2.0.1/hybris/bin/platform/bootstrap/gensrc/com/hybris/tddtrail/model/BandModel.java"));
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
	
	private String getWebsiteContent(URLConnection con) throws IOException{
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader( new InputStreamReader(con.getInputStream()));
		String input;
		while ((input = br.readLine()) != null){
			sb.append(input);
		}
		br.close();	
		return sb.toString();
	}
	
}