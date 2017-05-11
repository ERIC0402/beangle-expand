<#import "/formUtils.ftl" as f/>
<script type="text/javascript">
    function searchContent(form, allSite){
        if(allSite){
            form["allSite"].value = "true";
        }
        form.keyWord = encodeURIComponent(form.keyWord);
        form.submit();
        return false;
    }
</script>
<div class="BlankLine1"></div>
<div class="banner"><img src="${frontbase}/${config.img!'css/front/theme21/style_01/banner_pic.jpg'}"></div>
<div class="tool_bar">
    <table cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td width="15%">日期：<font class="f1">${today?string('yyyy年MM月dd日')}</font></td>
            <td width="35%"><iframe src="http://m.weather.com.cn/m/pn3/weather.htm " width="150" height="20" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe></td>
            <td width="50%" align="right">
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <#if config.search??>
                    <form action="${base}/${(site.basePath)!}/search-1-20.htm" onsubmit="return searchContent(this);">
                        <input type="hidden" name="allSite" value="${allSite!}"/>
                        <div class="top_menu">
                            <table align="right">
                                <tr>
                                    <td class="f1">搜 索</td>
                                    <td><input class="ip_1" type="text" name="keyWord" value="${(Parameter['keyWord'][0])!}"></td>
                                    <td><input class="ico_1" type="button" value="" onclick="searchContent(this.form)"></td>
                                </tr>
                            </table>
                        </div>
                    </form>
                    </#if>
        </tr>
    </table>
</td>
</tr>
</table>
</div>