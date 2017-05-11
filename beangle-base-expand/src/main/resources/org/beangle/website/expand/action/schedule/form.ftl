[#ftl]
[@b.head][/@]
[@b.toolbar title="日程安排-添加/修改"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="日程安排"]
		[@b.form action="!save" title="基本信息" theme="list"]
			
			[@b.textfield name="schedule.title" label="主题" value="${schedule.title!}" required="true" maxlength="100" style="width:200px;"  comment="长度不超过100个字符"/]
			
			[@b.select label="类型" required="true" name="schedule.type.id" empty="请选择..." value=(schedule.type.id)!  style="width:154px;" items=types option="id,name"/]
		 	
		 	[#--
		    [@b.datepicker label="开始时间" name="labTimeOpen.startTime" value="${startTime!}" required="true" format="HH:mm"/]
			[@b.datepicker label="结束时间" name="labTimeOpen.endTime" value="${endTime!}" required="true" format="HH:mm"/]
			--]
			
			[@b.startend label="开始/结束时间" name="schedule.kssj,schedule.jssj" required="true,true" start=schedule.kssj end=schedule.jssj  format="yyyy-MM-dd HH:mm"/]
			
			[@b.textfield name="schedule.address" label="地点" value="${schedule.address!}" required="true" maxlength="100" style="width:200px;"  comment="长度不超过100个字符"/]
			
			[@b.textarea label="详细内容" cols="50" rows="5" name="schedule.xxnr" value="${schedule.xxnr!}" maxlength="1000" comment="长度不超过1000个字符" required="true"/]
			
			[@b.radios label="是否显示"  name="schedule.visible" value=schedule.visible items={'true':'显示','false':'不显示'}/]
			
			[@b.formfoot]
				<input type="hidden" name="schedule.id" value="${schedule.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
			[/@]
		[/@]
	[/@]
[/@]
[@b.foot/]
