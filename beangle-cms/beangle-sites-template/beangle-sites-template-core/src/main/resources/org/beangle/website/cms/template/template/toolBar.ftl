<#macro tooBar title="" title2="" islist=false nomsg=false isform=false>
<#if title = "">
<#assign titles='位置：'+title2/>
<#else>
<#assign titles=title/>
</#if>
<table  width="100%" cellpadding="0" cellspacing="0" class="noneprint" width="30%">
    <tr>
        <td valign="middle"  class="top_title_left p_left_2">
            <span class="titleIframe" <#if islist || isform>style="color:#0066cc"</#if>>${titles}</span>
            <span class="msgIframe">&nbsp;<#if !nomsg><@getMessage/></#if></span>
        </td>
        <#if islist || isform>
        <td align="right" class="top_title_right p_right_2 f9" width="70%">
                <div id="itemIframe" style="float: right;"></div>
        </td>
        </#if>
    </tr>
</table>
</#macro>