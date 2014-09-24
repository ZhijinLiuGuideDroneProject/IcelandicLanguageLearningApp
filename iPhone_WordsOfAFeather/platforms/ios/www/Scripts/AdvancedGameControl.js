/*
 *Advanced game
 *1. Get a question and show on screen
 *2. Check if the question answered correctly
 *3. Calculate time for each level
 *4. Calculate score and record the score
 */

var levelQuestionNo=new Array(2,3,4,5); //how many questions each level has

/*
 *Before start the game, initial the game to set some variables
 *Get the first question
 */
function initialAdvancedGame(){
	var advancedGame={};
	advancedGame.checkpoint=0; //0 means in level 1, 1 in 2, 2 in 3, 3 in 4
	advancedGame.totalScore=0; //total score achieved
	advancedGame.playedQuestionNo=0; //how many questions has been played in each level	
	advancedGame.time=0; //Time spended	
	localStorage.setItem('advancedGame',JSON.stringify(advancedGame));	
	getAdvancedGameQuestion();
	setGameTime();
	showHintPoint();
	showTotalScore(0);
	var stageNo=document.getElementById('stageNumber');
	stageNo.innerText=(advancedGame.checkpoint+1);
}

/*
 *Get question and show on screen, meanwhile the question will be stored in local storage
 */
function getAdvancedGameQuestion(){
	getQuestion('advance');
	var hintpoint={};
	hintpoint=JSON.parse(localStorage.getItem('hintpoint')); 	
	if(hintpoint.hintpoint<=0){
		disabledHintButton();
	}
	else{
		abledHintButton();	
	}
}

/*
 *Set the game time for each level
 */
function setGameTime(){
	var time={};
	time.startTime=new Date().getTime();
	time.pauseTime=0;
	localStorage.setItem('time',JSON.stringify(time));	
}

/*
 *Check if the option choosed is right
 */
function checkAnswerIsRightAdvanced(id){
	var answerChoosed=document.getElementById(id);
	var problem={};
	problem=JSON.parse(localStorage.getItem('problem')); //get question from the local storage
	var advancedGame={};
	advancedGame=JSON.parse(localStorage.getItem('advancedGame')); //get checkpoint, levelscore, totalscore from the local storage
	var time={};
	time=JSON.parse(localStorage.getItem('time')); //get time
	if(answerChoosed.innerHTML==problem.answer){
		var playedQuestion=advancedGame.playedQuestionNo+1;
		advancedGame.playedQuestionNo=playedQuestion;
		var i=advancedGame.checkpoint;
		//when finish one level						
		if(playedQuestion==levelQuestionNo[i]){	
			advancedGame.checkpoint++;							
			//calculate how much time consumed in this level
			var endTime=new Date().getTime();			
			var levelTime=(endTime-time.startTime)/1000-time.pauseTime; 
			advancedGame.time+=levelTime;
			var checkpoint=advancedGame.checkpoint;
			//calculate score for this level
			var levelScore=calculateFinalScore(checkpoint,levelTime);
			//calculate totalScore, write to the local storage			
			advancedGame.totalScore=advancedGame.totalScore+levelScore;
			showTotalScore(advancedGame.totalScore);
			if(checkpoint!=4){
				alert("Congratulations!!!\nThe time for stage "+checkpoint+" is: "+Math.round(levelTime)+"s.\nKeep calm and carry on!");
				var stageNo=document.getElementById('stageNumber');
				stageNo.innerText=(advancedGame.checkpoint+1);
			}
			addHintPoint();
			setGameTime();			
			advancedGame.playedQuestionNo=0;
		}
		localStorage.setItem('advancedGame',JSON.stringify(advancedGame));
		initialOptionButton();
		getAdvancedGameQuestion();
		if(checkpoint==4){
			var name=prompt("Congratulations!\nPlease enter your name\n(No more than 7 characters)","");
			while(name.length>7){
				name=prompt("Exceed 7!\nPlease enter again\n(No more than 7 characters)","");
			}
			if(name.length==0){
				name="Player";
			}
			addRecord(name, Math.round(advancedGame.totalScore), Math.round(advancedGame.time));
			history.back();
		}
	}
	else{
		var message=generateRandomMessage(problem);		
		var r=confirm(message);
		if (r==true){
 	 		advancedGame.playedQuestionNo=0;
			localStorage.setItem('advancedGame',JSON.stringify(advancedGame));
			setGameTime();
			initialOptionButton();
			getAdvancedGameQuestion();
  		}
		else{
			history.back();
		}
		
	}	
}

