[#ftl]
[#import "/org/beangle/website/cms/template/action/widget.ftl" as w/]
[@b.head/]
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="${base}/static/scripts/uploadify/swfobject.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/upload.js"></script>
[@b.toolbar title="组件"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="组件基本信息"]
		[@b.form action="!save" title="组件" theme="list"]
			[@b.select label="模板组" name="template.group.id"  value=(template.group.id)!0  items=groups/]
			[@b.select label="类型" name="template.type.id"  value=(template.type.id)!0  items=types/]
			[@b.textfield name="template.name" label="common.name" value="${template.name!}" required="true" maxlength="100" size=60/]
			[@b.field label="预览图" ]
			 	<div class="formItem">
				 	<input type="hidden" name="oimg" value="${template.img!}"/>
				 	<input type="hidden" name="template.img" value="${template.img!}"/>
					<img id="templateImg" height="200" src="${base}/common/file.action?method=downFile&folder=${template.img!}"/>
					<div style="margin:3px 0;">
						<input id="file_upload" name="file_upload" type="file" />
					</div>
			 	</div>
			[/@]
			[@b.textarea label="简介" cols="50" rows="5" name="template.introduce" value="${template.introduce!}" maxlength="300" comment="长度不超过300个字符"/]
			[@b.radios label="common.status"  name="template.enabled" value=template.enabled items="1:激活,0:冻结"/]
			[@b.formfoot]
				<input type="hidden" name="template.id" value="${template.id!}" />
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
            dir:"/img/template/template",
            upBtn:"file_upload",
            imgNmae:"template.img",
            showImg:"templateImg"
        });
    });
</script>
[@b.foot/]