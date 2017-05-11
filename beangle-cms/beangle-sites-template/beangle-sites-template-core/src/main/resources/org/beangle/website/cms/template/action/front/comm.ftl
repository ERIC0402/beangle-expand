<#setting date_format="yyyy-MM-dd"/>
<#import "message.ftl" as msg>
<#import "htm.ftl" as htm>
<#import "table.ftl" as table>
<#assign frontbase>${base}/static/site-template</#assign>
<#macro text name><@msg.text name/></#macro>
<#macro getMessage><@s.actionmessage theme="beanfuse"/><@s.actionerror theme="beanfuse"/></#macro>

<#macro getEntry url="" c="" >${base}/<#if url != "">${url}&sideNav=${sideNav.id!}<#else><#if c.entry??>${c.entry!}<#else>/wlx.action?method=list</#if>&sideNav=${c.id!}</#if></#macro>
<#macro getColumnUrl c istop=false><#if c.type.id != 18></#if><#if c.entry??>${c.entry}<#if c.entry?index_of('?') == -1>?</#if><#else>index!list.action?</#if><#if istop>&topNav<#else>&sideNav</#if>=${c.id}</#macro>
<#macro back>
<input class="ip_6_1" type="button" style="float: right; margin: 8px;" value="返回" onclick="history.go(-1)"/>
</#macro>
<#macro select name data>
<select name="${name}">
    <option value="">请选择</option>
    <#list data as v>
    <option value="${v.id}">${v.name}</option>
    </#list>
</select>
</#macro>

<#function getEntry2 url>
<#assign str><@getEntry url=url/></#assign>
<#return str>
</#function>

<#function substring s mx>
<#assign str=s>
<#if (s?length > mx)>
<#assign str=s[0..mx] + "..."/>
</#if>
<#return str>
</#function>
<#macro token>
<input type="hidden" name="SESSION_KEY_TOKEN" value="${SESSION_KEY_TOKEN}"/>
</#macro>


<#import "url.ftl" as url/>
<#macro html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
        <title><#if site??>${(site.name)!}</#if></title>
        <link href="${frontbase}/css/front/theme${templateGroupId}/style_comm/comm.css" rel="stylesheet" type="text/css">
        <link href="${frontbase}/css/front/theme${templateGroupId}/style_comm/layout.css" rel="stylesheet" type="text/css">
        <#if perviewcss??>
        <link href="${frontbase}/css/front/theme${templateGroupId}/temp_style/${site.id}.css" rel="stylesheet" type="text/css">
        <#else>
        <#if site??>
        <link href="${frontbase}/css/front/theme${templateGroupId}/style_01/${site.id}.css" rel="stylesheet" type="text/css">
        <#else>
        <link href="${frontbase}/css/front/theme${templateGroupId}/style_01/style_comm.css" rel="stylesheet" type="text/css">
        </#if>
        </#if>
        <!--        <link href="${base}/css/front/theme${templateGroupId}/style_01/style_comm.css" rel="stylesheet" type="text/css">-->
        <script type="text/javascript" src="${frontbase}/js/jquery-latest.js"></script>
        <script type="text/javascript" src="${frontbase}/js/jquery.corner.js"></script>
        <script type="text/javascript" src="${frontbase}/js/jquery.cycle.all.js"></script>
        <script type="text/javascript" src="${frontbase}/js/front.js"></script>
        <script type="text/javascript" src="${frontbase}/js/display.js"></script>
    </head>
    <body>    
        <#nested>
    </body>
</html>
</#macro>
<#assign widget = "org.beangle.website.cms.template.service.WidgetDirective"?new()> 