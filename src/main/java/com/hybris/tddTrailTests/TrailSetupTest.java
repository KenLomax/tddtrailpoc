package com.hybris.tddTrailTests;

/**
 * When you add/adjust a test..
 * mvn clean install  (to test for mistakes)
 * git add .
 * git commit -m 'Adjusted tests'
 * git push      (to push this file)
 * Run prettifierTest.java
 * git add .
 * git commit -m 'Adjusted tests'
 * git push      (to push new prettified files)
 * cf push
 * 
 * To idtify new files Use find . -type f -mtime -120s 
 * 
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

	@Test
	// TddTrailSnippetStart testTddTrailPrerequisites
	public void testTddTrailPrerequisites() throws IOException {
		// Confirm that the TddTrail folder is sitting alongside the
		// hybris-commerce-suite-6.2.0.1 folder:
		// <someFolder>
		// |-- hybris-commerce-suite-6.2.0.1
		// |-- hybris and other subfolders
		// |-- tddTrailSite
		assertTrue("TddTrail and Suite need to be in the same folder",
				directoryExists("../hybris-commerce-suite-6.2.0.1/hybris/bin/platform"));
		// This trail assumes you have initialized the suite with the b2b_acc
		// recipe
		assertTrue("You should have installed the recipe b2b_acc",
				fileExistsAndContains("../hybris-commerce-suite-6.2.0.1/hybris/config/localextensions.xml",
						"extension name='b2bacceleratoraddon'"));
	}
	// TddTrailSnippetEnd

	// TddTrailSnippetStart testExtensionCreateddOk
	@Test
	public void testExtensionCreateddOk() {
		// If you have correctly added an extension there should be some new
		// folders and files
		assertTrue("I cannot find files expected for a new extension", directoryExists(
				"../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/tddtrail/src/com/hybris/tddtrail/jalo"));
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
	public void testExtensionModelOk() {
		assertTrue(fileExists(
				"../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/training/gensrc/org/tddtrail/jalo/GeneratedBand.java"));
		assertTrue(fileExists(
				"../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/training/src/com/hybris/tddtrail/jalo/Band.java"));
		assertTrue(fileExists(
				"../hybris-commerce-suite-6.2.0.1/hybris/bin/custom/training/gensrc/org/tddtrail/model/BandModel.java"));
	}
	// TddTrailSnippetEnd

	// TddTrailSnippetStart TestE
	public void testE_ExtensionModelOk() {
		assertTrue(false);
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
