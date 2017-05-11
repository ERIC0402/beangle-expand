<#include "/org/beangle/website/cms/template/action/comm.ftl"/>
{
<#list templates as v>
<#if v_index gt 0>,</#if>"${v.id}":{"id":"${v.id}","name":"${v.name}","img":"<@imgUrl url=v.img!'img/default.jpg'/>","typeId":"${v.type.id}"}
</#list>
}