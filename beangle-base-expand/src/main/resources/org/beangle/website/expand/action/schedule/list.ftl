[#ftl]
[@b.head/]

[@b.grid  items=schedules var="schedule" ]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	
	[@b.row]
	[@b.boxcol/]
		[@b.col width="15%" property="title" title="主题" /]
		[@b.col width="15%" property="type.name" title="类别" /]
		[@b.col width="15%" property="address" title="地点" /]
		[@b.col width="40%"  title="时间"]${schedule.kssj?string("yyyy-MM-dd HH:mm")}至${schedule.jssj?string("yyyy-MM-dd HH:mm")}[/@]
		[@b.col width="15%"  property="visible" title="是否显示"][#if schedule.visible=true]显示 [/#if][#if schedule.visible=false]不显示 [/#if][/@]
	
	[/@]
[/@]
[@b.foot/]