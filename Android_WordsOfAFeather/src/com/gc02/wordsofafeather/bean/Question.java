package com.gc02.wordsofafeather.bean;

import java.util.ArrayList;


public class Question {

	private String questionWord; //the word to be guessed
	private String answer; //the right answer of the word
	private ArrayList<String> options; //the wrong answers of the word
	
	public Question() {
		super();
	}
	
	public Question(String questionWord, String answer, ArrayList<String> options) {
		super();
		this.questionWord=questionWord;
		this.answer=answer;
		this.options=options;
	}
	
	
	public String getQuestionWord(){
		return this.questionWord;
	}
	
	public void setQuestionWord(String questionWord){
		this.questionWord=questionWord;
	}
	
	public String getAnswer(){
		return this.answer;
	}
	
	public void setAnswer(String answer){
		this.answer=answer;
	}
	
	public ArrayList<String> getOptions(){
		return this.options;
	}
	
	public void setOptions(ArrayList<String> options){
		this.options=options;
	}
	

}
