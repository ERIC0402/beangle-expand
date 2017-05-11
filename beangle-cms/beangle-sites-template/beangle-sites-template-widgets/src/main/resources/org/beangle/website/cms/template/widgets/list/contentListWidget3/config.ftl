<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>选择栏目</label>：</td>
        <td><@f.select name="config.columnId" data=columnList?sort_by('orders') value=((config.columnId!('0')))/></td>
    </tr>
   
    <tr>
        <td class="list_box1_title"><label>隐藏信息</label>：</td>
        <td><input name="config.Content" type="checkbox" <#if config.Content??>checked</#if>/></td>
    </tr>
   
     <tr>
        <td class="list_box1_title"><label>是否显示日期</label>：</td>
        <td><input name="config.showDate" type="checkbox" <#if config.showDate??>checked</#if>/></td>
    </tr>
    
</table>
