package org.elsys.netprog.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.*;
public class QuestionController {
	String question;
	JSONArray answers = new JSONArray();
	
	public QuestionController(String question, List<String> answers, int correctIndex){
		this.question = question;
		int index = 0;
		for(String answer : answers){
			JSONObject currAnswer = new JSONObject();
			currAnswer.put("answer", answer);
			if(index == correctIndex){
				currAnswer.put("correct", "true");
			}	
			this.answers.add(currAnswer);
			index++;
		}
	}
	public JSONObject getQuestion(){
		JSONObject response = new JSONObject();
		response.put("question", this.question);
		response.put("answers", this.answers);
		return response;
	}
	public static List<QuestionController> generateQuestionControllers(){
		List<QuestionController> questions = new ArrayList<QuestionController>();
		questions.add(new QuestionController("Dali Toni e gey?", new ArrayList<String>(Arrays.asList("im", "a", "sik")),1));
		questions.add(new QuestionController("Dali Teri e gey?", new ArrayList<String>(Arrays.asList("fuk", "like", "a")),2));
		questions.add(new QuestionController("Dali Vladi e gey?", new ArrayList<String>(Arrays.asList("dam dam", "estestveno", "ne")),0));
		questions.add(new QuestionController("Dali Peso e gey?", new ArrayList<String>(Arrays.asList("quik", "kvzimodo", "fuk qsno")),1));
		return questions;
	}
}
