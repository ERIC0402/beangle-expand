<#if config.en??>
<table align="right">
    <tr>
        <td>
            <font color="#777777">First&nbsp;&nbsp;Prev</font>
            <font color="#777777">1</font>
            <font color="#777777">&nbsp;Next&nbsp;&nbsp;End</font>
            <font color="#777777">1&nbsp;/&nbsp;1&nbsp;Page</font>
        </td>
        <td>
            Go to Page&nbsp;&nbsp;<input id="pageNo" type="text" class="ip_3" onkeyup="if(event.keyCode == 13){gotoPage()}">&nbsp;<input onclick="gotoPage()" type="button" class="ip_2" value="Go">
        </td>
    </tr>
</table>
<#else>
<table align="right">
    <tr>
        <td>
            <font color="#777777">首页&nbsp;&nbsp;上一页</font>
            <font color="#777777">1</font>
            <font color="#777777">&nbsp;下一页&nbsp;&nbsp;末页</font>
            <font color="#777777">1&nbsp;/&nbsp;1&nbsp;页</font>
        </td>
        <td> 到 </td>
        <td><input id="pageNo" type="text" class="ip_3" onkeyup="if(event.keyCode == 13){gotoPage()}">
        </td>
        <td> 页 </td>
        <td><input onclick="gotoPage()" type="button" class="ip_2" value="跳 转">
        </td>
    </tr>
</table>
</#if>