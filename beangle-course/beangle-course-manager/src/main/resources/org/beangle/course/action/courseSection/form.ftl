[#ftl]
[@b.head/]
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
[@b.toolbar title="课程" entityId=course.id!0]bar.addBack();[/@]
[#include "../course/nav.ftl"/]
[@b.form action="!save" title="课程章节" theme="list"]
	[@b.textfield label="章节名称" name="courseSection.name" value="${courseSection.name!}" style="width:200px;" required="true" maxlength="100" /]
	[@b.select label="上级章节" name="parent.id" value=(courseSection.parent.id)! style="width:200px;"  items=parents option="id,description" empty="请选择..."/]
	[@b.textfield label="同级排序" name="indexno" value="${courseSection.indexno!}" required="true" maxlength="3" check="match('integer').range(1,999)" /]
	[@b.field label="课程资源"]
		[#include "../courseContent/resourceAdd.ftl"/]
	[/@]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="courseSection.id" value="${courseSection.id!}"/>
		<input type="hidden" name="courseId" value="${course.id!}"/>
	[/@]
[/@]
[@b.foot/]