[#ftl]
[@b.head/]
<script src="${base}/static/scripts/uploadify/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/> 
[@b.toolbar title="附件管理"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="附件管理"]
		[@b.form action="!save" title="基本信息" theme="list"]
			[@b.field label="附件" ]	
			 	[#--]<img id="albumImg" src="${base}/common/file.action?method=downFile&folder=${annex.filePath!}" style="display: none"/>--]
                <div style="margin:3px 0;">
                    <input id="file_upload" name="file_upload" type="file" />
                </div>
			[/@]
			[@b.textfield id="fileName" name="annex.fileName" label="文件名称" value="${annex.fileName!}" required="true" maxlength="100" comment="长度不超过100个字符"/]	
			[@b.radios label="是否公开"  name="annex.open" value=annex.open items="1:公开,0:私有"/]
			
			[@b.formfoot]
				<input type="hidden" id="filePath" name="annex.filePath" value="${annex.filePath!}"/>
				<input type="hidden" name="oldFileName" value="${annex.filePath!}"/>
				<input type="hidden" id="fileSize" name="annex.fileSize" value="${annex.fileSize!}"/>
				<input type="hidden" name="annex.id" value="${annex.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;
				<input type="button" value="${b.text("action.submit")}" onClick="submit2(this.form);"/>
			[/@]
		[/@]
	[/@]
[/@]
<script language="JavaScript">
	function submit2(form){
		bg.form.submit(form,form.action,form.target,null,true);
	}
	
	$(function (){
        uploadFile({
            dir:"Transit/${currentDate?string("yyyyMM")}",
            upBtn:"file_upload",
            filePath:"filePath",
            fileName:"fileName",
            fileSize:"fileSize",
            sizeLimit:100*1024*1024,
            width:300
        });
    });

</script>
[@b.foot/]