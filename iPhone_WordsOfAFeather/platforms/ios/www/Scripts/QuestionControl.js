/*
 *This javascript file is used to 
 *1. Write all questions to the local webSql database(4 table: question1, question2, question3, question4);
 *2. Get one question from the database randomly
 */


//Open the database connection  
var db; 
db = openDatabase('question','','local database',2*1024*1024);
var success=false; //if the data inserted correctly 

/*
 *write all the question files: question1-4 to the local web sql
 */
function writeQuestion(){
	//All questions and relatived options		
	var questionfile1_question=new Array("Aðalbækistöðvar","Aðalréttur","Afsökunarbeiðni","Aðfangadagskvöld","Aðferðafræði","Aðframkominn","Aðgangsharður","Aðgerðaleysi","Aðgöngumiði","Aðhlátursefni","Aðildarumsókn","Aðkomumaður","Aðlögunarhæfni","Aðsetursskipti","Aðskotahlutur","Aðstoðarflugmaður","Aðventukrans","Aðþrengdur","Afbrigðilegur","Afturhjóladrif");		
	var questionfile2_question=new Array("Afburðanemandi","Afeitrun","Afgangur","Afgerandi","Afgirtur","Afgreiðsluborð","Afhendingardagur","Afhjúpa","Afhýða","Afkastalítill","Afkastamikill","Afklæðast","Aflabrestur","Aflaheimild","Aflangur","Aflausn","Aflaverðmæti","Afleggjari","Afleiðing","Afleysingamaður","Aflraunamaður","Aflögufær");		
	var questionfile3_question=new Array("Afmælisbarn","Afmælisdagur","Afmælisveisla","Afneita","Afnotaréttur","Afpöntun","Afrakstur","Afráða","Afrek","Afreksmaður","Afrétt","Afréttari","Afrískur","Afrækja","Afsaka","Afsanna","Afskiptaleysi","Afskiptasamur","Afskráning","Afslappaður","Afsláttarmiði","Afslöppun","Afstaða","Afstaðinn");
	var questionfile4_question=new Array("Aðdáendaklúbbur","Afstæðiskenning","Aftakaveður","Aftanákeyrsla","Afturhjól","Afturhjóladrif","Afur","Afþakka","Afþreying","Afþreyingarbókmenntir","Afþurrkunarklútur","Aganefnd","Agnarlítill","Akbraut","Akstursíþrótt","Akurhæna","Akuryrkja","Aldagamall","Aldamót","Aldarafmæli","Aldingarður","Aldurshópur","Aleiga","Aleinn","Alelda");	
	
	var questionfile1_answer=new Array("Headquarters","Main course","Request for apology","Christmas eve","Methodology","Exhausted","Aggressive","Inaction","Admission ticket","The subject of ridicule","Membership application","Stranger","Adaptability","Change of address","Foreign object","Co-pilot","Christmas wreath","In difficult straits","Abnormal","Rear-wheel drive");	
	var questionfile2_answer=new Array("Exceptional student","Detox","Remainder","Decisive","Fenced off","Counter","Delivery date","Expose","Peel","Unproductive","Productive","Undress","Fishing failure","Fishing permission","Oblong","Absolution","Catch value","Secondary road","Consequence","Substitute","Strongman","Have something to spare");	
	var questionfile3_answer=new Array("Birthday child","Birthday","Birthday party","Renounce","Right of use","Cancellation","Profits","Determine","Achievement","Achiever","Highland pasture","Hair of the dog","African","Neglect","Excuse","Disprove","Indifference","Meddlesome","Deregistration","Relaxed","Coupon","Relaxation","Position","Finished");	
	var questionfile4_answer=new Array("Fan club","Theory of relativity","Furious weather","Rear-ending","Rear wheel","Rear-wheel drive","Product","Decline","Amusement","Popular literature","Dusting cloth","Disciplinary committee","Tiny","Roadway","Motorsport","Game hen","Agriculture","Ancient","Turn of the century","Centennial","Orchard","Age group","Entire possessions","Alone","Ablaze");
	
	var questionfile1_option1=new Array("Doorway","Minor error","User manual","Winding road","Amperage","Fashionable","Irritated","Lack of limits","Spare tire","Alphabetical order","Marriage license","Student","Claustrophobia","Membership application","Loyal supporter","Additional forces","Candlelight","On leave","Transparent","Bicycle");	
	var questionfile2_option1=new Array("Troubled student","Medical treatment","Hallway","Limiting","Untidy","Surgical table","Deadline","Cover","Expose","Weak","Efficient","Upholster","Cracked gunwale","Hunting licence","Lengthy","Execution","Cost of living","Decommissioned route","Connection","Part-time aid","Bouncer","Have something to fix");	
	var questionfile3_option1=new Array("New-born baby","Doomsday","Funeral gathering","Refuse","Right of way","Order","Loss","Execute","Mistake","Failure","Corral","Hair straightener","Not well","Protect","Accuse","Verify","Inability","Divisive","Registration","Undressed","Admission ticket","Detox","Retirement","Retired");	
	var questionfile4_option1=new Array("Collectors club","Isolated teachings","Weather forecast","Rear driveway","Spare tire","Four-wheel drive","Result","Insult","Argument","Banned literature","Handkerchief","Law committee","Shrinking","Pavement","Triathlon","Partridge","Freelancing","100 years old","New-years","Millennial","Herb garden","Senior citizens","No possessions","Not alone","Flammable");
	
	var questionfile1_option2=new Array("Lorry","Mountain","Book of recipes","Narrow valley","Fence","Limited","Uneducated","Loneliness","Backdoor","Unsolved crime","High school diploma","Mechanic","Laziness","Driver's license","Limited access","Minor player","Cardamoms","Retired","Freezing","Roof-top window");	
	var questionfile2_option2=new Array("Top Applicant","Understanding","Rooftop","Thought-provoking","Brightly coloured","Gateway","Landing place","Paint","Stretch","Cross-eyed","Shy","Seduce","Flawed argument","Catalogue","Interesting","Confirmation","Psychiatric treatment","Coastline","Mistake","Supervisor","Scribe","Unreliable");	
	var questionfile3_option2=new Array("Illegitimate offspring","Deadline","Retirement feast","Object","Highland pasture","Home delivery","Close shave","Cancel","Demand","Criminal","Fields","Lever","healthy","Renounce","Release","Support","Lack of division","Argumentative","Reregistration","Slapped","One-time offer","Assault","Starting point","Incomplete");	
	var questionfile4_option2=new Array("Mourners association","Independent teachings","Not weather to speak of"," Rear entrance","Front wheel","All-wheel drive","Decision","Abolish","Exhaustion","Literary theory","Napkin","Nick name","New-borne","Pedestrian crossing","Speed bump","Chicken","Pastoral poetry","Born yesterday","Reunion","50th anniversary","Field","Target group","Equipment rental","Never alone","Flame-retardant");
	
	var questionfile1_option3=new Array("Committee","Husband","Litter of puppies","End of the month","Lighthouse","Silly","Hungry","Forgetfulness","Afternoon","Border dispute","Criminal record","Illustrator","Procrastination","Request extension","Hailstorm","Junky","Commonalities","Of no interest","Salty","Mountain top");	
	var questionfile2_option3=new Array("Judge","Lack of time","City limits","Flimsy","Shining","Fencepost","Corn field","Plaster","Comprehend","Damp","Clumsy","Instruct","Putty","Hearing aid","Fragile","Solution","Final offer","Hillock","Liability","Barrister","Layman","Limitless");	
	var questionfile3_option3=new Array("Teenager","End of month","Dinnertime","Agree","Heath","Menu","Understanding","Alert","Inquiry","Accountant","Playground","Crowbar","limitless","Include","Hinder","Divide","Partiality","Jealous","Selection","Tired","Attraction","Treatment","Goal","Unclear");	
	var questionfile4_option3=new Array("Maritime association ","Obsolete theory","Weather calming down","Reverse gear","Unicycle","Front-wheel drive","Consequences","Neglect","Long wait","Rhetoric","Tablecloth","Surname","Dwarf","Path","Reckless driving ","Ptarmigan","Game hen ","Of unknown age","Ocean surge ","10th anniversary","Graveyard","Underage population","Yearly rental","Aluminium rod","Pre-cooked");
		
	var question_array=new Array(questionfile1_question,questionfile2_question,questionfile3_question,questionfile4_question);
	var answer_array=new Array(questionfile1_answer,questionfile2_answer,questionfile3_answer,questionfile4_answer);
	var option1_array=new Array(questionfile1_option1,questionfile2_option1,questionfile3_option1,questionfile4_option1);
	var option2_array=new Array(questionfile1_option2,questionfile2_option2,questionfile3_option2,questionfile4_option2);
	var option3_array=new Array(questionfile1_option3,questionfile2_option3,questionfile3_option3,questionfile4_option3);
	var table_array=new Array("question1", "question2", "question3", "question4");

	//If table exists, delete the table. Avoiding same data insert into database again
	db.transaction(function(tx)
	{ 
		tx.executeSql('Drop table question1');
		tx.executeSql('Drop table question2');
		tx.executeSql('Drop table question3');
		tx.executeSql('Drop table question4');
	});

	for(var i=0;i<question_array.length;i++){
		writeQuestionFile(question_array[i],answer_array[i],option1_array[i],option2_array[i],option3_array[i],table_array[i]);
	}
}

