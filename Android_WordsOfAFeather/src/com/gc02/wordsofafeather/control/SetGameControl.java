package com.gc02.wordsofafeather.control;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import com.gc02.wordsofafeather.bean.Maintenance;
import com.gc02.wordsofafeather.bean.Setting;
import com.gc02.wordsofafeather.database.DataHelper;
import com.gc02.wordsofafeather.utils.Constant;

import android.content.Context;



public class SetGameControl {
	
	private Context context;
	private static SetGameControl setGameControlInstance=null;
	
	private SetGameControl(Context context) {
		this.context=context;
	}	
	
	public static SetGameControl getInstance(Context context){
		if(setGameControlInstance==null){
			setGameControlInstance=new SetGameControl(context);
		}
		return setGameControlInstance;
	}
	
	/**
	 * Show the setting, if first time see the setting, the setting set to the initial value
	 * @return
	 */
	public Setting readSetting(){
		boolean firstTime=getFirstTimeValue();
		if(firstTime==false){
			initialSetting(); //set the setting to the default value
			firstSetMaintenance();
		}
		DataHelper dataHelper=DataHelper.getInstance(context);
		Setting settings=dataHelper.readSettingFile();
		return settings;
	}	
	
	/**
	 * If returned false, means first time starts the program, there's no setting file
	 * @return
	 */
	private boolean getFirstTimeValue(){
		DataHelper dataHelper=DataHelper.getInstance(context);
		Setting settings=dataHelper.readSettingFile();
		return settings.getFirstTime();
	}
	
	
	/**
	 * used to initial the value of the setting file or reset setting file
	 */
	public void initialSetting(){
		Setting settings=new Setting();
		settings.setLanguage(Constant.TYPE_ENGLISH);
		settings.setSound(true);
		settings.setCartoonCharacter(true);
		settings.setFirstTime(true);
		DataHelper dataHelper=DataHelper.getInstance(context);
		dataHelper.setSettingFile(settings);	
	}
	
	/**
	 * 
	 * @param option
	 * @param content
	 */
	public void editSetting(String option, String content){
		DataHelper dataHelper=DataHelper.getInstance(context);
		Setting settings=dataHelper.readSettingFile();
		if(option.equals("language")){
			settings.setLanguage(content);
		}
		if(option.equals("sound")){
			settings.setSound(Boolean.parseBoolean(content));				
		}
		if(option.equals("cartoonCharacter")){
			settings.setCartoonCharacter(Boolean.parseBoolean(content));
		}
		if(option.equals("firstTime")){
			settings.setFirstTime(Boolean.parseBoolean(content));
		}
			
		dataHelper.setSettingFile(settings);
	}			
	
	/**
	 * Set the initial hintPoint to 3 in the maintenance file
	 */
	public void firstSetMaintenance(){
		Maintenance maintenance=new Maintenance();
		maintenance.setHintPoint(3);
		DataHelper dataHelper=DataHelper.getInstance(context);
		dataHelper.updateMaintenanceFile(maintenance);
	}
	
	public void resetGame(){
		DataHelper dataHelper=DataHelper.getInstance(context);
		dataHelper.deleteFile();
	}
	
}
