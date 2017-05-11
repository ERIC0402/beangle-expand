<div class="header">
    <div class="top_box">
        <div class="top_menu">
            <#if config.search??>
            <form action="${base}/${(site.basePath)!}/search-1-20.htm" onsubmit="return searchContent(this);">
                <table align="right">
                    <tr>
                        <td><input name="keyWord" class="ip_1" type="text" maxlength="30" value="${(Parameter['keyWord'][0])!}"></td>
                        <td><input class="ico_1" type="submit" value="搜索" ></td>
                    </tr>
                </table>
            </form>
            </#if>
        </div>
        <div class="pic_box"><img src="${frontbase}/${config.img!'css/front/theme81/style_01/top_bg.jpg'}" /></div>
    </div>
</div>
 <div style="display:none">&nbsp<a>a</a></div>