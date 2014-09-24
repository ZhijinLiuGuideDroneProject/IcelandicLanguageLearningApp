package com.gc02.wordsofafeather.activity;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class StageActivityMale extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage_activity_male);
		
		Intent intent=getIntent();
		String text=intent.getStringExtra("com.gc02.wordsofafeather.activity.stage");
		
		ImageView imagemale=(ImageView)findViewById(R.id.stage_male);	
		Animation animationImage= GetAnimationByStyleItem("image");
		imagemale.startAnimation(animationImage);
		TextView stage=(TextView)findViewById(R.id.stageNum);
		stage.setText(text);
		Animation animationText=GetAnimationByStyleItem("text");
		stage.startAnimation(animationText);		
		
		 Timer timer = new Timer();
	       TimerTask task = new TimerTask() {
	           @Override
	           public void run() {
	              StageActivityMale.this.finish();
	           }
	       };
	       timer.schedule(task, 2900);		
	}

	private Animation GetAnimationByStyleItem(String idName) {		
		int animationSrouceId = -1;
		if(idName.equals("image")){
			animationSrouceId = R.anim.scale_male;
		}
		if(idName.equals("text")){
			animationSrouceId = R.anim.scale_text;				
		}
		Animation animation = AnimationUtils.loadAnimation( 
			    getApplicationContext(), animationSrouceId);
		return animation; 		
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

