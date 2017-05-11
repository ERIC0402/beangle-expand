[#ftl]
[@b.form action="${actionUrl!'!save'}" title="编辑站点" theme="list"]
	[@b.textfield name="site.name" label="common.name" value="${site.name!}" required="true" maxlength="100" size=60/]
	[@b.textfield name="site.code" label="排序" value="${site.code!}" required="true" maxlength="100" size=60/]
	[@b.textfield name="site.domain" label="域名" value="${site.domain!}" maxlength="100" size=60/]
	[@b.textfield name="site.basePath" label="根路径" value="${site.basePath!}" required="true" maxlength="100"/]
	[@b.select label="关联流程" required="true" name="site.workflow.id" empty="请选择..." value=(site.workflow.id)!21 style="width:200px;" items=workflows option="id,name"/]
	[@b.select2 label="维护人员" name1st="roles"  name2nd="roleIds" items1st=groups items2nd=site.groups/]
	[@b.radios label="common.status"  name="site.enabled" value=site.enabled!0 items="1:启用,0:禁用"/]
	[@b.formfoot]
		<input type="hidden" name="site.id" value="${site.id!}" />
		<input type="hidden" name="setgroup" value="1" />
		${params!}
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
	[/@]
[/@]
