<div class="list_box_5">
    <h3 class="title_1">
        <div class="b_2"><span class="span_all_2"><font class="span_1"></font>${(st.typeName)!'友情链接'}</span></div>
    </h3>
    <ul>
        <#if links??>
        <#list links as k>
        <#if config.count?? && config.count?number == k_index>
        <#break/>
        </#if>
        <li><a target="_Blank" href="${k.url!}"><img src="${frontbase}/${k.img!'img/nopic.gif'}"></a></li>
        </#list>
        <#else>
        <li><a href="#"><img src="${frontbase}/css/front/theme41/style_01/photo_04.jpg"></a></li>
        <li><a href="#"><img src="${frontbase}/css/front/theme41/style_01/photo_05.jpg"></a></li>
        <li><a href="#"><img src="${frontbase}/css/front/theme41/style_01/photo_06.jpg"></a></li>
        </#if>
    </ul>
</div>