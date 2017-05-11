[#ftl]
[@b.head/]
[#include "../columnTree.ftl"/]
<div class="BlankLine0">&nbsp;</div>
<table  class="indexpanel">
	<tr>
		<td class="index_view" style="width:160px;">
			[@siteList formAction="!searchTree" isColumnManager=true/]
			[#include "leftColumnTree.ftl"/]
		</td>
		<td class="index_content">[@b.div id="columnlist" name="columnlist" href="!listandform" /]</td>
	</tr>
</table>
[@b.foot/]