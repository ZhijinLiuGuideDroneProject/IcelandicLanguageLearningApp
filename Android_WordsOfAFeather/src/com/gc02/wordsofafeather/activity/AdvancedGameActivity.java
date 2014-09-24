package com.gc02.wordsofafeather.activity;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import com.gc02.wordsofafeather.bean.Question;
import com.gc02.wordsofafeather.control.PlayAdvancedGameControl;
import com.gc02.wordsofafeather.control.RecordControl;
import com.gc02.wordsofafeather.control.SetGameControl;
import com.gc02.wordsofafeather.utils.Constant;


import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AdvancedGameActivity extends Activity {

    private ArrayList<String> questionWord;
    private Button answerA;
    private Button answerB;
    private Button answerC;
    private Button answerD;
    private Button useHint;
    private Button pauseButton;
    private Button continueGame;
    private Button restartGame;
    private Button exitGame;
    private TextView hint;
    private PlayAdvancedGameControl playAdvancedGameControl;
    private int[] levelQuestionNo;
    private int questionNumInStage=1;
    private int checkpoint;
    private long startTime;
    private long endTime;
    private long pauseStartTime;
    private long pauseEndTime;
    private long animationTime=(long)(2900*Math.pow(10, 6));
    private long pauseTime=0;
    private double totalTime;
    private long score;
	private SetGameControl setGameControl=SetGameControl.getInstance(this);
	private SoundPool soundPool;
	private int ding;


    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advanced_game);			
		stageAnimation("Let's go!");	
		
		soundPool=new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		ding=soundPool.load(this, R.raw.ding, 1);

		startTime=System.nanoTime();

		//judge if the maintenance file exists
		File dir = this.getFilesDir();
		File file = new File(dir, "maintenance.dat");
		if(file.exists()==false){
			setGameControl.firstSetMaintenance();
		}
		
		pauseButton=(Button)findViewById(R.id.btnpause);
		pauseButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {	
				pauseDialog();
				pauseStartTime=System.nanoTime();;
			}
			
		});		
		
		playAdvancedGameControl=new PlayAdvancedGameControl(this);
		startGame(playAdvancedGameControl);	    

	}


	private void startGame(PlayAdvancedGameControl playAdvancedGameControl2) {
		hint=(TextView)findViewById(R.id.hintPoint);
		int hintPoint=playAdvancedGameControl.readHintPoint();		
		hint.setText(Integer.toString(hintPoint));
		
		levelQuestionNo=playAdvancedGameControl.getLevelQuestionNo();
		checkpoint=playAdvancedGameControl.getCheckPoint();
		playQuestion(playAdvancedGameControl);//for each round (one question) of play
		
	}


	/**
	 * Start another activity which used before each stage started
	 */
	private void stageAnimation(String value) {
		Intent stageIntent=new Intent(this,StageActivityMale.class);
		stageIntent.putExtra("com.gc02.wordsofafeather.activity.stage", value);
		startActivity(stageIntent);
		
	}


	private void playQuestion(final PlayAdvancedGameControl playAdvancedGameControl) {
		
		Question question=getQuestion(playAdvancedGameControl);
		final String answer=question.getAnswer();
		final String message="Sorry, The answer is:\n"+question.getQuestionWord()+": "+answer;
	
		useHint=(Button)findViewById(R.id.btnHint);		
		int hintPoint=playAdvancedGameControl.readHintPoint();
		if(hintPoint<=0){
			useHint.setEnabled(false);
		}
		//Use Hint point button****************************************************************
		useHint.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {	
				hintPointButtonListener(answer);				
				useHint.setEnabled(false);
			}
			
		});
		
		//Answer Button A-D********************************************************************
		answerA.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				answerButtonListener(answerA,answer,message);
			}			
		});
		
		answerB.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				answerButtonListener(answerB,answer,message);
			}			
		});
		
		answerC.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				answerButtonListener(answerC,answer,message);
			}			
		});
		
		
		answerD.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				answerButtonListener(answerD,answer,message);
			}			
		});
		
	}
	
	/**
	 * Option buttons listener
	 * @param answerButton
	 * @param answer
	 * @param message
	 */
	protected void answerButtonListener(Button answerButton, String answer, String message) {
		String answerChoosed=answerButton.getText().toString();
		if(checkAnswerIsRight(answerChoosed,answer)){
			if(StartGameActivity.muted){
			soundPool.play(ding, 1, 1, 1, 0, 1);
			}
			answerButton.setSelected(true);
			setEnabled();
			setButtonDefault();
			judgeNextStage();

		}
		else{
			if(StartGameActivity.muted){
			soundPool.play(ding, 1, 1, 1, 0, 1);
			}
			answerButton.setBackgroundResource(R.drawable.general_button_wrong);
			answerButton.setSelected(true);
			setEnabled();
			gameOverDialog(message);
		}
		
	}


	/**
	 * Hint button listener
	 * @param answer
	 */
	protected void hintPointButtonListener(String answer) {
		boolean loop=true;
		while(loop){
			Random random=new Random();
			int randomnumber=random.nextInt(4);
			String getWord = null;
			if(randomnumber==0){
				getWord=answerA.getText().toString();
				if(!checkAnswerIsRight(getWord,answer)){
					loop=false;
					hint.setText(Integer.toString(playAdvancedGameControl.useHintPoint()));
					answerA.setBackgroundResource(R.drawable.advanced_use_hint_options);
					answerA.setEnabled(false);
				}
			}
			
			if(randomnumber==1){
				getWord=answerB.getText().toString();
				if(!checkAnswerIsRight(getWord,answer)){
					loop=false;
					hint.setText(Integer.toString(playAdvancedGameControl.useHintPoint()));
					answerB.setBackgroundResource(R.drawable.advanced_use_hint_options);
					answerB.setEnabled(false);
				}
			}
		
			if(randomnumber==2){
				getWord=answerC.getText().toString();
				if(!checkAnswerIsRight(getWord,answer)){
					loop=false;
					hint.setText(Integer.toString(playAdvancedGameControl.useHintPoint()));
					answerC.setBackgroundResource(R.drawable.advanced_use_hint_options);
					answerC.setEnabled(false);
				}
			}
			
			if(randomnumber==3){
				getWord=answerD.getText().toString();
				if(!checkAnswerIsRight(getWord,answer)){
					loop=false;
					hint.setText(Integer.toString(playAdvancedGameControl.useHintPoint()));
					answerD.setBackgroundResource(R.drawable.advanced_use_hint_options);
					answerD.setEnabled(false);							
				}
			}	
		}
		
	}


	/**
	 * Judge whether could go next stage, or whether the user finish the game
	 */
	protected void judgeNextStage() {
		if(questionNumInStage!=levelQuestionNo[checkpoint-1]){
			questionNumInStage++;
			playQuestion(playAdvancedGameControl);
		}
		else{
			if(checkpoint!=4){
				endTime=System.nanoTime();;
				long stageTime=endTime-startTime-pauseTime-animationTime;	
				double stageTimeSecond=(stageTime)/(Math.pow(10, 9));
				long levelScore=playAdvancedGameControl.calculateFinalScore(stageTime, playAdvancedGameControl.getCheckPoint());
				score+=levelScore;
				totalTime+=stageTimeSecond;
				playAdvancedGameControl.addHintPoint();
				stageTimeDialog(Integer.toString((int)stageTimeSecond),checkpoint);
				
				
			
			}
			else{
				endTime=System.nanoTime();
				long stageTime=endTime-startTime-pauseTime-animationTime;	
				double stageTimeSecond=(stageTime)/(Math.pow(10, 9));
				long levelScore=playAdvancedGameControl.calculateFinalScore(stageTime, playAdvancedGameControl.getCheckPoint());
				score+=levelScore;
				totalTime+=stageTimeSecond;
				pauseTime=0;
				
				playAdvancedGameControl.addHintPoint();
				conDialog();
			}

		}
		
	}


	/**
	 * Check whether the answer is correct or not
	 * @param answerChoosed
	 * @param answer
	 */
	protected boolean checkAnswerIsRight(String answerChoosed, String answer) {
		if(answerChoosed.equals(answer)){
			return true;
		}
		else{
			return false;
		}	
	}
	
	protected void setEnabled() {
		answerA.setEnabled(false);
		answerB.setEnabled(false);
		answerC.setEnabled(false);
		answerD.setEnabled(false);
		
	}

	/**
	 * Get a question, and change the order and show on the screen
	 * @param playGeneralGameControl
	 * @return Question Object
	 */
	private Question getQuestion(PlayAdvancedGameControl playAdvancedGameControl) {
		TextView question=(TextView)findViewById(R.id.advancedQuestion);
		answerA=(Button)findViewById(R.id.advancedAnswerA);
		answerB=(Button)findViewById(R.id.advancedAnswerB);
		answerC=(Button)findViewById(R.id.advancedAnswerC);
		answerD=(Button)findViewById(R.id.advancedAnswerD);
		questionWord=playAdvancedGameControl.getQuestionWord();
		Question q=playAdvancedGameControl.getValidQuestion(Constant.SAME_DATABASE, Constant.DATABASE_NUM, questionWord);		
		question.setText("Q:"+q.getQuestionWord());
		
		ArrayList<String> options=playAdvancedGameControl.changeOptionsOrder(q);
		answerA.setText(options.get(0));
		answerB.setText(options.get(1));
		answerC.setText(options.get(2));
		answerD.setText(options.get(3));		
		return q;
	}


	
	/**
	 * Set the button and list view to default
	 */
	protected void setButtonDefault() {
		if(answerA.isEnabled()==false){
		answerA.setEnabled(true);
		answerB.setEnabled(true);
		answerC.setEnabled(true);
		answerD.setEnabled(true);
		answerA.setSelected(false);
		answerB.setSelected(false);
		answerC.setSelected(false);
		answerD.setSelected(false);
		int hintPoint=playAdvancedGameControl.readHintPoint();
		if(hintPoint<=0){
			useHint.setEnabled(false);
		}
		else{
			useHint.setEnabled(true);
		}
		answerA.setBackgroundResource(R.drawable.general_button_all);
		answerB.setBackgroundResource(R.drawable.general_button_all);
		answerC.setBackgroundResource(R.drawable.general_button_all);
		answerD.setBackgroundResource(R.drawable.general_button_all);
		}
	}
	
	
	/**
	 * The game over dialog, show the correct answer of the word
	 * ask if start this level again
	 * @param message
	 */
	protected void gameOverDialog(String message) { 
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this); 
	    	
	    	Random random=new Random();
			int messageNo=random.nextInt(3);
   			if(messageNo==0){
				message+="\n\nDon't give up!";
   			}
   			if(messageNo==1){
   				message+="\n\nJust a little frustration. Keep going!";
   			}
   			if(messageNo==2){
   				message+="\n\nKeep calm and carry on!";	
   			}
   			
   			message+="\nWould you like to try again?";
   			
	    	builder.setMessage(message) 
	    	       .setTitle("Game Over")
	    	       .setCancelable(false) 
	    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
	    	           public void onClick(DialogInterface dialog, int id) { 
	    	   			questionNumInStage=1;
	    	   			dialog.cancel();
	    	   			checkpoint=playAdvancedGameControl.getCheckPoint();
	    				String value="Stage "+Integer.toString(checkpoint)+"!";
	    	   			stageAnimation(value);	    	   			
	    	   			startTime=System.nanoTime();
	    	   			playQuestion(playAdvancedGameControl);
	    	   			setButtonDefault();
	    	           } 
	    	       }) 
	    	       .setNegativeButton("No", new DialogInterface.OnClickListener() { 
	    	           public void onClick(DialogInterface dialog, int id) { 
	    	                dialog.cancel(); 
	    	                AdvancedGameActivity.this.finish();
	    	           } 
	    	       });
	    	builder.create().show();
	} 
	
	/**
	 * Congratulation dialog
	 */
	protected void conDialog(){
		final EditText name=new EditText(this);
		new AlertDialog.Builder(this).setTitle("Congratulations!")
						.setIcon(R.drawable.ic_launcher)
						.setView(name)
						.setTitle("Congratulations!")
						.setMessage("Total time consumption is: "+(int)totalTime+"s."+"\nYou have got 1 hint point!\nPlease Input your name: ")
						.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						     public void onClick(DialogInterface dialog, int which) {
						          String userName=name.getText().toString();
						          if(userName.equals("")){
						        	  userName="Player";
						          }
						          recordRecord(userName);
						         }
						         }).show();
	}


	/**
	 * Record the user's record
	 * @param userName
	 */
	protected void recordRecord(String userName) {
		RecordControl recordControl=RecordControl.getInstance(this);
		recordControl.addRecord(userName, (int)score, (long)totalTime);
		Intent intent=new Intent(this,RecordActivity.class);
		startActivity(intent);
		finish();
		
	}
	
	protected void pauseDialog(){		
		  
		final AlertDialog pause= new AlertDialog.Builder(this).create();  
		LayoutInflater inflater = LayoutInflater.from(this);  
		View contentView = inflater.inflate(R.layout.pause_menu, null); 
		pause.setView(contentView); 
		
		continueGame=(Button) contentView.findViewById(R.id.btn_pause_continue);
		continueGame.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				pause.cancel();
				pauseEndTime=System.nanoTime();;
				pauseTime+=(pauseEndTime-pauseStartTime);
			}			
		});	
		
		
		restartGame=(Button) contentView.findViewById(R.id.btn_pause_restart);
		restartGame.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				pause.cancel();
				playAdvancedGameControl=new PlayAdvancedGameControl(AdvancedGameActivity.this);
				stageAnimation("Let's go!");
				startGame(playAdvancedGameControl);
				pauseTime=0;
				score=0;
				totalTime=0;				
				TextView scoreRecord=(TextView)findViewById(R.id.score);
				scoreRecord.setText(Long.toString(score));
				
				startTime=System.nanoTime();;
				
			}			
		});
		
		exitGame=(Button) contentView.findViewById(R.id.btn_pause_exit);
		exitGame.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				pause.cancel();
				finish();
				
			}			
		});	


		pause.show();
	}
	
	protected void stageTimeDialog(String message, int checkPoint) { 
    	AlertDialog.Builder builder = new AlertDialog.Builder(this); 
    	message="Congratulations!!!\nThe time for stage "+checkPoint+" is: "+message+"s.\nKeep calm and carry on!";
    	builder.setMessage(message) 
    	       .setTitle("Time Consumption")
    	       .setCancelable(false) 
    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() { 
    	           public void onClick(DialogInterface dialog, int id) { 
    	        		playAdvancedGameControl.setCheckPoint();
    					checkpoint=playAdvancedGameControl.getCheckPoint();
    					questionNumInStage=1;
    					String value="Stage "+Integer.toString(checkpoint)+"!";
    					stageAnimation(value);
    					
    					
    					hint=(TextView)findViewById(R.id.hintPoint);
    					int hintPoint=playAdvancedGameControl.readHintPoint();		
    					hint.setText(Integer.toString(hintPoint));
    					
    					
    					TextView scoreRecord=(TextView)findViewById(R.id.score);
    					scoreRecord.setText(Long.toString(score));
    					
    					startTime=System.nanoTime();
    					pauseTime=0;
    					playQuestion(playAdvancedGameControl);
    	           } 
    	       }) ;
    	builder.create().show();
} 

	@Override
	public void onResume(){
		if(StartGameActivity.muted){
			StartGameActivity.serviceFlag=false;
		}else{
			StartGameActivity.serviceFlag=true;
		}
		if(StartGameActivity.serviceFlag==false){
			startService(StartGameActivity.musicIntent);
		}	
		super.onResume();
	}
	
	@Override
	public void onPause(){
		StartGameActivity.serviceFlag=true;
		super.onPause();
	}
	
	@Override
	public void onStop(){	
		if(StartGameActivity.serviceFlag==true){
			stopService(StartGameActivity.musicIntent);
		}
		super.onStop();
	}
	
	protected void onDestory(){
		stopService(StartGameActivity.musicIntent);
		super.onDestroy();
	}
	
 
	
 

}