/*
 *Calculate Score for each level
 */
function calculateFinalScore(checkPoint,levelTime){	
	var levelScore;
	if(levelTime<=(5+(checkPoint-1)*5)){
		levelScore=(5+(checkPoint-1)*5)-levelTime+20;
	}
	if(levelTime>(5+(checkPoint-1)*5)&&levelTime<=(10+(checkPoint-1)*5)){
		levelScore=(10+(checkPoint-1)*5)-levelTime+10;
	}
	if(levelTime>(10+(checkPoint-1)*5)&&levelTime<=(15+(checkPoint-1)*5)){
		levelScore=(15+(checkPoint-1)*5)-levelTime;
	}
	if(levelTime>(15+(checkPoint-1)*5)&&levelTime<=(20+(checkPoint-1)*5)){
		levelScore=(20+(checkPoint-1)*5)-levelTime-10;
	}
	if(levelTime>(20+(checkPoint-1)*5)&&levelTime<=(25+(checkPoint-1)*5)){
		levelScore=(20+(checkPoint-1)*5)-levelTime-20;
	}
	if(levelTime>(25+(checkPoint-1)*5)){
		levelScore=-20;
	}
	return levelScore;
}

/*
 *After finish each level, print the total score the user got to the screen
 */
function showTotalScore(totalScore){
	var score=document.getElementById('scoreNumber');
	score.innerHTML=Math.round(totalScore);
}

/*
 *Generate message, when died
 */
function generateRandomMessage(problem){
	var message="The answer is:\n"+problem.question+": "+problem.answer;
	var messageNo=Math.floor(Math.random()*3);
   	if(messageNo==0){
		message+="\n\nDon't give up!";
   	}
  	if(messageNo==1){
   		message+="\n\nJust a little frustration. Keep going!";
   	}
   	if(messageNo==2){
   		message+="\n\nKeep calm and carry on!";	
   	}
	message+="\nWould you like to try again?";	
	return message;	
}

/*
 *If pausebutton pressed, calculate the pausetime
 */
function calculatePauseTime(){
	var pauseStartTime={};
	pauseStartTime=JSON.parse(localStorage.getItem('pauseStartTime'));
	var time={};
	time=JSON.parse(localStorage.getItem('time'));
	var endPauseTime=new Date().getTime();	
	time.pauseTime+=(endPauseTime-pauseStartTime.startTime)/1000;
	localStorage.setItem('time',JSON.stringify(time));
	localStorage.removeItem('pauseStartTime');
	
}

/*
 *If pausebutton pressed, store the pause start time
 */
function startPauseTime(){
	var pauseStartTime={}; 
	pauseStartTime.startTime=new Date().getTime();
	localStorage.setItem('pauseStartTime',JSON.stringify(pauseStartTime));
}

/*
 *If the hintpoint is used, reduce one
 *Set Button disabled, because for each question, can only use once
 */
function useHintPoint(){
	var hintpoint={};
	hintpoint=JSON.parse(localStorage.getItem('hintpoint')); 	
	hintpoint.hintpoint--;
	var hintValue=document.getElementById('hintNumber');
	hintValue.innerHTML=hintpoint.hintpoint;
	localStorage.setItem('hintpoint',JSON.stringify(hintpoint));
	randomWrongAnswer();
	disabledHintButton();
}

/*
 *Randomly get a wrong answer and change the button to states-disable
 */
