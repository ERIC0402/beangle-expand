<#include "/widgetComm.ftl"/>
<table width="100%" class="list_box1">
    <tr class="hidden">
        <td class="list_box1_title"><label>标题风格</label>：</td>
        <td>
            <#list 1..3 as v>
            <input name="config.titleStyle" id="titleStyle${v}" type="radio" value="${v}" <#if config.titleStyle??><#if config.titleStyle?number == v>checked</#if><#else><#if v==1>checked</#if></#if>/>
                   <label for="titleStyle${v}">风格一</label>&nbsp;&nbsp;
            </#list>
        </td>
    </tr>
</table>
<script type="text/javascript">
</script>