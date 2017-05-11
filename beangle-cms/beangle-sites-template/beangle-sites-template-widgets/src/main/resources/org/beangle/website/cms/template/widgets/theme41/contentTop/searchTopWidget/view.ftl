<script type="text/javascript">
</script>

<div class="tool_bar">
<table cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <td width="20%">&middot; 2011年5月13日 星期五 <font class="f2">| </font> </td>
              
              <td  width="30%">
              <#if config.num??>
              <#list 1..(config.num?number) as v>
             
              <#if v==1>
              
              <a class="ico_2" href="${config.url1!}"  target="_Blank">${config.uname1!}</a>
              
              </#if>
              <#if v==2>
               &nbsp;
              <a class="ico_2" href="${config.url2!}"  target="_Blank">${config.uname2!}</a>
              </#if>
              <#if v==3>
               &nbsp;
              <a class="ico_2" href="${config.url3!}"  target="_Blank">${config.uname3!}</a>
              </#if>
              <#if v==4>
               &nbsp;
              <a class="ico_2" href="${config.url4!}"  target="_Blank">${config.uname4!}</a>
              </#if>
              <#if v==5>
               &nbsp;
              <a class="ico_2" href="${config.url5!}"  target="_Blank">${config.uname5!}</a>
              </#if>
             
          
              </#list>
              </#if>
               </td>
              
              <#if config.search??>
              <td width="50%" align="right">
              <form action="${base}/${(site.basePath)!}/search-1-20.htm?" onsubmit="return searchContent(this);">
            <input type="hidden" name="allSite" value="${allSite!}"/>
            <div class="top_menu">
                <table align="right">
                    <tr>
                        <td><input class="ip_1" type="text" name="keyWord" value="${(Parameter['keyWord'][0])!}"></td>
                        <td><input class="ico_1" type="button" value="站内" onclick="searchContent(this.form)"></td>
                        <#if config.allSiteSearch??>
                        <td><input class="ico_1" type="button" value="全站" onclick="searchContent(this.form, true)"></td>
                        </#if>
                    </tr>
                </table>
            </div>
        </form>
       </td>
       </#if>
            </tr>
          </table>
          </div>