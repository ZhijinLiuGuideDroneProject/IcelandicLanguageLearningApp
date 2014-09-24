/*
 *This javascript file is used to 
 *1. Initial setting and hint point (store in local storage)
 *2. Set setting
 *3. Show setting
 */
 
var dbRecordReset; 
var dbWordListReset; 
dbRecordReset = openDatabase('record','','local database',2*1024*1024);
dbWordListReset = openDatabase('wordlist','','local database',2*1024*1024);
 
 
 /*
  *Read setting, if setting data is not exists, create a new one in local storage as well as hintpoint
  */
 function readSetting(){
	var setting={};
	var hintpoint={};
	setting=JSON.parse(localStorage.getItem('setting')); 	
	if(setting==null){
		var setting={};
		setting.language="english";
		setting.sound="on";
		setting.avatar="male";
		hintpoint.hintpoint="3";		
		localStorage.setItem('setting',JSON.stringify(setting));
		localStorage.setItem('hintpoint',JSON.stringify(hintpoint));
	}	
	showSetting();	
 }
 
 /*
  *Show setting on screen
  */
 function showSetting(){
	 var setting={};
	 setting=JSON.parse(localStorage.getItem('setting'));
	 var language=document.getElementsByName('language');
	 var sound=document.getElementsByName("sound"); 
	 var avatar=document.getElementsByName("avatar");	 
	 for(var i=0;i<language.length;i++){
		 if(language.item(i).value==setting.language){
			 language.item(i).checked=true;
		 }
	 }
	 for(var i=0;i<sound.length;i++){
		 if(sound.item(i).value==setting.sound){
			 sound.item(i).checked=true;
		 }
	 }
	 for(var i=0;i<avatar.length;i++){
		 if(avatar.item(i).value==setting.avatar){
			 avatar.item(i).checked=true;
		 }
	 }	 
 }
 
 /*
  *Update the setting content in the local storage
  */
 function editSetting(option){
	var setting={};
	setting=JSON.parse(localStorage.getItem('setting'));
	var language=document.getElementsByName('language');
	var sound=document.getElementsByName("sound"); 
	var avatar=document.getElementsByName("avatar");
	if(option=="language"){
		for(var i=0;i<language.length;i++){
			if(language.item(i).checked==true){
				setting.language=language.item(i).value;
		 	}
	 	}
	}
	if(option=="sound"){
		for(var i=0;i<sound.length;i++){
			if(sound.item(i).checked==true){
				setting.sound=sound.item(i).value;
				if(sound.item(i).value=='on'){
					play();
				}
				if(sound.item(i).value=='off'){
					pause();
				}
		 	}
	 	}
	}
	if(option=="avatar"){
		for(var i=0;i<avatar.length;i++){
			if(avatar.item(i).checked==true){
				setting.avatar=avatar.item(i).value;
		 	}
	 	}
	}
	localStorage.setItem('setting',JSON.stringify(setting));
 }
 
 /*
  *Reset game: reset setting, hintpoint, wordlist and record
  */
function resetGame(){
	var dropRecordSql="Drop table record";
	var dropWordListSql="Drop table wordlist";
	dbRecordReset.transaction(function(tx)
	{  			
		tx.executeSql(dropRecordSql);	
    });	
	dbWordListReset.transaction(function(tx)
	{  			
		tx.executeSql(dropWordListSql);	
    });
	play();
	localStorage.removeItem('setting');
	localStorage.removeItem('hintpoint');
	localStorage.removeItem('deleteWords');
	localStorage.removeItem('problem');
	localStorage.removeItem('advancedGame');
	localStorage.removeItem('time');
	localStorage.removeItem('count');
	readSetting();
}
 
function resetConfirm(){
	var r=confirm("Do you want to reset the game?");
	if (r==true){
 	 	resetGame();
  	}
}
