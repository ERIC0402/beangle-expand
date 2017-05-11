[#ftl]
[@b.head][/@]
<script src="${base}/static/scripts/uploadify/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/> 
[@b.toolbar title="漂浮条-添加/修改"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="漂浮条"]
		[#--[@b.form action="!save" title="基本信息" theme="list"]--]
		[@b.form name="yssswpwj" action="!save" title="基本信息" theme="list"]
			[@b.textfield name="floatingBar.name" label="名称" value="${floatingBar.name!}" required="true" maxlength="100" style="width:200px;"  comment="长度不超过100个字符"/]
			
			[@b.select label="显示位置" required="true" name="floatingBar.szwz.id" empty="请选择..." value=(floatingBar.szwz.id)!  style="width:154px;" items=szwzs option="id,name"/]
		 	
			[@b.select label="所属站点" required="true" name="floatingBar.site.id" empty="请选择..." value=(floatingBar.site.id)!  style="width:154px;" items=sites option="id,name"/]
		 	
		 	[@b.textfield name="floatingBar.pftgs" label="漂浮条格式" value="${floatingBar.pftgs!}" required="true" maxlength="100" style="width:200px;"  comment="长度不超过100个字符"/]
			
			[@b.textfield name="floatingBar.url" id="url" label="链接" value="${floatingBar.url!}" required="true" maxlength="100" style="width:200px;" comment="请输入完整的URL地址，长度不超过100个字符"/]
			
			[@b.field label="图片" ]	
				 	<img id="albumImg" src="${base}/common/file.action?method=downFile&folder=${floatingBar.filePath!}" style="display: none"/>
	                <div style="padding-left: 120px">
	                    <input id="file_upload" name="file_upload" type="file" />
	                </div>
		    [/@]
			
			
			[@b.radios label="是否显示"  name="floatingBar.visible" value=floatingBar.visible items={'true':'显示','false':'不显示'}/]
			
			[@b.formfoot]
				<input type="hidden" name="floatingBar.id" value="${floatingBar.id!}" />
				
				<input type="hidden" name="oldFilePath" id="oldFilePath" value="${floatingBar.filePath!}" />
				<input type="hidden" id="imgName" name="floatingBar.filePath" value="${floatingBar.filePath!}" />
				[@b.redirectParams/]
			
				
				[@b.reset/]&nbsp;&nbsp;	<input type="button" value="提交" onclick="toAction('save')"/>
			[/@]
			<script language="JavaScript">	
				    function toAction(methodName){
				    	
				    		if(($("#imgName").val()==null) ||($("#imgName").val()=="")){
					    		alert("请选择图片");
					    		return;
					    	}
					    	
					    	
					    	var url = $("#url").val();
						 	if(url != "" && url.indexOf("http://")!=0){
						 		alert("主题链接必须是'http://'开头的完整URL地址！");
						 		return false;
						 	}else if(url == "http://"){
						 		$("#url").val("");
						 	}
					    	
					    	
    					
						bg.form.submit("yssswpwj","${base}/cms/floating-bar.action?method="+methodName);
					}
			</script>
			
			
		[/@]
	[/@]
[/@]
[@b.foot/]


<script language="JavaScript">
	$(function (){
        uploadimg({
            dir:"img/tm",
            ofile:"${floatingBar.filePath!}",
            upBtn:"file_upload",
            imgNmae:"floatingBar.filePath",
            showImg:"albumImg",
            width:100
        });
    });

	[#if floatingBar.filePath??]
		$("#albumImg").show();
	[/#if]
</script>