<#include "/widgetComm.ftl"/>
<div class="c_Right_2">
    <div class="list_box_3 position_bar">
        <#include "/comm/positionAndTitle.ftl"/>
        <#if contents??>
        <div class="list_box_2">
            <#list contents as v>
            <div class="pic_box_3">
                <a href="<@url.content cc=v/>" target="_blank" title="${(v.content.title)!}">
                    <img class="autoSize" width="198" height="151" src="<@url.getTitleImage cc=v/>"><br>
                    <div><#if ((v.content.title)!)?length gt 30>${(v.content.title)?substring(0,30)}...<#else>${(v.content.title)!}</#if></div>
                </a> 
            </div>
            <#if (v_index+1)%3==0>
            <div class="line_2"></div>
            </#if>
            </#list>
            <div style="clear: both"></div>
        </div>
        <#assign perfix><@url.columnPerfix c=column/></#assign>
        <#assign suffix>.htm</#assign>
        <@wv.pageBar perfix=perfix  suffix=suffix/>
        <#else>
        <div class="list_box_2 list_box_2_3">
            <#list 1..18 as v>
            <#if v != 1 && v%3 == 1>
            <div class="line_2 line_2_3"></div>
            </#if>
            <div class="pic_box_3 pic_box_3_3">
                <a href="#">
                    <img src="${frontbase}/css/front/theme2/style_01/temp_3.jpg">
                    <label>交大摩根士丹利共建金融本早稻 本早稻本早稻上海。</label>
                </a> 
            </div>
            </#list>
            <div class="line_2 line_2_3"></div>
        </div>
        <div class="BlankLine1"></div>
        <div class="turn_page">
            <#include "/comm/pageBar.ftl"/>
        </div>
        <div class="BlankLine4"></div>
        </#if>
    </div>
</div>
<script type="text/javascript">
    $(function (){
        autoImgSize($(".autoSize"));
    })
</script>
