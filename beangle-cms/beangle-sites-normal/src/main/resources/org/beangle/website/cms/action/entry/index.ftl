[#ftl]
[@b.head/]
		[@b.form name="entrySearchForm"  action="!search" target="entrylist" title="ui.searchForm" theme="search"]
			[@b.select name="entry.enabled" label="common.status" value="" empty="全部" items={'1':'启用','0':'禁用'}/]
			[@b.textfields names="entry.url;访问入口"/]
			[@b.textfields names="entry.name;名称"/]
		[/@]
		[@b.div id="entrylist" href="!search" /]
[@b.foot/]