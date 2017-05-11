<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>选择栏目</label>：</td>
        <td><@f.select name="config.columnId" data=columnList?sort_by('orders') value=((config.columnId!('0')))/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>显示图片数量</label>：</td>
        <td><input name="config.picNum" type="text" value="${config.picNum!(4)}" maxlength=2 style="width:160px;"/></td>
    </tr>
</table>
<script type="text/javascript">
    var a_fields = {
        'config.picNum':{'l':'最多输入2位正整数','f':'unsigned'}
    };
    var v = new validator(a_fields);
</script>