[#ftl]
[@b.head/]
[#include "../templateNav.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="widgetSearchForm"  action="!search" target="widgetlist" title="ui.searchForm" theme="search"]
			[@b.textfields names="widget.name;名称"/]
			[@b.select name="widget.groups.id" label="模板组" value="" empty="全部" items=groups/]
			[@b.select name="widget.type.id" label="类型" value="" empty="全部" items=types/]
			[@b.select name="widget.enabled" label="common.status" value="" empty="全部" items={'1':'启用','0':'禁用'}/]
			[@b.textfields names="widget.className;类名"/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="widgetlist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]