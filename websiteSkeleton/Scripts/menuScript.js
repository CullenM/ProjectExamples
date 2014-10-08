$(document).ready(function() {
	var origHeight;
  $('li').hover(function(){
  		origHeight = $(this).height();
  		$(this).css('height','100px');
        $(this).addClass('active');
    },
    function(){
    	$(this).css('height',origHeight);
        $(this).removeClass('active');
    }
  );
});