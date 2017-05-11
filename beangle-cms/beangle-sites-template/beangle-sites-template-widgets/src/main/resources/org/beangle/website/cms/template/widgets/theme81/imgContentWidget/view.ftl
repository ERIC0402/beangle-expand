<#include "/widgetComm.ftl"/>
<div class="c_Left_Bottom_Middle">
    <div class="list_box_1 ">
        <div class="list_box_1_bg">
            <div class="pic_box_1">
                <h3 class="title_1">
                    <div class="b_1"><span class="span_all_1"><font class="span_1">&nbsp;</font> ${config.title!'图片新闻'}</span><a href="<#if column??><@url.column c=column /><#else>#</#if>">&rsaquo;更多</a></div>
                </h3>
                <div class="nav3"></div>
                <div class="s4">
                    <#if picContents??>
                     <#list picContents as cc>
                    <div>
                        <img src="<@url.getTitleImage cc=cc/>"/><br>
                        <dt class="p1">
                            <a href="<@url.content cc=cc/>">
                                <h3>${(cc.content.title)!}</h3>
                                ${(cc.content.abstracts)!}[详细信息] 
                            </a>
                        </dt>
                    </div>
                     </#list>
                    <#else>
                    <#list 1..4 as v>
                    <div>
                        <img src="${frontbase}/css/front/theme81/style_01/photo_2.jpg"/><br>
                        <dt class="p1">
                            <a href="#">
                                <h3>算奥斯陆进行互操作 </h3>
                                解答问题限于图书馆资源利用，图书馆服务范畴，不提供查找全文文献的服务，但可提供相关线索... [详细信息] 
                            </a>
                        </dt>
                    </div>
                    </#list>
                    </#if>
                </div>
            </div>
        </div>
    </div>
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