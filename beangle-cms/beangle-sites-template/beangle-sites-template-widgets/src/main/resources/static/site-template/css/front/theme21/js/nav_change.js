// JavaScript Document


$(document).ready(function(){
    $(".div_2").corner("5px");
    //$("dd:not(:first)").hide();
    $("dd").hide();
    $("dt .li4").prepend("<span>+</span>");
		
    $("dt .div_2, dd .div_3").hover(function() { 
		$(this).removeClass("div_3_bg");
        $(this).addClass("li_hover");
    }, function() { 
        if($(this).attr("selected") != "true"){
            $(this).removeClass("li_hover");
					$(this).addClass("div_3_bg");

        }
    }); 
		
    $("dt a").click(function(){
        
        if( $(this).attr("class")=="li2"  ) {
        //alert( $(this).attr("class"))
        //$(this).removeClass("li3");
        } else{
            var div = $(this).parent();
            $("div[selected='true']").each(function (){
                $(this).removeClass("li_hover");
                div.attr("selected", "");
            })
            div.addClass("li_hover");
            div.attr("selected", "true");
            if( $(this).attr("class")=="li3"  ){
                //$("dd:visible").slideUp("slow");
                //已经是打开状态的dd，将被收起
                //$(this).parent().next().slideDown("slow");
                //点击此dt后，它的父级中在它下面的dd会被打开。                
                $(this).parent().parent().next("dd").find(".jquery-corner").each(function(){
                    $(this).parent().attr("style","");
                    $(this).remove();
                });
                $(this).parent().parent().next("dd").slideUp("slow");
                $("span",this).remove();
                $(this).prepend("<span>+</span>");
                $(this).removeClass("li3");
                $(this).addClass("li4");
                return false;
            }else{
                $(this).addClass("li3");
                $(this).removeClass("li4");
                $("span",this).remove();
                $(this).prepend("<span>-</span>");
                $(this).parent().parent().next("dd").slideDown("slow", function (){
                    $(".div_3", this).corner("5px");
                });	
                ////$(this).parent().parent().next("dd .div_3").corner("5px");
                return false;
            }					
        }
    });
});
