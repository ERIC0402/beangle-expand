<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>是否显示图片</label>：</td>
        <td><input name="config.showPic" type="checkbox" <#if config.showPic??>checked</#if>/></td>
    </tr>
</table>
<script type="text/javascript">
    var a_fields = {
        
    };
    var v = new validator(a_fields);
</script>