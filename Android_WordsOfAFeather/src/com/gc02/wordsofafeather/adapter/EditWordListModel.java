package com.gc02.wordsofafeather.adapter;

public class EditWordListModel {

	    private String question;
	    private String answer;
	    private boolean selected;

	    public EditWordListModel(String question,String answer) { 
	       this.question = question;  
	       this.answer = answer;
	       selected = false;    
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

	    public boolean isSelected() {   
	        return selected;    
	        }

	    public void setSelected(boolean selected) {    
	     this.selected = selected;    
	     }
}
