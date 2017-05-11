[#ftl]
[@b.head/]
<script src="${base}/static/scripts/uploadify/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/> 
[@b.toolbar title="课程" entityId=course.id!0]bar.addBack();[/@]
[#include "nav.ftl"/]
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
<script type="text/javascript" src="${base}/static/scripts/my97/WdatePicker-4.72.js"></script>
[#include "/template/list/comm.ftl"]

[@b.form action="!save"  title="基本信息" theme="list"]
	[@b.textfield label="课程代码" name="course.code" value="${course.code!}"  style="width:200px;" required="true" maxlength="10"/]
	[@b.textfield label="课程名称" name="course.name" value="${course.name!}" style="width:200px;" required="true" maxlength="100" /]
	[@b.textfield label="英文名称" name="course.engName" value="${course.engName!}" style="width:200px;" maxlength="100" /]
	[@b.select label="课程性质" name="course.courseType.id" value=(course.courseType.id)! style="width:200px;" required="true" items=courseTypes option="id,name" empty="请选择..."/]
	[@b.field label="专业"]
		<select data-placeholder="选择专业" tabindex="1" id="zy"  multiple="true" name="professionalIds" style="width:200px;">
			<option value=""></option>
			[#list professionals as item]
				<option value="${(item.id)!}" [#if course.professionals?seq_contains(item)]selected="selected"[/#if]>${(item.name)!}</option>
			[/#list]
        </select>
	[/@]
	[@b.field label="课程负责人"]
		<select data-placeholder="选择负责人" tabindex="1" id="zyfzr" name="course.courseLeader.id" style="width:200px;">
			<option value=""></option>
			[#list faculties as item]
				<option value="${(item.id)!}" [#if course.courseLeader??&&course.courseLeader.id=item.id]selected="selected"[/#if]>${(item.fullname)!}</option>
			[/#list]
        </select>
	[/@]
	[@b.field label="课程封面" ]
		 <img id="albumImg" width=160 height=120 src="${base}/common/file.action?method=downFile&folder=${(course.previewImage)!}" style="display: none"/>
		 <input id="pic_upload" name="picAdress" type="file" />
	[/@]
	[@b.datepicker label="发布日期" name="course.publishAt" value=(course.publishAt)! format="date"/]
	
	[@b.radios label="状态" name="course.enabled" value=course.enabled items="1:有效,0:无效"/]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="course.id" value="${course.id!}" />
		<input type="hidden" name="iconPath" value="${(course.previewImage)!}" />
	[/@]
[/@]

<script language="JavaScript">
	jQuery("#zyfzr").chosen();
	jQuery("#zy").chosen();
	
	$(function (){
        uploadimg({
            dir:"img/courseImg",
            ofile:"${(course.previewImage)!}",
            upBtn:"pic_upload",
            imgNmae:"iconPath",
            showImg:"albumImg",
            width:400
        });
    });
     [#if course.previewImage??]
		$("#albumImg").show();
	[/#if]
</script>
[@b.foot/]