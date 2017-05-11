[#ftl]
[@b.head/]
[#include "../comm.ftl"/]
[@b.form action="!search?onlineMessType.id=${onlineMessTypeId!}" name="onlineMessSearchForm" id="onlineMessSearchForm"]
[@b.grid items=onlineMesss var="onlineMess" sortable="true" filterable="true"]
	[@b.gridbar]
		//bar.addItem("处理",openCurrent("edit","onlineMessTypeId","${onlineMessTypeId!}"));
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("${b.text("action.back")}",back("back","${base}/cms/online-mess-type.action"),"${base}/static/themes/default/icons/16x16/actions/backward.png");
		
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
		[@b.col width="10%" property="title" title="主题" ]
			[#if (onlineMess.title?length > 10)]
				${onlineMess.title[0..10]}...
			[#else]
				${onlineMess.title}
			[/#if]
		[/@]
		[@b.col width="10%" property="person" title="留言人" /]
		[@b.col width="10%" property="linkEmail" title="联系邮箱" /]
		[@b.col width="10%" property="linkPhone" title="联系电话" /]
		[@b.col width="10%" property="time" title="留言时间" /]
		[@b.col width="10%" property="onlineMessType.typeName" title="留言类型" filterable="false"/]
		[@b.col width="10%" title="是否热点" property="rd" align="center"]
			[#if onlineMess.rd?? && onlineMess.rd]是[#else]否[/#if]
		[/@]
		[@b.col width="10%" title="是否回复" property="isback" align="center"]
			[#if onlineMess.back]已回复[#else]未回复[/#if]
		[/@]
		[@b.col width="10%" title="是否显示" property="enabled" align="center"]
			[#if onlineMess.visible]显示[#else]不显示[/#if]
		[/@]
		[@b.col width="10%" title="操作" align="center"]
			[#if !onlineMess.back]
				[@b.a href="!edit?onlineMess.id=${onlineMess.id}&flag=1"]
					处理
				[/@]
			[/#if]
		[/@]
	[/@]
	[/@]
[/@]
[@b.foot/]