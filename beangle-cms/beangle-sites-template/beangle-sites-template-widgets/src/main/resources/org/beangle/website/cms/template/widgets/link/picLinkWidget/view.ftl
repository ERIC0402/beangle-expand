<div class="BlankLine1"></div>
<div class="list_box_1">
    <h3 class="title_1">
        <div class="div_2"><span><span class="span_1">|</span> ${(siteType.typeName)!'快速通道'} </span></div>
    </h3>
    <ul>
    	<#if links??>
        <#list links as v>
        <li><a href="${v.url!}" target="_blank" title="${v.name!}"><img src="${frontbase}/${v.img!'/css/front/theme2/style_01/photo_04.jpg'}"/></a></li>
        </#list>
        <#else>
        	<li><a href="#"><img src="${frontbase}/css/front/theme2/style_01/photo_04.jpg"></a></li>
	        <li><a href="#"><img src="${frontbase}/css/front/theme2/style_01/photo_05.jpg"></a></li>
	        <li><a href="#"><img src="${frontbase}/css/front/theme2/style_01/photo_06.jpg"></a></li>
        </#if>
    </ul>
</div>