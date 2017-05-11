<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>正在设计模板-${(site.name)!}-${(template.name)!}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
        <link rel="stylesheet" type="text/css" href="${base}/static/site-template/css/template/style.css">
        <script type="text/javascript">var base = "${base}";</script>
        <script type="text/javascript" src="${base}/static/site-template/js/jquery-latest.js"></script>
        <script type="text/javascript" src="${base}/static/site-template/js/jquery.corner.js"></script>
        <script type="text/javascript" src="${base}/static/site-template/js/jquery.cycle.all.js"></script>
        <script type="text/javascript" src="${base}/static/site-template/js/comm.js"></script>
        <script type="text/javascript" src="${base}/static/site-template/js/comm/form.js"></script>
        <link rel="stylesheet" type="text/css" href="${base}/static/site-template/js/jqueryui/redmond/jquery-ui-1.8.12.css">
        <script type="text/javascript" src="${base}/static/site-template/js/jqueryui/jquery-ui-1.8.12.js"></script>
        <script type="text/javascript" src="${base}/static/site-template/js/jquery.form.js"></script>
        <script type="text/javascript" src="${base}/static/site-template/js/template/jsformat.js"></script>
        <script type="text/javascript" src="${base}/static/site-template/js/template/htmlformat.js"></script>
        <script type="text/javascript" src="${base}/static/site-template/js/template/widget.js"></script>
        <link href="${base}/static/site-template/js/validator2/Validator2.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="${base}/static/site-template/js/validator2/Validator2.js"></script>
        <link href="${base}/static/site-template/js/uploadify/uploadify.css" rel="stylesheet" type="text/css" /> 
        <script type="text/javascript" src="${base}/static/site-template/js/uploadify/swfobject.js"></script>
        <script type="text/javascript" src="${base}/static/site-template/js/uploadify/jquery.uploadify.v2.1.4.js"></script>
        <script type="text/javascript" src="${base}/static/site-template/js/upload.js"></script>
        <link href="${base}/static/site-template/css/front/theme${(template.group.id)!(1)}/style_comm/comm.css" rel="stylesheet" type="text/css">
        <link href="${base}/static/site-template/css/front/theme${(template.group.id)!(1)}/style_comm/layout.css" rel="stylesheet" type="text/css">
        <link href="${base}/static/site-template/css/front/theme${(template.group.id)!(1)}/temp_style/${site.id}.css" rel="stylesheet" type="text/css">
        
        <script type="text/javascript">
            $(function (){
                $("button").button();
            })
            $(function (){        
                action = "site.action";
                siteId = "${site.id}";
                $(".previewDiv a").button();
                initContent();
            });
            
            function changeColor(){
                var div = $("#changeColorDiv");
                var iframe = div.find("iframe");
                if(iframe.attr("src") == undefined){
                    iframe.attr("src","site.action?method=configColor&siteId=${site.id}");
                }
                div.dialog({
                    minWidth:800,
                    modal: true, 
                    show: 'blind',
                    hide: 'explode',
                    title: '选择颜色'
                });
            }
        </script>
    </head>
    <body>
        <div class="previewDiv">
            <a href="###" onClick="publishTemplate('${(template.id)!}','${site.id}');">发布</a>
            <a href="###" onClick="changeColor('${site.id}');">换色</a>
            <a href="site.action?method=viewContent&templateId=${(template.id)!}&siteId=${site.id}" target="_blank">预览</a>
        </div>
        <div id="contentTabs" class="contentTabs"> 
            <ul> 
                <li><a href="#ContentEditView">可视化编辑</a></li> 
            </ul> 
            <div id="ContentEditView" class="contentMode">        
            </div>
        </div>

        <div style="display: none" id="editContentDiv">
            <div id="templateLayout">
                ${template.layout!}
            </div>
            <div class="editContentDiv" style="margin: 0px; border: 0;">
            </div>
            <div class="toolBar2"><button class="edit" onclick="editWdigetConfig(this);">编辑</button></div>
        </div>

        <div id="editWidgetConfigFormDiv" style="display: none">
            <form name="wdigetConfigForm" action="site.action" method="post">
                <input type="hidden" name="method" value="saveConfig"/>
                <input type="hidden" name="templateId" value="${template.id}"/>
                <input type="hidden" name="type"/>
                <input type="hidden" name="siteId" value="${site.id}"/>
                <input type="hidden" name="data" value="{}"/>
                <input type="hidden" name="parentId"/>
                <input type="hidden" name="widgetConfigId"/>
                <input type="hidden" name="swidgetConfigId"/>
                <div id="widgetConfigFormContent" class="divForm"></div>
            </form>
            <div class="clearDiv"></div>
        </div>
        <div id="resultDiv" style="display: none">
            <div style='text-align:center'>发布成功</div>
        </div>
        <div id="changeColorDiv" style="display: none">
            <iframe src="site.action?method=configColor&siteId=${site.id}" width="100%" height="600" frameborder="0"></iframe>
        </div>
    </body>
</html>