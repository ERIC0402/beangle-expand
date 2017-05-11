[#ftl]
[@b.head/]
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="${base}/static/scripts/uploadify/swfobject.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/upload.js"></script>
[@b.toolbar title="模板组"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="模板组基本信息"]
		[@b.form action="!save" title="模板组" theme="list"]
			[@b.textfield name="templateLayout.name" label="common.name" value="${templateLayout.name!}" required="true" maxlength="100" size=60/]
			[@b.field label="预览图" ]
			 	<div class="formItem">
				 	<input type="hidden" name="oimg" value="${templateLayout.img!}"/>
				 	<input type="hidden" name="templateLayout.img" value="${templateLayout.img!}"/>
					<img id="templateLayoutImg" height="200" src="${base}/common/file.action?method=downFile&folder=${templateLayout.img!}"/>
					<div style="margin:3px 0;">
						<input id="file_upload" name="file_upload" type="file" />
					</div>
			 	</div>
			[/@]
			[@b.textarea label="内容" cols="60" rows="10" name="templateLayout.content" value="${templateLayout.content!}" maxlength="1000" comment="长度不超过1000个字符"/]
			[@b.radios label="common.status"  name="templateLayout.enabled" value=templateLayout.enabled items="1:激活,0:冻结"/]
			[@b.formfoot]
				<input type="hidden" name="templateLayout.id" value="${templateLayout.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
			[/@]
		[/@]
	[/@]
[/@]

<script type="text/javascript">
    function save(form){
		bg.form.submit(form,form.action,form.target,null,true);
    }
    $(function (){
        uploadimg({
            dir:"/img/template/templateLayout",
            upBtn:"file_upload",
            imgNmae:"templateLayout.img",
            showImg:"templateLayoutImg"
        });
    });
</script>
[@b.foot/]