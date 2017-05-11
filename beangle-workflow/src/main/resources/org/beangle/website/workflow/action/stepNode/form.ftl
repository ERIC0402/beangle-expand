[#ftl]
[@b.head/]
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
[@b.toolbar title="步骤节点" entityId=stepNode.id!0]bar.addBack();[/@]
[@b.form action="!save" title="基本信息" theme="list"]
	[@b.field label="序号" ]${stepNode.order!}[/@]
	[@b.textfield name="stepNode.name" label="节点名称" value="${stepNode.name!}" required="true" maxlength="30" comment="最多输入30个字符"/]
	[@b.textfield name="stepNode.entry" label="方法入口" value="${stepNode.entry!}" required="true" maxlength="150" comment="最多输入150个字符"/]
	[@b.select label="所属流程" required="true" id="stepNode.workflow.id" name="stepNode.workflow.id"  value=(stepNode.workflow.id)!(workflow.id)  style="width:160px;" items=workflows option="id,name" onchange="typechange()"/]
	[@b.field label="类型" ]
		<select name="stepNode.type" id="stepNode.type" style="width:160px;">
  				<option [#if stepNode.type == 0]selected [/#if] value="0" >普通类型</option>
				<option [#if stepNode.type == 1]selected [/#if] value="1" >跳转类型</option>
				<option [#if stepNode.type == 2]selected [/#if] value="2" >限制类型</option>
				<option [#if stepNode.type == 3]selected [/#if] value="3" >跳转限制类型</option>
		</select>
	[/@]
	[@b.textfield name="stepNode.logFormat" label="日志格式" value="${stepNode.logFormat!}" required="true" maxlength="300" comment="最多输入300个字符"/]
	[@b.field label="操作人"]
		<select data-placeholder="操作人" tabindex="1" id="userIds" name="stepNode.user.id" style="width:200px;">
			<option value=""></option>
			[#list users?sort_by("name") as item]
				<option value="${(item.id)!}" [#if !(stepNode.user??&&stepNode.user.id=item.id)]selected="selected"[/#if]>[${(item.name)!}]${(item.fullname)!}</option>
			[/#list]
	    </select>
	    <script language="JavaScript">
			jQuery("#userIds").chosen();
		</script>
	[/@]
	[@b.select2 label="维护人员" name1st="roles"  name2nd="roleIds" items1st=roles items2nd=stepNode.roles/]
	[@b.formfoot]
		<input type="hidden" name="stepNode.id" value="${stepNode.id!}" />
		<input type="hidden" name="stepNode.order" value="${stepNode.order!}" />
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
	[/@]
[/@]
[@b.foot/]