[#ftl]
[@b.head/]
[@b.grid  items=tasks var="task"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		//bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("${b.text("action.scan")}",action.openNew("scan","${base}/workflow/task-node.action"));
		bar.addItem("${b.text("action.delete")}",action.remove());
		function NamedFunction(name,func){
			this.name=name;
			this.func=func;
		}
		
		function openNew(methodName,formAction){
			return new NamedFunction(methodName,function(){
				try {
					var form = action.getForm();
					form.target= bg.ui.list.getMainId("tasklist");
					form.action=formAction;
					bg.form.submitId(action.getForm(),"task.id",null,null,null,true);
					form.target="";
				}catch(e){
					bg.alert(e)
				}
			});	
	    }
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="20%" property="name" title="任务名称" /]
		[@b.col width="10%" property="code" title="任务代码" /]
		[@b.col width="10%" property="state" title="状态" ]
			[#if task.state?if_exists == 1]已完成[#else]进行中[/#if]
		[/@]
		[@b.col width="30%" title="维护人员"]
			[#list task.roles?sort_by("name") as role]
       	   		${role.name!}&nbsp;
       		[/#list]
		[/@]
		[@b.col width="30%" property="currentNode.name" title="当前节点"/]
	[/@]
[/@]
[@b.foot/]