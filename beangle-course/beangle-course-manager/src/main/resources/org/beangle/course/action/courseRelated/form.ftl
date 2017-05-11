[#ftl]
[@b.head/]
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
[@b.toolbar title="课程" entityId=course.id!0]bar.addBack();[/@]
[#include "../course/nav.ftl"/]
[@b.form action="!save"  title="相关课程" theme="list"]
	[@b.field label="相关课程"]
		<select data-placeholder="选择课程" tabindex="1" id="xgkc"  multiple="true" name="courseIds" style="width:200px;">
			<option value=""></option>
			[#list courses as item]
				<option value="${(item.id)!}">${(item.name)!}</option>
			[/#list]
        </select>
	[/@]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="courseRelated.id" value="${courseRelated.id!}" />
		<input type="hidden" name="courseId" value="${course.id!}" />
	[/@]
[/@]
<script language="JavaScript">
	jQuery("#xgkc").chosen();
</script>
[@b.foot/]