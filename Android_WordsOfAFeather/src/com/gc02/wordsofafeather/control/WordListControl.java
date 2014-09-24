package com.gc02.wordsofafeather.control;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import java.util.ArrayList;

import com.gc02.wordsofafeather.bean.Word;
import com.gc02.wordsofafeather.database.DataHelper;

import android.content.Context;

public class WordListControl {

	private Context context;
	private static WordListControl wordListControlInstance=null;
	
	private WordListControl(Context context) {
		this.context=context;
	}	
	
	public static WordListControl getInstance(Context context){
		if(wordListControlInstance==null){
			wordListControlInstance=new WordListControl(context);
		}
		return wordListControlInstance;
	}
	
	
	public void addWord(String question, String answer){
		Word word=new Word(question, answer);
		DataHelper dataHelper=DataHelper.getInstance(context);
		ArrayList<Word> words=dataHelper.readWordList();
		boolean add=true;
		for(int i=0;i<words.size();i++){
			if(words.get(i).getWord().equals(question)){
				add=false;
				break;
			}
		}
		if(add==true){
			dataHelper.addWordtoWordList(word);
		}
		dataHelper.updateWordList();
	}
	
	/**
	 * Count how many words in the word list
	 * @return
	 */
	public int countWordNumber(){
		int number = 0;
		DataHelper dataHelper=DataHelper.getInstance(context);
		ArrayList<Word> words=dataHelper.readWordList();
		number=words.size();
		return number;
	}
	
	/**
	 * This method is used to delete select words from wordList and show the new Word list 
	 * @param wordsInWordList
	 * @return
	 */
	public ArrayList<Word> deleteWords(ArrayList<String> wordsInWordList){
		DataHelper dataHelper=DataHelper.getInstance(context);
		ArrayList<Word> words=dataHelper.readWordList();
		ArrayList<Word> wordsDeleted=new ArrayList<Word>();
		for(Word word:words){
			for(String w:wordsInWordList){
				if(word.getWord().matches(w)){
					wordsDeleted.add(word);
				}
			}
		}
		dataHelper.deleteWordFromWordList(wordsDeleted);
		dataHelper.updateWordList();
		ArrayList<Word> newWords=dataHelper.readWordList();
		return newWords;
	}
	
	public ArrayList<Word> showList(){
		DataHelper dataHelper=DataHelper.getInstance(context);
		ArrayList<Word> words=dataHelper.readWordList();
		return words;
	}

}
