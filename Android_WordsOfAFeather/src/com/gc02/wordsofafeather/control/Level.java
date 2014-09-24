package com.gc02.wordsofafeather.control;

/**
 * For all modes who have levels 
 *
 */
public interface Level {

	/**
	 * This method used to get the number of question in each level, 
	 * if int[] levelQuestionNo=0, there's no limitation for the number of questions
	 * @return
	 */
	public abstract int[] getLevelQuestionNo();

}
