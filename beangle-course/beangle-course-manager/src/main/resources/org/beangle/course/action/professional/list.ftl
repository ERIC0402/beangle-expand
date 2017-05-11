[#ftl]
[@b.head/]
<script type="text/javascript" src="${base}/static/scripts/comm/jquery.blockUI.js"></script>
[@b.grid  items=professionals var="professional"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("专业教师设置",action.single("addPTeacherList"));
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="10%" property="code" title="专业代码" /]
		[@b.col width="20%" property="name" title="专业名称" /]
		[@b.col width="16%" property="engName" title="英文名" /]
		[@b.col width="16%" property="department.name" title="所属部门" /]
		[@b.col width="15%" property="manager.fullname" title="负责人" /]
		[@b.col width="15%" property="group.name" title="管理角色"]
			[#list professional.roles as group]
				${(group.name)!} [#if group_has_next]<br>[/#if]
			[/#list]
		[/@]
		[@b.col width="10%" title="状态" ]
			[@c.sfyx professional.enabled/]
		[/@]
	[/@]
[/@]
[@b.foot/]