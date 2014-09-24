package com.gc02.wordsofafeather.activity;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import com.gc02.wordsofafeather.control.SetGameControl;
import com.gc02.wordsofafeather.fragment.FragmentSlide;
import com.gc02.wordsofafeather.slidingmenu.lib.SlidingMenu;
import com.gc02.wordsofafeather.slidingmenu.lib.SlidingMenu.CanvasTransformer;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Interpolator;
import android.widget.ImageButton;

public class StartGameActivity extends FragmentActivity {

	protected static Intent musicIntent=new Intent("com.gc02.wordsofafeather.service.service.BIND_SERVICE");
	public static boolean serviceFlag;
	private SetGameControl setGameControl=SetGameControl.getInstance(this);
	public static boolean muted;
	
	private ImageButton helpbutton;
	private ImageButton startbutton;
	private ImageButton slidebutton;
	FragmentManager fragmentManager; 
	FragmentSlide fragmentslide;
	private SlidingMenu menu; 
	
	
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.content_frame);

        initAnimation();  

        initSlidingMenu();  		
 
        helpbutton=(ImageButton) findViewById(R.id.helpbutton); 
    	helpbutton.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			Intent intent = new Intent(); 
    			intent.setClass(StartGameActivity.this,HelpActivity.class);
    			startActivity(intent);
    		}
    	});
    	
    	startbutton=(ImageButton)findViewById(R.id.startbutton);
    	startbutton.setOnClickListener(new OnClickListener() {
   		 	@Override
   		 	public void onClick(View v) {
   		 		Intent intent = new Intent(); 
   		 		intent.setClass(StartGameActivity.this,StartActivity.class);
   		 		startActivity(intent);
   		 	}
    	});

        fragmentManager = getSupportFragmentManager(); 
        } 
 
    	
    private void initSlidingMenu() { 
    	fragmentslide= new FragmentSlide();

        setContentView(R.layout.content_frame); 
        
        android.support.v4.app.FragmentTransaction t= this.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content_frame, new Fragment()).commit();      	
                             
        fragmentslide= new FragmentSlide();
        
        menu = new SlidingMenu(this);  
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN); 
        menu.setShadowWidth(50);
        menu.setBehindOffsetRes(R.dimen.right_menu_margin);
        menu.setFadeDegree(0.35f);  
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);  
        menu.setMenu(R.layout.menu_frame);  
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame,fragmentslide).commit(); 
  
        
        slidebutton=(ImageButton)findViewById(R.id.slidebutton);
        slidebutton.setOnClickListener(new OnClickListener() {
   		 	@Override
   		 	public void onClick(View v) {
   		 		menu.showMenu();
   		 	}
    	});

    }  
    
    private static Interpolator interp = new Interpolator() {  
        @Override  
        public float getInterpolation(float t) {  
            t -= 1.0f;  
            return t * t * t + 1.0f;  
        }         
    };  

    /**
     * Initial animation
     */
    private void initAnimation(){                 
        new CanvasTransformer(){  
            @Override  
            public void transformCanvas(Canvas canvas, float percentOpen) {  
                canvas.translate(0, canvas.getHeight() * (1 - interp.getInterpolation(percentOpen)));         
            }         
        }; 
    }


	@Override  
	public void onBackPressed() {  
		
	   if (menu.isMenuShowing()) {  
	       menu.showContent();  
	   } 
	   else {  
	       super.onBackPressed();  
	   }  
	}

	
	@Override
	public void onResume(){
		muted=setGameControl.readSetting().getSound();
		if(muted){
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
		serviceFlag=true;
		super.onPause();
	}
	
	@Override
	public void onStop(){	
		if(serviceFlag==true){
			stopService(musicIntent);
		}
		super.onStop();
	}
	
	protected void onDestory(){
		stopService(musicIntent);
		super.onDestroy();
	}
	

	
	

}
