package com.gc02.wordsofafeather.database;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

import com.gc02.wordsofafeather.bean.Maintenance;
import com.gc02.wordsofafeather.bean.Question;
import com.gc02.wordsofafeather.bean.Record;
import com.gc02.wordsofafeather.bean.Setting;
import com.gc02.wordsofafeather.bean.Word;

import android.content.Context;




public class DataHelper {

	private String basePath = "";
	private String maintenanceFile = basePath + "maintenance.dat";
	private String recordFile = basePath + "record.dat";
	private String settingFile = basePath + "setting.dat";
	private String wordFile = basePath +"word.dat";
	private Context context;
	private int numberofOptionsinQuestion=5;
	private int numberofQuestionFile=4;

	private static DataHelper dataHelperInstance=null;
	
	private DataHelper(Context context) {
		this.context=context;
	}	
	
	public static DataHelper getInstance(Context context){
		if(dataHelperInstance==null){
			dataHelperInstance=new DataHelper(context);
		}
		return dataHelperInstance;
	}

	/**
	 * add the word the user chosen to the wordList
	 * @param word
	 */
	public void addWordtoWordList(Word word){
		ArrayList<String> words=new ArrayList<String>();
		words.add(word.getWord());
		words.add(word.getAnswer());
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		fileInputOutput.insertFile("word.dat",words);
	}

	/**
	 * order the wordList from A-Z
	 */
	public void updateWordList(){
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		ArrayList<ArrayList<String>> wordList=fileInputOutput.readFile(wordFile);
		for(int i=0;i<wordList.size();i++){
			for(int j=i+1;j<wordList.size();j++){
				ArrayList<String> word1=wordList.get(i);
				ArrayList<String> word2=wordList.get(j);
				//need to add w switch to change the word from ice to number, word1' and word2' to get a sort
				if (word1.get(0).toLowerCase().compareTo(word2.get(0).toLowerCase())>0){
					wordList.add(i, word2);
					wordList.remove(j+1);
				}				
			}
		}
		fileInputOutput.updateFile(wordFile, wordList);
	}

	/**
	 * get the words from the wordFile, create the word objects
	 * @return
	 */
	public ArrayList<Word> readWordList(){
		ArrayList<Word> words=new ArrayList<Word>();
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		ArrayList<ArrayList<String>> wordList=fileInputOutput.readFile(wordFile);
		for(ArrayList<String> wordListword:wordList){
			Word word=new Word();
			word.setWord(wordListword.get(0));
			word.setAnswer(wordListword.get(1));
			words.add(word);
		}
		return words;
	}
	
	/**
	 * This method delete set to words from the wordList
	 * @param words
	 */
	public void deleteWordFromWordList(ArrayList<Word> wordsDeleted){
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		ArrayList<Word> wordsRead=readWordList();
		ArrayList<Word> words=new ArrayList<Word>();
		for(Word wordReaded: wordsRead){
			boolean add=true;
			for(Word word: wordsDeleted){
				if((wordReaded.getWord()).matches(word.getWord())){
					add=false;
				}
			}
			if(add){
				words.add(wordReaded);	
			}
		}
		ArrayList<ArrayList<String>> newWordList=new ArrayList<ArrayList<String>>();
		for(int i=0;i<words.size();i++){
			ArrayList<String> newWord= new ArrayList<String>();
			newWord.add(words.get(i).getWord());
			newWord.add(words.get(i).getAnswer());
			newWordList.add(newWord);
		}
		fileInputOutput.updateFile(wordFile, newWordList);
	}
	

	/**
	 * This method is used to generate a Question
	 * @param level 
	 * if 0: treat the all databases same; else: for each level, get the question from specific database
	 * @return
	 */
	public Question getQuestion(int level){
		Question question=new Question();
		int fileNumber=0;
		String questionFile = null;
		//get the question database
		if(level==0){
			Random r=new Random();
			fileNumber=r.nextInt(numberofQuestionFile)+1;
		}
		else{
			fileNumber=level;
		}		
		switch(fileNumber){
			case 1: questionFile="question1.dat";break;
			case 2: questionFile="question2.dat";break;
			case 3: questionFile="question3.dat";break;
			case 4: questionFile="question4.dat";break;
			default: System.out.println("The fileName is not exists");break;
		}
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		ArrayList<ArrayList<String>> questionList = fileInputOutput.readRaw(questionFile);
		ArrayList<String> questionContent=new ArrayList<String>();
		Random random=new Random();
		int row=random.nextInt(questionList.size());
		questionContent=questionList.get(row);
		question.setQuestionWord(questionContent.get(0));
		question.setAnswer(questionContent.get(1));
		ArrayList<String> options=new ArrayList<String>();
		for(int option=2;option<numberofOptionsinQuestion;option++){
			options.add(questionContent.get(option));
		}
		question.setOptions(options);
		return question;
	}
	
