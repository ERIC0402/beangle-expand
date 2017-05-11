<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr class="hidden">
        <td class="list_box1_title"><label>标题风格</label>：</td>
        <td>
            <#list 1..5 as v>
            <input name="config.titleStyle" id="titleStyle${v}" type="radio" value="${v}" <#if config.titleStyle??><#if config.titleStyle?number == v>checked</#if><#else><#if v==1>checked</#if></#if>/>
                   <label for="titleStyle${v}">风格${v}</label>&nbsp;&nbsp;
            </#list>
        </td>
    </tr>
    <tr class="hidden">
        <td class="list_box1_title"><label>隐藏当前位置</label>：</td>
        <td><input type="checkbox" name="config.hidePosition" <#if config.hidePosition??>checked</#if>/></td>
    </tr>
    <tr class="hidden">
        <td class="list_box1_title"><label>是否是英文网站</label>：</td>
        <td><input type="checkbox" name="config.en" <#if config.en??>checked</#if>/></td>
    </tr>
</table>
<script type="text/javascript">
</script>