var audio = null;
audio = document.createElement("audio");
audio.src = "Music/guessmusic.mp3";
audio.autoplay = false;
audio.addEventListener('ended', function () {
	audio.play();
}, false);

// Multiple calls in a row of the play button won't have effect
function play() {
	audio.play();
}

// Resetting currentTime will restart the sound
function pause() {
	 audio.pause();
}

function initialPlay(){
	var setting={};
	setting=JSON.parse(localStorage.getItem('setting')); 	
	if(setting==null){
		var setting={};
		setting.language="english";
		setting.sound="on";
		setting.avatar="male";	
		localStorage.setItem('setting',JSON.stringify(setting));
	}	
	if(setting.sound=='on'){
		audio.play();	
	}
	if(setting.sound=='off'){
		audio.pause();
	}
}