package com.gc02.wordsofafeather.activity;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.gc02.wordsofafeather.adapter.MyPagerAdapter;
import com.gc02.wordsofafeather.control.SetGameControl;

public class HelpActivity extends Activity{
	private SetGameControl setGameControl=SetGameControl.getInstance(this);
	private LayoutInflater inflater;
	private View first;
	private View second;
	private View third;
	private ViewPager viewPager;
	private List<View> viewList=new ArrayList<View>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StartGameActivity.serviceFlag=false;
		setContentView(R.layout.view_pager);
		init();
	} 
	
	// slide help
	public void init(){
		viewPager	=(ViewPager) findViewById(R.id.viewpager);
		this.getLayoutInflater();
		inflater = getLayoutInflater();  
		first = inflater.inflate(R.layout.activity_help, null);
		second = inflater.inflate(R.layout.activity_help2,null);
		third = inflater.inflate(R.layout.activity_help3,null);
		viewList.add(first);
		viewList.add(second);
		viewList.add(third);
		viewPager.setAdapter(new MyPagerAdapter(viewList));
	}
	
	// back to menu button
	public void backMenu(View view) {		
		 HelpActivity.this.finish(); 
	}
	
	
	@Override
	public void onResume(){
		if(setGameControl.readSetting().getSound()){
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

