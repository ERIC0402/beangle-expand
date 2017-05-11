<#include "/widgetComm.ftl"/>

<script type="text/javascript">
    $('.home_now').corner("tl");
    $('.last_now').corner("tr");
    $('.nav_box').corner("top 10px");
    $('.list_box_1_bg').corner("10px");
    $('.span_2').corner("top 5px");
    $('.title_2_a').corner("top 5px");
    $('.li_2').corner("3px");
</script>

<div class="c_top">
    <div class="list_box_1 ">
        <div class="list_box_1_bg">
            <div class="pic_box_1">
                <div class="nav3"></div>
                <div class="s4">
                    <#if picContents??>
                    <#list picContents as item>
                    <div>
                        <a href="<@url.content cc=item/>" target="_blank">
                            <img src="<@url.getTitleImage cc=item/>"/><br>
                            <dt class="p1"><font>&middot;</font>
	                    	 <@wv.contentTitle cc=item/></dt> </a>
                    </div>

                    </#list>
                    <#else>
                    <div><img src="/css/front/theme41/style_01/photo_2.jpg"/> </div>
                    <div><img src="/css/front/theme41/style_01/photo_2.jpg"/> </div>
                    <div><img src="/css/front/theme41/style_01/photo_2.jpg"/> </div>
                    <div><img src="/css/front/theme41/style_01/photo_2.jpg"/> </div>
                    </#if>
                </div>
            </div>
            <div class="text_box_1">

                <h3 class="title_1">
                    <div class="b_1"> 
                        <#if column??>
                        <span class="span_all_1">
                            <font class="span_1"></font>${column.name!}</span><a href="<@url.column c=column! />">&rsaquo;更多</a>
                        <#else>
                        <span class="span_all_1"><font class="span_1">&middot;</font>图片新闻</span>
                        <a href="#">&rsaquo;更多</a>
                        </#if>
                    </div>
                </h3>
                <#if contents??>
                <ul>
                    <#list contents as item>
                    <#if (item_index<config.count?number)>
                        <#if !item_has_next>
                        <li style="border-bottom:none">
                            <#else>
                        <li> 
                            </#if>

                            <font>&gt;</font>
                            <a href="<@url.content cc=item/>" target="_blank"> <@wv.contentTitle cc=item/></a>
                            <#if showDate??>
                            <span> [${(item.publishDate?date)!}] </span> 
                            </#if>
                        </li>
                        </#if>
                        </#list>
                </ul>
                <#else>
                <ul>
                    <#list 1..4 as v>
                    <li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本稻 本早稻</a>
                        <#if config.showDate??>
                        <span> [2011.4.1] </span></#if> 
                    </li>
                    </#list>
                </ul>
                </#if>

            </div>
        </div>
    </div>
</div>
<div class="BlankLine2"></div>