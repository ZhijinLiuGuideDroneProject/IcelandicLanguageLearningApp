package com.gc02.wordsofafeather.database;

/**
 * @author Ningxin CUI; Zhijin LIU; Qingzi YANG;
 * Words of a feather
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.gc02.wordsofafeather.activity.R;



import android.content.Context;


public class FileInputOutput{
	
	private Context context;
	public static String lineSep=System.getProperty("line.separator");
	private static FileInputOutput fileInputOutputInstance=null;
	
	
    private FileInputOutput(Context context){
    	this.context=context;
    }
    
    /**
     * Get the FileInputOutput instant
     * @param context
     * @return return the instance
     */
    public static FileInputOutput getInstance(Context context){
 		if(fileInputOutputInstance==null){
 			fileInputOutputInstance=new FileInputOutput(context);
		}
		return fileInputOutputInstance;    	 
    }

    public void deleteFile(String filename){
		File dir = context.getFilesDir();
		File file = new File(dir, filename);
		file.delete();
    }
    
    /**
     * Read the file and store the file's content into an ArrayList<ArrayList<String>>
     * @param filename
     * @return
     */
  	public ArrayList<ArrayList<String>> readFile(String filename){
 		ArrayList<ArrayList<String>> contents=new ArrayList<ArrayList<String>>();		
 		try{
 	 		InputStream inputStream = context.openFileInput(filename);
 	 		if ( inputStream != null ) {
 	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
 	            BufferedReader reader = new BufferedReader(inputStreamReader);
 	 			String thisLine="";
 	 			int i=0;
 	 			while ((thisLine = reader.readLine()) != null) {
 	 				String[] line=thisLine.split(",",0);
 	 				contents.add(i,new ArrayList<String>());
 	 				for(int j=0;j<line.length;j++){
 	 					contents.get(i).add(line[j]);
 	 				}
 	 				i++;
 	 			}
 	 			reader.close();
 	 		}
 		}catch(Exception e){
 			e.printStackTrace();
 		}
				
 		
 		return contents;
 	}
  
     /**
      * Create a new file and write contents to the file
      * @param filename
      * @param contents
      */
     public void updateFile(String filename,ArrayList<ArrayList<String>> contents) {
 		try{
			File file=new File(filename);
			if (file.exists()){
				file.delete();
			}
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
			String output = "";
			for(int i=0;i<contents.size();i++){
				for(int j=0;j<contents.get(i).size();j++){
					output+=contents.get(i).get(j);
					output+=((i==(contents.size()-1)&&(j==(contents.get(i).size())))?"":",");
				}
				output+=lineSep;
			}		
			BufferedWriter writer = new BufferedWriter(outputStreamWriter);
			writer.write(output);
			writer.flush();
			writer.close();			
			
		}catch(Exception e){
			e.printStackTrace();
		}
       
     }
		
	/**
	 * insert new data to the file, separated by ","
	 * @param filename
	 * @param contents
	 */
 	public void insertFile(String filename, ArrayList<String> contents){
 		try{

			String output = "";
			for(int i=0;i<contents.size();i++){
				output+=contents.get(i);
				output+=(i==contents.size()-1?"":",");
			}
			output+=lineSep;	
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_APPEND));
			BufferedWriter writer = new BufferedWriter(outputStreamWriter);
			writer.write(output);
			writer.flush();
			writer.close();			
			
		}catch(Exception e){
			e.printStackTrace();
		}		
			
	}
	
	/**
	 * Read the file in raw, in this game, used for reading question files
	 * @param filename
	 * @return
	 */
 	public ArrayList<ArrayList<String>> readRaw(String filename){
		   ArrayList<ArrayList<String>> contents=new ArrayList<ArrayList<String>>();
		   try{
			   InputStream inputStream = null;
			   if(filename.equals("question1.dat")){
				   inputStream = context.getResources().openRawResource(R.raw.question1);
			   }
			   if(filename.equals("question2.dat")){
				   inputStream = context.getResources().openRawResource(R.raw.question2);
			   }
			   if(filename.equals("question3.dat")){
				   inputStream = context.getResources().openRawResource(R.raw.question3);
			   }
			   if(filename.equals("question4.dat")){
				   inputStream = context.getResources().openRawResource(R.raw.question4);
			   }

			   if ( inputStream != null ) {
				   InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"iso-8859-1");
				   BufferedReader reader = new BufferedReader(inputStreamReader);
				   String thisLine="";
				   int i=0;
				   while ((thisLine = reader.readLine()) != null) {
					   String[] line=thisLine.split(",",0);
					   contents.add(i,new ArrayList<String>());
					   for(int j=0;j<line.length;j++){
						   contents.get(i).add(line[j]);
					   }
					   i++;
				   }
				   reader.close();
			   }
			   
		   }catch(Exception e){
			   e.printStackTrace();
		   }	 		
	 	return contents;			
		     
	   }	   






	


}
