[#ftl]
[@b.head/]
[@b.form action="!search?content.id=${content.id!}" name="relColumnSearchForm" id="relColumnSearchForm"]
[@b.grid  items=ccs var="cc" sortable="true"]
	[@b.gridbar]
		bar.addItem("${b.text("action.add")}",newObj("edit","content.id=${content.id!}"),"${base}/static/themes/default/icons/16x16/actions/new.png");
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("返回待处理",back("back","${base}/cms/process-content.action"),"${base}/static/themes/default/icons/16x16/actions/backward.png");
		function NamedFunction(name,func){
			this.name=name;
			this.func=func;
		}
		function applyMethod(action,method){
			var last1=action.lastIndexOf("!"), lastDot=action.lastIndexOf(".action"), shortAction=action, sufix="";
			if(-1 == last1) last1 = lastDot;
			if(-1!=last1){
				shortAction=action.substring(0,last1);
			}
			if(-1!=lastDot){
				sufix=action.substring(lastDot);
			}
			return shortAction+"!"+method+sufix;
		}
		
		function newObj(methodName,params){
			return new NamedFunction(methodName,function(){
				var form=action.getForm();
				bg.form.submit(form,applyMethod(action.page.actionurl + "?" + params,"edit"));
			});	
			
	    }
	    
	    function back(methodName,formAction){
			return new NamedFunction(methodName,function(){
				var form=action.getForm();
				form.target="main";
				bg.form.submit(form,applyMethod(formAction,""));
				form.target="";
			});	
	    }
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="20%" property="column.name" title="栏目"]
			<div title="【${(cc.column.site.name)!}】${(cc.column.name)!}">
				${(cc.column.name)!}
			</div>
		[/@]
		[@b.col width="20%" property="doesMainColumn" title="是否主栏目"]
			[#if cc.doesMainColumn]主栏目[#else]非主栏目[/#if]
		[/@]
		[@b.col width="20%" property="publishDate" filterable="false" title="发布日期"]
			${((cc.publishDate)?string("yyyy-MM-dd HH:mm:ss"))!}
		[/@]
		[@b.col width="20%" property="doesTop" filterable="false" title="是否置顶"]
			[#if cc.doesTop]置顶[#else]不置顶[/#if]
		[/@]
		[@b.col width="20%" property="readPurview" filterable="false" title="阅读权限"]
			${(cc.readPurview.name)!}
		[/@]
	[/@]
[/@]
[/@]
[@b.foot/]