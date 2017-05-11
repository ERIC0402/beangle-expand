<#include "head.ftl"/>
<#assign toolBarId="radio2"/>
<#if !layouts??>
<#assign heads>
<script type="text/javascript" src="${base}/static/site-template/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/static/site-template/js/template/jsformat.js"></script>
<script type="text/javascript" src="${base}/static/site-template/js/template/htmlformat.js"></script>
<script type="text/javascript" src="${base}/static/site-template/js/template/layout.js"></script>
<script type="text/javascript">
    $(function (){
        initLayout();
    });
</script>
</#assign>
</#if>
<@html>
<#if layouts??>
<#include "selectLayout.ftl"/>
<#else>
<#include "toolBar.ftl"/>
<div class="toolBox">
    <#include "layoutBox.ftl"/>
</div>
<div class="pageContainer">
    <div class="inPageContainer">
        <div id="layoutTabs" class="layoutTabs"> 
            <ul> 
                <li><a href="#layoutEditView">可视化编辑</a></li> 
                <li><a href="#layoutEditHtml">源代码</a></li> 
            </ul> 
            <div id="layoutEditView" class="layoutMode">        
                ${template.layout}
            </div>
            <div id="layoutEditHtml">
                <textarea id="templateLayoutTextarea" class="templateLayoutTextarea">${template.layout?html}</textarea>
                <button onclick="saveLayout($('#templateLayoutTextarea').val())">保存</button>
            </div>
        </div>
    </div>
</div>

<div style="display: none" id="editLayoutDiv">
    <div class="editLayoutDiv">
    </div>
    <div class="toolBar3"><button class="edit" onclick="editLayout(this);">编辑</button><button class="remove" onclick="removeLayout(this);">删除</button></div>
    <div class="emptyDiv"></div>
</div>

<div id="editLayoutFormDiv" style="display: none">
    <form name="layoutUpdateForm" action="${base}/cms/template.action" method="post">
        <input type="hidden" name="method" value="updateLayout"/>
        <input type="hidden" name="templateId" value="${template.id}"/>
        <input type="hidden" name="type"/>
        <input type="hidden" name="data" value="{}"/>
        <input type="hidden" name="content"/>
        <input type="hidden" name="parentId"/>
        <ul> 
            <li><a href="#editLayoutFormSize">大小</a></li> 
            <li><a href="#editLayoutFormStyle">自定义样式</a></li> 
            <li><a href="#editLayoutFormClazz">类属性</a></li> 
        </ul> 
        <div id="editLayoutFormSize">
            <p>大小单位：<input type="radio" id="percent" name="sizeType" value="%"/><label for="percent">%</label><input type="radio" id="px" name="sizeType" value="px"/><label for="px">px</label></p>
            <hr/>
            <div id="editLayoutFormSizes" class="editLayoutFormSizes"></div>
        </div>
        <div id="editLayoutFormStyle">            
            <div id="editLayoutFormStyles" class="editLayoutFormStyles"></div>
        </div>
        <div id="editLayoutFormClazz">            
            <div id="editLayoutFormClazzs" class="editLayoutFormClazzs"></div>
        </div>
    </form>
</div>
</#if>

</@html>