[#ftl]
[@b.head/]
[@b.toolbar title="任务节点限制"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="任务节点限制"]
		[@b.form action="!save" title="基本信息" theme="list"]
			[@b.field label="所属节点" ]
				<select name="nodeConstraint.taskNode.id" id="nodeConstraint.taskNode.id" style="width:160px;">
	   	  			[#if nodeConstraint.id??]
						<option selected value="${nodeConstraint.taskNode.id}">${nodeConstraint.taskNode.name}</option>
					[#else]
						<option selected value="${taskNode.id}">${taskNode.name}</option>
					[/#if]
				</select>
			[/@]
			[@b.field label="代码" ]
				<select name="nodeConstraint.code" id="nodeConstraint.code" style="width:160px;">
	   	  			<option [#if nodeConstraint.code??&&nodeConstraint.code="1"]selected[/#if] value="1" >限制通过人数</option>
					<option [#if nodeConstraint.code??&&nodeConstraint.code="2"]selected[/#if] value="2" >限制完成时间</option>
					<option [#if nodeConstraint.code??&&nodeConstraint.code="3"]selected[/#if] value="3" >限制人数和时间</option>
				</select>
			[/@]
			[@b.textfield name="nodeConstraint.passNum" label="限制人数" value="${nodeConstraint.passNum!}" required="true" maxlength="8" style="width:160px;"/]
			[@b.textfield name="nodeConstraint.time" label="限制时间（小时）" value="${nodeConstraint.time!}" required="true" maxlength="8"  style="width:160px;"/]
			[@b.formfoot]
				<input type="hidden" name="nodeConstraint.id" value="${nodeConstraint.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
			[/@]
		[/@]
	[/@]
[/@]
[@b.foot/]