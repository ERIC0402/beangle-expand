{
<#list widgetConfigs as v>
<#if v_index gt 0>,</#if>
"${v.id}":{"id":"${v.id}",
"position":"${v.position}",
"className":"${v.widget.className}",
<#if v.quote??>
<#assign config = v.quote.config!'{}'/>
<#else>
<#assign config = v.config!'{}'/>
</#if>
<#assign config = config?replace("\\","\\\\")/>
<#assign config = config?replace("\"","\\\"")/>
"config":"${config}"}
</#list>
}