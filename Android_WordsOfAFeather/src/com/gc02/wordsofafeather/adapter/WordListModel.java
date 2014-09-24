package com.gc02.wordsofafeather.adapter;

public class WordListModel {
    private String question;
    private String answer;

    public WordListModel(String question,String answer) { 
       this.question = question;  
       this.answer = answer;  
       }
    public String getQuestion() { 
        return question;    	        
        }

    public void setQuestion(String word) { 
        this.question = word; 
        }
    
    public String getAnswer() { 
        return answer;    	        
        }

    public void setAnswer(String word) { 
        this.answer = word; 
        }
}
