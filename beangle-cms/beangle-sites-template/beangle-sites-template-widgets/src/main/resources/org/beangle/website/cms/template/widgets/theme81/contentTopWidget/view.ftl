<#include "/widgetComm.ftl"/>
<div class="list_box_4">
    <h3 class="title_1">
        <div class="b_2">
            <span class="span_all_2" style="float: left;"><font class="span_1">&nbsp;</font> ${config.title!'国际化简讯'}</span>
            <a style="float: right; color: #000" href="<@url.column c=column!/>" title="${(column.id)!}">&rsaquo;更多</a>
        </div>
    </h3>
    <div class="BlankLine0"></div>
    <img width="87" height="94" src="${base}/${config.img!'css/front/theme81/style_comm/index3_07.jpg'}">

    <ul>
        <#if contents??>
         <#list contents as v>
        <li>
            <div class="li_2" style="overflow: hidden;"><a title="<@wv.contentTitle cc=v/>" href="<@url.content cc=v/>">&middot; <@wv.contentTitle cc=v/></a></div>
        </li>
        </#list>
        <#else>
        <#list 1..3 as v>
        <li>
            <div class="li_2"><a href="#">&middot; 2011年第${v}期</a></div>
        </li>
        </#list>
        </#if>
    </ul>
</div>
<script type="text/javascript">
    $('.list_box_4 .li_2').corner("3px");
</script>
