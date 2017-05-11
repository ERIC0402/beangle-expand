[#ftl]
[@b.head/]
[@b.form action="!search" name="xxmbSearchForm" id="xxmbSearchForm"]
[@b.grid  items=xxmbs var="xxmb" sortable="true" filterable="false"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	[#--
		[@b.gridfilter property="mblx.name"]
			<select  name="xxmb.mblx.id" style="width:100%;" onchange="bg.form.submit(this.form)">
				<option value="" [#if (Parameters['xxmb.mblx.id']!"")=""]selected="selected"[/#if]>...</option>
				[#list template as item]
					<option value="${item.id}" [#if (Parameters['xxmb.mblx.id']!"")="${item.id}"]selected="selected"[/#if]>${item.name!}</option>
				[/#list]
			</select>
		[/@]
		[@b.col width="35%" property="mblx.name" title="模板类型"/]
		[@b.col width="20%" property="px" title="排序" /]
	[@b.gridfilter property="enabled"]
		<select  name="xxmb.enabled" style="width:100%;" onchange="bg.form.submit(this.form)">
			<option value="" [#if (Parameters['xxmb.enabled']!"")=""]selected="selected"[/#if]>...</option>
			<option value="true" [#if (Parameters['xxmb.enabled']!"")="true"]selected="selected"[/#if]>有效</option>
			<option value="false" [#if (Parameters['xxmb.enabled']!"")="false"]selected="selected"[/#if]>无效</option>
		</select>
	[/@]
	
	--]
	[@b.row]
	[@b.boxcol/]
		[@b.col width="30%" property="mbmc" title="模板名称" /]
		[@b.col width="60%" property="xxnr" title="选项内容" sortable="false"/]
		[@b.col width="10%" property="enabled" title="是否有效" align="center"]
			[@c.sfyx enabled=xxmb.enabled/]
		[/@]
	[/@]
[/@]
[/@]
[@b.foot/]