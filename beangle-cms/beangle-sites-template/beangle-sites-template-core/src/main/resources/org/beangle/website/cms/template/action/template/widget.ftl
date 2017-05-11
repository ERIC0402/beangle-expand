<#macro img v...>
<#assign widget=(v['widget'])!/>
<#assign rel=(v['rel'])!'imgBig'/>
<#--<a rel="${rel}" href="${base}/common/file!showWidgetImg.action?img=${widget.img!}">-->
    <img class="autoSize" src="${base}/cms/template/template-common!showWidgetImg.action?img=${widget.img!}" <#if v['width']??>width="${v['width']}"</#if> <#if v['height']??>height="${v['height']}"</#if>/>
<#--</a>-->
</#macro>