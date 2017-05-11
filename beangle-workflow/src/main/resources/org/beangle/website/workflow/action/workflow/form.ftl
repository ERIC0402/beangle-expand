[#ftl]
[@b.head/]
[@b.toolbar title="流程" entityId=workflow.id!0]bar.addBack();[/@]
[@b.form title="基本信息" action="!save" theme="list"]
	[@b.textfield name="workflow.code" label="流程代码" value="${workflow.code!}" required="true" maxlength="20" comment="最多输入20个字符"/]
	[@b.textfield name="workflow.name" label="流程名称" value="${workflow.name!}" required="true" maxlength="100" comment="最多输入100个字符"/]
	[@b.textfield name="workflow.enName" label="英文名称" value="${workflow.enName!}" maxlength="50"/]
	[@b.field label="流程类型" required="true"]
		<select  name="workflow.type" style="width:160px;" title="流程类型">
			<option value="0" [#if workflow.type==0]selected="selected"[/#if]>固定</option>
			<option value="1" [#if workflow.type==1]selected="selected"[/#if]>不固定</option>
		</select>
	[/@]
	[@b.select2 label="维护人员" name1st="roles"  name2nd="roleIds" items1st=roles items2nd=workflow.roles/]
	[@b.textfield name="workflow.description" label="流程描述" value="${workflow.description!}" maxlength="100" comment="最多输入100个字符"/]
	[@b.formfoot]
		<input type="hidden" name="workflow.id" value="${workflow.id!}" />
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
	[/@]
[/@]
[@b.foot/]