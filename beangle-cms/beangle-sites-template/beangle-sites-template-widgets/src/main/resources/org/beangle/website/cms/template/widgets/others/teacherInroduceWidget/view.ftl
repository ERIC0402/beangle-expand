<#include "/widgetComm.ftl"/>
<script type="text/javascript">
    $('.s3').cycle({
        fx:     'turnLeft',
        speed:  'slow',
        timeout: 4000
    });
</script>
<h4 class="title_2"><span><font>·</font> ${config.title!'师资介绍'} </span></h4>
<div class="list_box_2 list_bg_1">
    <div class="photo_box_1">
        <div class="s3">
            <#if teachers??>
            <#list teachers as v>
            <div> 
                <a href="<#if column??><@url.detail c=column v=v/><#else>#</#if>">
                    <img src="${frontbase}/${(v.src?replace('\\','/'))!'img/nopic.gif'}">
                    <h4>${v.xm!}&nbsp;&nbsp;${v.jszw!}</h4>
                    <#list fileds as w>
                    ${v[w[0]]!}<br>
                    </#list>
                </a>
            </div>
            </#list>
            <#else>
            <#list 1..4 as v>
            <div> 
                <a href="#">
                    <img src="${frontbase}/css/front/theme1/style_01/photo_1.jpg">
                    <h4>徐凯  助理教授</h4>
                    新的世纪优秀的学者 <br>
                    在大学人才 <br>
                    哥伦比亚大学 <br>
                    医疗机器人技术和服务机器人技术</a> 
            </div>
            </#list>
            </#if>
        </div>
    </div>
</div>