[#ftl]
[@b.head/]
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script type="text/javascript" src="${base}/static/scripts/uploadify/swfobject.js"></script>
<script type="text/javascript" src="${base}/static/scripts/uploadify/upload.js"></script>
[@b.toolbar title="模板组"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="模板组基本信息"]
		[@b.form action="!save" title="模板组" theme="list"]
			[@b.textfield name="templateGroup.name" label="common.name" value="${templateGroup.name!}" required="true" maxlength="100" size=60/]
			[@b.field label="预览图" ]
			 	<div class="formItem">
				 	<input type="hidden" name="oimg" value="${templateGroup.img!}"/>
				 	<input type="hidden" name="templateGroup.img" value="${templateGroup.img!}"/>
					<img id="templateGroupImg" height="200" src="${base}/common/file.action?method=downFile&folder=${templateGroup.img!}"/>
					<div style="margin:3px 0;">
						<input id="file_upload" name="file_upload" type="file" />
					</div>
			 	</div>
			[/@]
			[@b.textarea label="简介" cols="50" rows="5" name="templateGroup.introduce" value="${templateGroup.introduce!}" maxlength="300" comment="长度不超过300个字符"/]
			[@b.radios label="common.status"  name="templateGroup.enabled" value=templateGroup.enabled items="1:激活,0:冻结"/]
			[@b.formfoot]
				<input type="hidden" name="templateGroup.id" value="${templateGroup.id!}" />
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
            dir:"/img/template/templateGroup",
            upBtn:"file_upload",
            imgNmae:"templateGroup.img",
            showImg:"templateGroupImg"
        });
    });
</script>
[@b.foot/]