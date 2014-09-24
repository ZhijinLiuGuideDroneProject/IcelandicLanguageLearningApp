package com.gc02.wordsofafeather.control;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import java.sql.Date;
import java.util.ArrayList;

import com.gc02.wordsofafeather.bean.Record;
import com.gc02.wordsofafeather.database.DataHelper;
import com.gc02.wordsofafeather.utils.Constant;

import android.content.Context;

public class RecordControl {	
	
	private Context context;
	private static RecordControl recordControlInstance=null;
	
	private RecordControl(Context context) {
		this.context=context;
	}	
	
	public static RecordControl getInstance(Context context){
		if(recordControlInstance==null){
			recordControlInstance=new RecordControl(context);
		}
		return recordControlInstance;
	}
	
	/**
	 * Add a record to the record file
	 * @param name
	 * @param score
	 * @param time
	 */
	public void addRecord(String name, int score,long time){	
		Date date= new Date(System.currentTimeMillis());
	    Record record=new Record(name, score,time, date);
	    DataHelper dataHelper=DataHelper.getInstance(context);
		dataHelper.insertRecord(record);
		dataHelper.updateRecord();
	}

	/**
	 * Show the top ten records
	 * @return
	 */
	public Record[] showRecord(){
		DataHelper dataHelper=DataHelper.getInstance(context);
		ArrayList<Record> records=dataHelper.readRecord();
		Record[] scoreBoards;
		if(records.size()>=10){
			scoreBoards=new Record[Constant.RECORD_BOARD_LENGTH];
			for(int i=0;i<scoreBoards.length;i++){
				scoreBoards[i]=records.get(i);
			}
		}
		else{
			scoreBoards=new Record[records.size()];
			for(int i=0;i<scoreBoards.length;i++){
				scoreBoards[i]=records.get(i);
			}
		}
		return scoreBoards;
    }
}
