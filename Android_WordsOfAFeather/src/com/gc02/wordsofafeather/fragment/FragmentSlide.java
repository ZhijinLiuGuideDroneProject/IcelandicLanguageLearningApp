package com.gc02.wordsofafeather.fragment;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import com.gc02.wordsofafeather.activity.R;
import com.gc02.wordsofafeather.activity.RecordActivity;
import com.gc02.wordsofafeather.activity.SettingActivity;
import com.gc02.wordsofafeather.activity.WordListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentSlide extends  Fragment implements OnClickListener {
	FragmentManager fragmentManager; 
	FragmentSlide fragmentslide;
	Button settingbutton;
	Button rankingbutton;
	Button wordlistbutton;
	
	@Override   
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 	
	} 
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 	
		View view=inflater.inflate(R.layout.slidemenu, container, false); 		
		settingbutton=(Button) view.findViewById(R.id.settingbutton); 
		rankingbutton=(Button) view.findViewById(R.id.rankingbutton);
		wordlistbutton=(Button) view.findViewById(R.id.wordlistbutton); 
		settingbutton.setOnClickListener(this);
		rankingbutton.setOnClickListener(this);
		wordlistbutton.setOnClickListener(this);
		return view;

	} 
	
	@Override 
	public void onPause() { 
		super.onPause(); 
	} 
	
	@Override 
	public void onResume() { 
		super.onResume();  
	} 
	
	@Override 
	public void onStop() { 
		super.onStop(); 
	} 


	@Override
	public void onClick(View v) {
		 switch (v.getId()) {
	         case R.id.settingbutton:
	         	{
	         		Intent intent = new Intent(); 
	         		intent.setClass(getActivity(),SettingActivity.class);
	         		startActivity(intent);
	         	};break;
	         case R.id.rankingbutton:
	         	{
	         		Intent intent = new Intent(); 
	         		intent.setClass(getActivity(),RecordActivity.class);
	         		startActivity(intent);
	         	};break;
	         case R.id.wordlistbutton:
	         	{
	         		Intent intent = new Intent(); 
	         		intent.setClass(getActivity(),WordListActivity.class);
	         		startActivity(intent);
	         	};break;
		 }
		
	} 
	}



