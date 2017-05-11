[#ftl]
[@b.head/]
<div class="contentBody">
	[#assign currStep=2/]
	[#include 'step.ftl'/]

	[#assign actionUrl='site!stepSetColumn'/]
	[#include 'editTemplateForm.ftl'/]
</div>
[@b.foot/]