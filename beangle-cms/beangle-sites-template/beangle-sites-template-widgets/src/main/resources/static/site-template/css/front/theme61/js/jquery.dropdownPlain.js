$(function(){

    $("ul.dropdown .first_li").hover(function(){
    
       $(this).addClass("hover");
	    $('a:first',this).addClass("hover_a");
        $('ul:first',this).css('visibility', 'visible');
    
    }, function(){
    
        $(this).removeClass("hover");
		 $('a:first',this).removeClass("hover_a");
        $('ul:first',this).css('visibility', 'hidden');
    
    });
    
    $("ul.dropdown li ul li:has(ul)").find("a:first").append(" &raquo; ");

});