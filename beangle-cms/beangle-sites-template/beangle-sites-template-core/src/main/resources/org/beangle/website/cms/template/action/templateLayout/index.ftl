[#ftl]
[@b.head/]
[#include "../templateNav.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="templateLayoutSearchForm"  action="!search" target="templateLayoutlist" title="ui.searchForm" theme="search"]
			[@b.select name="templateLayout.enabled" label="common.status" value="" empty="全部" items={'1':'启用','0':'禁用'}/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="templateLayoutlist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]