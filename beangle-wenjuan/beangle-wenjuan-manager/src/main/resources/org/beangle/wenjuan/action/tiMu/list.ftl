[#ftl]
[@b.head/]
[@b.grid  items=tiMus var="tiMu"]
	[@b.gridbar]
		bar.addItem("从文本添加",action.method("wbtj"));
		bar.addItem("导入",action.method("importForm"));
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("${b.text("action.export")}",
			action.exportData("tmmc:题目名称,tmlx.name:题目类型,tmfl.name:题目分类,tmOption:选项,zqda:正确答案",null,"&fileName=题目信息"));
	
		[#if tiKuId??]
			bar.addItem("返回题库管理",action.goTo("index","${base}/wenjuan/ti-ku.action"),"${base}/static/themes/default/icons/16x16/actions/backward.png");
		[/#if]
		[#if userId == 1]
			bar.addItem("刷新添加人",action.add("refreshDepartment"));
		[/#if]
	[/@]
	[@b.row]
		[@b.boxcol/]
		[#--
		[@b.col width="55%" property="tmmc" title="题目名称"][@c.substringLabel tiMu.tmmc 30/][/@]
		--]
		[@b.col width="45%" property="tmmc" title="题目名称"][/@]
		[@b.col width="10%" property="tmlx.name" title="题目类型" /]
		[@b.col width="15%" property="sstk.tkmc" title="所属题库" ][@c.substringLabel (tiMu.sstk.tkmc)!'' 7/][/@]
		[@b.col width="15%" property="tmfl.name" title="题目分类"/]
		[@b.col width="10%" property="tmnd.name" title="题目难度"/]
		[@b.col width="10%" property="enabled" title="状态" align="center"]
			[@c.sfyx enabled=tiMu.enabled/]
		[/@]
	[/@]
[/@]
[@b.foot/]