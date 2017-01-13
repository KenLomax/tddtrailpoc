package com.hybris.tddTrail.prettifier;


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.junit.Test;


public class PrettifierTest {	// Using https://github.com/google/code-prettify	
	private static CodePrettifierEngine pe = new CodePrettifierEngine();	
	   	
	@Test
	public void convertGithubSnippetsToHTML() throws Exception {	
		String targetDir = "src/main/webapp/demotests";
		
		Map<String, String> snippets = pe.getSnippets( 
			new File ("src/main/java/com/hybris/tddTrail/TrailSetupTest.java")
//			new URL("https://raw.githubusercontent.com/KenLomax/tddtrailpoc/master/src/main/java/com/hybris/tddTrailTests/TrailSetupTest.java?nocache="+System.currentTimeMillis())
			);
		
		pe.saveSnippets(targetDir, snippets);
		assertTrue(new File( targetDir+"/testTddTrailPrerequisites.html").exists());		
	
	}

}
