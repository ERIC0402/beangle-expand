<#include "/widgetComm.ftl"/>
<script type="text/javascript">
$('.home_now').corner("tl");
$('.last_now').corner("tr");
$('.nav_box').corner("top 10px");
$('.list_box_1_bg').corner("10px");
$('.span_2').corner("top 5px");
$('.title_2_a').corner("top 5px");
$('.li_2').corner("3px");
</script>


<script type="text/javascript">
    function searchContent(form,allSite){
        if(allSite){
            form["allSite"].value = "true";
        }
        form.keyWord = encodeURIComponent(form.keyWord);
        form.submit();
        return false;
    }
</script>

<div class="tool_bar">
       <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <td width="20%">&middot; 2011年5月13日 星期五 <font class="f2">| </font> </td>
              <td class="ico_2" width="30%"><a href="#">校 历</a></td>
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
        
        
        
  <div class="c_top">
    <div class="list_box_1">
    <div class="list_box_1_bg">
        <#if !config.hiddenPic??>
         <div class="pic_box_1">
            <div class="nav3"></div>
            <div class="s4">
                <#if picContents??>
                <#list picContents as item>
                <div>
                    <a href="<@url.content cc=item/>" target="_blank">
                        <img src="<@url.getTitleImage cc=item/>"/><br>
                        <dt class="p1"><font>&middot;</font>
	                    	 <@wv.contentTitle cc=item/> </a></dt>
                </div>
               
                </#list>
                <#else>

                  <div><img src="/css/front/theme41/style_01/photo_2.jpg"/></div>
                  <div><img src="/css/front/theme41/style_01/photo_2.jpg"/></div>
                  <div><img src="/css/front/theme41/style_01/photo_2.jpg"/></div>
                  <div><img src="/css/front/theme41/style_01/photo_2.jpg"/></div>
                  
                </#if>
            </div>
        </div>
        </#if> 
       
        <#if !config.hiddenContent??>
        <div class="text_box_1">
        <h3 class="title_1">
	         <div class="b_1">
	         <#if column??>
	          <span class="span_all_1"><font class="span_1">&middot;</font>${column.name!}</span>
	         <a href="<@url.column c=column! />"><font>&rsaquo;更多</a>
	         <#else>
	         <span class="span_all_1"><font class="span_1">&middot;</font>图片新闻</span>
	         <a href="#"><font>&rsaquo;更多</a>
	         </#if>
	         </div>
        </h3>
             <ul>
                <#if contents??>
                <#list contents as item>
                <li> 
                    <font>&gt;</font>
                    <a href="<@url.content cc=item/>" target="_blank"> <@wv.contentTitle cc=item/> </a>
                    <#if showDate??>
                    <span> [${(item.publishDate?date)!}] </span> 
                    </#if>
                </li>
                </#list>
                <#else>
                
                <li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本稻 本早稻</a>
                <#if config.showDate??>
                <span> [2011.4.1] </span></#if> 
                </li>
                <li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本稻 本早稻</a>
                <#if config.showDate??>
                <span> [2011.4.1] </span></#if> 
                </li>
                <li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本稻 本早稻</a>
                <#if config.showDate??>
                <span> [2011.4.1] </span></#if> 
                </li>
                <li> <font>&gt;</font><a href="#"> 上海交通大学—日本早稻 本早稻 本稻 本早稻</a>
                <#if config.showDate??>
                <span> [2011.4.1] </span></#if> 
                </li>
               
                </#if>
            </ul>
        </div>
      </#if>
    </div>
     </div>
</div>
<div class="BlankLine2"></div>