package com.gc02.wordsofafeather.control;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import java.util.Random;

import com.gc02.wordsofafeather.bean.Maintenance;
import com.gc02.wordsofafeather.database.DataHelper;
import com.gc02.wordsofafeather.utils.Constant;

import android.content.Context;






public class PlayAdvancedGameControl extends AbstractPlayGameControl implements Level{

	private int checkPoint=1;
	private int hintPoint; 
	private int score=0;
	private long time=0;
	
	public PlayAdvancedGameControl(Context context) {
		super(context);
	}

	@Override
	public int[] getLevelQuestionNo() {
		int[] levelQuestionNo={2,3,4,5};
		return levelQuestionNo;
	}	
	
	
	public void setCheckPoint(){
		this.checkPoint+=1;
	}
	
	/**
	 * Used to remember which level the user has passed
	 * @return
	 */
	public int getCheckPoint(){
		return this.checkPoint;
	}
	
	/**
	 * 
	 * @return
	 */
	public int readHintPoint(){
		DataHelper dataHelper=DataHelper.getInstance(context);
		this.hintPoint=dataHelper.readMaintenanceFile().getHintPoint();
		return this.hintPoint;
	}
	
	/**
	 * For each use, the hint number reduced 1
	 * @return
	 */
	public int useHintPoint(){
		this.hintPoint=readHintPoint();
		this.hintPoint-=1;
		writeHintPoint();
		return this.hintPoint;
	}
	
	/**
	 * For each round, the user will get a hint point
	 * @return
	 */
	public int addHintPoint(){
		this.hintPoint=readHintPoint();
		this.hintPoint+=1;
		writeHintPoint();
		return this.hintPoint;
	}
	
	/**
	 * Create maintenance object and write it to file
	 */
	public void writeHintPoint(){
		Maintenance maintenance=new Maintenance();
		maintenance.setHintPoint(this.hintPoint);
		DataHelper dataHelper=DataHelper.getInstance(context);
		dataHelper.updateMaintenanceFile(maintenance);
	}
	
	/**
	 * Return the index of the wrong option to be removed
	 * @return
	 */
	public int removeWrongAnswer(){
		Random random=new Random();
		int optionsIndex=random.nextInt(Constant.NUMBER_WRONG_OPTION);
		return optionsIndex;		
	}
	
	public long calculateTime(long totalTime,long pausedTime){
		this.time=totalTime-pausedTime;
		return this.time;
	}		
	
	/**
	 * This method calculates each level's score and add it to the final score
	 * @param levelTime
	 * @param checkPoint
	 * @return
	 */
	public long calculateFinalScore(long levelTime, int checkPoint){
		long levelScore=0;
		levelTime=levelTime/((long)(Math.pow(10, 9)));//from nanoTime to seconds
		if(levelTime<=(5+(checkPoint-1)*5)){
			levelScore=(5+(checkPoint-1)*5)-levelTime+20;
		}
		if(levelTime>(5+(checkPoint-1)*5)&&levelTime<=(10+(checkPoint-1)*5)){
			levelScore=(10+(checkPoint-1)*5)-levelTime+10;
		}
		if(levelTime>(10+(checkPoint-1)*5)&&levelTime<=(15+(checkPoint-1)*5)){
			levelScore=(15+(checkPoint-1)*5)-levelTime;
		}
		if(levelTime>(15+(checkPoint-1)*5)&&levelTime<=(20+(checkPoint-1)*5)){
			levelScore=(20+(checkPoint-1)*5)-levelTime-10;
		}
		if(levelTime>(20+(checkPoint-1)*5)&&levelTime<=(25+(checkPoint-1)*5)){
			levelScore=(20+(checkPoint-1)*5)-levelTime-20;
		}
		if(levelTime>(25+(checkPoint-1)*5)){
			levelScore=-20;
		}
		this.score+=(int)levelScore;
		return levelScore;		
	}
	
	/**
	 * Use to get the final score in each round of game
	 * @return
	 */
	public int getFinalScore(){
		return this.score;
	}
	
	
	
	
}
