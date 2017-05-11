[#ftl]
[@b.head/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="onlineMessTypeSearchForm"  action="!search" target="onlineMessTypelist" title="ui.searchForm" theme="search"]
			[@b.select name="onlineMessType.site.id" label="所属站点" value="" empty="..." items=sites option="id,name"/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="onlineMessTypelist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]