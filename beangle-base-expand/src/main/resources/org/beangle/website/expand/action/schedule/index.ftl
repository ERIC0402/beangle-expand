[#ftl]
[@b.head/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="scheduleSearchForm"  action="!search" target="schedulelist" title="ui.searchForm" theme="search"]
			
			[@b.textfields names="schedule.title;主题"/]
			[@b.textfields names="schedule.address;地点"/]
			[@b.select name="schedule.type.id" label="类型" items=types empty="..." option="id,name" style="width:85px;"/]
			[@b.select name="schedule.visible" label="是否显示" value="1" empty="..." items={'true':'显示','false':'不显示'} style="width:85px;"/]
			
		[/@]
		</td>
		<td class="index_content">[@b.div id="schedulelist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]