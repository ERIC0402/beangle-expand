<#include "/widgetComm.ftl"/>
<#if !config.hideTopBlank??>
<div class="BlankLine3"></div>
</#if>

<@wv.position/>

<div class="list_box_3">
    <#if contents??>
    <h3 class="title_5_2">
        <span><span class="span_1">|</span>${column.name!}</span>
    </h3>
    <hr class="line_2">
    <ul class="list_box_5_2">
        <#list contents as v>
        <li><span>[${v.publishDate?date}]</span><a style="overflow-x:hidden;" href="<@url.content cc=v/>" title="<@wv.contentTitle cc=v/>" target="_blank"><font>&rsaquo;</font><@wv.contentTitle cc=v/></a></li>
        </#list>
    </ul> 
    <#assign perfix><@url.columnPerfix c=column/></#assign>
    <#assign suffix>.htm</#assign>
    <@wv.pageBar perfix=perfix  suffix=suffix/>
    <#else>
    <h3 class="title_5_2">
        <span><span class="span_1">|</span> 图片列表</span>
    </h3>
    <hr class="line_2">

    <ul class="list_box_5_2">
        <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科。</a> </li>
        <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科。</a> </li>
        <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科。</a> </li>
        <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科。</a> </li>
    </ul>
    <div class="turn_page">
        <#include "/comm/pageBar.ftl"/>
    </div>
    </#if>
</div>





