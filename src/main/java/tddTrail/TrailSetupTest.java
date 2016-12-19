package tddTrail;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrailSetupTest {

	// YaaSBiteSnippetStart hybrisInstallationIsWhereIExpectIt
	@Test 
	public void testHybrisInstallationIsWhereIExpectIt(){		
		// The folder TddTrail should be sitting alongside the folder hybris-commerce-suite-6.2.0.1
		assertTrue( directoryExists( "../hybris-commerce-suite-6.2.0.1/hybris/bin/platform" ) );
	}
	// YaaSBiteSnippetEnd
	
	// YaaSBiteSnippetStart test2ExtensionAddedOk
	@Test 
	public void test2ExtensionAddedOk(){
		// If you have correctly added an extension there should be some new foldes and files
		assertTrue( directoryExists( "../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/training/gensrc" ) );
	}	
	// YaaSBiteSnippetEnd

		
	//@Test 
	public void test2ExtensionModelOk(){
		assertTrue( fileExists( "../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/training/gensrc/org/training/jalo/GeneratedBand.java" ) );
		assertTrue( fileExists( "../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/training/gensrc/org/training/model/BandModel.java" ) );
	}

	
	private boolean directoryExists( String f){
		System.out.println("CHECKING IF THIS FILE EXISTS "+new File(f).getPath());
		return new File( f ).exists();
	}
	private boolean fileExists( String f){
		return new File( f ).exists();
	}
	
}
