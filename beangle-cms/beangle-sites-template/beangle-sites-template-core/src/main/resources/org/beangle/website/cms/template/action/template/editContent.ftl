<#include "head.ftl"/>
<#assign toolBarId="radio3"/>
<#assign heads>
<link href="${base}/static/site-template/css/front/theme${(template.group.id)!(1)}/style_comm/comm.css" rel="stylesheet" type="text/css">
<link href="${base}/static/site-template/css/front/theme${(template.group.id)!(1)}/style_comm/layout.css" rel="stylesheet" type="text/css">
<link href="${base}/static/site-template/css/front/theme${(template.group.id)!(1)}/style_01/style_comm.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${base}/static/site-template/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/static/site-template/js/template/jsformat.js"></script>
<script type="text/javascript" src="${base}/static/site-template/js/template/htmlformat.js"></script>
<script type="text/javascript" src="${base}/static/site-template/js/jquery.combobox.js"></script>
<script type="text/javascript" src="${base}/static/site-template/js/template/widget.js"></script>
<link href="${base}/static/site-template/js/validator2/Validator2.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${base}/static/site-template/js/validator2/Validator2.js"></script>

<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="${base}/static/scripts/uploadify/swfobject.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/upload.js"></script>

<script type="text/javascript">
    $(function (){ 
        action = "template.action";
        $(".previewDiv a").button();
        initContent();
    });
</script>
</#assign>
<@html>
<#include "toolBar.ftl"/>
<div class="toolBox">
    <#include "widgetBox.ftl"/>
</div>
<div class="previewDiv">
    <a href="${base}/cms/template/template!viewContent.action?templateId=${(template.id)!}" target="_blank">预览</a>
</div>
<div class="pageContainer">
    <div class="inPageContainer">
        <div id="contentTabs" class="contentTabs"> 
            <ul> 
                <li><a href="#ContentEditView">可视化编辑</a></li> 
            </ul> 
            <div id="ContentEditView" class="contentMode">        
            </div>
        </div>
    </div>
</div>

<div style="display: none" id="editContentDiv">
    <div id="templateLayout">
        ${template.layout!}
    </div>
    <div class="editContentDiv">
    </div>
    <div class="toolBar2"><button class="edit" onclick="editWdigetConfig(this);">编辑</button><button class="remove" onclick="removeContent(this);">删除</button></div>
    <div class="emptyDiv">
        <p>把组件拖到这里来吧</p>
    </div>
</div>

<div id="editWidgetConfigFormDiv" style="display: none">
    <form name="wdigetConfigForm" action="${base}/cms/template.action" method="post">
        <input type="hidden" name="method" value="updateContent"/>
        <input type="hidden" name="templateId" value="${template.id}"/>
        <input type="hidden" name="type"/>
        <input type="hidden" name="data" value="{}"/>
        <input type="hidden" name="parentId"/>
        <input type="hidden" name="widgetConfigId"/>
        <div id="widgetConfigFormContent" class="divForm"></div>
    </form>
    <div class="clearDiv"></div>
</div>
</@html>