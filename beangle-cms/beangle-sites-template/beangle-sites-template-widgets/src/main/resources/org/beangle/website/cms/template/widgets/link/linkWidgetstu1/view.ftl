<div class="list_box_4">
    <h3 class="title_1">
        <div class="b_2"><span class="span_all_2"><font class="span_1"></font>${(siteType.typeName)!'快速通道'} </span></div>
    </h3>
    <ul>
        <#if links??>
        <#list links as v>
        <li><div class="li_2"><a target="_Blank" href="${v.url}">&middot; ${v.name}</a></div></li>
        </#list>
        <#else>
        <#list 1..3 as v>
        <li><div class="li_2"><a href="#">&middot; 快速通道${v_index + 1}</a></div></li>
        </#list>
        </#if>
    </ul>
</div>