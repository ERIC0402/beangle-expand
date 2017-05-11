[#ftl]
<style>
.right_content_box {
	font: 12px Verdana, Arial, Helvetica, sans-serif;
	margin: 0px;
	padding: 0px;
	color: #333333;
	line-height:25px;
	
}
.clearfloat_3{ margin:10px 30px;;margin:0 auto;}
.title_4{ text-align:center; line-height:18px; padding-bottom:10px; }
.cotent_box{ padding:20px 30px 50px 30px; width:650px;margin:0 auto;border: 2px solid #dddddd; }
.button_box{ padding:10px 30px 10px 30px; width:650px;margin:0 auto;text-align:center;}
</style>
<div class="right_content_box">
	<input type="hidden" name="cc.id" value="${cc.id!}"/>
	<div class="clearfloat_3">
       <h3 class="title_4">
        	${(cc.content.title)!}
       </h3>
    </div>
    <div class="cotent_box">
     	[#if cc.content.detail??]
	       [#if base='/website']
	          ${(cc.content.detail)?replace('[IN]','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;')?replace('[B]','<B>')?replace('[/B]','</B>')?replace('[BR]','<br>')}
	       [#else]
	          ${(cc.content.detail)?replace('[IN]','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;')?replace('[B]','<B>')?replace('[/B]','</B>')?replace('[BR]','<br>')?replace('/website/content','/content')}
	       [/#if]
	    [/#if]
	</div>
</div>