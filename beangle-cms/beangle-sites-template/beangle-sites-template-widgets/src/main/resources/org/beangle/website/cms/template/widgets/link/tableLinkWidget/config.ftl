<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>选择链接组</label>：</td>
        <td><@f.select name="config.siteTypeId" data=siteTypes textName="typeName" value=config.siteTypeId!0/></td>
    </tr>
</table>
