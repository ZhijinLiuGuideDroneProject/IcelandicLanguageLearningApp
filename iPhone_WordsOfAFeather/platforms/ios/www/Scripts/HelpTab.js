//help
function help(){ 
	$(document).ready(function () {

		//set the default location (fix ie 6 issue)
		$('.lava').css({left:$('span.item:first').position()['left']});
		
		$('.item').mouseover(function () {
			//scroll the lava to current item position
			$('.lava').stop().animate({left:$(this).position()['left']}, {duration:100});
			
			//scroll the panel to the correct content
			$('.panel').stop().animate({left:$(this).position()['left'] * (-3)}, {duration:200});
		});
		
	});
}