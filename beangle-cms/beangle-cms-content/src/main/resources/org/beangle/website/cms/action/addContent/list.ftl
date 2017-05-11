[#ftl]
[@b.head/]
[#include "../comm.ftl"/]
[@b.form action="!search?column.id=${columnId!}" name="contentSearchForm" id="contentSearchForm"]
[@b.grid  items=contents var="content" sortable="true" filterable="true"]
	[@b.gridbar]
		[#if enabledAdd??&&enabledAdd==1]
			bar.addItem("起草",newObj("edit"),"${base}/static/themes/default/icons/16x16/actions/new.png");
			bar.addItem("${b.text("action.edit")}",openCurrent("edit","",""));
		[/#if]

		function NamedFunction(name,func){
			this.name=name;
			this.func=func;
		}
		function applyMethod(action,method){
			var last1=action.lastIndexOf("!"), lastDot=action.lastIndexOf("."), shortAction=action, sufix="";
			if(-1 == last1) last1 = lastDot;
			if(-1!=last1){
				shortAction=action.substring(0,last1);
			}
			if(-1!=lastDot){
				sufix=action.substring(lastDot);
			}
			return shortAction+"!"+method+sufix;
		}
		
		function newObj(methodName){
			return new NamedFunction(methodName,function(){
				jQuery().colorbox(
				{
					iframe : true,
					width : "100%",
					height : "100%",
					href : "${base}/cms/add-content!edit.action?column.id=${columnId!}&isAdd=1"
				});
			});	
			
	    }
	    
	    function openCurrent(methodName,paramName,paramValue){
			return new NamedFunction(methodName,function(){
				var selectId = bg.input.getCheckBoxValues("content.id");
				var	isMulti=false;
				if(""==selectId){
					alert(isMulti?"请选择一个或多个进行操作":"请选择一个进行操作");
					return;
				}
				if(!isMulti && (selectId.indexOf(",")>0)){
					alert("请仅选择一个");
					return;
				}
				
				var state = $("#state"+selectId).val();
				if(state != 7 && state != 11){
					alert("只有起草状态和退回状态的信息才能修改！");
					return;
				}
				jQuery().colorbox(
				{
					iframe : true,
					width : "100%",
					height : "100%",
					href : "${base}/cms/add-content!edit.action?column.id=${columnId!}&content.id="+selectId
				});
			});	
	    }
	    
	    function close2(){
			jQuery.colorbox.close();
			$("[name='content.id']:checked").each(function(){this.checked=false});
			bg.form.submit('contentSearchForm');
	    }
	    function refresh(){
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
		[@b.col width="40%" property="content.title" title="信息标题"]
			[#if content.doesTop]【置顶】[#else]【不置顶】[/#if]
			${(content.content.title)?if_exists}
			<input type="hidden" id="state${(content.id)!}" value="${(content.contentState.id)!}">
		[/@]
		[@b.col width="20%" property="column.name" title="栏目" /]
		[@b.col width="20%" property="content.draftDate" filterable="false" title="起草时间"]
			${((content.content.draftDate)?string("yyyy-MM-dd HH:mm:ss"))!}
		[/@]
		[@b.col width="20%" property="contentState.name" title="信息状态"/]
	[/@]
[/@]
[/@]
[@b.foot/]