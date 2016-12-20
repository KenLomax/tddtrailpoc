package com.hybris.tddTrailTests;


import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrailSetupTest {

	// TddTrailSnippetStart testA
	@Test 
	public void testA_HybrisInstallationIsWhereIExpectIt(){		
		// Confirm that the TddTrail folder is sitting alongside the hybris-commerce-suite-6.2.0.1 folder
		// If this fails, you need to 
		assertTrue( directoryExists( "../hybris-commerce-suite-6.2.0.1/hybris/bin/platform" ) );
	}
	// TddTrailSnippetEnd
	
	// TddTrailSnippetStart testB
	@Test 
	public void testB_ExtensionAddedOk(){
		// If you have correctly added an extension there should be some new foldes and files
		assertTrue( directoryExists( "../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/tddtrail/gensrc" ) );
	}	
	// TddTrailSnippetEnd
		
	// TddTrailSnippetStart testC
	public void testC_ExtensionModelOk(){
		assertTrue( fileExists( "../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/training/gensrc/org/tddtrail/jalo/GeneratedBand.java" ) );
		assertTrue( fileExists( "../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/training/gensrc/org/tddtrail/model/BandModel.java" ) );
	}
	// TddTrailSnippetEnd
	
	private boolean directoryExists( String f){
		System.out.println("CHECKING IF THIS FILE EXISTS "+new File(f).getPath());
		return new File( f ).exists();
	}
	private boolean fileExists( String f){
		return new File( f ).exists();
	}
	
}
