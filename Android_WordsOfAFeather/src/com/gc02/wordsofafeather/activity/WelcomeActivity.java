package com.gc02.wordsofafeather.activity;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends Activity{
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_welcome);

		 TextView welcome=(TextView)findViewById(R.id.welcometext);
		 Animation animationTextWelcome= GetAnimationByStyleItem();
		 welcome.startAnimation(animationTextWelcome); 
	 
		 Button backbutton = (Button)findViewById(R.id.welcomescreen);
		 backbutton.setOnClickListener(new OnClickListener(){	    		   
	       public void onClick(View v) {
				 Intent intent = new Intent(); 
				 intent.setClass(WelcomeActivity.this, StartGameActivity.class);
				 startActivity(intent);
				 WelcomeActivity.this.finish(); 
	       }
		 });
		 

	 }
	 
	 
		private Animation GetAnimationByStyleItem() {		
			int animationSrouceId = -1;
			animationSrouceId = R.anim.scale_welcome;
			Animation animation = AnimationUtils.loadAnimation( 
			getApplicationContext(), animationSrouceId);
			return animation; 		
		}

}
