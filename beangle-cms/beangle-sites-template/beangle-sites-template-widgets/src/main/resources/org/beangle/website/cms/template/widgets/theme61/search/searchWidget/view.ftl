<#include "/widgetComm.ftl"/>
<#setting locale='en'>
<div class="list_box_3">
            <div class="BlankLine3"></div>
            <h2 class="title_4">${(column_content.content.title)!'Seacher'}<div class="line_1"></div></h2>
			<div class="date_bar"> <span><#if column_content??>${(column_content.publishDate)?string('MMM dd,yyyy')}</span>        <span> Author:${(column_content.content.drafter.name)!} </span></#if></div>
			<div class="article_box">
		  <div class="article_content">
		  		<div align="center"><#if config.showPic??><img src="${frontbase}/${config.img!'css/front/theme61/style_01/temp_5.jpg'}"></#if></div><br>
	<#if (column_content.content.detail)??>
		${column_content.content.detail}
	<#else>
		  	   
NOT Fond!

	</#if>

                <@wv.readTimeEn/>
</div>
		  </div>
            <div class="BlankLine1"></div>
          </div>