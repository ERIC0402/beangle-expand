<#include "/widgetComm.ftl"/>
<script type="text/javascript">
</script>

<div class="header">
    <div class="top_box">
        <#if config.search??>
        <form action="${base}/${(site.basePath)!}/search-1-20.htm" onsubmit="return searchContent(this);">
            <#if config.allSiteOnly??><#assign allSite='true'/></#if>
            <input type="hidden" name="allSite" value="${allSite!}"/>
            <div class="top_menu">
                <table align="right">
                    <tr>
                        <td><input class="ip_1" type="text" name="keyWord" value="${(Parameter['keyWord'][0])!}"></td>
                        <#if !config.allSiteOnly??>
                        <td><input class="ico_1" type="button" value="站内" onclick="searchContent(this.form)"></td>
                        </#if>
                        <#if config.allSiteSearch??>
                        <td><input class="ico_1" type="button" value="<#if config.allSiteOnly??>搜索<#else>全站</#if>" onclick="searchContent(this.form, true)"></td>
                        </#if>
                    </tr>
                </table>
            </div>
        </form>
        </#if>
        <div class="pic_box"><img src="${frontbase}/${config.img!'css/front/theme1/style_01/top_bg.jpg'}"></div>
    </div>
</div>