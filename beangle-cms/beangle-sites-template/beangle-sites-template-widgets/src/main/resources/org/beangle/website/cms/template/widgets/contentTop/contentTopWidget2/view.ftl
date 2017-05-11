<#include "/widgetComm.ftl"/>
   <div class="list_box_1">
   	  <h3 class="title_1">
        <div class="div_2">
            <#if column??>
            <span><span class="span_1">|</span>${column.name!} </span><a href="<@url.column c=column />"><font>&gt;</font>更 多</a>
            <#else>
            <span><span class="span_1">|</span>通知公告 </span><a href="#"><font>&gt;</font>更 多</a>
            </#if>
        </div>
      </h3>
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
                <div><img src="${frontbase}/css/front/theme2/style_01/temp_1.jpg"/><br>
                    <dt class="p1"><font>&middot;</font><a href="#"> 电院代表队在2010年教职工运动会上再获佳绩 </a></dt>
                </div>
                <div><img src="${frontbase}/css/front/theme2/style_01/temp_1.jpg"/><br>
                    <dt class="p1"><font>&middot;</font><a href="#"> 电院代表队在2010年教职工运动会上再获佳绩 </a></dt>
                </div>
                <div><img src="${frontbase}/css/front/theme2/style_01/temp_1.jpg"/><br>
                    <dt class="p1"><font>&middot;</font><a href="#"> 电院代表队在2010年教职工运动会上再获佳绩 </a></dt>
                </div>
                <div><img src="${frontbase}/css/front/theme2/style_01/temp_1.jpg"/><br>
                    <dt class="p1"><font>&middot;</font><a href="#"> 电院代表队在2010年教职工运动会上再获佳绩 </a></dt>
                </div>
                </#if>
            </div>
        </div>
            <script type="text/javascript">
				$(function() {
					  $('.s4').cycle({
				        fx:     'fade',
				        speed:  'slow',
				        timeout: 4000,
				        pager:  '.nav3'
				    });
				});
			</script>
    </div>