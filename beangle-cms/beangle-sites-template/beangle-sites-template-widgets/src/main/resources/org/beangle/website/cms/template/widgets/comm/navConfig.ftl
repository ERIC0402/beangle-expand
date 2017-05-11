<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>栏目间距</label>：</td>
        <td><input name="config.space" type="text" value="${config.space!(defaultSpace!29)}" maxlength=30/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>更新其它页面相同组件</label>：</td>
        <td><input name="updateSameWidget" type="checkbox" checked/></td>
    </tr>
</table>

<script type="text/javascript">    
    var a_fields = {
        "config.space":{f:"integer", r:true}
    };
</script>
