package org.elsys.netprog.rest;

import java.util.ArrayList;
import java.util.List;

public class GameContainer {
	List<Game> games = new ArrayList<Game>();
	
	public GameContainer(){}
	public boolean addGame(Game g) {
		if(!collisionFound(g.getGameId())) {
			this.games.add(g);
			return true;
		}
		return false;
		
	}
	public Game getGameById(String id) {
		for(Game g : this.games) {
			if(g.getGameId().equals(id)) {
				return g;
			}
		}
		return null;
	}
	public List<Game> getAllGames(){
		return this.games;
	}
	private boolean collisionFound(String id) {
		for(Game g : games) {
			if(g.getGameId().equals(id)) return true;
		}
		return false;
	}
}
