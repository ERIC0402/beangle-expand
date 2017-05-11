[#ftl]
[@b.head/]
[#include "../templateNav.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="templateGroupSearchForm"  action="!search" target="templateGrouplist" title="ui.searchForm" theme="search"]
			[@b.textfields names="templateGroup.name;名称"/]
			[@b.select name="templateGroup.enabled" label="common.status" value="" empty="全部" items={'1':'启用','0':'禁用'}/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="templateGrouplist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]