/*
 *Write a table
 */
function writeQuestionFile(question_array,answer_array,option1_array,option2_array,option3_array,table_array){			
	for(var i=0;i<question_array.length; i++){
		var question = question_array[i];  
    	var answer = answer_array[i]; 
		var option1 = option1_array[i];  
		var option2=option2_array[i]; 
		var option3=option3_array[i]; 
		var table=table_array;	
		addData(question,answer,option1,option2,option3,table);
	}	
}

/*
 *Write data into each table (if table not exists, create a table first)
 */ 
function addData(question,answer,option1,option2,option3,table){  
	var createSql="create table if not exists "+table+"(question text,answer text,option1 text,option2 text,option3 text)";
	var insertSql="insert into "+table+" values(?,?,?,?,?)";

    db.transaction(function(tx)
	{  	
	 	tx.executeSql(createSql,[])
		tx.executeSql(insertSql,[question,answer,option1,option2,option3],onSuccess,onError)		
    });  
}  
 
function onSuccess(tx,rs){  
    success=true; 	
}  
 
function onError(tx,error){  
	success=false;
	alert("Data cannot insert correctly");
}  

/*
 *Get one question from the database randomly
 *write the question geted into the local storage
 *format: problem, {question, answer,option1, option2, option3}
 *and random the options of the question in order to get a valid question
 */
