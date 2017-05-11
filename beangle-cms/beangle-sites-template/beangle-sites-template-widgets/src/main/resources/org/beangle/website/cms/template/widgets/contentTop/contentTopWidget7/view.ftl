<#include "/comm.ftl">
<h3 class="title_3">
    <div class="div_2"><span><span class="span_2"> </span><a href="<#if column??><@url.column c=column /><#else>#</#if>">${config.title!'专业学位'}</a></span></div>
</h3>
<ul>
<#if contents??>
    <#list contents as item>
    <#if (item_index < (config.count)?number)>
    <li>
        <table width="100%">
            <tr>
                <td valign="top" width="10"  class="rsaquo">&rsaquo;</td>
                <td>
                    <div><#if config.showDate??>(${(item.publishDate)?string('yyyy-MM-dd')!})</#if></div>
                    <a href="<@url.content cc=item/>" target="_Blank"><@wv.contentTitle cc=item/></a>
                </td>
            </tr>
        </table>
    </li>
    </#if>
    </#list>
    <#else>
    <#list 1..4 as item>
    <li>
        <table width="100%">
            <tr>
                <td valign="top" width="10"  class="rsaquo">&rsaquo;</td>
                <td><div><#if config.showDate??>(2011-01-24)</#if></div>
                    <a href="#" target="_Blank">快速通道快速通道1快速通道1快速通道1快速通道1快速通道1快速通道1快速通道1</a>
                </td>
            </tr>
        </table>
    </li>
    </#list>
    </#if>
</ul>
