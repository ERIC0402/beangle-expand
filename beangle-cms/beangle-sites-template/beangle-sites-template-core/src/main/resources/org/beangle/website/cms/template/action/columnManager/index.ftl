[#ftl]
[@b.head/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="columnSearchForm"  action="!search" target="columnlist" title="ui.searchForm" theme="search"]
			[@b.select name="column.site.id" label="站点" value="" items=sites option="id,name"/]
			[@b.textfields names="column.name;栏目名"/]
			<input type="hidden" value="${idValues!}" name="idValues"/>
			<input type="hidden" value="${nameValues!}" name="nameValues"/>
		[/@]
		</td>
		<td class="index_content">[@b.div id="columnlist" href="!search?idValues=${idValues}&nameValues=${nameValues}" /]</td>
	</tr>
</table>
[@b.foot/]