[#ftl]
[@b.head/]
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
[@b.toolbar title="专业" entityId=professional.id!0]bar.addBack();[/@]
[@b.form action="!save" title="专业信息" theme="list"]
	[@b.textfield label="专业代码" name="professional.code" value="${professional.code!}"  style="width:200px;" required="true" maxlength="10"/]
	[@b.textfield label="专业名称" name="professional.name" value="${professional.name!}" style="width:200px;" required="true" maxlength="100" /]
	[@b.textfield label="英文名称" name="professional.engName" value="${professional.engName!}" style="width:200px;" maxlength="100" /]
	[@b.field label="专业负责人"]
		<select data-placeholder="选择负责人" tabindex="1" id="zyfzr" name="professional.manager.id" style="width:200px;">
			<option value=""></option>
			[#list faculties as item]
				<option value="${(item.id)!}" [#if professional.manager??&&professional.manager.id=item.id]selected="selected"[/#if]>${(item.fullname)!}</option>
			[/#list]
        </select>
	[/@]
	[@b.select label="所属部门" name="professional.department.id" value=(professional.department.id)! style="width:200px;"  items=departments option="id,name" empty="请选择..."/]
	[@b.textarea label="专业介绍"  name="professional.introduction" maxlength="500" value=professional.introduction! rows="3" cols="40"/]
	[@b.textarea label="培养方向"  name="professional.trainingDirection" maxlength="500" value=professional.trainingDirection! rows="3" cols="40"/]
	[@b.textfield label="学生规模" name="professional.stdNum" value="${professional.stdNum!}"  style="width:200px;" maxlength="5"/]
	[@b.field label="管理角色"]
		<select data-placeholder="选择岗位（可多选）" tabindex="1" id="groupIds" name="groupIds" multiple="true" style="width:400px;">
			<option value=""></option>
			[#list groups?sort_by("name") as item]
				<option value="${(item.id)!}" [#if professional.roles?seq_contains(item)]selected="selected"[/#if]>${(item.name)!}</option>
			[/#list]
        </select>
	[/@]
	[@b.radios label="状态" name="professional.enabled" value=professional.enabled items="1:有效,0:无效"/]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="professional.id" value="${professional.id!}" />
	[/@]
[/@]
<script language="JavaScript">
	jQuery("#zyfzr").chosen();
	jQuery("#groupIds").chosen();
</script>
[@b.foot/]