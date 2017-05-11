<#include "/widgetComm.ftl"/>
<div class="list_box_7">
    <a href="<#if column??><@url.column c=column /><#else>#</#if>">
        <h3 class="title_1">
            <div class="div_2" style="cursor: pointer"><span><span> </span> ${(config.title)!'办事指南'} </span></div>
        </h3>
    </a>
    <ul>
        <#if contents??>
        <#list contents as v>
        <li>
            <table width="100%">
                <tr>
                    <Td valign="top" width="10"  class="rsaquo">&rsaquo;</Td>
                    <td><a href="<@url.content cc=v/>" target="_Blank"><@wv.contentTitle cc=v/></a></td>
                </tr>
            </table>
        </li>
        </#list>
        <#else>
        <#list 1..4 as v>
        <li>
            <table width="100%">
                <tr>
                    <Td valign="top" width="10"  class="rsaquo">&rsaquo;</Td>
                    <td><a href="#" target="_Blank">快速通道快速通</a></td>
                </tr>
            </table>
        </li>
        </#list>
        </#if>
    </ul>
</div>