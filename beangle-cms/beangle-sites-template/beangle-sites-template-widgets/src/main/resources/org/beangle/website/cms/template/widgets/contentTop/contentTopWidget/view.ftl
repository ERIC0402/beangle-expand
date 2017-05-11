<#include "/widgetComm.ftl"/>
<script type="text/javascript">
    $(function (){
        $(".title_1 .div_1").hide().show();
    })
</script>
<div class="c_Left_Top">
    <h4 class="title_1">
        <div class="div_2">
            <span><font>|</font> ${config.title!'通知公告'} </span>
            <a href="<#if column??><@url.column c=column /><#else>#</#if>">更 多</a>
        </div>
        <div class="div_1">&nbsp;</div>
    </h4>
    <#if !config.hiddenPic?? || config.showDate??>    
    <div class="list_box_1">
        <#if !config.hiddenPic??>
        <div class="pic_box_1">
            <div class="nav3"></div>
            <div class="s4" <#if (config.hiddenContent)??>style="width:100%"</#if>>
                 <#if picContents??>
                 <#list picContents as item>
                 <div>
                    <a href="<@url.content cc=item/>" target="_blank" title="${(item.content.title)!}">
                        <img src="<@url.getTitleImage cc=item/>"/><br>
                        <dt class="p1"><font>&middot;</font>
                            <@wv.contentTitle cc=item/></dt> </a>
                </div>
                </#list>
                <#else>
                <div><img src="${frontbase}/css/front/theme1/style_01/photo_2.jpg"/><br>
                    <dt class="p1"><font>&middot;</font><a href="#"> 电院代表队在2010年教职工运动会上再获佳绩 </a></dt>
                </div>
                <div><img src="${frontbase}/css/front/theme1/style_01/photo_2.jpg"/><br>
                    <dt class="p1"><font>&middot;</font><a href="#"> 电院代表队在2010年教职工运动会上再获佳绩 </a></dt>
                </div>
                <div><img src="${frontbase}/css/front/theme1/style_01/photo_2.jpg"/><br>
                    <dt class="p1"><font>&middot;</font><a href="#"> 电院代表队在2010年教职工运动会上再获佳绩 </a></dt>
                </div>
                <div><img src="${frontbase}/css/front/theme1/style_01/photo_2.jpg"/><br>
                    <dt class="p1"><font>&middot;</font><a href="#"> 电院代表队在2010年教职工运动会上再获佳绩 </a></dt>
                </div>
                </#if>
            </div>
            <script type="text/javascript">
                $(function() {
                    $('.s4').cycle({
                        fx:     'fade',
                        speed:  'slow',
                        timeout: 4000,
                        pager:  '.nav3'
                    });
                });
            </script>
        </div>
        </#if>
        <#if !config.hiddenContent??>
        <div class="text_box_1" <#if (config.hiddenPic)??>style="width:100%"<#else>style="float:right;width:53%"</#if> >
    <@showList/>
    </div>
    </#if>
</div>
<#else>
<div class="list_box_1_2">
    <@showList/>
</div>
</#if>
</div>
<div class="BlankLine1"></div>
<#macro showList>
<ul>
    <#if contents??>
    <#list contents as item>
    <li <#if item_index + 1 == contents?size>class="boder-bottom-none"</#if>> 
        <font>&gt;</font>
        <a title="${(item.content.title)!}" href="<@url.content cc=item/>" target="_blank"> <@wv.contentTitle cc=item/> </a>
        <#if showDate??>
        <span> [${(item.publishDate?date)!}] </span> 
        </#if>
    </li>
    </#list>
    <#else>
    <li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本早稻 本早稻 </a>
        <#if config.showDate??>
        <span> [2011.4.1] </span>
        </#if>
    </li>
    </#if>
</ul>
</#macro>