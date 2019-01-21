package org.elsys.netprog.rest;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.xml.bind.DatatypeConverter;

import java.io.*;
public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		HttpURLConnection connection_post = null;
	    URL url_post = new URL("http://localhost:8080/jersey-rest-homework/guess/");
	    connection_post = (HttpURLConnection) url_post.openConnection();
	    connection_post.connect();
	    byte[] input = new byte[Integer.parseInt(getInfo().get("LENGHT").toString())];
	    String guess = null;
	    while(connection_post.getResponseCode() != 200) {
	    	new Random().nextBytes(input);
	    	guess = new String(input);
	    	System.out.println(guess);
	    	String str = new String(Base64.getEncoder().encode(input));
	    	url_post = new URL("http://localhost:8080/jersey-rest-homework/guess/"+str);
	    	connection_post = (HttpURLConnection) url_post.openConnection();
	    	connection_post.setRequestMethod("POST");
	    	connection_post.connect();
	    }
	    System.out.println("The input was: " + guess);
	}
	public static JSONObject getInfo() throws ParseException, IOException {
		HttpURLConnection connection = null;
		URL url = new URL("http://localhost:8080/jersey-rest-homework/new");
	    connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");
	    int code = connection.getResponseCode();
	    BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    StringBuilder response = new StringBuilder();
	    String line;
	    while ((line = rd.readLine()) != null) {
	      response.append(line);
	      response.append('\r');
	    }
	    JSONParser parser = new JSONParser();
	    return (JSONObject) parser.parse(response.toString());
	}
}
