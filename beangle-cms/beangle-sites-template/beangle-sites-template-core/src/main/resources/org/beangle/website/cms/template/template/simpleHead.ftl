<#include "/org/beangle/website/cms/template/template/comm.ftl"/>
<#import "/template/message.ftl" as msg>
<#import "/template/htm.ftl" as htm>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <title><@text name="web.back.name"/></title>
        <!--IE下用静态态icon-->
        <link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
        <!--FF下可用gif动态icon，暂时没有-->
        <!--link rel="icon" href="${base}/favicon.gif" type="image/gif" /-->
        <link rel="shortcut icon" href="${base}/favicon.ico" type="image/x-icon" />
        <link rel="bookmark" href="${base}/favicon.ico" type="image/x-icon" />

        <link href="${base}/static/images/style_comm.css" rel="stylesheet" type="text/css">
        <link href="${base}/static/css/toolBar.css" rel="stylesheet" type="text/css">
        <link href="${base}/static/css/default.css" rel="stylesheet" type="text/css">
        <link href="${base}/static/css/messages.css" rel="stylesheet" type="text/css">
        <link href="${base}/images/style_comm.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="${base}/static/scripts/common/jquery-latest.js"></script>
        <script type="text/JavaScript" src="${base}/js/comm.js"></script>
        <script type="text/JavaScript" src="${base}/static/scripts/common/Common.js"></script>
        <script type="text/JavaScript" src="${base}/static/scripts/common/ToolBar.js"></script>
        <script type="text/JavaScript" src="${base}/static/scripts/common/OnReturn.js"></script>
        <script type="text/JavaScript" src="${base}/comm/text_toggle.js"></script>
        <script type="text/javascript">var base = "${base}";</script>
    </head>
    <#if Session['WW_TRANS_I18N_LOCALE']??>
    <#assign language= Session['WW_TRANS_I18N_LOCALE'].language>
    <#else>
    <#assign language="zh">
    </#if>
    <#macro i18nName(entity)><#if language?index_of("en")!=-1><#if entity.engName!?trim=="">${entity.name!}<#else>${entity.engName!}</#if><#else><#if entity.name!?trim!="">${entity.name!}<#else>${entity.engName!}</#if></#if></#macro>
    <#macro getMessage><@s.actionmessage theme="beanfuse"/><@s.actionerror theme="beanfuse"/></#macro>
    <#macro text name><@msg.text name/></#macro>
    <#macro getBeanListNames(beanList)><#list beanList as bean>${bean.name}&nbsp;</#list></#macro>
    <#macro searchParams><input name="params" type="hidden" value="${Parameters['params']!('')}"/></#macro>