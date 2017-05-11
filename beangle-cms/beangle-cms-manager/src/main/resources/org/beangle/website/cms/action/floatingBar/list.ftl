[#ftl]
[@b.head/]

[@b.grid  items=floatingBars var="floatingBar" ]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	
	[@b.row]
	[@b.boxcol/]
		[@b.col width="15%" property="name" title="名称" /]
		[@b.col width="15%" property="szwz.name" title="显示位置" /]
		[@b.col width="15%" property="pftgs" title="漂浮条格式" /]
		[@b.col width="15%" property="site.name" title="所属站点" /]
		[@b.col width="25%" property="url" title="链接" /]
		[@b.col width="15%"  property="visible" title="是否显示"][#if floatingBar.visible=true]显示 [/#if][#if floatingBar.visible=false]不显示 [/#if][/@]
	
	[/@]
[/@]
[@b.foot/]