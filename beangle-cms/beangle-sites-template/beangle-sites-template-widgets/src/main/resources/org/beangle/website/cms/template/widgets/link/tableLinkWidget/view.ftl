<div class="BlankLine4"></div>
<div class="list_box_6">
    <h3 class="title_1">
        <div class="div_2"><span><span class="span_2">&middot;</span>${(siteType.typeName)!'快速通道'}</span></div>
    </h3>
    <ul class="list_2">
        <#if links??>
        <#list links as v>
        <li <#if v_index + 1 == links?size>class="boder-bottom-none"</#if>> <table width="100%">
                <tr>
                    <Td valign="top" width="15"  class="rsaquo">&rsaquo;</Td>
                    <td><a href="${v.url}" target="_blank">${v.name}</a></td>
                </tr>
            </table></li>
        </#list>
        <#else>
        <li> <table width="100%">
                <tr>
                    <Td valign="top" width="15"  class="rsaquo">&rsaquo;</Td>
                    <td><a href="#">快速通道快速通道1快速通道1快速通道1快速通道1快速通道1快速通道1快速通道1</a></td>
                </tr>
            </table></li>
        <li> <table width="100%">
                <tr>
                    <Td valign="top" width="15"  class="rsaquo">&rsaquo;</Td>
                    <td><a href="#">快速通道</a></td>
                </tr>
            </table></li>
        <li> <table width="100%">
                <tr>
                    <Td valign="top" width="15"  class="rsaquo">&rsaquo;</Td>
                    <td><a href="#">快速通道</a></td>
                </tr>
            </table></li>
        </#if>
    </ul>

</div>