package com.gc02.wordsofafeather.bean;

public class Word {

	private String word; //a word in the Word List
	private String answer; //the related answer of the word
	
	public Word() {
		super();
	}
	
	public Word(String word, String answer) {
		super();
		this.word=word;
		this.answer=answer;
	}

	
	public String getWord(){
		return this.word;
	}
	
	public void setWord(String word){
		this.word=word;
	}
	
	public String getAnswer(){
		return this.answer;
	}
	
	public void setAnswer(String answer){
		this.answer=answer;
	}

}
