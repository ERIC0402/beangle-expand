[#ftl]
[@b.head/]
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
<script>
	var base='${base}';
</script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/jquery-validation/RegForm.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/jquery-validation/jquery.metadata.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/jquery-validation/jquery-validation/jquery.validate.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/jquery-validation/jquery-validation/additional-methods.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/jquery-validation/jquery-validation/localization/messages_cn.js"></script>
[@b.toolbar title="快速流程设置" ]bar.addBack();[/@]
[@b.form title="基本信息" action="!saveWorkflowAtOne" theme="list"]
	[@b.textfield name="workflow.code" label="流程代码" value="" required="true" maxlength="20" comment="最多输入20个字符"/]
	[@b.textfield name="workflow.name" label="流程名称" value="" required="true" maxlength="100" comment="最多输入100个字符"/]
	[@b.field label="流程步骤设置"]
		<table width="600px" border="1" cellspacing="0" cellpadding="0">
		    <thead>
				<tr>
					[#--<th>序号</th>--]
				    <th>步骤名称</th>
				    <th>操作人</th>
				    <th>操作</th>
				</tr>
		    </thead>
		    <tbody>
				[@subjectTr num=0 last=true/]
		    </tbody>
		</table>
		<textarea class="subjectTemplate template" style="display:none">
		    [@subjectTr/]
		</textarea>
		[#macro subjectTr num='{v}' v={} last=true]
			<tr>
				[#--<td align="center">
			    	<label class="seqno">${num}</label>
			    </td>--]
			    <td align="center">
			    	<input type="text" name="stepNode${num}.name" maxlength="100"/>
			    </td>
			    <td>
			    	<select data-placeholder="选择用户" tabindex="1" id="user${num}" name="stepNode${num}.user.id"  style="width:300px;">
						<option value=""></option>
						[#list users as item]
							<option value="${(item.id)!}">[${(item.name)!}]${(item.fullname)!}</option>
						[/#list]
		             </select>
		             <script language="JavaScript">
						jQuery("#user${num}").chosen();
					 </script>
		             <input type="hidden" name="stepNode" value="${num}"/>
			    </td>
			    <td>
					[#if last]
						<input class="btn" onclick="addSubjectTr(this, 'subjectTemplate')" type="button" value="添加"/>
						[#if num?string != '0']
							<input class="btn" onclick="delSubjectTr(this)" type="button" value="删除"/>
						[/#if]
					[#else]
						<input class="btn" onclick="delSubjectTr(this)" type="button" value="删除"/>
					[/#if]
			    </td>
			</tr>
		[/#macro]
	[/@]
	[@b.formfoot]
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
	[/@]
[/@]
[@b.foot/]