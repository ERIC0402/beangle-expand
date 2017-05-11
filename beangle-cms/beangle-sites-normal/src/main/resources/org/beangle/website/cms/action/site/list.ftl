[#ftl]
[@b.head/]
[@b.grid  items=sites var="site"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="20%" property="name" title="名称" /]
		[@b.col width="20%" property="domain" title="域名" /]
		[@b.col width="20%" property="basePath" title="根路径" /]
		[@b.col width="20%" property="workflow.name" title="流程" /]
		[@b.col width="20%" property="enabled" title="状态" ]
			[#if site.enabled]
				启用
			[#else]
				禁用
			[/#if]
		[/@]
	[/@]
[/@]
[@b.foot/]