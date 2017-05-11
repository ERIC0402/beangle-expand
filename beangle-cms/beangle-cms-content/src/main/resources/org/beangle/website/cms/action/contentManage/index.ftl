[#ftl]
[@b.head/]
[#include "../columnTree.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view" style="width:200px;">
			[@siteList "!searchTree"/]
			[#include "leftColumnTree.ftl"/]
		</td>
		
		<td class="index_content">[@b.div id="contentlist" name="contentlist" href="!listandform" /]</td>
	</tr>
</table>
[@b.foot/]