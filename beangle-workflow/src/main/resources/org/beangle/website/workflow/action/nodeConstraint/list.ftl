[#ftl]
[@b.head/]
[@b.grid  items=nodeConstraints var="nodeConstraint"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("${b.text("action.back")}",back("back","${base}/workflow/step-node.action?method=index&workflow.id=${wid!}"),"${base}/static/themes/default/icons/16x16/actions/backward.png");
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
		[@b.col width="20%" property="code" title="代码" ]
			[#if nodeConstraint.code?if_exists == "1"]
				限制通过人数
	    	[#elseif nodeConstraint.code?if_exists == "2"]
	    		限制完成时间
	    	[#else]
	    		限制人数和时间
	    	[/#if]
		[/@]
		[@b.col width="25%" property="stepNode.name" title="所属节点" /]
		[@b.col width="25%" property="passNum" title="限制人数" /]
		[@b.col width="30%" property="time" title="限制时间（小时）"/]
	[/@]
[/@]
[@b.foot/]