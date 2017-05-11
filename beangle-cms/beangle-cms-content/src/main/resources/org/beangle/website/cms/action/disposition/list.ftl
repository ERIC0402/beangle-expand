[#ftl]
[@b.head/]
[#include "../comm.ftl"/]
[@b.form action="!search" name="dispositionSearchForm" id="dispositionSearchForm"]
[@b.grid items=dispositions var="disposition" sortable="true" filterable="true"]
	[@b.gridbar]
		//bar.addItem("处理",openCurrent("edit","dispositionTypeId","${dispositionTypeId!}"));
		//bar.addItem("${b.text("action.delete")}",action.remove());
		//bar.addItem("${b.text("action.back")}",back("back","${base}/cms/online-mess-type.action"),"${base}/static/themes/default/icons/16x16/actions/backward.png");
		
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
		[@b.col width="50%" property="onlineMess.title" title="主题" /]
		[@b.col width="20%" property="onlineMess.person" title="留言人" /]
		[@b.col width="10%" property="onlineMess.time" title="留言时间" ]
			${(disposition.onlineMess.time?string("yyyy-MM-dd"))!}
		[/@]
		[@b.col width="10%" property="onlineMess.onlineMessType.typeName" title="留言类型" filterable="false"/]
		[@b.col width="10%" title="操作" align="center"]
			[@b.a href="!edit?disposition.id=${disposition.id}"]
				处理
			[/@]
		[/@]
	[/@]
	[/@]
[/@]
[@b.foot/]