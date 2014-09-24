package com.gc02.wordsofafeather.activity;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import java.util.ArrayList;
import java.util.List;

import com.gc02.wordsofafeather.adapter.WordListAdapter;
import com.gc02.wordsofafeather.adapter.WordListModel;
import com.gc02.wordsofafeather.bean.Word;
import com.gc02.wordsofafeather.control.WordListControl;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class WordListActivity extends Activity {
	private WordListControl wc=WordListControl.getInstance(this);
	private ArrayList<Word> words;
	private List<WordListModel> list=new ArrayList<WordListModel>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_list);

		showWordList();
		
		TextView textView=(TextView) this.findViewById(R.id.editwordlist);
		textView.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(WordListActivity.this, EditWordListActivity.class);
				WordListActivity.this.startActivity(intent);	
				overridePendingTransition(0,0);
				finish();
			}
		});	
		
		
		Button backButton=(Button) this.findViewById(R.id.btnback);
		backButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				finish();	
			}
		});
	}
	
	@Override
	public void onBackPressed(){
		finish();
	}
	
	private void showWordList() {
		ArrayAdapter<WordListModel> adapter=new WordListAdapter(this,getWordListModel());
		ListView lv=new ListView(this);
		lv.setAdapter(adapter);
		lv.setDividerHeight(5);
		lv.setCacheColorHint(00000000);
		LinearLayout ll=(LinearLayout) findViewById(R.id.scroll);
		ll.addView(lv);
		
	}

	private List<WordListModel> getWordListModel() {	
        words=wc.showList();		
		for(int i=0;i<words.size();i++){
			list.add(get(words.get(i).getWord(),words.get(i).getAnswer()));
		}	
		return list;
	}

	private WordListModel get(String question, String answer) {
		return new WordListModel(question, answer);
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