function getQuestion(model){  
    //choose a table randomly
	var tableNo=Math.floor((Math.random()*4)); 
	var table;
	switch(tableNo){
		case 0: table="question1"; break;
		case 1: table="question2"; break;
		case 2: table="question3"; break;
		case 3: table="question4"; break;
	}
	//get a question randomly from the table selected and do the other things
	selectSql="select * from "+table;
    db.transaction(function(tx){   
        tx.executeSql(selectSql,[],function(tx,rs){  
       		if(rs.rows.length>0)
		   	{                
				var total=rs.rows.length;
				var i=Math.floor((Math.random()*total));
                var row = rs.rows.item(i);  
				writeQuestionToLocalStorage(row.question, row.answer,row.option1,row.option2,row.option3);					
				var problemReal={};
				problemReal=getValidQuestion();	
				if(model=='general'){
					showRealQuestionGeneral(problemReal);	
				}
				if(model=='advance'){
					showRealQuestionAdvance(problemReal);
				}
           	}
		   	else
		   	{  
           		list.innerHTML = "Database is empty!\nPlease quit the game and re-enter~";  
           	}		   
        });  
    }); 
}

/*
 *Insert the question to  the local storage
 */
function writeQuestionToLocalStorage(question, answer,option1,option2,option3){
	var problem = {};
	problem.question= question;
	problem.answer = answer;
	problem.option1=option1;
	problem.option2=option2;
	problem.option3=option3;
	localStorage.setItem('problem',JSON.stringify(problem));
}

/*
 *Change the option orders
 */
function getValidQuestion(){
	var problem={};
	problem=JSON.parse(localStorage.getItem('problem')); //get question from the local storage
	var optionArray=new Array(4); //stores 4 different number
	for(var i=0;i<optionArray.length;i++){
		optionArray[i]=getRandomNumber(optionArray);
	}
	
	var optionsReal=new Array(4); //stores the options which already been upsetted
	for(var i=0;i<optionArray.length;i++){
		switch(optionArray[i]){
			case 1: optionsReal[i]=problem.answer; break;
			case 2:	optionsReal[i]=problem.option1; break;
			case 3: optionsReal[i]=problem.option2; break;
			case 4: optionsReal[i]=problem.option3; break;
		}
	}
	var problemReal={};
	problemReal.question=problem.question;
	problemReal.optionA=optionsReal[0];
	problemReal.optionB=optionsReal[1];
	problemReal.optionC=optionsReal[2];
	problemReal.optionD=optionsReal[3];
	return problemReal;
}

/*
 *Produce different random array in array optionArray
 */
function getRandomNumber(optionArray){
	var valid=false;
	var randomNo;
	while(valid==false){
		randomNo=Math.floor((Math.random()*4)+1);
		for(var i=0;i<optionArray.length;i++){
			if(randomNo==optionArray[i]){
				valid=false; break;
			}
			else{
				valid=true;
			}
		}		
	}
	return randomNo;
}

/*
 *Print the question to the page
 */
function showRealQuestionGeneral(problemReal){
	var question=document.getElementById("question");
	var optionA=document.getElementById("optionA");
	var optionB=document.getElementById("optionB");
	var optionC=document.getElementById("optionC");
	var optionD=document.getElementById("optionD");
	question.innerHTML=problemReal.question;
	optionA.innerHTML=problemReal.optionA;
	optionB.innerHTML=problemReal.optionB;
	optionC.innerHTML=problemReal.optionC;
	optionD.innerHTML=problemReal.optionD;
}

/*
 *Print the question to the page
 */
function showRealQuestionAdvance(problemReal){
	var question=document.getElementById("AQuestion");
	var optionA=document.getElementById("AOptionA");
	var optionB=document.getElementById("AOptionB");
	var optionC=document.getElementById("AOptionC");
	var optionD=document.getElementById("AOptionD");
	question.innerHTML=problemReal.question;
	optionA.innerHTML=problemReal.optionA;
	optionB.innerHTML=problemReal.optionB;
	optionC.innerHTML=problemReal.optionC;
	optionD.innerHTML=problemReal.optionD;
}