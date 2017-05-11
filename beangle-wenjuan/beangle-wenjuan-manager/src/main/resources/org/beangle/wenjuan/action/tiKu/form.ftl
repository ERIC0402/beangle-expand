[#ftl]
[@b.head]
[/@]

<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/ajax-chosen.js"></script>
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
[@b.toolbar title=c.formTitle("题库", tiKu)]bar.addBack();[/@]
		[@b.form action="!save" title="题库信息" theme="list"]
					[@b.textfield name="tiKu.tkmc" label="题库名称" required="true" value="${tiKu.tkmc!}"style="width:300px;" maxlength="100" comment="最多输入不超过100个字"/]
					[@b.select label="题库分类" required="true" name="tiKu.tkfl.id" empty="请选择" value=(tiKu.tkfl.id)!  style="width:200px;" items=types option="id,name"/]
					[@b.field label="管理角色"]
						<select data-placeholder="选择角色（可多选）" tabindex="1" id="groupIds" name="groupIds" multiple="true" style="width:400px;">
							<option value=""></option>
							[#list tiKu.groups as item]
								<option value="${(item.id)!}" selected>${(item.name)!}</option>
							[/#list]
				        </select>
				        <br>
				        <input type="button" value="选择角色" onclick="selectGroup()">
					[/@]
					[@b.field label="管理人员添加"]
						<select id="userSelect" name="userIds" style="width: 300px;" multiple="true" >
							[#list tiKu.users as u]
								<option value="${u.id}" selected="selected">${u.fullname!}</option>
							[/#list]
						</select>
					[/@]
					[@b.radios name="tiKu.enabled" label="是否有效" value=tiKu.enabled items="1:有效,0:无效"/]
			[@b.formfoot]
				<input type="hidden" name="tiKu.id" value="${tiKu.id!}" />
				[@b.redirectParams/]
				[@b.submit value="action.submit"/]
			[/@]
		[/@]
<script type="text/javascript">
	jQuery("#groupIds").chosen();
	function selectGroup(){
		var groupIds = $("#groupIds").val();
		if(groupIds == null){
			groupIds = "";
		}
		jQuery().colorbox({
			iframe : true,
			width : "800",
			height : "80%",
			href : "${base}/zyk/core/select-group.action?groupIds="+groupIds
		});
	}
	
	function setGroup() {
		jQuery("#groupIds").trigger("liszt:updated");
		jQuery("#groupIds").chosen();
		jQuery.colorbox.close();
	}
	jQuery("#userSelect").data("placeholder","请选择...").ajaxChosen({
			method: 'POST',
			url: '${base}/common/chosen!user.action'
		}
	);
	
</script>
[@b.foot/]
