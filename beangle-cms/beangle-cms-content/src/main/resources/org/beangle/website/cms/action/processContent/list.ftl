[#ftl]
[@b.head/]
[@b.form action="!search" name="contentSearchForm" id="contentSearchForm"]
[@b.grid  items=ccs var="cc" sortable="true" filterable="true"]
	[@b.gridbar]
		function NamedFunction(name,func){
			this.name=name;
			this.func=func;
		}
		function openCurrent(methodName,params){
			jQuery().colorbox(
			{
				iframe : true,
				width : "100%",
				height : "100%",
				href : "${base}/cms/process-content!"+methodName+".action?"+params
			});
			return false;
	    }
		function close2(){
			jQuery.colorbox.close();
			$("[name='content.id']:checked").each(function(){this.checked=false});
			bg.form.submit('contentSearchForm');
	    }
		[@b.gridfilter property="contentState.name"]
			<select name="cc.contentState.id" style="width:100%;" onchange="bg.form.submit(this.form)">
				<option value="" [#if (Parameters['cc.contentState.id']!"")=""]selected="selected"[/#if]>...</option>
				[#list states as item]
					<option value="${item.id}" [#if (Parameters['cc.contentState.id']!"")="${item.id}"]selected="selected"[/#if]>${item.name!}</option>
				[/#list]
			</select>
		[/@]
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="30%" property="content.title" title="信息标题"]
			[#if cc.doesTop]【置顶】[#else]【不置顶】[/#if]
			${(cc.content.title)?if_exists}
		[/@]
		[@b.col width="20%" property="column.name" title="栏目"]
			<div title="【${(cc.column.site.name)!}】${(cc.column.name)!}">
				${(cc.column.name)!}
			</div>
		[/@]
		[@b.col width="10%" property="content.drafter.fullname" title="起草人" /]
		[@b.col width="15%" property="content.draftDate" filterable="false" title="起草时间"]
			${((cc.content.draftDate)?string("yyyy-MM-dd HH:mm:ss"))!}
		[/@]
		[@b.col width="10%" property="contentState.name" title="状态"/]
		[@b.col width="15%" title="操作"]
		    [#if cc.task??]
		    	[@b.a href="#" onclick="return openCurrent('${(cc.task.currentNode.entry)!}','cc.id=${cc.id}')"]
		    		[${(cc.task.currentNode.name)!}]
		    	[/@]
		    [#else]
		    	[@b.a href="!handed?cc.id=${cc.id}" onclick="return confirm('是否提交审核？')"]
		    		提交审核
		    	[/@]
		    [/#if]
		    [@b.a href="#" onclick="return openCurrent('edit','cc.id=${cc.id!}');"]
		    	编辑
		    [/@]
		    [@b.a href="set-rel-column!search?content.id=${(cc.content.id)!}"]
		    	关联栏目
		    [/@]
		[/@]
	[/@]
[/@]
[/@]
[@b.foot/]