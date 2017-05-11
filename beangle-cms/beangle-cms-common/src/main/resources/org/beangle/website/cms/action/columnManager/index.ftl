[#ftl]
[@b.head/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="columnSearchForm"  action="!search" target="columnlist" title="ui.searchForm" theme="search"]
			[@b.select name="column.site.id" label="站点" value=(site.id)! items=sites option="id,name"/]
			[@b.textfields names="column.name;栏目名"/]
			<input type="hidden" value="${idValues!}" name="idValues"/>
			<input type="hidden" value="${nameValues!}" name="nameValues"/>
			<input type="hidden" value="${isSingle!}" name="isSingle"/>
		[/@]
		</td>
		<td class="index_content">[@b.div id="columnlist" href="!search?column.site.id=${site.id!}&idValues=${idValues}&nameValues=${nameValues}&isSingle=${isSingle!}" /]</td>
	</tr>
</table>
[@b.foot/]