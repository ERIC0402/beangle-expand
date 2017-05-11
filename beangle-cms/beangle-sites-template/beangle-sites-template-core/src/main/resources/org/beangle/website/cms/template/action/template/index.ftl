[#ftl]
[@b.head/]
[#include "../templateNav.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="templateSearchForm"  action="!search" target="templatelist" title="ui.searchForm" theme="search"]
			[@b.textfields names="template.name;名称"/]
			[@b.select name="template.group.id" label="模板组" value="" empty="全部" items=groups/]
			[@b.select name="template.type.id" label="类型" value="" empty="全部" items=types/]
			[@b.select name="template.enabled" label="common.status" value="" empty="全部" items={'1':'启用','0':'禁用'}/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="templatelist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]