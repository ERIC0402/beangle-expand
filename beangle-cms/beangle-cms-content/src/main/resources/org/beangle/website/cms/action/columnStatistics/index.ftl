[#ftl]
[@b.head/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="statisticsSearchForm"  action="!search" target="statisticslist" title="ui.searchForm" theme="search"]
			[@b.datepicker id="startime" name="startime" label="开始日期"/]
			[@b.datepicker id="endtime" name="endtime" label="结束日期"/]
			[@b.select name="subColumn.enabled" label="站点" value="" empty="..." items=sites option="id,name"/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="statisticslist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]