[#ftl]
[@b.head/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="annexSearchForm"  action="!search" target="annexlist" title="ui.searchForm" theme="search"]
			[@b.textfields names="annex.fileName;文件名"/]
			[#if !selectId??]
				[@b.select name="annex.isOpen" label="是否公开" value="" empty="..." items={'1':'公开','0':'私有'}/]
			[/#if]
		<input type="hidden" name="selectId" id="selectId" value="${(selectId)!}"  />
		[/@]
		</td>
		<td class="index_content">[@b.div id="annexlist" href="!search?selectId=${selectId!}&t=${time!}}" /]</td>
	</tr>
</table>
[@b.foot/]