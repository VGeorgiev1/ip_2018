package org.elsys.netprog.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.elsys.netprog.rest.*;

@Path("game")
public class GameController {
	public static GameContainer gc= new GameContainer();
	@POST
	@Path("/startGame")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response startGame() throws URISyntaxException{
		Game game = new Game();
		boolean added = false;
		while(!added) {
			added = gc.addGame(game);
			if(!added) {
				game.generateNewId();
			}
		}
		JSONObject g = new JSONObject();
		return Response.created(new URI("/games")).entity(game.getGameId()).build();
	}
	private boolean hasAllUniqueChars(String guess) {
	    Set<Character> set = new HashSet<Character>();
	    char[] characters = guess.toCharArray();
	    for (Character c : characters) {
	    	if (!set.add(c)) {
	            return false;
	         }
	    }
	    return true;
	}

	@PUT
	@Path("/guess/{id}/{guess}")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response guess(@PathParam("id") String gameId, @PathParam("guess") String guess) throws Exception{
		Game g = gc.getGameById(gameId);
		if(g == null) {
			return Response.status(404).build();
		}
		int numericGuess = 0;
		List<Integer> bullsAndCows = g.makeGuess(guess);
		if(bullsAndCows == null) {
			return Response.status(400).build();
		}
		
		JSONObject response = new JSONObject();
		
		JSONObject game = g.toJSON();
		game.put("bullsNumber", bullsAndCows.get(0));
		game.put("cowsNumber",bullsAndCows.get(1));
		return Response.ok().entity(game).build();
	}
	
	@GET
	@Path("/games")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response getGames() {
		JSONArray response = new JSONArray();
		for(Game g :  gc.getAllGames()) {
			response.add(g.toJSON());
		}
	
		return Response.status(Response.Status.OK).entity(response).build();
	}
}
