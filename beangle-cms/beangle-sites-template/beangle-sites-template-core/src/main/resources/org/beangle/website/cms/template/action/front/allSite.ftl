<#import "url.ftl" as url/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div style="width: 600px; margin: 100px auto">
            <ul>
            <#list sites as v>
            <li><a href="<@url.index site=v/>" target="_blank">${v.name!}</a></li>
            </#list>
            <li><a href="${base}/login.action">后台管理</a></li>
            </ul>
        </div>
    </body>
</html>