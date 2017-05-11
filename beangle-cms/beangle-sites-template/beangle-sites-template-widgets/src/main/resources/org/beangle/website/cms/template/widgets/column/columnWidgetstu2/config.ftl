<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">   
     <tr>
        <td class="list_box1_title"><label>是否隐藏上方空行</label>：</td>
        <td><input name="config.hideTopBlank" type="checkbox" <#if config.hideTopBlank??>checked</#if>/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>更新其它页面相同组件</label>：</td>
        <td><input name="updateSameWidget" type="checkbox" checked/></td>
    </tr>
</table>

<script type="text/javascript">
</script>
