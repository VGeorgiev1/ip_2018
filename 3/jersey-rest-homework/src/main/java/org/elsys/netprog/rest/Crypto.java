package org.elsys.netprog.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.elsys.netprog.rest.*;

@Path("/")
public class Crypto {
	public static int lenght = 2;
	public static byte[] input = new byte[lenght];
	public static String hash;
	@POST
	@Path("/guess/{guess}")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response guess(@PathParam("guess") String guessStr) throws NoSuchAlgorithmException{
		String decoded = new String(Base64.getDecoder().decode(guessStr.getBytes()));
		
		if(hash == null) {
			return Response.ok("Generate a hash first!").build();
		}
		if(hash.equals(generateHash(decoded.getBytes()))) {
			return Response.ok().build();
		}
		return Response.status(406).build();
	}
	private String generateHash(byte[] in) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(in);
	    return DatatypeConverter
	      .printHexBinary(md.digest()).toUpperCase();
	}
	@GET
	@Path("/new")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response generateHash() throws NoSuchAlgorithmException {
		new Random().nextBytes(input);
		hash = generateHash(input);
		JSONObject res = new JSONObject();
		res.put("HASH", hash);
		res.put("LENGHT", lenght);
		System.out.println(new String(input));
		return Response.ok(res).build();
	}
}
