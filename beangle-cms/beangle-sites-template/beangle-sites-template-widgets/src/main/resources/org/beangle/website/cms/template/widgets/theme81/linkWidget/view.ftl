<#include "/widgetComm.ftl"/>
<div class="list_box_5">
    <h3 class="title_1">
        <div class="b_2"><span class="span_all_2"><font class="span_1">&nbsp;</font> ${(siteType.typeName)!'快速通道'}</span></div>
    </h3>
    <ul>
        <#if links??>       
        <#list links as v>    
        <#if config.count?? && (config.count?number lte v_index)>
        <#break>
        </#if>
        <li>
            <div class="li_2"><a href="${v.url}" title="${v.name}">&middot; ${v.name}</a></div>
        </li>
        </#list>
        <#else>
        <#list 1..3 as v>        
        <li>
            <div class="li_2"><a href="#">&middot; 友情链接${v}</a></div>
        </li>
        </#list>
        </#if>
    </ul>
</div>