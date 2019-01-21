package org.elsys.netprog.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.json.simple.*;
@Path("/")
public class RandomController {
	private byte[] hash;
	private int lenght;
	public RandomController() throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update("string".getBytes());
	    this.lenght = 6;
	    this.hash = md.digest();
	}
	@GET
	@Path("/hash")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response getHashAndLenght() throws URISyntaxException, NoSuchAlgorithmException{
	    JSONObject obj = new JSONObject();
		obj.put("hash", this.hash);
	    obj.put("lenght", this.lenght);
		return Response.ok(obj).build();
	}
	@POST
	@Path("/guess/{guess}")
	public Response guess(@PathParam("guess") String guess) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(guess.getBytes());
	    if(Arrays.equals(md.digest(), this.hash)) {
			return Response.ok().build();
	    }
	    return Response.status(406).build();
		
	}
	
}
