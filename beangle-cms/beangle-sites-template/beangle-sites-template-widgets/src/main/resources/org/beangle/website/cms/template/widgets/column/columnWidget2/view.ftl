<#include "/widgetComm.ftl"/>
<script type="text/javascript" src="${base}/js/jquery.corner.js"></script>
<script type="text/javascript">

    $(document).ready(function(){
        $(".Nav2 .menu").corner("5px");

        $(".Nav2 .menu").hover(function() { 
            $(this).addClass("li_hover");
        }, function() { 
            if($(this).attr("currentColumn") != "true"){			
                $(this).removeClass("li_hover");
            }
        }); 
        $(".Nav2 .span_1").each(function(){
            var div = $(this).parent();
            div.attr("running", "0");
            div.click(function (){
                if(div.attr("running") !="0"  ){ 
                    return;
                }
                div.attr("running", "1");
                if( div.attr("opened")!="1"  ){
                    div.attr("opened","1");
                    div.attr("o","1");
                    div.children("span").html("-");
                    var ul = div.next("ul");
                    removecorner(ul)
                    ul.slideDown("slow", function (){
                        $(".menu",ul).corner("5px");
                        div.attr("running", "0");
                    });
                }else{
                    div.attr("opened","0");
                    div.children("span").html("+");
                    var ul = div.next("ul");
                    removecorner(ul)
                    ul.slideUp("slow", function (){
                        div.attr("running", "0");
                    });	
                }	
                return false;
            })
        });
        $("a[href*='/${(column.id)!}-']").parent().attr("currentColumn","true").addClass("li_hover");
        $("a[href*='/${(column.id)!}-']").parents("ul").prev("div").click();
    });
    
    function removecorner(ul){
        ul.find(".jquery-corner").each(function(){
            $(this).parent().attr("style","");
            $(this).remove();
        });
    }

</script>
<div class="c_Left_2">
    <div class="BlankLine1"></div>
    <h4 class="title_4"><font>&middot;</font><span>${(topColumn.name)!'栏目标题'}</span></h4>
    <#if topColumn??>
    <ul class="Nav2">
        <#macro showColumn c level>
        <#list columns as i>
        <#if i.columns.id = c.id>
        <li>
            <#if i.node>
            <div class="menu div_${level}  div_${level}_bg"><span class="span_1">+</span><a>${i.name}</a></div>
            <ul style="display: none">
                <@showColumn c=i level=level+1/>
            </ul>
            <#else>
            <div class="menu div_${level}  div_${level}_bg"><span>&nbsp;</span><a href="<@url.column c=i/>">${i.name}</a></div>
            </#if>
        </li>
        </#if>
        </#list>
        </#macro>
        <@showColumn c=topColumn level=1/>
    </ul>
    <#else>
     <ul class="Nav2">
        <li>
            <div class="menu div_1  div_1_bg"><span>&nbsp;</span><a href="#">单页信息</a></div>
        </li>
        <li>
            <div class="menu div_1  div_1_bg"><span>&nbsp;</span><a href="#">普通列表</a></div>
        </li>
        <li>
            <div class="menu div_1  div_1_bg"><span>&nbsp;</span><a href="#">图片列表1</a></div>
        </li>
        <li>
            <div class="menu div_1  div_1_bg"><span>&nbsp;</span><a href="#">图片列表2</a></div>
        </li>
        <li>
            <div class="menu div_1  div_1_bg"><span>&nbsp;</span><a href="#">图片列表3</a></div>
        </li>
        <li>
            <div class="menu div_1  div_1_bg"><span>&nbsp;</span><a href="#">热点信息</a></div>
        </li>
    </ul>

    </#if>
</div>