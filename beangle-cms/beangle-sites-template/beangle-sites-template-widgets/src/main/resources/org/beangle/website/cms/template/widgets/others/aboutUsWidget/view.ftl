<div class="BlankLine2"></div>
<div class="list_box_6">
    <h3 class="title_1">
        <div class="div_2"><span><span class="span_2">&middot;</span> <#if config.title??>${config.title!}<#else>联系我们</#if> </span></div>
    </h3>
    <div class="pic_box_2" align="center">
        <img src="${frontbase}/${config.img!'css/front/theme2/style_01/temp_2.jpg'}">
    </div>
    <ul class="list_1">
        <#list 1..4 as item>
        <#if (config['title'+item]??&&config['content'+item]??&&config['title'+item]?length>0&&config['content'+item]?length>0)>
        <li>
            <h4>
                <span>&middot;</span> ${config['title'+item]}：
            </h4>
            ${config['content'+item]}
        </li>
        </#if>
        </#list>
    </ul>
</div>