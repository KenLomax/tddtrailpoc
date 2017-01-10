package com.hybris.tddTrailTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrailSetupTest {

	 private TrustManager[ ] get_trust_mgr() {
	     TrustManager[ ] certs = new TrustManager[ ] {
	        new X509TrustManager() {
	        	@Override
	        	public X509Certificate[ ] getAcceptedIssuers() { return null; }
	        	@Override
	        	public void checkClientTrusted(X509Certificate[ ] certs, String t) { }
	        	@Override
	        	public void checkServerTrusted(X509Certificate[ ] certs, String t) { }
	         }
	      };
	      return certs;
	  }

	@Test
	// TddTrailSnippetStart testCanReachStoreFrontAndBackend
	public void testCanReachStoreFrontAndBackend() throws Exception {
	    allowHttps();
	    URL url = new URL("https://localhost:9002/yb2bacceleratorstorefront/?site=powertools&clear=true");
	    URLConnection con = url.openConnection();
	    String content = getWebsiteContent(con);
        assertTrue("Did not find storefront", content.contains("Our Bestselling Products"));    
        
        url = new URL("http://localhost:9001");
        con = url.openConnection();
	    content = getWebsiteContent(con);
        assertEquals("Expected redirect", HttpURLConnection.HTTP_MOVED_TEMP,  ((HttpURLConnection)con).getResponseCode());               
	}
	// TddTrailSnippetEnd
	
	private void allowHttps() throws KeyManagementException, NoSuchAlgorithmException{
		// Create a context that doesn't check certificates.
        SSLContext ssl_ctx = SSLContext.getInstance("TLS");
        TrustManager[ ] trust_mgr = get_trust_mgr();
        ssl_ctx.init(null,                // key manager
                     trust_mgr,           // trust manager
                     new SecureRandom()); // random number generator
        HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());  	
	    HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);
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
				"File hybris-commerce-suite-6.2.0.1/hybris/bin/custom/training/gensrc/org/tddtrail/jalo/GeneratedBand.java does not exist. Have you done ABC?", fileExists(
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

}
