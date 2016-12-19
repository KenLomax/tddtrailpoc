package com.hybris.tddTrailTests;


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.Map;

import org.junit.Test;

import com.hybris.tddTrail.prettifier.CodePrettifierEngine;


public class PrettifierTest {	// Using https://github.com/google/code-prettify	
	private static CodePrettifierEngine pe = new CodePrettifierEngine();	
	   	
	@Test
	public void convertGithubSnippetsToHTML() throws Exception {	
		String targetDir = "src/main/webapp/demotests";
		
		Map<String, String> snippets = pe.getSnippets( 
			new URL("https://raw.githubusercontent.com/KenLomax/tddtrailpoc/master/src/main/java/tddTrail/TrailSetupTest.java?nocache="+System.currentTimeMillis()));
		
		pe.saveSnippets(targetDir, snippets);
		assertTrue(new File( targetDir+"/hybrisInstallationIsWhereIExpectIt.html").exists());		
	
	}

}
