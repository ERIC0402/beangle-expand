[#ftl]
[@b.head/]
[@b.grid  items=nodeDecisions var="nodeDecision"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("${b.text("action.back")}",back("back","${base}/workflow/task-node.action?method=index&task.id=${wid!}"),"${base}/static/themes/default/icons/16x16/actions/backward.png");
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
			alert(sufix);
			return shortAction+"!"+method+sufix;
		}
	    
	    function back(methodName,formAction){
			return new NamedFunction(methodName,function(){
				var form=action.getForm();
				form.target="main";
				bg.form.submit(form,formAction);
				form.target="";
			});	
	    }
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="30%" property="value" title="标识" /]
		[@b.col width="40%" property="taskNode.name" title="所属节点" /]
		[@b.col width="30%" property="gotoOrder" title="跳转节点"/]
	[/@]
[/@]
[@b.foot/]