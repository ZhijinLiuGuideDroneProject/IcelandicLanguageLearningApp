package com.gc02.wordsofafeather.control;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import java.util.ArrayList;
import java.util.Random;

import com.gc02.wordsofafeather.bean.Question;
import com.gc02.wordsofafeather.database.DataHelper;
import com.gc02.wordsofafeather.utils.Constant;

import android.content.Context;


public abstract class AbstractPlayGameControl {
	
	protected Context context;
	protected ArrayList<String> questionWord=new ArrayList<String>();
	
	public AbstractPlayGameControl(Context context){
		this.context=context;
	}
	
	/**
	 * Get a valid question
	 * @param isSpecificDatabase false: any file true:specifc one-equals to level
	 * @param level
	 * @param questionWord //questionWord does not show repeatedly!
	 * @return
	 */
	public Question getValidQuestion(boolean isSpecificDatabase,int level,ArrayList<String> questionWord){
		Question question=new Question();
		boolean get=true;
		while(get==true){
			question=getQuestion(isSpecificDatabase,level);
			if(questionWord.size()!=0){
				for(String q:questionWord){
					if(!q.equals(question.getQuestionWord())){
						get=false;
					}
					else{
						get=true;
						break;
					}
				}
			}
			else{
				get=false;
			}			
		}
		this.questionWord.add(question.getQuestionWord());
		if(this.questionWord.size()==Constant.REPEATED_QUESTION_RANGE){
			this.questionWord.remove(0);
		}
		return question;
	}
	
	/**
	 * if difficulty==false: treat the all databases same 
	 * else: for each level, get the question from specific database
	 * @param isSpecificDatabase
	 * @param level
	 * @return
	 */
	public Question getQuestion(boolean isSpecificDatabase,int level){
		Question question=new Question();
		DataHelper dataHelper=DataHelper.getInstance(context);
		if(isSpecificDatabase==false){
			question=dataHelper.getQuestion(0);
		}
		else{
			question=dataHelper.getQuestion(level);
		}

		return question;
	}
	
	
	public ArrayList<String> getQuestionWord(){
		return this.questionWord;
	}
	
	
	/**
	 * Change the order of the question's options
	 * @param q
	 * @return
	 */
	public ArrayList<String> changeOptionsOrder(Question q) {
		ArrayList<Integer> randomNumber=new ArrayList<Integer>();
		for(int i=0;i<4;i++){
			Random random=new Random();
			int queRandom=random.nextInt(4);
			if(!randomNumber.contains(queRandom)){
				randomNumber.add(queRandom);
			}
			else{
				i--;
			}
		}
		
		ArrayList<String> options=new ArrayList<String>();
		for(int i=0;i<4;i++){
			if(randomNumber.get(i)!=3){
				options.add(q.getOptions().get(randomNumber.get(i)));
			}
			else{
				options.add(q.getAnswer());
			}
		}

		return options;
	}
	


	

	
	

	
	
}
