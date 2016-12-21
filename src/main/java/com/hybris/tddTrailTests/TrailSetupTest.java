package com.hybris.tddTrailTests;

/**
 * When you add/adjust a test..
 * mvn clean install  (to test for mistakes)
 * git add .
 * git commit -m 'Adjusted tests'
 * git push      (to push this file)
 * mvn clean install  (to generate new prettified test files)
 * git add .
 * git commit -m 'Adjusted tests'
 * git push      (to push new prettified files)
 * 
 */
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrailSetupTest {

	// TddTrailSnippetStart TestA
	@Test 
	public void testA_HybrisInstallationIsWhereIExpectIt(){		
		// Confirm that the TddTrail folder is sitting alongside the hybris-commerce-suite-6.2.0.1 folder:
		// <someFolder>
		// |-- hybris-commerce-suite-6.2.0.1
		//     |-- README
		//     |-- hybris
		//     |-- ...
		// |-- tddTrailSite
		assertTrue( directoryExists( "../hybris-commerce-suite-6.2.0.1/hybris/bin/platform" ) );
	}
	// TddTrailSnippetEnd
	
	// TddTrailSnippetStart TestB
	@Test 
	public void testB_ExtensionAddedOk(){
		// If you have correctly added an extension there should be some new foldes and files
		assertTrue( directoryExists( "../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/tddtrail/gensrc" ) );
	}	
	// TddTrailSnippetEnd

	// TddTrailSnippetStart TestC
	public void testC_LocalExtensionsContainsTDDTrail() throws IOException{
		assertTrue( fileExistsAndContains( "../hybris-commerce-suite-6.2.0.1/hybris/config/localextensions.xml", "<extension name=\"tddtrail\" />"));
	}
	// TddTrailSnippetEnd
	
	// TddTrailSnippetStart TestD
	public void testD_ExtensionModelOk(){
		assertTrue( fileExists( "../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/training/gensrc/org/tddtrail/jalo/GeneratedBand.java" ) );
		assertTrue( fileExists( "../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/training/gensrc/org/tddtrail/model/BandModel.java" ) );
	}
	// TddTrailSnippetEnd
	
	// TddTrailSnippetStart TestE
	public void testE_ExtensionModelOk(){
		assertTrue(false);
	}
	// TddTrailSnippetEnd
	
	private boolean directoryExists( String f){
		System.out.println("CHECKING IF THIS FILE EXISTS "+new File(f).getPath());
		return new File( f ).exists();
	}
	
	private boolean fileExists( String f){
		return new File( f ).exists();
	}
	
	private boolean fileExistsAndContains( String f, String s) throws IOException{
		 String content = new String(Files.readAllBytes(Paths.get("duke.java")));
		 return content.contains(s);
	}
}
