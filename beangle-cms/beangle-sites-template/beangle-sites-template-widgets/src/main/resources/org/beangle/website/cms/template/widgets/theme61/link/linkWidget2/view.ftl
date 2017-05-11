<#include "/widgetComm.ftl"/>
<div class="list_box_6">
    <div class="p_1">
        <h3 class="title_1_1">
            <div class="div_2"><span><span class="span_2"></span> ${(k.site_type.typeName)!'Quick Links'} </span></div>
        </h3>
        <div class="BlankLine1"></div>
        <ul class="list_3">
        <#if links??>
            <#list links as k>
            <li><a href="${k.url!}" target="_Blank"><img src="${frontbase}/${k.img!'/css/front/theme61/style_01/photo_04.jpg'}" style="width:170px; height:48px;"></a></li>
            </#list>
        <#else>
            <li><a href="#"><img src="${frontbase}/${config.img!'/css/front/theme61/style_01/photo_04.jpg'}"></a></li>
            <li><a href="#"><img src="${frontbase}/${config.img!'/css/front/theme61/style_01/photo_05.jpg'}"></a></li>
            <li><a href="#"><img src="${frontbase}/${config.img!'/css/front/theme61/style_01/photo_06.jpg'}"></a></li>
        </#if>
        </ul>
    </div>
</div>
<script>
    $(function (){
        $(".box_2_left_shadow").after("<DIV class=box_2_bottom_shadow></DIV>")
    });
</script>