[#ftl]
[@b.head/]
[@b.grid  items=taskNodes var="taskNode"]
	[@b.gridbar]
		bar.addItem("${b.text("action.constraint")}",openNew("constraint","${base}/workflow/task-node-constraint.action"));
   		bar.addItem("${b.text("action.decision")}",openNew("decision","${base}/workflow/task-node-decision.action"));
   		bar.addItem("${b.text("action.fadd")}",openCurrent("edit","ad","1"),"${base}/static/themes/default/icons/16x16/actions/new.png");
   		bar.addItem("${b.text("action.badd")}",openCurrent("edit","ad","0"),"${base}/static/themes/default/icons/16x16/actions/new.png");
   		bar.addItem("${b.text("action.upmove")}",openCurrent("up","",""),"${base}/static/themes/default/icons/16x16/actions/go-up.png");
   		bar.addItem("${b.text("action.downmove")}",openCurrent("down","",""),"${base}/static/themes/default/icons/16x16/actions/go-down.png");
	    [#if (size == 0)]
	   		bar.addItem("${b.text("action.add")}",newObj("edit","order=1"),"${base}/static/themes/default/icons/16x16/actions/new.png");
	    [/#if]
		bar.addItem("${b.text("action.edit")}",openCurrent("edit","",""));
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("${b.text("action.back")}",back("back","${base}/workflow/task.action"),"${base}/static/themes/default/icons/16x16/actions/backward.png");
		
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
	    
	    function openNew(methodName,formAction){
			return new NamedFunction(methodName,function(){
				try {
					var form = action.getForm();
					form.target="main";
					form.action=formAction;
					bg.form.submitId(action.getForm(),"taskNode.id",null,null,null,true);
					form.target="";
				}catch(e){
					bg.alert(e)
				}
			});	
	    }
	    
	    function back(methodName,formAction){
			return new NamedFunction(methodName,function(){
				var form=action.getForm();
				form.target=bg.ui.list.getMainId("taskNodelist");
				bg.form.submit(form,applyMethod(formAction,""));
				form.target="";
			});	
	    }
		
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="20%" property="name" title="节点名称" /]
		[@b.col width="10%" property="order" title="节点代码" /]
		[@b.col width="30%" property="task.name" title="任务" /]
		[@b.col width="20%" property="workflow.name" title="流程" /]
		[@b.col width="10%" property="type" title="类型" ]
			[#if taskNode.type?if_exists == 0]
				普通类型
	    	[#elseif taskNode.type?if_exists == 1]
	    		跳转类型
	    	[#elseif taskNode.type?if_exists == 2]
	    		限制类型
	    	[#else]
	    		跳转限制类型
	    	[/#if]
		[/@]
		[@b.col width="10%" property="state" title="状态"]
			[#if taskNode.state?if_exists == 0]未开始
	    	[#elseif taskNode.state?if_exists == 2]已完成
	    	[#else]进行中
	    	[/#if]
		[/@]
	[/@]
[/@]
[@b.foot/]