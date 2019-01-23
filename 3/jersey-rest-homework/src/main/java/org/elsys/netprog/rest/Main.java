package org.elsys.netprog.rest;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.function.BinaryOperator;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.xml.bind.DatatypeConverter;

import java.io.*;
public class Main {

	public static void main(String[] args) throws IOException, ParseException, NoSuchAlgorithmException {
	    List<Double> times = new ArrayList<Double>();
	    List<Integer> iterations_l = new ArrayList<Integer>();
	    JSONObject res = new JSONObject();
	    for(int i=0;i<10;i++) {
			res = getInfo();
			byte[] input = new byte[Integer.parseInt(res.get("LENGHT").toString())];
		    String hash = (String) res.get("HASH");
			System.out.println("The income hash is: " + hash);
			int iterations = 0;
		    String guessHash = "";
		    long start = System.nanoTime();
		    while(!hash.equals(guessHash)) {
		    	new Random().nextBytes(input);
		    	guessHash = generateHash(input);
		    	iterations++;
		    }
		    long end = System.nanoTime();
		    String req = new String(Base64.getEncoder().encode(input));
		    String encGuess = new String(Base64.getEncoder().encode(guessHash.getBytes()));
			HttpURLConnection connection_post = null;
		    URL url_post = new URL("http://localhost:8080/jersey-rest-homework/guess/"+ req + "/hash/" + encGuess );
	    	connection_post = (HttpURLConnection) url_post.openConnection();
	    	connection_post.setRequestMethod("POST");
	    	connection_post.connect();
	    	int code = connection_post.getResponseCode();
		    if(code == 200) {
		    	System.out.println("The secret was:" + new String(input));;
		    	times.add(Math.floor(((end - start)/1000000000.0) * 100) / 100);
		    	iterations_l.add(iterations);
		    	System.out.println("It took: " + iterations + " hashes and "+ times.get(i)  +" seconds \n");
		    }else {
		    	System.out.println(code);
		    }
	    }
		double avg = times.stream().mapToDouble(x->x).average().getAsDouble();
		double sum_time = times.stream().mapToDouble(x->x).sum();
		double sum_iterations = iterations_l.stream().mapToInt(x->x).sum();
		
		System.out.println("Average time for " + res.get("LENGHT").toString() + " byte long input was " + avg + " seconds");
		System.out.println("Average h/sec : " + sum_iterations / sum_time);
	}
	private static String generateHash(byte[] in) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(in);
	    return DatatypeConverter
	      .printHexBinary(md.digest()).toUpperCase();
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
