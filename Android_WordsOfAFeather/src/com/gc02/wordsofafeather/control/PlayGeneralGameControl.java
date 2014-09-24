package com.gc02.wordsofafeather.control;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import android.content.Context;


public class PlayGeneralGameControl extends AbstractPlayGameControl{
	
	private int count;
	
	public PlayGeneralGameControl(Context context) {
		super(context);
	}

	public int getCount() {
		return count;
	}

	
	/**
	 * count how many words has been guessed in this game round
	 * @return
	 */
	public int countAnsweredQuestionNumber(){
		count+=1;
		return count;
	}



		
		
	
}
