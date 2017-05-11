<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr class="hidden">
        <td class="list_box1_title"><label>不显示详细</label>：</td>
        <td><input name="config.hideDetail" type="checkbox" <#if config.hideDetail??>checked</#if>/></td>
    </tr>
</table>
<script type="text/javascript">
    var a_fields = {
        
    };
    var v = new validator(a_fields);
</script>