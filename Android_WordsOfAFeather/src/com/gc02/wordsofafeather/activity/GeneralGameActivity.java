package com.gc02.wordsofafeather.activity;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import java.util.ArrayList;
import java.util.List;

import com.gc02.wordsofafeather.adapter.ChatAdapter;
import com.gc02.wordsofafeather.adapter.ChatEntity;
import com.gc02.wordsofafeather.bean.Question;
import com.gc02.wordsofafeather.control.PlayGeneralGameControl;
import com.gc02.wordsofafeather.control.WordListControl;
import com.gc02.wordsofafeather.utils.Constant;



import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class GeneralGameActivity extends Activity {

    private List<ChatEntity> chatList = null;
    private ArrayList<String> questionWord;
    private Button answerA;
    private Button answerB;
    private Button answerC;
    private Button answerD;
    private Button nextButton;
    private Button backButton;
    private Button addtowordlist;
    private TextView count;
    private PlayGeneralGameControl playGeneralGameControl;
    private WordListControl wordListControl;   
	private SoundPool soundPool;
	private int ding;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_general_game);
		
		soundPool=new SoundPool(5, AudioManager.STREAM_MUSIC,0);
		ding=soundPool.load(this, R.raw.ding, 1);	
		
		playGeneralGameControl=new PlayGeneralGameControl(this);
		wordListControl=WordListControl.getInstance(this);
		playQuestion(playGeneralGameControl);//for each round (one question) of play
		count=(TextView)findViewById(R.id.count);
		
		nextButton=(Button)findViewById(R.id.btnnext);
		Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_welcome);
		nextButton.startAnimation(animation);
		nextButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				setButtonDefault();				
				playQuestion(playGeneralGameControl);
			}			
		});
		
		backButton=(Button)findViewById(R.id.btnback);
		backButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}			
		});		
		

	}


	/**
	 * Play, ask other method to get the method, set the buttons' action
	 * @param playGeneralGameControl
	 */
	private void playQuestion(final PlayGeneralGameControl playGeneralGameControl) {
		Question question=getQuestion(playGeneralGameControl);
		final String quetionsWord=question.getQuestionWord();
		final String answer=question.getAnswer();
		addtowordlist=(Button)findViewById(R.id.addtowordlist);
		
		//add word to word list****************************************************************
		addtowordlist.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				wordListControl.addWord(quetionsWord, answer);
				addtowordlist.setSelected(true);
				addtowordlist.setEnabled(false);

			}			
		});	
		
		//Answer Button A-D********************************************************************
		answerA.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				answerButtonListener(answerA,answer);
				
			}			
		});
		
		answerB.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				answerButtonListener(answerB,answer);
			}			
		});
		
		answerC.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				answerButtonListener(answerC,answer);
			}			
		});
		
		answerD.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				answerButtonListener(answerD,answer);
			}			
		});
		
	}



	protected void answerButtonListener(Button answerButton, String answer) {
		String answerChoosed=answerButton.getText().toString();
		if(checkAnswerIsRight(answerChoosed,answer)){
			if(StartGameActivity.muted){
			soundPool.play(ding, 1, 1, 1, 0, 1);
			}
			playGeneralGameControl.countAnsweredQuestionNumber();
			answerButton.setSelected(true);
			setEnabled();
			count.setText(Integer.toString(playGeneralGameControl.getCount()));
		}
		else{
			if(StartGameActivity.muted){
			soundPool.play(ding, 1, 1, 1, 0, 1);
			}
			answerButton.setBackgroundResource(R.drawable.general_button_wrong);
			answerButton.setSelected(true);
			setEnabled();
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
	private Question getQuestion(PlayGeneralGameControl playGeneralGameControl) {
		TextView question=(TextView)findViewById(R.id.question);
		answerA=(Button)findViewById(R.id.answerA);
		answerB=(Button)findViewById(R.id.answerB);
		answerC=(Button)findViewById(R.id.answerC);
		answerD=(Button)findViewById(R.id.answerD);
		questionWord=playGeneralGameControl.getQuestionWord();
		Question q=playGeneralGameControl.getValidQuestion(Constant.SAME_DATABASE, Constant.DATABASE_NUM, questionWord);		
		question.setText("Q:"+q.getQuestionWord());
		
		ArrayList<String> options=playGeneralGameControl.changeOptionsOrder(q);
		answerA.setText(options.get(0));
		answerB.setText(options.get(1));
		answerC.setText(options.get(2));
		answerD.setText(options.get(3));		
		return q;
	}


	/**
	 * Check whether the answer is correct or not
	 * @param answerChoosed
	 * @param answer
	 */
	protected boolean checkAnswerIsRight(String answerChoosed, String answer) {
		String chatContent;
		if(answerChoosed.equals(answer)){
			chatContent = "Congratulations!\nGo next!";
		}
		else{
			chatContent = "Sorry,the answer is:\n"+answer;
		}	
		addChat(chatContent);
		return answerChoosed.equals(answer);
	}

	/**
	 * Show the chat to tell whether the user answered correctly
	 * @param chatContent
	 */
	private void addChat(String chatContent) {
		ListView listView=(ListView)findViewById(R.id.generalAvatarCheckAnswer);
		chatList = new ArrayList<ChatEntity>();  
		ChatEntity chatEntity = null;  
        chatEntity = new ChatEntity();    
        chatEntity.setContent(chatContent);  
        chatList.add(chatEntity); 
        ChatAdapter chatAdapter=new ChatAdapter(this,chatList);
        listView.setAdapter(chatAdapter);
        listView.setCacheColorHint(00000000);
        listView.setDivider(null);
        chatAdapter.notifyDataSetChanged();
        
		
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
		answerA.setBackgroundResource(R.drawable.general_button_all);
		answerB.setBackgroundResource(R.drawable.general_button_all);
		answerC.setBackgroundResource(R.drawable.general_button_all);
		answerD.setBackgroundResource(R.drawable.general_button_all);

		ListView listView=(ListView)findViewById(R.id.generalAvatarCheckAnswer);
		chatList.clear();
		listView.setAdapter(null);
		}
		if(addtowordlist.isEnabled()==false){
			addtowordlist.setEnabled(true);
			addtowordlist.setSelected(false);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.general_game, menu);
		return true;

	}
	
	@Override
	public void onBackPressed(){
		GeneralGameActivity.this.finish();
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
