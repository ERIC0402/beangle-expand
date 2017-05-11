[#ftl]
[@b.head/]
[#include "../comm.ftl"/]
[@b.grid  items=columns var="column" sortable="true"]
	[@b.gridbar]
		bar.addItem("${b.text("action.add")}",newObj("edit","nodeid=${column.id!}&siteId=${siteId?if_exists}"),"${base}/static/themes/default/icons/16x16/actions/new.png");
		bar.addItem("${b.text("action.edit")}",openCurrent("edit","siteId","${siteId}"));
		//bar.addItem("上移",openCurrent("up","",""),"${base}/static/themes/default/icons/16x16/actions/go-up.png");
   		//bar.addItem("下移",openCurrent("down","",""),"${base}/static/themes/default/icons/16x16/actions/go-down.png");
	    bar.addItem("${b.text("action.delete")}",remove("remove"));
		bar.addItem("仅显示启用",listCurrent("listandform","nodeid=${column.id!}&siteId=${siteId?if_exists}&all=0"));
		bar.addItem("显示全部",listCurrent("listandform","nodeid=${column.id!}&siteId=${siteId?if_exists}&all=1"));
		
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
	    
	    function listCurrent(methodName,params){
			return new NamedFunction(methodName,function(){
				var form=action.getForm();
				bg.form.submit(form,applyMethod(action.page.actionurl + "?" + params,"listandform"));
			});	
	    }
	    
	    function remove(methodName){
			return new NamedFunction(methodName,function(){
				try {
					var form = action.getForm();
					form.action=applyMethod(form.action,"remove");
					bg.form.submitId(form,"column.id",null,null,"只有未被使用的栏目才能删除，您是否确定继续？",true);
				}catch(e){
					bg.alert(e)
				}
			});	
	    }
	    var enabledRefresh = '${enabledRefresh!}';
	    refresh();
	    function refresh(){
		   	if(enabledRefresh!='0'){
		   		bg.form.submit('siteSearchForm');
		   	}
	   	}
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="20%" property="name" title="名称" /]
		[@b.col width="20%" property="orders" title="排序" /]
		[@b.col width="20%" property="type.name" title="栏目类型" /]
		[@b.col width="10%" property="enabled" title="是否启用" align="center"]
			[#if column.enabled]
				启用
			[#else]
				禁用
			[/#if]
		[/@]
		[@b.col width="20%" property="visible" title="前台菜单是否显示" align="center"]
			[#if column.visible]
				显示
			[#else]
				不显示
			[/#if]
		[/@]
		[@b.col width="10%" title="操作" align="center"]
			[@b.a href="!up?column.id=${column.id}"]
				<img class="toolbar-icon" title="上移" src="${b.theme.iconurl('actions/go-up.png')}"/>
			[/@]
			[@b.a href="!down?column.id=${column.id}"]
				<img class="toolbar-icon" title="下移" src="${b.theme.iconurl('actions/go-down.png')}"/>
			[/@]
		[/@]
	[/@]
[/@]
[@b.foot/]