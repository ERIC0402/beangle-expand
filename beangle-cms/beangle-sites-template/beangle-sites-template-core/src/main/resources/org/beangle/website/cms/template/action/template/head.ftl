<#include "../comm.ftl"/>
<#macro html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>正在设计模板-${(template.name)!}</title>
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${base}/static/site-template/css/template/style.css">
		<script type="text/javascript" src="${base}/static/scripts/comm/jquery-latest.js"></script>
        <script type="text/javascript" src="${base}/static/site-template/js/comm.js"></script>
        <link rel="stylesheet" type="text/css" href="${base}/static/site-template/js/jqueryui/redmond/jquery-ui-1.8.12.css">
        <script type="text/javascript" src="${base}/static/site-template/js/jqueryui/jquery-ui-1.8.12.js"></script>
        <script type="text/javascript">var base = "${base}";</script>
        ${heads!}
        <script type="text/javascript">
            $(function (){
                $("button").button();
            })
        </script>

    </head>
    <body>
        <div id="commendBrowser" style="position: fixed;width: 100%;padding: 10px; background-color: #aaaaaa; color: #ffffff;text-align: center;">推荐使用IE8浏览器，谷歌（Chorme）浏览器</div>
        <#nested/>
    </body>
</html>
</#macro>