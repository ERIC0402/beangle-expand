{
<#list widgetConfigs as v>
<#assign wc=v.widgetConfig/>
<#if v_index gt 0>,</#if>
"${wc.id}":{
"id":"${wc.id}",
"position":"${wc.position}",
"className":"${wc.widget.className}",
"config":"${(v.config?replace("\\","\\\\")?replace("\"","\\\""))!'{}'}",
"sid":"${(v.id)!}"}
</#list>
}