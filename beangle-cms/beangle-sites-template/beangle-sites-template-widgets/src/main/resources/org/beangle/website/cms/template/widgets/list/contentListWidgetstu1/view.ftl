<#include "/widgetComm.ftl"/>
<div class="c_Left_Bottom_Left_border">
    <div class="list_box_2">
        <h3 class="title_2">
            <div class="div_1">

                <span class="span_2"><font class="span_1"></font>${config.title!'图片新闻'}</span>
                <a class="title_2_a" href="<#if column??><@url.column c=column /><#else>#</#if>">&rsaquo;更 多</a>
            </div>
        </h3> 
        <#if !config.Content??>
        <ul>
            <#if contents??>
            <#list contents as item>
            <#if (item_index<config.count?number)>
                <#if !item_has_next>
                <li style="border-bottom:none">
                    <#else>
                <li> 
                    </#if>
                    <font>&gt;</font>
                    <a title="<@wv.contentTitle cc=item/>" href="<@url.content cc=item/>" target="_blank"><@wv.contentTitle cc=item/></a>
                    <#if config.showDate??><span>[${(item.publishDate?date)!}]</span></#if>

                </li>
                </#if>
                </#list>
                <#else>
                <#list 1..4 as item>
                <#if !item_has_next>
                <li style="border-bottom:none">
                    <#else>
                <li> 
                    </#if>
                    <font>&gt;</font>
                    <a href="#"> 上海交通大学—日本早稻 本早稻 本早稻 本早稻 本早稻 本早稻</a>
                    <#if config.showDate??><span> [2011.4.1] </span> </#if>
                </li>
                </#list>
                </#if>
        </ul>
        </#if>
    </div>
</div>








