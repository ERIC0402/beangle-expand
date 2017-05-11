[#ftl]
[@b.head/]
[#include "../comm.ftl"/]
[@b.grid  items=onlineMessTypes var="onlineMessType" sortable="true"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("留言列表",openNew("wscan","${base}/cms/online-mess.action"),"${base}/static/themes/default/icons/16x16/actions/go-next.png");
		function NamedFunction(name,func){
			this.name=name;
			this.func=func;
		}
		
		function openNew(methodName,formAction){
			return new NamedFunction(methodName,function(){
				try {
					var form = action.getForm();
					form.target="main";
					form.action=formAction;
					bg.form.submitId(action.getForm(),"onlineMessType.id",null,null,null,true);
					form.target="";
				}catch(e){
					bg.alert(e)
				}
			});	
	    }
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="25%" property="typeName" title="类型名称" /]
		[@b.col width="25%" property="typeCode" title="类型代码" /]
		[@b.col width="25%" property="site.name" title="所属站点" /]
		[@b.col width="25%" title="状态" property="enabled" align="center"]
			[#if onlineMessType.enabled]启用[#else]禁用[/#if]
		[/@]
	[/@]
[/@]
[@b.foot/]