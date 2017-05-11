[#ftl]
[@b.head/]
<div class="contentBody">
	[#assign currStep=4/]
	[#include 'step.ftl'/] <iframe src="site!templateList.action?siteId=${site.id}&step=1" width="100%" height="400" frameborder="0"></iframe>
	[@b.form action="site!search" theme="list"]
	[@b.formfoot]
	[@b.submit value="完成"/]
	[/@b.formfoot]
	[/@b.form]
</div>
[@b.foot/]