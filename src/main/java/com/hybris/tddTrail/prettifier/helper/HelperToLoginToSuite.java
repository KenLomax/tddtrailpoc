package com.hybris.tddTrail.prettifier.helper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@ManagedBean
public class HelperToLoginToSuite { 
	// Adapted from https://www.mkyong.com/java/how-to-automate-login-a-website-java-example/

	private String ylogin = "https://localhost:9002/login.jsp";
	private String ylogin2 = "https://localhost:9002/j_spring_security_check";
	private List<String> cookies;
	private HttpsURLConnection conn;
	private final String USER_AGENT = "Mozilla/5.0";

	public void login() throws Exception {
		allowHttps();
		// make sure cookies are turned on
		CookieHandler.setDefault(new CookieManager());
		// 1. Send a "GET" request, so that you can extract the form's data.
		String page = getPageContent(ylogin);
		String postParams = adjustFormParams(page, "admin", "nimda");

		// 2. Construct above post's content and then send a POST request for
		// authentication
		sendPost(ylogin2, postParams);
	}

	public String open(String hac) throws Exception {
		// 3. success then go to gmail.
		String content = getPageContent(hac);
		return content;
	}

	private void sendPost(String url, String postParams) throws Exception {
		URL obj = new URL(url);
		conn = (HttpsURLConnection) obj.openConnection();

		// Acts like a browser
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Host", "accounts.google.com");
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		for (String cookie : this.cookies) {
			conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
		}
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Referer", "https://accounts.google.com/ServiceLoginAuth");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

		conn.setDoOutput(true);
		conn.setDoInput(true);

		// Send post request
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(postParams);
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
	}

	private String getPageContent(String url) throws Exception {
		URL obj = new URL(url);
		conn = (HttpsURLConnection) obj.openConnection();
		conn.setRequestMethod("GET");
		conn.setUseCaches(false);

		// act like a browser
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		if (cookies != null) {
			for (String cookie : this.cookies) {
				conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// Get the response cookies
		setCookies(conn.getHeaderFields().get("Set-Cookie"));
		return response.toString();
	}

	public String adjustFormParams(String html, String username, String password) throws UnsupportedEncodingException {

		Document doc = Jsoup.parse(html);

		Element loginform = doc.getElementById("logincontrols");
		Elements inputElements = loginform.getElementsByTag("input");
		List<String> paramList = new ArrayList<String>();
		for (Element inputElement : inputElements) {
			String key = inputElement.attr("name");
			String value = inputElement.attr("value");

			if (key.equals("j_username"))
				value = username;
			else if (key.equals("j_password"))
				value = password;
			paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
		}

		// build parameters list
		StringBuilder result = new StringBuilder();
		for (String param : paramList) {
			if (result.length() == 0) {
				result.append(param);
			} else {
				result.append("&" + param);
			}
		}
		return result.toString();
	}

	public List<String> getCookies() {
		return cookies;
	}

	public void setCookies(List<String> cookies) {
		this.cookies = cookies;
	}

	private void allowHttps() throws KeyManagementException, NoSuchAlgorithmException {
		// Create a context that doesn't check certificates.
		SSLContext ssl_ctx = SSLContext.getInstance("TLS");
		TrustManager[] trust_mgr = get_trust_mgr();
		ssl_ctx.init(null, // key manager
				trust_mgr, // trust manager
				new SecureRandom()); // random number generator
		HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
	}

	private TrustManager[] get_trust_mgr() {
		TrustManager[] certs = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String t) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String t) {
			}
		} };
		return certs;
	}
}
