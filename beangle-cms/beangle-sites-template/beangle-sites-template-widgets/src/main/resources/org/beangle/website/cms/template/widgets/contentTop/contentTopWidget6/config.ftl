<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>标题</label>：</td>
        <td><input name="config.title" type="text" value="${config.title!}" maxlength=30 style="width:160px;"/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>选择栏目</label>：</td>
        <td><@f.columnSelect name="config.columnId" data=columnList?sort_by('orders') value=((config.columnId!('0')))/></td>
    </tr>
     <tr>
        <td class="list_box1_title"><label>请输入显示信息的数量</label><font style="color:#FF0000;">*</font>：</td>
        <td><input name="config.count" type="text" value="${config.count!}" maxlength=2 style="width:160px;"/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>是否显示日期</label>：</td>
        <td><input name="config.showDate" type="checkbox" <#if config.showDate??>checked</#if>/></td>
    </tr>
</table>
<script type="text/javascript">
    var a_fields = {
        'config.count':{'l':'最多输入2位正整数','r':'true','f':'unsigned'}
    };
    var v = new validator(a_fields);
    $(function (){
        selectToText($name("config.columnId")[0],  $name("config.title")[0], ")");
    });
</script>