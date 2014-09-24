package com.gc02.wordsofafeather.activity;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import com.gc02.wordsofafeather.bean.Setting;
import com.gc02.wordsofafeather.control.SetGameControl;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SettingActivity extends Activity {

	private RadioGroup groupLanguage; 
	private RadioGroup groupSound;
	private RadioGroup groupAvatar;
	private Button reset;
	private SetGameControl setGameControl=SetGameControl.getInstance(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);		
		
		groupLanguage=(RadioGroup) findViewById(R.id.languageRdbGp);
		groupSound=(RadioGroup) findViewById(R.id.soundRdbGp);
		groupAvatar=(RadioGroup) findViewById(R.id.avatarRdbGp);
		getRadioButtonValue();			
		
		groupLanguage.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedID) {
				RadioButton radioButton=(RadioButton)findViewById(R.id.languageRdb1);
				if(radioButton.isChecked()){
					setGameControl.editSetting("language", "English");
					DisplayToast("Language sets to English");
				}
				
			}
		});
		
		groupSound.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedID) {
				RadioButton radioButton=(RadioButton)findViewById(R.id.soundRdb1);
				if(radioButton.isChecked()){
					setGameControl.editSetting("sound", "true");
					DisplayToast("Sound is on");
					startService(StartGameActivity.musicIntent);
				}
				else{
					setGameControl.editSetting("sound", "false");
					DisplayToast("Sound is off");
					stopService(StartGameActivity.musicIntent);
					StartGameActivity.serviceFlag=true;
				}				
			}
		});
		
		groupAvatar.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedID) {
				RadioButton radioButton=(RadioButton)findViewById(R.id.avatarRdb1);
				if(radioButton.isChecked()){
					setGameControl.editSetting("cartoonCharacter", "true");
					DisplayToast("Male puffin selected");
				}
				else{
					setGameControl.editSetting("cartoonCharacter", "false");
					DisplayToast("Female puffin selected");
				}				
			}
		});
		
		
		reset=(Button)findViewById(R.id.resetbnt);
		reset.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {	
				resetDialog();
			}
			
		});		
		
	    
		ImageButton backButton=(ImageButton) findViewById(R.id.btnback);
		backButton.setOnClickListener(new View.OnClickListener(){	  		 
			public void onClick(View v) {
				SettingActivity.this.finish(); 
	  		 }
	  	});
		

	}

	protected void resetDialog() {

	    	AlertDialog.Builder builder = new AlertDialog.Builder(this); 
	    	builder.setMessage("Are you sure?") 
	    	       .setTitle("Reset Confirmation")
	    	       .setCancelable(false) 
	    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
	    	           public void onClick(DialogInterface dialog, int id) { 
	    	        	   setGameControl.resetGame();
	    	        	   dialog.cancel();
	    	        	   DisplayToast("Reset Successfully");
	    	        	   Intent intent=new Intent(SettingActivity.this,SettingActivity.class);
	    	        	   startActivity(intent);
	    	        	   overridePendingTransition(0,0);
	    	        	   finish();
	    	        	   
	    	           } 
	    	       }) 
	    	       .setNegativeButton("No", new DialogInterface.OnClickListener() { 
	    	           public void onClick(DialogInterface dialog, int id) { 
	    	                dialog.cancel(); 
	    	                DisplayToast("Reset Canceled");
	    	           } 
	    	       });
	    	builder.create().show();

		
	}

	//************************************************************************************************************
	/**
	 * Get the radio button values from the setting.dat file
	 */
	private void getRadioButtonValue() {
		Setting setting=setGameControl.readSetting();
		setLanguage(setting,groupLanguage);
		setSound(setting,groupSound);
		setAvatar(setting,groupAvatar);
	}

	private void setLanguage(Setting setting, RadioGroup groupLanguage) {
		RadioButton radioButton = null;
		if(setting.getLanguage().equals("English")){
			radioButton=(RadioButton)groupLanguage.getChildAt(0);			
		}
		radioButton.setChecked(true);
	}
	
	private void setSound(Setting setting, RadioGroup groupSound) {
		RadioButton radioButton;
		if(setting.getSound()==true){
			radioButton=(RadioButton)groupSound.getChildAt(0);			
		}
		else{
			radioButton=(RadioButton)groupSound.getChildAt(1);
		}
		radioButton.setChecked(true);
	}
	
	private void setAvatar(Setting setting, RadioGroup groupAvatar) {
		RadioButton radioButton;
		if(setting.getCartoonCharacter()==true){
			radioButton=(RadioButton)groupAvatar.getChildAt(0);			
		}
		else{
			radioButton=(RadioButton)groupAvatar.getChildAt(1);
		}
		radioButton.setChecked(true);
		
	}

	//************************************************************************************************************
	/**
	 * Display the toast, tell the client the change is made successful
	 * @param msg
	 */
	protected void DisplayToast(String msg) {
		Toast toast = Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 100);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView imageCodeProject = new ImageView(getApplicationContext());
		imageCodeProject.setImageResource(R.drawable.homepage);
		toastView.addView(imageCodeProject, 0);
		toast.show();
		
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
