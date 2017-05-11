[#ftl]
[@b.head/]
<div class="contentBody">
	[#assign currStep=3/]
	[#include 'step.ftl'/] <iframe src="column.action?siteId=${site.id}" width="100%" height="400" frameborder="0"></iframe>
	[@b.form action="site!stepSetPage" theme="list"]
	[@b.formfoot]
	<input type="hidden" name="siteId" value="${site.id}" />
	[@b.submit value="下一步"/]
	[/@b.formfoot]
	[/@b.form]
</div>
[@b.foot/]