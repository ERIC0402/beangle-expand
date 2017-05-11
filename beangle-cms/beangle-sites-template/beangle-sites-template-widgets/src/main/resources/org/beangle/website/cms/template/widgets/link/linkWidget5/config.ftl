<#include "/widgetComm.ftl"/>
<table align="center">
    <tr>
        <td colspan="2" align="right">切换时间：</td>
        <td colspan="2"><input name="config.time" value="${config.time!5}"/>(秒)</td>
    </tr>
    <tr>
        <td>序号</td>
        <td>请输入栏目别名<font color="red">*</font></td>
        <td>请选择栏目</td>
        <td>请输入显示数量<font color="red">*</font></td>
    </tr>
    <tr>
        <td>1</td>
        <td><input name="config.name1" value="${(config.name1)!'无'}"/></td>
        <td><@f.columnSelect name="config.columnId1" data=columnList?sort_by('orders') value=((config.columnId1!('0')))/></td>
        <td><input name="config.num1" value="${(config.num1)!}"/></td>
    </tr>
    <tr>
        <td>2</td>
        <td><input name="config.name2" value="${(config.name2)!'无'}"/></td>
        <td><@f.columnSelect name="config.columnId2" data=columnList?sort_by('orders') value=((config.columnId2!('0')))/></td>
        <td><input name="config.num2" value="${(config.num2)!}"/></td>
    </tr>
    <tr>
        <td>3</td>
        <td><input name="config.name3" value="${(config.name3)!'无'}"/></td>
        <td><@f.columnSelect name="config.columnId3" data=columnList?sort_by('orders') value=((config.columnId3!('0')))/></td>
        <td><input name="config.num3" value="${(config.num3)!}"/></td>
    </tr>
    <tr>
        <td>4</td>
        <td><input name="config.name4" value="${(config.name4)!'无'}"/></td>
        <td><@f.columnSelect name="config.columnId4" data=columnList?sort_by('orders') value=((config.columnId4!('0')))/></td>
        <td><input name="config.num4" value="${(config.num4)!}"/></td>
    </tr>
    <tr>
        <td>5</td>
        <td><input name="config.name5" value="${(config.name5)!'无'}"/></td>
        <td><@f.columnSelect name="config.columnId5" data=columnList?sort_by('orders') value=((config.columnId5!('0')))/></td>
        <td><input name="config.num5" value="${(config.num5)!}"/></td>
    </tr>
</table>

<script type="text/javascript">
    var a_fields = {
        'config.num1':{'l':'最多输入2位正整数','r':'true','f':'unsigned'},
        'config.num2':{'l':'最多输入2位正整数','r':'true','f':'unsigned'},
        'config.num3':{'l':'最多输入2位正整数','r':'true','f':'unsigned'},
        'config.num4':{'l':'最多输入2位正整数','r':'true','f':'unsigned'},
        'config.num5':{'l':'最多输入2位正整数','r':'true','f':'unsigned'}
    };
    var v = new validator(a_fields);
</script>