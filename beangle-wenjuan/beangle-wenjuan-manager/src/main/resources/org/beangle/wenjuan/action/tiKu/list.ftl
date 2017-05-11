[#ftl]
[@b.head/]
[@b.grid  items=tiKus var="tiKu"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("题目管理",action.openNew("index","${base}/wenjuan/ti-mu.action","tiKu.id"),"${base}/static/themes/default/icons/16x16/actions/go-next.png");
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="50%" property="tkmc" title="题库名称"/]
		[@b.col width="20%" property="tkfl.name" title="题库分类"/]
		[@b.col width="10%" property="tmsl" title="题目数量" /]
		[@b.col width="10%" property="createUser.fullname" title="创建人" /]
		[@b.col width="10%" property="enabled" title="是否有效" align="center"]
			[@c.sfyx enabled=tiKu.enabled/]
		[/@]
	[/@]
[/@]
[@b.foot/]