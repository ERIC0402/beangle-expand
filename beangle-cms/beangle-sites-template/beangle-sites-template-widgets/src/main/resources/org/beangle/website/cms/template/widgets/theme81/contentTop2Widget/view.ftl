<#include "/widgetComm.ftl"/>
<div class="list_box_4">
    <h3 class="title_1">
        <div class="b_2"><span class="span_all_2"><font class="span_1">&nbsp;</font>  ${config.title!'国际化简讯'} </span></div>
    </h3>
    <ul>
        <#if contents??>
        <#list contents as v>
        <li>
            <div class="li_2"><a title="<@wv.contentTitle cc=v/>" href="<@url.content cc=v/>">&middot; <@wv.contentTitle cc=v/></a></div>
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