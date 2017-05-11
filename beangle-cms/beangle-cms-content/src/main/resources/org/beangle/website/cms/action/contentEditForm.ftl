[#ftl]
<script>
	var base='${base}';
</script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/ckeditor/ckeditor.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/contentFileUpload.js"></script>
<script src="${base}/static/scripts/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/> 
<link href="${base}/static/themes/${b.theme.ui}/homePage.css" rel="stylesheet" type="text/css" />
<input type="hidden" name="cc.id" value="${(cc.id)!}" />
<input type="hidden" name="content.id" id="content.id" value="${content.id!}" />
<input type="hidden" name="column.id" id="column.id" value="${column.id!}" />
<input type="hidden" name="newsType" id="newsType" value="${(column.newsType.id)!}"  />
<input type="hidden" name="content.titleImage.id" id="titleImage" value="${(content.titleImage.id)!}" />
<input type="hidden" name="edit" id="edit" value="${(edit)!}"  />
<input type="hidden" name="annexIds" id="annexIds" value=""  />
<div style="display:none">
<textarea cols="50" rows="10" id="oldDetail" name="oldDetail">${content.detail!}</textarea>
</div>
<fieldset>
	<legend>基本信息</legend>
	<ol>
		[@b.textfield style="width:400px;" id="content.title" name="content.title" label="标题" value="${content.title!}" required="true" maxlength="200" comment="长度不超过200个字符"/]
		[#assign colors=[['#000000','黑色'],['#808080','灰色'],['#FF0000','红色'],['#800080','紫色'],['#0000FF','蓝色'],['#008000','绿色'],['#FFFF00','黄色']]/]
		[@b.field label="标题显示颜色" ]
		    <select name="content.titleColor" style="width:120px;">
		        <option value=""><div style="float:left;width:14px;height:14px;background-color:red;">&nbsp;</div>默认</option>
		        [#list colors as item]
		        	<option style="background-color:${item[0]!}" [#if content.titleColor??&&content.titleColor=item[0]]selected[/#if] value="${(item[0])?if_exists}">${(item[1])?if_exists}</option>
		        [/#list]
		    </select>
		    <label class="comment">默认：按照网站页面的风格显示</label>
		[/@]
		[@b.radios label="标题是否加粗" name="content.doesBold" value=content.doesBold items="1:加粗,0:不加粗"/]
		[@b.textfield style="width:400px;" id="content.subTitle" name="content.subTitle" label="副标题" value="${content.subTitle!}" maxlength="200" comment="长度不超过200个字符"/]
		[@b.select label="访问权限" name="content.access.id" id="content.access.id" empty="请选择..." value=(content.access.id)!  style="width:200px;" items=accesss option="id,name"/]
	</ol>
</fieldset>
[#if pros == 1]
<fieldset>
	<legend>扩展属性</legend>
	<ol>
		[#list propertys?sort_by("orders") as s]
			
		[/#list]
	</ol>
</fieldset>
[/#if]
<fieldset>
	<legend>详细内容</legend>
	<ol>
		[@b.textfield style="width:400px;" id="content.keyword" name="content.keyword" label="关键词" value="${content.keyword!}" maxlength="200" comment="长度不超过200个字符"/]
		[@b.textarea label="摘要" cols="63" rows="3" id="content.abstracts" name="content.abstracts" value="${content.abstracts!}" maxlength="500" comment="长度不超过500字符"/]
		[@b.select label="内容样式" id="content.contentStyle.id" name="content.contentStyle.id"  value=(content.contentStyle.id)!(2)  style="width:160px;" items=contentStyles option="id,name" onchange="change(this.value)"/]
		<div id="url" style="display:none">
			[@b.textfield id="content.url" name="content.url" label="URL" value="${content.url!}" maxlength="200" comment="长度不超过200个字符"/]
		</div>
		<div id="rt" style="display:none">
			<input type="button" value="换行" name="br" onClick="huanhang(this.form)" class="ip1" />
        <input type="button" value="首行缩进" name="br" onClick="shsj(this.form)" class="ip1" />
        <input type="button" value="加粗" name="br" onClick="jc(this.form)" class="ip1" />
        <input type="hidden" id="start" size="3"/>
        <input type="hidden" id="end" size="3"/>
        	[@b.field label="内容" ]
        		<textarea cols="50" rows="10" id="richText" name="richText" value="${content.detail!}" onKeydown="savePos(this)" onKeyup="savePos(this)" onmousedown="savePos(this)" onmouseup="savePos(this)" onfocus="savePos(this)"></textarea>
        	[/@]
		</div>
		<div id="details">
			[@b.field label="附件管理" ]
				<div style="margin:3px 0;float:left;">
                    <input id="file_upload" name="file_upload" type="file" />
                </div>
                <div style="margin:3px 0;float:left;">
                    &nbsp;<input style="width:130px;height:30px;cursor:pointer;" type="button" value="从文件库中选择文件" onClick="selectAnnex()" />
                </div>
                <div style="height:0px;clear:both;"></div>
			[/@]
		</div>
		<div id="details1">
			[@b.field label="文件操作" ]
				<span id="select1file">
		            <select name="fileList1" id="fileList1" style="width:160px;">
		                <option value="">请选择文件</option>
		                [#list content.annexs as file]
		                	<option title="${(file.fileName)?if_exists}" value="${(file.id)?if_exists}">${(file.fileName)?if_exists}</option>
		                [/#list]
		            </select>
		        </span>
		        <select name="fileOpList1" id="fileOpList1" style="width:160px;">
		            <option value="" selected>请选择操作</option>
		            <option value="0">插入附件</option>
		            <option value="1">插入图片</option>
		            <option value="5">插入FLASH</option>
		            <option value="6">插入asf视频</option>
		            <option value="7">插入mp3音频</option>
		            <option value="2">标题图片</option>
		            <option value="3">删除标题图片</option>
		            <option value="4">删除文件</option>
		        </select>
		        <input type="button" value="确定"  id="button3" class="ip1" onClick="handleother()" />
			[/@]
		</div>
		<div id="tti">
			[@b.field label="标题图片" ]
				<div id="showImage" width='80px' height='65px'>
					[#if content.titleImage??]
			        	<img src='${base}/cms/file-utils!downFileById.action?isShowImage=1&fileId=${(content.titleImage.id)!}' width='80px' height='65px'/>
			        [/#if]
		        	&nbsp;</div>
			[/@]
		</div>
		<div id="details2" >
			[@b.field label="内容" ]
			<div style="width:750px;padding-left:120px;">
				<textarea id="editor1" name="content.detail">${content.detail!}</textarea>
				<script type="text/javascript">
		           CKEDITOR.replace( 'editor1',
					{
						fullPage : true,
						extraPlugins : 'docprops'
					});
	        	</script>
        	</div>
        	[/@]
		</div>
	</ol>
</fieldset>
<script>
$(function (){
    uploadContentFile({
        dir:"Transit/${currentDate?string('yyyyMM')}",
        upBtn:"file_upload",
        sizeLimit:10*1024*1024,
        selectId:"fileList1"
    });
});
</script>