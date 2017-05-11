[#ftl]
[@b.head/]
<div class="contentBody">
	[#assign currStep=1/]
	[#include 'step.ftl'/]

	[#assign actionUrl='site!stepSetTemplateGroup'/]
	[#include 'editSiteForm.ftl'/]
</div>
[@b.foot/]