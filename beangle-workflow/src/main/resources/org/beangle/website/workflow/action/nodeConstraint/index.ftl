[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="nodeConstraintSearchForm"  action="!search" target="nodeConstraintlist" title="ui.searchForm" theme="search"]
			[@b.select name="nodeConstraint.stepNode.id" label="节点" value=(stepNode.id)! empty="..." items=stepNodes option="id,name"/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="nodeConstraintlist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]