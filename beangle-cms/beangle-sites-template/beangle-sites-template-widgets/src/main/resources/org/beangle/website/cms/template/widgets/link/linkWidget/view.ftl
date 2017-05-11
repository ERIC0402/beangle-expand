
<h4 class="title_2"><span><font>·</font> ${(siteType.typeName)!'快速通道'} </span></h4>
<div class="list_box_2 list_bg_2">
    <ul>
        <#if links??>
        <#list links as v>
        <li <#if v_index + 1 == links?size>class="boder-bottom-none"</#if>><font>&gt;</font><a href="${v.url}" target="_blank"> ${v.name}</a> </li>
        </#list>
        <#else>
        <#list 1..4 as v>
        <li><font>&gt;</font><a href="#"> 快速通道${v_index + 1}</a> </li>
        </#list>
        </#if>
    </ul>
</div>
