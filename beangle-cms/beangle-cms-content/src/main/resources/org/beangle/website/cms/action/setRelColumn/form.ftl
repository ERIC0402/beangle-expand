[#ftl]
[@b.head/]
[@b.toolbar title="设置关联栏目"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="关联栏目"]
		[@b.form action="!save" title="基本信息" theme="list"]
			[@b.field label="信息" ]${(cc.content.title)!}[/@]
			[@b.field label="关联栏目"]
				<input type="text" name="columnNames" readonly="true" id="columnNames" value="${(cc.column.name)!}">
				<input style="margin:auto" type="button" onClick="listRelationColumns(this);" value="选择关联栏目"/>
			[/@]
			[@b.select label="阅读权限" name="cc.readPurview.id" empty="请选择..." value=(cc.readPurview.id)!  style="width:200px;" items=purviews option="id,name"/]
			[#if cc.doesMainColumn]
				[@b.field label="是否主栏目" ]是[/@]
			[#else]
				[@b.radios label="是否主栏目" name="cc.doesMainColumn" value=cc.doesMainColumn items="1:是,0:否"/]
			[/#if]
			[@b.startend label="发布开始结束日期" name="cc.publishDate,cc.endDate" required="true,false" start=cc.publishDate end=cc.endDate format="yyyy-MM-dd HH:mm:ss"/]
			[@b.radios label="是否置顶" name="cc.doesTop" value=cc.doesTop items="1:置顶,0:不置顶"/]
			[@b.startend label="置顶开始结束日期" name="cc.topStartDate,cc.topEndDate" required="false,false" start=cc.topStartDate end=cc.topEndDate format="yyyy-MM-dd HH:mm:ss"/]
			[@b.checkboxes label="信息属性" name="propertyIds" items=properties?sort_by("id") value=cc.contentProperties valueName="name"/]
			[@b.formfoot]
				<input type="hidden" name="cc.id" value="${cc.id!}"/>
				<input type="hidden" id="columnIds" name="cc.column.id" value="${(cc.column.id)!}"/>
				<input type="hidden" name="cc.content.id" value="${(cc.content.id)!}"/>
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;
				[@b.submit value="action.submit"/]
			[/@]
		[/@]
	[/@]
[/@]
<script>
    function listRelationColumns(ele) {
	
		jQuery(ele).colorbox(
		{
			iframe : true,
			width : "800px",
			height : "600px",
			href : "${base}/cms/column-manager.action?idValues=columnIds&nameValues=columnNames&isSingle=1"
		});
	}
</script>
[@b.foot/]