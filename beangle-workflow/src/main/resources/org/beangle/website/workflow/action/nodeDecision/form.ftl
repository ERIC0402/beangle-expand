[#ftl]
[@b.head/]
[@b.toolbar title="步骤节点跳转"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="步骤节点跳转"]
		[@b.form action="!save" title="基本信息" theme="list"]
			[@b.field label="所属节点" ]
				<select name="nodeDecision.stepNode.id" id="nodeDecision.stepNode.id" style="width:160px;">
	   	  			[#if nodeDecision.id??]
						<option selected value="${nodeDecision.stepNode.id}">${nodeDecision.stepNode.name}</option>
					[#else]
						<option selected value="${stepNode.id}">${stepNode.name}</option>
					[/#if]
				</select>
			[/@]
			[@b.textfield name="nodeDecision.value" label="返回值" value="${nodeDecision.value!}" required="true" maxlength="60" style="width:160px;"/]
			[@b.textfield name="nodeDecision.gotoOrder" label="跳转到节点" value="${nodeDecision.gotoOrder!}" required="true" maxlength="8"  style="width:160px;"/]
			[@b.formfoot]
				<input type="hidden" name="nodeDecision.id" value="${nodeDecision.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
			[/@]
		[/@]
	[/@]
[/@]
[@b.foot/]