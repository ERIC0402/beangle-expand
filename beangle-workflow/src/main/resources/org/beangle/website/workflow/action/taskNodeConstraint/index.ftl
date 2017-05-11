[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="taskNodeConstraintSearchForm"  action="!search" target="taskNodeConstraintlist" title="ui.searchForm" theme="search"]
			[@b.select name="taskNodeConstraint.taskNode.id" label="节点" value=(taskNode.id)! empty="..." items=taskNodes option="id,name"/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="taskNodeConstraintlist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]