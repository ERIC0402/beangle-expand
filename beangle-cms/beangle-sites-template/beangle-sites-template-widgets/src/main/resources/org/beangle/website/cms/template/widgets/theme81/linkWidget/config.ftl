<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>选择链接组</label>：</td>
        <td><@f.select name="config.siteTypeId" data=siteTypes textName="typeName" value=config.siteTypeId!0/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>显示数量</label><font color="red">*</font>：</td>
        <td><input name="config.count" type="text" value="${config.count!3}" maxlength=2 /></td>
    </tr>
</table>
<script type="text/javascript">
    var a_fields = {
    };
    var v = new validator(a_fields);
</script>