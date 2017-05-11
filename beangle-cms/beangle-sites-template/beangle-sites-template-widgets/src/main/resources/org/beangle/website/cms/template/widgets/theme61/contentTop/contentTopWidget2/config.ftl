<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>标题</label>：</td>
        <td><input name="config.title" type="text" value="${config.title!}" maxlength=30 style="width:160px;"/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>文档数量</label><font color="red">*</font>：</td>
        <td><input name="config.count" type="text" value="${config.count!5}" maxlength=30 style="width:160px;"/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>选择栏目</label>：</td>
        <td><@f.columnSelect name="config.columnId" data=columnList?sort_by('orders') value=((config.columnId!('0')))/></td>
    </tr>
</table>
<script type="text/javascript">
    var a_fields = {
        "config.count":{f:"integer", r:true}
    };
    var v = new validator(a_fields);
    $(function (){
        selectToText($name("config.columnId")[0],  $name("config.title")[0], ")");
    });
</script>