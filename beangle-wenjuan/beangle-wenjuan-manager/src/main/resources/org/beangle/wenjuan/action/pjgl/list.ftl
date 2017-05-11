[#ftl]
[@b.head/]
[#assign wenJuans=wenJuans/]
[@b.form action="!search" name="wenJuanPjSearchForm" id="wenJuanPjSearchForm"]
	[@b.grid  items=wenJuans var="wenJuan" filterable="false"]
		[@b.gridbar]
			bar.addItem("${b.text("action.new")}",action.add());
			//bar.addItem("${b.text("action.new")}",action.goTo("edit", "${base}/wenjuan/sjgl!edit.action"));
			bar.addItem("${b.text("action.modify")}",action.edit());
			bar.addItem("${b.text("action.delete")}",action.remove());
			bar.addItem("题目设置",action.openNew("index", "${base}/wenjuan/wjtmgl.action"));
			bar.addItem("预览",action.single("yuLan"));
			bar.addItem("发布",action.multi("faBu"));
			bar.addItem("收回",action.multi("shouHui"));
			//bar.addItem("复制",action.multi("fuZhi"));
		[/@]
		[#--
		[@b.gridfilter property="enabled"]
			<select  name="wenJuan.enabled" style="width:100%;" onchange="bg.form.submit(this.form)">
				<option value="" [#if (Parameters['wenJuan.enabled']!"")=""]selected="selected"[/#if]>...</option>
				<option value="true" [#if (Parameters['wenJuan.enabled']!"")="true"]selected="selected"[/#if]>已发布</option>
				<option value="false" [#if (Parameters['wenJuan.enabled']!"")="false"]selected="selected"[/#if]>未发布</option>
			</select>
		[/@]
		--]
		[@b.row]
			[@b.boxcol/]
			[@b.col width="40%" property="wjmc" title="问卷名称"]
				[@b.a href="!yuLan?wenJuan.id=${wenJuan.id}"]${wenJuan.wjmc!}[/@]
			[/@]
			[@b.col sortable="false" width="30%" property="sm" title="说明" /]
			[@b.col sortable="false" filterable="false" width="20%" property="xxScore" title="选项分数"/]
			[@b.col width="10%" property="enabled" title="状态" align="center"]
				[@c.sfyx enabled=wenJuan.enabled yes="已发布" no="未发布"/]
			[/@]
		[/@]
	[/@]
[/@]
[@b.foot/]