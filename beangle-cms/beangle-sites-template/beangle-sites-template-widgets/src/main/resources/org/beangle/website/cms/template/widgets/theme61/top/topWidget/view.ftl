<#include "/widgetComm.ftl"/>
<div class="line_3"></div>
<div class="header">
  <div class="top_box">
    <div class="top_bar"><a href="${config.link1!'#'}">Contact US</a> | <a href="${config.link2!'#'}">中文版</a></div>
    <div class="top_menu">
    <input type="hidden" name="allSite" value="${allSite!}"/>
    <#if config.search??>
    <form action="${base}/${(site.basePath)!}/search-1-20.htm" onsubmit="return searchContent(this);">
      <table cellpadding="0" cellspacing="0" align="right">
        <tr>
			<td><input class="ip_1" type="text" name="keyWord" value="${(Parameter['keyWord'][0])!}"></td>
            <td><input class="ico_1" type="button" value="" onclick="searchContent(this.form)"></td>
          	<td width="10">&nbsp;</td>
        </tr>
      </table>
   </form>
     </#if>
    </div>
    <div class="pic_box"><img src="${frontbase}/${config.img!'css/front/theme61/style_01/top_bg.jpg'}" /></div>
  </div>
</div>