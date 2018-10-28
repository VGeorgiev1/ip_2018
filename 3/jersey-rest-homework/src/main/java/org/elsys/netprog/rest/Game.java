package org.elsys.netprog.rest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

public class Game {
	private char[] gameId;
	private int turnsCount;
	private boolean success;
	private int secret;
	private static final String symbols = "ABCDEFGJKLMNPRSTUVWXYZ0123456789"; 
	private static int idLenght = 16;
	private final Random random = new SecureRandom();
	public Game(){
		this.turnsCount = 0;
		generateSecret();
		System.out.println(this.secret);
		this.success = Boolean.FALSE;
		this.gameId = new char[idLenght];
		generateNewId();
	}
	public void generateNewId() {
		for (int idx = 0; idx < gameId.length; ++idx) 
			gameId[idx] = symbols.charAt(random.nextInt(symbols.length()));
	}
	private void generateSecret() {
		List<Integer> digits = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
		Collections.shuffle(digits);
		while(digits.get(0) == 0) {
			Collections.shuffle(digits);
		}
		this.secret = 0;
		for(int i=0;i<3;i++) {
			this.secret*=10;
			this.secret += digits.get(i)*10;
		}
		this.secret+=digits.get(3);
	}
	public String getGameId() {
		return new String(this.gameId);
	}
	public int getTurnsCount() {
		return this.turnsCount;
	}
	public boolean isCompleted() {
		return this.success;
	}
	public Object getSecret() {
		if(this.isCompleted()) {
			return Integer.toString(this.secret);
		}
		return "****";
	}
	public List<Integer> makeGuess(String guess){
		int guessCpy;
		try {
			guessCpy = Integer.parseInt(guess);
		}catch(NumberFormatException e) {
			return null;
		}
		if(guess.length() != 4 || !hasAllUniqueChars(guess) || guess.charAt(0) == '0') 
		{
			return null;
		}
		if(this.secret == guessCpy) {
			this.success = true;
		}
		int secretCpy = this.secret;
		int bulls = 0;
		int cows = 0;
		while(guessCpy != 0) {
			 if(guessCpy % 10 == secretCpy % 10) { 
				 bulls++;
			 }
			 guessCpy/=10;
			 secretCpy/=10;
		}
		char guessArr[] = guess.toCharArray();
		char secretArr[] = Integer.toString(this.secret).toCharArray();
		for(int i=0;i < guessArr.length;i++) {
			for(int b=0;b< secretArr.length;b++) {
				if(guessArr[i] == secretArr[b] && i!=b) {
					cows++;
				}
			}
		}
		this.turnsCount++;
		return new ArrayList<Integer>(Arrays.asList(bulls,cows));
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
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		jo.put("gameId", getGameId());
		jo.put("turnsCount", getTurnsCount());
		jo.put("secret", getSecret());
		jo.put("success", isCompleted());
		return jo;
	}
}