function randomWrongAnswer(){		
	var loop=true;
	while(loop){
		var randomOption=Math.floor(Math.random()*4);
		if(randomOption==0){
			var option=document.getElementById('iceBlueAdvanced1');
			var optionTest=document.getElementById('AOptionA');
			var getWord=optionTest.innerHTML;
			if(disabledWrongAnswer(getWord)){
				deleteWrongOption(option,optionTest);	
				loop=false;
			}
		}
		if(randomOption==1){
			var option=document.getElementById('iceBlueAdvanced2');
			var optionTest=document.getElementById('AOptionB');
			var getWord=optionTest.innerHTML;
			if(disabledWrongAnswer(getWord)){
				deleteWrongOption(option,optionTest);				
				loop=false;
			}
		}
		if(randomOption==2){
			var option=document.getElementById('iceBlueAdvanced3');
			var optionTest=document.getElementById('AOptionC');
			var getWord=optionTest.innerHTML;
			if(disabledWrongAnswer(getWord)){
				deleteWrongOption(option,optionTest);					
				loop=false;
			}
		}
		if(randomOption==3){
			var option=document.getElementById('iceBlueAdvanced4');
			var optionTest=document.getElementById('AOptionD');
			var getWord=optionTest.innerHTML;
			if(disabledWrongAnswer(getWord)){
				deleteWrongOption(option,optionTest);						
				loop=false;
			}
		}		
	}
}

/*
 *Disabled the button
 */
function deleteWrongOption(option,optionTest){
	optionTest.onclick=function() {};	
	option.className="iceWrong";
}

/*
 *Check if the random button is not the answer
 */
function disabledWrongAnswer(getWord){
	var problem={};
	problem=JSON.parse(localStorage.getItem('problem'));
	if(getWord!=problem.answer){
		return true;
	}
	else{
		return false;	
	}
}

/*
 *Change all options' button to the initial state
 */
function initialOptionButton(){
	var optionA=document.getElementById('iceBlueAdvanced1');
	var optionB=document.getElementById('iceBlueAdvanced2');
	var optionC=document.getElementById('iceBlueAdvanced3');
	var optionD=document.getElementById('iceBlueAdvanced4');
	optionA.className="iceBlue";
	optionB.className="iceBlue";
	optionC.className="iceBlue";
	optionD.className="iceBlue";
	var optionTestA=document.getElementById('AOptionA');
	var optionTestB=document.getElementById('AOptionB');
	var optionTestC=document.getElementById('AOptionC');
	var optionTestD=document.getElementById('AOptionD');
	optionTestA.onclick=function() {checkAnswerIsRightAdvanced('AOptionA')};
	optionTestB.onclick=function() {checkAnswerIsRightAdvanced('AOptionB')};
	optionTestC.onclick=function() {checkAnswerIsRightAdvanced('AOptionC')};
	optionTestD.onclick=function() {checkAnswerIsRightAdvanced('AOptionD')};
}

/*
 *Show the hint point on the screen
 */
function showHintPoint(){
	var hintpoint={};
	hintpoint=JSON.parse(localStorage.getItem('hintpoint')); 	
	if(hintpoint==null){
		var hintpoint={};
		hintpoint.hintpoint="3";		
		localStorage.setItem('hintpoint',JSON.stringify(hintpoint));
	}	
	hintpoint=JSON.parse(localStorage.getItem('hintpoint')); 
	var hintValue=document.getElementById('hintNumber');
	hintValue.innerHTML=hintpoint.hintpoint;
	if(hintpoint.hintpoint<=0){
		disabledHintButton();
	}
	else{
		abledHintButton();	
	}
}

/*
 *Add hintpoint
 */
function addHintPoint(){
	var hintpoint={};
	hintpoint=JSON.parse(localStorage.getItem('hintpoint')); 	
	hintpoint.hintpoint++;
	var hintValue=document.getElementById('hintNumber');
	hintValue.innerHTML=hintpoint.hintpoint;
	localStorage.setItem('hintpoint',JSON.stringify(hintpoint));
}

function disabledHintButton(){
	var hintButton=document.getElementById('hintButton');
	hintButton.className='hintButtonDisabled';	
	var hintText=document.getElementById('useHint');
	hintText.onclick=function(){};	
}

function abledHintButton(){
	var hintButton=document.getElementById('hintButton');
	hintButton.className='hintButton';	
	var hintText=document.getElementById('useHint');
	hintText.onclick=function(){useHintPoint()};
}


