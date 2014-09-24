package com.gc02.wordsofafeather.bean;

public class Setting {

	private String language;
	private boolean sound;
	private boolean cartoonCharacter;
	private boolean firstTime; //judge if the game is first started, if first time start the game, equals to false
	
	
	
	public Setting() {
		super();
	}
	
	public Setting(String language,boolean sound,boolean cartoonCharacter,boolean firstTime) {
		super();
		this.language=language;
		this.sound=sound;
		this.cartoonCharacter=cartoonCharacter;
		this.firstTime=firstTime;
	}
	
	public String getLanguage(){
		return this.language;
	}
	
	public void setLanguage(String language){
		this.language=language;
	}
	
	public boolean getSound(){
		return this.sound;
	}
	
	public void setSound(boolean sound){
		this.sound=sound;
	}
	
	public boolean getCartoonCharacter(){
		return this.cartoonCharacter;
	}
	
	public void setCartoonCharacter(boolean cartoonCharacter){
		this.cartoonCharacter=cartoonCharacter;
	}
	
	public boolean getFirstTime(){
		return this.firstTime;
	}
	
	public void setFirstTime(boolean firstTime){
		this.firstTime=firstTime;
	}
	


}
