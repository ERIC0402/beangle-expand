<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title"><label>选择栏目</label>：</td>
        <td><@f.select name="config.columnId" data=columnList?sort_by('orders') value=((config.columnId!('0')))/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>隐藏图片</label>：</td>
        <td><input name="config.hiddenPic" type="checkbox" <#if config.hiddenPic??>checked</#if>/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>隐藏信息</label>：</td>
        <td><input name="config.hiddenContent" type="checkbox" <#if config.hiddenContent??>checked</#if>/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>显示图片数量</label>：</td>
        <td><input name="config.picNum" type="text" value="${config.picNum!(4)}" maxlength=2 style="width:160px;"/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>显示信息数量</label>：</td>
        <td><input name="config.count" type="text" value="${config.count!}" maxlength=2 style="width:160px;"/></td>
    </tr>
    <tr>
        <td class="list_box1_title"><label>是否显示日期</label>：</td>
        <td><input name="config.showDate" type="checkbox" <#if config.showDate??>checked</#if>/></td>
    </tr>
    
   <tr>
        <td class="list_box1_title"><label>是否显示搜索</label>：</td>
        <td><input name="config.search" type="checkbox" <#if config.search??>checked</#if>/></td>
    </tr>
    
    <tr>
        <td class="list_box1_title"><label>是否全站搜索搜索</label>：</td>
        <td><input name="config.allSiteSearch" type="checkbox" <#if config.allSiteSearch??>checked</#if>/></td>
    </tr>
</table>
<script type="text/javascript">
    var a_fields = {
        'config.count':{'l':'最多输入2位正整数','f':'unsigned'},
        'config.picNum':{'l':'最多输入2位正整数','f':'unsigned'}
    };
    var v = new validator(a_fields);
</script>