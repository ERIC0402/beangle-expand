[#ftl]
[@b.head/]
[@b.toolbar title="任务节点" entityId=taskNode.id!0]bar.addBack();[/@]
[@b.form action="!save" title="基本信息" theme="list"]
	[@b.field label="序号" ]${taskNode.order!}[/@]
	[@b.textfield name="taskNode.name" label="节点名称" value="${taskNode.name!}" required="true" maxlength="30" comment="最多输入30个字符"/]
	[@b.textfield name="taskNode.entry" label="方法入口" value="${taskNode.entry!}" required="true" maxlength="150" comment="最多输入150个字符"/]
	[@b.select label="所属任务" required="true" id="taskNode.task.id" name="taskNode.task.id"  value=(taskNode.task.id)!  style="width:160px;" items=ts option="id,name" onchange="typechange()"/]
	[@b.select label="所属流程" required="true" id="taskNode.workflow.id" name="taskNode.workflow.id"  value=(taskNode.workflow.id)!  style="width:160px;" items=workflows option="id,name" onchange="typechange()"/]
	[@b.field label="类型" ]
		<select name="taskNode.type" id="taskNode.type" style="width:160px;">
  				<option [#if taskNode.type == 0]selected [/#if] value="0" >普通类型</option>
				<option [#if taskNode.type == 1]selected [/#if] value="1" >跳转类型</option>
				<option [#if taskNode.type == 2]selected [/#if] value="2" >限制类型</option>
				<option [#if taskNode.type == 3]selected [/#if] value="3" >跳转限制类型</option>
		</select>
	[/@]
	[@b.field label="状态" required="true"]
		<select  name="taskNode.state" style="width:160px;" >
			<option value="0" [#if taskNode.state == 0]selected [/#if]>未开始</option>
			<option value="1" [#if taskNode.state == 1]selected [/#if]>进行中</option>
			<option value="2" [#if taskNode.state == 2]selected [/#if]>已完成</option>
		</select>
	[/@]
	[@b.textfield name="taskNode.logFormat" label="日志格式" value="${taskNode.logFormat!}" required="true" maxlength="300" comment="最多输入300个字符"/]
	[@b.select2 label="维护人员" name1st="roles"  name2nd="roleIds" items1st=roles items2nd=taskNode.roles/]
	[@b.formfoot]
		<input type="hidden" name="taskNode.id" value="${taskNode.id!}" />
		<input type="hidden" name="taskNode.order" value="${taskNode.order!}" />
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
	[/@]
[/@]
[@b.foot/]