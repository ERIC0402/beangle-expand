[#ftl]
[@b.head/]
[@b.grid  items=entrys var="entry"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="20%" property="name" title="名称" /]
		[@b.col width="20%" property="url" title="访问入口" /]
		[@b.col width="40%" property="basePath" title="相关站点"]
			[#list entry.sites as v][#if v_index gt 0]，[/#if]${v.name}[/#list]
		[/@]
		[@b.col width="20%" property="enabled" title="状态" ]
			[#if entry.enabled]
				启用
			[#else]
				禁用
			[/#if]
		[/@]
	[/@]
[/@]
[@b.foot/]