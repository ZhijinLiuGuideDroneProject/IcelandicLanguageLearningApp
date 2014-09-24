package com.gc02.wordsofafeather.bean;

import java.sql.Date;

public class Record {

	private String name;
	private int score;
	private long time;
	private Date date;
	
	
	public Record() {
		super();
	}
	
	public Record(String name,int score,long time,Date date) {
		super();
		this.name=name;
		this.score=score;
		this.time=time;
		this.date=date;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public void setScore(int score){
		this.score=score;
	}
	
	public long getTime(){
		return this.time;
	}
	
	public void setTime(long time){
		this.time=time;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public void setDate(Date date){
		this.date=date;
	}

}
