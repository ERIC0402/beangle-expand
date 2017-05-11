<#include "/widgetComm.ftl"/>
<script type="text/javascript" src="${base}/js/jquery.corner.js"></script>

<script type="text/javascript">

    $(document).ready(function(){
        $(".div_2").corner("5px");
        //$("dd:not(:first)").hide();
        $("dd").hide();
        $("dt .li4").prepend("<span>+</span>");
		
        $("dt .div_2, dd .div_3").hover(function() { 
            $(this).removeClass("div_3_bg");
            $(this).addClass("li_hover");
        }, function() { 
            if($(this).attr("currentColumn") != "true"){			
                $(this).removeClass("li_hover");
                $(this).addClass("div_3_bg");
            }
        }); 
		
        $("dt a").click(function(){
        
            if( $(this).attr("class")=="li2"  ) {
            } else{
                if( $(this).attr("class")=="li3"  ){
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
                    return false;
                }					
            }
        });
        $("a[href*='/${(column.id)!}-']").parent().attr("currentColumn","true").addClass("li_hover");
        $("a[href*='/${(column.id)!}-']").parents("dd").prev("dt").find("a").click();
    });

</script>


<#if !config.hideTopBlank??>
<div class="BlankLine3"></div>
</#if>


<h4 class="title_4">



    <#if topColumn??>
    <font>&middot;</font><span>${topColumn.name}</span></h4>
        <dl class="Nav2">
            <#macro showColumn c level>
            <#list columns as i>
            <#if i.columns.id = c.id>
            <#if level==1>
            <dt class="<#if i.node>li2_1<#else>li2_2</#if>" >
              <div class="div_2"><a href="<@url.column c=i/>" class="<#if i.node>li4<#else>li2</#if>">${i.name}</a></div>
            </dt>
            
            <#else>
            <li><div class="div_3"><a href="<@url.column c=i/>">&middot;${i.name}</a></div></li>
            </#if>
            <#if i.node>
            <dd>
            <ul>
                    <@showColumn c=i level=level+1/>
                </ul>
                </dd>
            </#if>
            </#if>
            </#list>
            </#macro>
            <@showColumn c=topColumn level=1/>
        </dl>
        <#else>


          <font>&middot;</font><span>学院概况</span></h4>
          <dl class="Nav2">
            <dt class="li2_2" >
              <div class="div_2"><a href="#" class="li2">简介</a></div>
            </dt>
            <dt class="li2_1" >
              <div class="div_2"><a href="index.html" class="li4">学学术委员会委员会</a></div>
            </dt>
            <dd>
              <ul>
                <li>
                  <div class="div_3 div_3_bg"><a href="#">&middot;学术委员组织机构会 </a></div>
                </li>
                <li>
                  <div class="div_3 div_3_bg"><a href="#">&middot;学术委员组织机构会 </a></div>
                </li>
                <li>
                  <div class="div_3 div_3_bg"><a href="#">&middot;学术委员组织机构会 </a></div>
                </li>
                <li>
                  <div class="div_3 div_3_bg"><a href="#">&middot;学术委员组织机构会 </a></div>
                </li>
              </ul>
            </dd>
            <dt class="li2_1" >
              <div class="div_2"><a href="index.html" class="li4">学学术委员会委员会</a></div>
            </dt>
            <dd>
              <ul>
                <li>
                  <div class="div_3 div_3_bg"><a href="#">&middot;学术委员组织机构会 </a></div>
                </li>
                <li>
                  <div class="div_3 div_3_bg"><a href="#">&middot;学术委员组织机构会 </a></div>
                </li>
                <li>
                  <div class="div_3 div_3_bg"><a href="#">&middot;学术委员组织机构会 </a></div>
                </li>
                <li>
                  <div class="div_3 div_3_bg"><a href="#">&middot;学术委员组织机构会 </a></div>
                </li>
              </ul>
            </dd>
            <dt class="li2_1" >
              <div class="div_2"><a href="index.html" class="li4">学学术委员会委员会</a></div>
            </dt>
            <dd>
              <ul>
                <li>
                  <div class="div_3 div_3_bg"><a href="#">&middot;学术委员组织机构会 </a></div>
                </li>
                <li>
                  <div class="div_3 div_3_bg"><a href="#">&middot;学术委员组织机构会 </a></div>
                </li>
                <li>
                  <div class="div_3 div_3_bg"><a href="#">&middot;学术委员组织机构会 </a></div>
                </li>
                <li>
                  <div class="div_3 div_3_bg"><a href="#">&middot;学术委员组织机构会 </a></div>
                </li>
              </ul>
            </dd>
          </dl>
          </#if>