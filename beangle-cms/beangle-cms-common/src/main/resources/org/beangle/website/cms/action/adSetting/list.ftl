[#ftl]
[@b.head/]
[@b.grid  items=adSettings var="adSetting"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="10%" property="name" title="名称" /]
		[@b.col width="10%" property="position.name" title="位置" /]
		[@b.col width="75%" property="content" title="内容" sortable="false"]${adSetting.content!}[/@]
		[@b.col width="5%" property="enabled" title="状态" align="center"][@c.enabled adSetting.enabled!true/][/@]
	[/@]
[/@]
[@b.foot/]