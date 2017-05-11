<#import "/formUtils.ftl
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>标题</label><font color="red">*</font>：</td>
        <td><input name="config.title" type="text" value="${config.title!}" maxlength=30 style="width:160px;"/></td>
    </tr>         
    <tr>
        <td class="list_box1_title"><label>关联栏目</label>：</td>
        <td><@f.columnSelect name="config.columnId" data=columns?sort_by('orders') value=((config.columnId!('0')))/></td>
    </tr>      
    <tr>
        <td class="list_box1_title"><label>显示信息数量</label>：</td>
        <td><input name="config.count" type="text" value="${config.count!}" maxlength=2 style="width:160px;"/></td>
    </tr>
</table>
<script type="text/javascript">
    var a_fields = {
        'config.title':{'r':true, mx:30},
        'config.count':{'l':'最多输入2位正整数','r':true,'f':'unsigned'}
    };
    var v = new validator(a_fields);
    $(function (){
        selectToText($name("config.columnId")[0],  $name("config.title")[0], "(");
    });
</script>