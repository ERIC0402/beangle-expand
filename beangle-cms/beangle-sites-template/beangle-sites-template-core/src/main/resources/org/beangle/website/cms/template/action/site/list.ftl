[#ftl]
[@b.head/]
[@b.grid  items=sites var="site"]
	[@b.gridbar]
		bar.addItem("发布",action.multi('publishAll', '是否将后台修改应用到前台网站？'));
		bar.addItem("清除缓存",action.multi('cleanCache'));
		bar.addItem("新建向导",action.method('stepAddSite'));
		bar.addItem("编辑站点",action.edit());
		bar.addItem("选择模板组",action.single('editTemplate'));
		bar.addItem("设置页面",action.single('templateList'));
		bar.addItem("${b.text("action.delete")}",action.remove('删除站点后相关数据将不能恢复，是否确定删除？'));
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="10%" property="code" title="排序" /]
		[@b.col width="20%" property="name" title="名称" /]
		[@b.col width="20%" property="domain" title="域名" /]
		[@b.col width="20%" property="basePath" title="根路径" /]
		[@b.col width="20%" property="workflow.name" title="流程" /]
		[@b.col width="10%" property="enabled" title="状态" ]
			[#if site.enabled]
				启用
			[#else]
				禁用
			[/#if]
		[/@]
	[/@]
[/@]
[@b.foot/]