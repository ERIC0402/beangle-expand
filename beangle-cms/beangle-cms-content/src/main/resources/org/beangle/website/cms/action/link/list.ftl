[#ftl]
[@b.head/]
[#include "../comm.ftl"/]
[@b.form action="!search?linksType.id=${linksTypeId!}" name="linkSearchForm" id="linkSearchForm"]
[@b.grid items=links var="link" sortable="true" filterable="true"]
	[@b.gridbar]
		bar.addItem("${b.text("action.add")}",newObj("edit","linksTypeId=${linksTypeId!}"),"${base}/static/themes/default/icons/16x16/actions/new.png");
		bar.addItem("${b.text("action.edit")}",openCurrent("edit","linksTypeId","${linksTypeId!}"));
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("${b.text("action.back")}",action.goTo("index","${base}/cms/links-type.action"),"${base}/static/themes/default/icons/16x16/actions/backward.png");
		
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
		
		function newObj(methodName,params){
			return new NamedFunction(methodName,function(){
				var form=action.getForm();
				bg.form.submit(form,applyMethod(action.page.actionurl + "?" + params,"edit"));
			});	
			
	    }
	    
		function openCurrent(methodName,paramName,paramValue){
			return new NamedFunction(methodName,function(){
				try {
					var form = action.getForm();
					bg.form.addInput(form,paramName,paramValue,"hidden");
					action.submitIdAction(methodName, null, null,true);
				}catch(e){
					bg.alert(e)
				}
			});	
	    }
	    var linkAction = action;
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="20%" property="name" title="名称" /]
		[@b.col width="10%" property="orders" title="排序" /]
		[@b.col width="20%" property="linkType.typeName" title="所属类型" filterable="false"/]
		[@b.col width="30%" property="url" title="URL" /]
		[@b.col width="10%" title="状态" property="enabled" align="center"]
			[#if link.enabled?? && link.enabled]启用[#else]禁用[/#if]
		[/@]
		[@b.col width="10%" title="操作" align="center"]
			[#if link_index!=0]
				[@b.a href="?#" onclick="linkAction.method('up', null, 'link.id=${link.id}&flag=1').func();return false;"]
					<img class="toolbar-icon" title="上移" src="${b.theme.iconurl('actions/go-up.png')}"/>
				[/@]
			[#else]
				&nbsp;&nbsp;
			[/#if]
			[#if links?last.id!=link.id]
				[@b.a href="?#" onclick="linkAction.method('up', null, 'link.id=${link.id}&flag=0').func();return false;"]
					<img class="toolbar-icon" title="下移" src="${b.theme.iconurl('actions/go-down.png')}"/>
				[/@]
			[#else]
				&nbsp;&nbsp;
			[/#if]
		[/@]
	[/@]
	[/@]
[/@]
[@b.foot/]