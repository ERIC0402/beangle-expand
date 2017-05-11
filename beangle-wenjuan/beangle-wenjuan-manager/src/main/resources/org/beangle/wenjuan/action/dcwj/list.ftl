[#ftl]
[@b.head/]
[@b.grid  items=wenJuans var="wenJuan"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		//bar.addItem("${b.text("action.new")}",action.goTo("edit", "${b.url('!edit')}"));
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		//bar.addItem("随机题目设置",action.single("wjsz"));
		//bar.addItem("题目设置",action.single("tmsj"));
		bar.addItem("预览",action.single("yuLan"));
		bar.addItem("发布",action.multi("faBu"));
		bar.addItem("收回",action.multi("shouHui"));
		//bar.addItem("复制",action.multi("fuZhi"));
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="30%" property="wjmc" title="问卷名称"]
			[@b.a href="!yuLan?wenJuan.id=${wenJuan.id}"]${wenJuan.wjmc!}[/@]
		[/@]
		[@b.col sortable="false" width="60%" property="sm" title="说明"]
			[@c.substring str=wenJuan.sm!'' mx=16/]
		[/@]
		[@b.col width="10%" property="enabled" title="状态" align="center"]
			[@c.sfyx enabled=wenJuan.enabled yes="已发布" no="未发布"/]
		[/@]
	[/@]
[/@]
[@b.foot/]