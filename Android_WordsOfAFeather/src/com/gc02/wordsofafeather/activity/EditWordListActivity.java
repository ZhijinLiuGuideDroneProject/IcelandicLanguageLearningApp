package com.gc02.wordsofafeather.activity;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import java.util.ArrayList;
import java.util.List;

import com.gc02.wordsofafeather.adapter.EditWordListModel;
import com.gc02.wordsofafeather.adapter.EditWordListAdapter;
import com.gc02.wordsofafeather.bean.Word;
import com.gc02.wordsofafeather.control.WordListControl;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class EditWordListActivity extends Activity {
	protected Intent musicIntent=new Intent("com.GC02.WordsOfAFeather.Service.service.BIND_SERVICE");
	
	private WordListControl wc=WordListControl.getInstance(this);
	private ArrayList<Word> words;
	private ArrayList<String> delete=new ArrayList<String>();
	private List<EditWordListModel> list=new ArrayList<EditWordListModel>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_word_list);
							
		showList();
			
		Button back=(Button) this.findViewById(R.id.backbtn);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(EditWordListActivity.this, WordListActivity.class);
				EditWordListActivity.this.startActivity(intent);
				EditWordListActivity.this.finish();
				overridePendingTransition(0,0);
			}
		});
		
		Button deleteBtn=(Button) this.findViewById(R.id.delete_btn);
		deleteBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String message="Are you sure to delete the words?";
				gameOverDialog(message);			
			}
		});		
	}
	
	@Override
	public void onBackPressed(){
		Intent intent=new Intent(EditWordListActivity.this, WordListActivity.class);
		EditWordListActivity.this.startActivity(intent);
		EditWordListActivity.this.finish();
		overridePendingTransition(0,0);
	}
	
	private void showList() {
		ArrayAdapter<EditWordListModel> adapter=new EditWordListAdapter(this,getEditWordListModel());
		ListView lv=new ListView(this);
		lv.setAdapter(adapter);
		lv.setDividerHeight(5);
		lv.setCacheColorHint(00000000);
		LinearLayout ll=(LinearLayout) findViewById(R.id.scroll);
		ll.addView(lv);
		
	}

	private List<EditWordListModel> getEditWordListModel() {	
        words=wc.showList();		
		for(int i=0;i<words.size();i++){
			list.add(get(words.get(i).getWord(),words.get(i).getAnswer()));
		}	
		return list;
	}

	private EditWordListModel get(String icelandic, String english) {
		return new EditWordListModel(icelandic, english);
	}
	
	private void edit() {		
		for(int i=0;i<words.size();i++){
			if(list.get(i).isSelected()==true){
				delete.add(words.get(i).getWord());
			}
			else{
				delete.remove(words.get(i).getWord());
			}
		}	
	}
	
	protected void gameOverDialog(String message) { 
    	AlertDialog.Builder builder = new AlertDialog.Builder(this); 
    	builder.setMessage(message) 
    	       .setTitle("Confirmation")
    	       .setCancelable(false) 
    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
    	           public void onClick(DialogInterface dialog, int id) { 
    	        	   edit();
    	        	   wc.deleteWords(delete);
    	        	   Intent intent=new Intent(EditWordListActivity.this, WordListActivity.class);
    	        	   EditWordListActivity.this.startActivity(intent);	
    	        	   EditWordListActivity.this.finish();
    	        	   overridePendingTransition(0,0);
    	           } 
    	       }) 
    	       .setNegativeButton("No", new DialogInterface.OnClickListener() { 
    	           public void onClick(DialogInterface dialog, int id) {
    	               dialog.cancel(); 
    	               Intent intent=new Intent(EditWordListActivity.this, WordListActivity.class);
     	        	   EditWordListActivity.this.startActivity(intent);	
     	        	   EditWordListActivity.this.finish();
     	        	   overridePendingTransition(0,0);
    	           } 
    	       });
    	builder.create().show();
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
