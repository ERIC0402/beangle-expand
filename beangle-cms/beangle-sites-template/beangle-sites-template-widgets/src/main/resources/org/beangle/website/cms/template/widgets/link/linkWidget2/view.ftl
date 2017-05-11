<#if links??>

<#list links as k>
<#if k_index =0>
<h3 class="title_3">
    <div class="div_2"><span><span> </span> ${(k.site_type.typeName)!} </span></div>
</h3>
<ul>
    </#if>
    <li><a href="${k.url!}" target="_Blank"><img src="${frontbase}/${k.img!'img/nopic.gif'}"></a></li>
    </#list>
</ul>
<#else>
<h3 class="title_3">
    <div class="div_2"><span><span> </span> 通知公告 </span></div>
</h3>
<ul>
    <li><a href="#"><img src="${frontbase}/css/front/theme21/style_01/photo_04.jpg'}"></a></li>
    <li><a href="#"><img src="${frontbase}/css/front/theme21/style_01/photo_05.jpg'}"></a></li>
    <li><a href="#"><img src="${frontbase}/css/front/theme21/style_01/photo_06.jpg'}"></a></li>
</ul>
</#if>
