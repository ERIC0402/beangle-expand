[#ftl]
[@b.head/]
	[@b.form action="!search?orderBy=adSetting.id" title="ui.searchForm" target="adSettinglist" theme="search"]
		[@b.textfields names="adSetting.name;名称"/]
		<input type="hidden" name="orderBy" value="adSetting.position.code"/>
	[/@]
	[@b.div href="!search" id="adSettinglist" class="dataList"/]
[@b.foot/]
