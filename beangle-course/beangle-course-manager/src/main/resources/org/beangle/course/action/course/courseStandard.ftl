[#ftl]
[@b.head/]
<script type="text/javascript" src="${base}/static/scripts/ckeditor/ckeditor.js"></script>
[@b.toolbar title="课程" entityId=course.id!0]bar.addBack();[/@]
[#include "nav.ftl"/]
[#import "/template/form/utils.ftl" as lu/]

[@b.form action="!save"  title="课程标准" theme="list"]
[@lu.ckeditor id="standard" name="course.standard" value="${(course.standard?string)!}"/]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="course.id" value="${course.id!}" />
	[/@]
[/@]

[@b.foot/]