	/**
	 * This method can get the hint points value stored in the maintenance file
	 * @return
	 */
	public Maintenance readMaintenanceFile(){
		Maintenance maintenance=new Maintenance();
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		ArrayList<ArrayList<String>> maintenanceList=fileInputOutput.readFile(maintenanceFile);
		for(ArrayList<String> m: maintenanceList){
			maintenance.setHintPoint(Integer.parseInt(m.get(0)));
		}
		return maintenance;
	}
	
	/**
	 * Update the hint point value in the maintenance file
	 * @param maintenance
	 */
	public void updateMaintenanceFile(Maintenance maintenance){
		int hintPoint=maintenance.getHintPoint();
		ArrayList<ArrayList<String>> maintenanceList=new ArrayList<ArrayList<String>>();
		ArrayList<String> m=new ArrayList<String>();
		m.add(Integer.toString(hintPoint));
		maintenanceList.add(m);
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		fileInputOutput.updateFile(maintenanceFile, maintenanceList);
	}
	
	/**
	 * set/edit the setting file
	 * @param setting
	 */
	public void setSettingFile(Setting setting){
		ArrayList<String> set=new ArrayList<String>();
		set.add(setting.getLanguage());
		set.add(Boolean.toString(setting.getSound()));
		set.add(Boolean.toString(setting.getCartoonCharacter()));
		set.add(Boolean.toString(setting.getFirstTime()));
		ArrayList<ArrayList<String>> settings=new ArrayList<ArrayList<String>>();
		settings.add(set);
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		fileInputOutput.updateFile(settingFile, settings);		
	}
	
	/**
	 * read setting file and get setting
	 * @return
	 */
	public Setting readSettingFile(){
		Setting setting=new Setting();
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		ArrayList<ArrayList<String>> settings=fileInputOutput.readFile(settingFile);
		for(int i=0;i<settings.size();i++){
			setting.setLanguage(settings.get(i).get(0));
			setting.setSound(Boolean.parseBoolean(settings.get(i).get(1)));
			setting.setCartoonCharacter(Boolean.parseBoolean(settings.get(i).get(2)));
			setting.setFirstTime(Boolean.parseBoolean(settings.get(i).get(3)));
		}
		return setting;
	}	
	
	/**
	 * Add a new record to the record file, the format is: name, score, time, date
	 * @param record
	 */
	public void insertRecord(Record record){
		ArrayList<String> newRecord=new ArrayList<String>();
		newRecord.add(record.getName());
		newRecord.add(Integer.toString(record.getScore()));
		newRecord.add(Long.toString(record.getTime()));
		newRecord.add(record.getDate().toString());		
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		fileInputOutput.insertFile(recordFile, newRecord);
	}
	
	/**
	 * Read the content from the record file, and create record objects list
	 * @return
	 */
	public ArrayList<Record> readRecord(){
		ArrayList<Record> records=new ArrayList<Record>();
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		ArrayList<ArrayList<String>> recordReaded=fileInputOutput.readFile(recordFile);
		for(ArrayList<String> r:recordReaded){
			Record record=new Record();
			record.setName(r.get(0));
			record.setScore(Integer.parseInt(r.get(1)));
			record.setTime(Long.parseLong(r.get(2)));
			record.setDate(Date.valueOf(r.get(3)));
			records.add(record);
		}
		return records;
	}
	
	/**
	 * order the records from high to low and if the scores are equal, record from less time consumed to more
	 */
	public void updateRecord(){
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		ArrayList<ArrayList<String>> recordReaded=fileInputOutput.readFile(recordFile);
		for(int i=0;i<recordReaded.size();i++){
			for(int j=i+1;j<recordReaded.size();j++){
				ArrayList<String> record1=recordReaded.get(i);
				ArrayList<String> record2=recordReaded.get(j);
				if(Integer.parseInt(record2.get(1))>Integer.parseInt(record1.get(1))){
					recordReaded.add(i, record2);
					recordReaded.remove(j+1);
				}
				if(Integer.parseInt(record2.get(1))==Integer.parseInt(record1.get(1))){
					if(Long.parseLong(record1.get(2))>Long.parseLong(record2.get(2))){
						recordReaded.add(i, record2);	
						recordReaded.remove(j+1);
					}
					if(Long.parseLong(record1.get(2))==Long.parseLong(record2.get(2))){
						if (record1.get(0).toLowerCase().compareTo(record2.get(0).toLowerCase())>0){
							recordReaded.add(i, record2);	
							recordReaded.remove(j+1);
						}				
					}
				}
			}
		}
		fileInputOutput.updateFile(recordFile, recordReaded);
	}
	
	public void deleteFile(){
		FileInputOutput fileInputOutput=FileInputOutput.getInstance(context);
		fileInputOutput.deleteFile(recordFile);
		fileInputOutput.deleteFile(settingFile);
		fileInputOutput.deleteFile(wordFile);
		fileInputOutput.deleteFile(maintenanceFile);
	}

}
