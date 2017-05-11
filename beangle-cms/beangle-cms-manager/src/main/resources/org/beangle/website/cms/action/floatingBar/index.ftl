[#ftl]
[@b.head/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="floatingBarSearchForm"  action="!search" target="floatingBarlist" title="ui.searchForm" theme="search"]
			
			[@b.textfields names="floatingBar.name;名称"/]
			
			[@b.select name="floatingBar.szwz.id" label="显示位置" items=szwzs empty="..." option="id,name" style="width:85px;"/]
			
			[@b.select name="floatingBar.site.id" label="所属站点" items=sites empty="..." option="id,name" style="width:85px;"/]
			
			
		[/@]
		</td>
		<td class="index_content">[@b.div id="floatingBarlist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]