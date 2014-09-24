/*
 *General game
 *1. Get a question and show on screen
 *2. Check if the question answered correctly
 *3. Count how many questions have been answered correctly
 */

/*
 *Start a new general game
 *Set all to default and get a new question
 */
function startGeneralGame(){
	initialCount();
	initialButton();	
	initialPuffinandBubble();
	getGeneralGameQuestion();
}

/*
 *Get question and show on screen, meanwhile the question will be stored in local storage
 */
function getGeneralGameQuestion(){
	getQuestion('general');
	var result=document.getElementById('result');
	result.innerHTML="";
}

/*
 *Check if the option choosed is right
 */
function checkAnswerIsRight(id){
	disabledButton();
	var answerChoosed=document.getElementById(id);	
	var result=document.getElementById('result');
	var problem={};
	problem=JSON.parse(localStorage.getItem('problem')); //get question from the local storage
	if(answerChoosed.innerHTML==problem.answer){
		var state="right";
		changeColor(id,state);
		setCount();
		getCount();
		showPuffin();
		showBubble();
		result.innerHTML="Congratulations!</br>Go next!";
	}
	else{
		var state="wrong";
		changeColor(id,state);
		showPuffin();
		showBubble();
		result.innerHTML="Sorry, the answer is:</br>"+problem.answer;
	}	
}

/*
 *Every time, start a new game, set the count to 0
 */
function initialCount(){
	var count={};
	count.count=0;	
	localStorage.setItem('count',JSON.stringify(count));
	getCount();
}

/*
 *Read the count Number from local storage
 */
function getCount(){
	var count={};	
	count=JSON.parse(localStorage.getItem('count'));
	var countNumber=document.getElementById('countNumber');
	countNumber.innerHTML=count.count;
}

/*
 *count=count+1
 */
function setCount(){
	var count={};
	count=JSON.parse(localStorage.getItem('count')); 	
	if(count==null){
		var count={};
		count.count=0;	
		localStorage.setItem('count',JSON.stringify(count));
	}
	count=JSON.parse(localStorage.getItem('count'));
	count.count++;
	localStorage.setItem('count',JSON.stringify(count));		
}

/*
 *Set the puffin image according to the setting
 */
function showPuffin(){
	var puffin=document.getElementById('puffin');	
	var setting={};
	setting=JSON.parse(localStorage.getItem('setting')); 			
	if(setting==null||setting.avatar=='male'){			
		puffin.className='puffinMale';
	}
	else{
		puffin.className='puffinFemale';
	}	
}

/*
 *Show bubble image
 */
function showBubble(){
	var bubble=document.getElementById('bubble');
	bubble.className='bubble';
}

/*
 *Set the puffin and bubble to default--empty
 */
function initialPuffinandBubble(){
	var puffin=document.getElementById('puffin');	
	puffin.className='puffinEmpty';
	var bubble=document.getElementById('bubble');
	bubble.className='bubbleEmpty';	
}

/*
 *After user choose one option, disabled all options
 */
function disabledButton(){
	var OptionA=document.getElementById('optionA');
	var OptionB=document.getElementById('optionB');
	var OptionC=document.getElementById('optionC');
	var OptionD=document.getElementById('optionD');
	OptionA.onclick=function() {};
	OptionB.onclick=function() {};
	OptionC.onclick=function() {};
	OptionD.onclick=function() {};
}

/*
 *When next button click, set all options abled and get a new word
 */
function goNextQuestion(){
	initialButton();
	initialPuffinandBubble();
	getGeneralGameQuestion();		
}

/*
 *Change the color of the buttons
 */
function changeColor(id,state){
	var ice1=document.getElementById('iceBlueGeneral1');
	var ice2=document.getElementById('iceBlueGeneral2');
	var ice3=document.getElementById('iceBlueGeneral3');
	var ice4=document.getElementById('iceBlueGeneral4');
	var ice=new Array(ice1,ice2,ice3,ice4);
	var iceId=new Array("optionA","optionB","optionC","optionD");	
	for(var i=0;i<ice.length;i++){
		if(id==iceId[i]){
			if(state=='right'){
				ice[i].className='iceRight';
			}
			else{
				ice[i].className='iceWrong';
			}
		}
		else{
			ice[i].className='iceDisabled';
		}
	}	
}

/*
 *Set the buttons abled and to initial color
 */
function initialButton(){
	var OptionA=document.getElementById('optionA');
	var OptionB=document.getElementById('optionB');
	var OptionC=document.getElementById('optionC');
	var OptionD=document.getElementById('optionD');
	var ice1=document.getElementById('iceBlueGeneral1');
	var ice2=document.getElementById('iceBlueGeneral2');
	var ice3=document.getElementById('iceBlueGeneral3');
	var ice4=document.getElementById('iceBlueGeneral4');
	var addButton=document.getElementById('addButton');
	OptionA.onclick=function() {checkAnswerIsRight('optionA')};
	OptionB.onclick=function() {checkAnswerIsRight('optionB')};
	OptionC.onclick=function() {checkAnswerIsRight('optionC')};
	OptionD.onclick=function() {checkAnswerIsRight('optionD')};	
	ice1.className='iceBlue';
	ice2.className='iceBlue';
	ice3.className='iceBlue';
	ice4.className='iceBlue';	
	addButton.className='addButton';
	addButton.onclick=function() {addWord()};
}











