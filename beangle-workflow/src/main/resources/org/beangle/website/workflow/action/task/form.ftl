[#ftl]
[@b.head/]
[@b.toolbar title="任务管理" entityId=task.id!0]bar.addBack();[/@]
[@b.form action="!save" title="基本信息" theme="list"]
	[@b.textfield name="task.code" label="任务代码" value="${task.code!}" required="true" maxlength="20" comment="最多输入20个字符"/]
	[@b.textfield name="task.name" label="任务名称" value="${task.name!}" required="true" maxlength="100" comment="最多输入100个字符"/]
	[@b.field label="任务状态" required="true"]
		<select  name="task.state" style="width:160px;" >
			<option value="0" [#if task.state != 1]selected [/#if]>进行中</option>
			<option value="1" [#if task.state == 1]selected [/#if]>已完成</option>
		</select>
	[/@]
	[@b.field label="当前任务节点"]
		<select  name="task.currentNode.id" style="width:160px;" >
			<option value="0">开始</option>
			[#list cns as cn]
				<option [#if (cn.id == (task.currentNode.id)!(0))]selected[/#if] value="${cn.id}">${cn.name}</option>
			[/#list]
		</select>
	[/@]
	[@b.select2 label="维护人员" name1st="roles"  name2nd="roleIds" items1st=roles items2nd=task.roles/]
	[@b.formfoot]
		<input type="hidden" name="task.id" value="${task.id!}" />
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
	[/@]
[/@]
[@b.foot/]