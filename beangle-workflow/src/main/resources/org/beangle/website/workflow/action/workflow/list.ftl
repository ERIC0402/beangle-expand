[#ftl]
[@b.head/]
[@b.grid  items=workflows var="workflow"]
	[@b.gridbar]
		bar.addItem("快速添加流程",action.add("addWorkflowAtOne"));
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.wscan")}",openNew("wscan","${base}/workflow/step-node.action"));
		bar.addItem("${b.text("action.delete")}",action.remove());
		
		function NamedFunction(name,func){
			this.name=name;
			this.func=func;
		}
		
		function openNew(methodName,formAction){
			return new NamedFunction(methodName,function(){
				try {
					var form = action.getForm();
					form.target= bg.ui.list.getMainId("workflowlist");
					form.action=formAction;
					bg.form.submitId(action.getForm(),"workflow.id",null,null,null,true);
					form.target="";
				}catch(e){
					bg.alert(e);
				}
			});
	    }
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="15%" property="code" title="流程代码" /]
		[@b.col width="20%" property="name" title="流程名称" /]
		[@b.col width="15%" property="type" title="流程类型" ]
			[#if workflow.type?if_exists == 1]不固定[#else]固定[/#if]
		[/@]
		[@b.col width="30%" title="维护人员"]
			[#list workflow.roles?sort_by("name") as role]
       	   		${role.name!}&nbsp;
       		[/#list]
		[/@]
		[@b.col width="20%" property="description" title="描述"/]
	[/@]
[/@]
[@b.foot/]