<#macro trinput title='标题' name='name' value='value' maxlength=100>
<tr>
    <td class="list_box1_title" name="configTd"><label>${title}</label>：</td>
    <td>
        <input type="text" name="${name}" value="${value}" maxlength="${maxlength}"/>
    </td>
</tr>
</#macro>
