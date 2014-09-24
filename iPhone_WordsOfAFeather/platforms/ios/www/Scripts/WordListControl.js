/*
 *This javascript file is used to 
 *1. Add a word to word list
 *2. Count how many words in the word list
 *3. Delete select words from word list
 *4. Show the word list
 */
 
//Open the database connection  
var dbWordList; 
dbWordList = openDatabase('wordlist','','local database',2*1024*1024);
var insertWordListSuccess=false; //if the data inserted correctly

/*
 *Add a word to the word list
 *This function read the word saved in the local storage
 *format: problem, {question, answer,option1, option2, option3}
 *and write it to the word list if the word has not been inserted into the word list
 */  
 function addWord(){
	var problem={};
	problem=JSON.parse(localStorage.getItem('problem')); //get question from the local storage
	var question=problem.question;
	var answer=problem.answer;
	var createSql="create table if not exists wordlist(question text,answer text)";
	var judgeWordSql="select * from wordlist where question= ?";
	var insertSql="insert into wordlist values(?,?)"; 
	
	dbWordList.transaction(function(tx)
	{  		
	 	tx.executeSql(createSql,[])
		//judge if the data already exists in the database, if not, insert into the database	
		tx.executeSql(judgeWordSql,[question],function(tx,rs){  
       		if(rs.rows.length==0){
				tx.executeSql(insertSql,[question,answer],onSuccess,onError);
			}
		});
    });
	//disabled add button
	var addButton=document.getElementById('addButton');
	addButton.className='addButtonDisabled';
	addButton.onclick=function() {};
 }
 
function onSuccess(tx,rs){  
    insertWordListSuccess=true; 	
}  
 
function onError(tx,error){  
	insertWordListSuccess=false;
	alert("Data cannot insert correctly");
}  

/*
 *count how many words in the word list
 */
function countWordNumber(){
	var number=0;
	var createSql="create table if not exists wordlist(question text,answer text)";
	var countSql="select * from wordlist";
	dbWordList.transaction(function(tx)
	{  		
		tx.executeSql(createSql,[])
		tx.executeSql(countSql,[],function(tx,rs){ 
			var rows=rs.rows.length;
			var countNo=document.getElementById("countNo");
			countNo.innerHTML=rows;
		});
    });
}

/*
 *Show the words in word list (ordered by question, from a-z)
 *And store the words to local storage for future use
 */
function showList(){
	var list = document.getElementById("list"); 
	var deleteWordsWord=new Array();
	var deleteWordsAnswer=new Array();
	var createSql="create table if not exists wordlist(question text,answer text)";
	var showListSql="select * from wordlist order by question";
	dbWordList.transaction(function(tx){
		tx.executeSql(createSql,[])
		tx.executeSql(showListSql,[],function(tx,rs){
			if(rs.rows.length>0){
				var result="<ul>";
				for(var i=0;i<rs.rows.length;i++){
					var row = rs.rows.item(i);
					deleteWordsWord[i]=row.question;
					deleteWordsAnswer[i]=row.answer;
					result+="<li class='listGap'><p style='text-shadow:none'>"+row.question+"</p><p class='wordGap'>"+row.answer+"</p></li>";
					if(i!=(rs.rows.length-1)){
						result+="<li class='lineGap'></li>";
					}
				}
				list.innerHTML=result;
				writeWordsToLS(deleteWordsWord,deleteWordsAnswer);
			}
			else{
				list.innerHTML="";
			}
		});
	});
}

/*
 *Write the words in the word list to local storage
 */
function writeWordsToLS(word,answer){
	var deleteWords = {};
	deleteWords.word= word;
	deleteWords.answer=answer;
	localStorage.setItem('deleteWords',JSON.stringify(deleteWords));
}

/*
 *Get the word from the local storage and Show the words with check box
 */
function editList(){
	var list = document.getElementById("listEdit"); 
	var deleteWords={};
	deleteWords=JSON.parse(localStorage.getItem('deleteWords')); 
	var words=new Array();
	var answers=new Array();
	words=deleteWords.word;
	answers=deleteWords.answer;
	var result="<table width='100%;'><ul>";
	for(var i=0;i<words.length;i++){		
		result+="<tr>";
		result+="<td width='15%;'><input type='checkbox' name='delete' class='checkbox'></td>"
		result+="<td width='85%;'><li style='list-style-type:none;'><p style='text-shadow:none'>"+words[i]+"</p><p class='editWordGap'>"+answers[i]+"</p></li>";
		result+="</td></tr>"		
		if(i!=(words.length-1)){
			result+="<tr><td colspan='2'><li class='editLineGap'></li></td></tr>";
		}		
	}
	list.innerHTML=result;	
}


/*
 *Check which box selected and delete the words
 */
 function deleteCheckedWords(){
	var wordsDeleted=new Array();//words to be deleted
	//read all words in the list from the local storage
	var deleteWords={};
	deleteWords=JSON.parse(localStorage.getItem('deleteWords')); 
	var words=new Array();
	words=deleteWords.word;
	var box=document.getElementsByName('delete');
	var j=0;
	for(var i=0;i<box.length;i++){
		if(box.item(i).checked){
			wordsDeleted[j]=words[i];
			j++;
		}
	}
	deleteWord(wordsDeleted);	
	localStorage.removeItem('deleteWords');
 }
 
 
/*
 *Get the word selected one by one and delete the selected words from the word list
 */
function deleteWord(wordsDeleted){
	
	for(var i=0;i<wordsDeleted.length;i++){
		var question=wordsDeleted[i];
		deleteTransaction(question);
	}
}

/*
 *Delete the word
 */
function deleteTransaction(question){
	var deleteSql="delete from wordlist where question = ?";
	dbWordList.transaction(function(tx)
	{
		tx.executeSql(deleteSql,[question])
	});
}