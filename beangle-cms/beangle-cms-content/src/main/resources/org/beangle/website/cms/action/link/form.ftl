[#ftl]
[@b.head/]
<script src="${base}/static/scripts/uploadify/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/> 
[@b.toolbar title="链接管理"]bar.addBack();[/@]
		[@b.form action="!save" title="链接信息" theme="list"]
			[@b.textfield name="link.name" label="名称" value="${link.name!}" required="true" maxlength="30" comment="长度不超过30个字符"/]
			[@b.textfield name="link.url" label="URL" value="${link.url!}" maxlength="300" style="width:500px;" comment="长度不超过300个字符"/]
			[@b.field label="图片" ]	
			 	<img id="albumImg" src="${base}/common/file.action?method=downFile&folder=${link.img!}" style="display: none"/>
                <div style="margin:3px 0;">
                    <input id="file_upload" name="file_upload" type="file" />
                </div>
			[/@]
			[@b.radios label="是否启用"  name="link.enabled" value=link.enabled items="1:启用,0:禁用"/]
			
			[@b.formfoot]
				<input type="hidden" name="link.linkType.id" value="${(link.linkType.id)!(linksTypeId)}" />
				<input type="hidden" name="link.img" value="${link.img!}" />
				<input type="hidden" name="link.id" value="${link.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;
				<input type="button" value="${b.text("action.submit")}" onClick="submit2(this.form);"/>
			[/@]
[/@]
<script>
function submit2(form){
		bg.form.submit(form,form.action,form.target,null,true);
	}
	
$(function (){
        uploadimg({
            dir:"Transit/${currentDate?string('yyyyMMdd')}",
            ofile:"${link.img!}",
            upBtn:"file_upload",
            imgNmae:"link.img",
            showImg:"albumImg",
            width:300
        });
    });

    
//     [#if link.img??]
    $("#albumImg").show();
//     [/#if]
</script>
[@b.foot/]