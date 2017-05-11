<table width="100%" class="list_box1">
    <tr>
        <td class="list_box1_title" name="configTd"><label>是否显示上方空行</label>：</td>
        <td>
            <input type="checkbox" name="config.topBlank" <#if config.topBlank??>checked</#if>/>
        </td>
    </tr>
    <tr>
        <td class="list_box1_title" name="configTd"><label>内容</label>：</td>
        <td>
            <textarea cols="70" rows="10" name="config.content"><#if config.content??>${config.content}<#else>E-mail：<a href="mailto:seiewm@sjtu.edu.cn">seiewm@sjtu.edu.cn</a> 备案号：沪交ICP备05021<br>Copyright&copy; 2011, 上海交通大学电子信息与电气工程学院 版权所有</#if></textarea>
        </td>
    </tr>
</table>
