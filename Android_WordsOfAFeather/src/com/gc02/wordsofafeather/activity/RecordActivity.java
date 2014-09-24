package com.gc02.wordsofafeather.activity;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import com.gc02.wordsofafeather.bean.Record;
import com.gc02.wordsofafeather.control.RecordControl;
import com.gc02.wordsofafeather.control.SetGameControl;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecordActivity extends Activity {

	private SetGameControl setGameControl=SetGameControl.getInstance(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		
		StartGameActivity.serviceFlag=false;
	
		showRecordList();
		
		ImageButton backButton=(ImageButton) findViewById(R.id.btnback);
		backButton.setOnClickListener(new View.OnClickListener(){	  		 
			public void onClick(View v) {
				RecordActivity.this.finish(); 
	  		 }
	  	});
	}		

	private void showRecordList() {
		RecordControl rc=RecordControl.getInstance(this);
		Record[] record=rc.showRecord();
		int idIncrement=0;
		
		for(int i=0;i<record.length;i++){
		TextView tvName=(TextView) findViewById(R.id.name1+idIncrement);
		String name=record[i].getName();
		tvName.setText(name);
		
		TextView tvScore=(TextView) findViewById(R.id.score1+idIncrement);
		String score=Integer.toString(record[i].getScore());
		tvScore.setText(score);
		
		TextView tvTime=(TextView) findViewById(R.id.time1+idIncrement);
		String time=Long.toString(record[i].getTime())+"s";
		tvTime.setText(time);
		
		CharSequence date=android.text.format.DateFormat.format("dd/MM/yy", record[i].getDate());
		TextView tvDate=(TextView) findViewById(R.id.date1+idIncrement);
		tvDate.setText(date);
		
		idIncrement+=5;
		
		}
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




