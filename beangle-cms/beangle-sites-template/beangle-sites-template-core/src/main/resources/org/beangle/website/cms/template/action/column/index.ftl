[#ftl]
[@b.head/]
[#include "/org/beangle/website/cms/action/columnTree.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view" style="width:200px;">
			[@siteList formAction="!searchTree" isColumnManager=true/]
			[#include "leftColumnTree.ftl"/]
		</td>
		<td class="index_content">[@b.div id="columnlist" name="columnlist" href="!listandform?siteId=${(site.id)!}" /]</td>
	</tr>
</table>
[@b.foot/]