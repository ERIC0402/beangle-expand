[#ftl]
[@b.toolbar title="创建站点向导"]bar.addBack();[/@]
<style type="text/css">
	.step{float:left; font-size: 16px; padding: 5px 20px; color: #aaa; margin-top: 5px;}
	.currSetp a{color:red;}
	.disabledStep{color:#333}
</style>
<div>
	[#macro Setp href name step]
	<div class="step [#if currStep == step]currSetp[/#if]">
		[#if currStep gte step]
		[@b.a href="${href}" ajaxHistory="false"]${name}[/@]
		[#else]
		<label class="disabledStep">${name}</label>
		[/#if]
	</div>
	[/#macro]
	[@Setp name="第一步、创建站点" href="site!stepAddSite?site.id=${site.id!}" step=1/]
	[@Setp name="第二步、选择模板组" href="site!stepSetTemplateGroup?site.id=${site.id!}" step=2/]
	[@Setp name="第三步、添加栏目" href="site!stepSetColumn?site.id=${site.id!}" step=3/]
	[@Setp name="第四步、设置页面" href="site!stepSetPage?site.id=${site.id!}" step=4/]
	<div style="clear: both"></div>
</div>
<hr/>