package com.gc02.wordsofafeather.activity;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

public class StartActivity extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		 TextView learning=(TextView)findViewById(R.id.learning);
		 TextView testing=(TextView)findViewById(R.id.testing);
		 Animation animationTextWelcome= GetAnimationByStyleItem();
		 learning.startAnimation(animationTextWelcome); 
		 testing.startAnimation(animationTextWelcome);
		
		StartGameActivity.serviceFlag=false;
	        
		ImageButton backbutton = (ImageButton)findViewById(R.id.backButton);
		backbutton.setOnClickListener(new View.OnClickListener(){		  		 
			public void onClick(View v) {
				StartActivity.this.finish(); 
			}
		});
		
		ImageButton helpbutton=(ImageButton) findViewById(R.id.helpbutton);
		helpbutton.setOnClickListener(new View.OnClickListener(){	  		 
			public void onClick(View v) {
				Intent intent = new Intent(); 
				intent.setClass(StartActivity.this, HelpActivity.class);
				StartActivity.this.startActivity(intent);
				StartActivity.this.finish(); 
	  		 }
	  	});
		
		ImageButton generalGameButton=(ImageButton) findViewById(R.id.generalgame);
		generalGameButton.setOnClickListener(new View.OnClickListener(){	  		 
			public void onClick(View v) {
				Intent intent = new Intent(); 
				intent.setClass(StartActivity.this, GeneralGameActivity.class);
				StartActivity.this.startActivity(intent);
	  		 }
	  	});
		
		ImageButton advancedGameButton=(ImageButton) findViewById(R.id.advancedgame);
		advancedGameButton.setOnClickListener(new View.OnClickListener(){	  		 
			public void onClick(View v) {
				Intent intent = new Intent(); 
				intent.setClass(StartActivity.this, AdvancedGameActivity.class);
				StartActivity.this.startActivity(intent);
	  		 }
	  	});
		
	}
	
	private Animation GetAnimationByStyleItem() {		
		int animationSrouceId = -1;
		animationSrouceId = R.anim.scale_destext;
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

