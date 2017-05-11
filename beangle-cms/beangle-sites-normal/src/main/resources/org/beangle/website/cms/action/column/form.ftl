[#ftl]
[@b.head/]
[@b.toolbar title="栏目管理"]bar.addBack();[/@]
		[@b.form action="!save" title="栏目信息" theme="list"]
			[@b.textfield id="fileName" name="column.name" label="栏目名称" value="${column.name!}" required="true" maxlength="100" comment="长度不超过100个字符"/]
			[@b.select label="栏目类型" required="true" id="column.type.id" name="column.type.id" empty="请选择..." value=(column.type.id)!  style="width:200px;" items=types option="id,name" onchange="typeChange(this.value)"/]
			[@b.field label="上级栏目" required="true"]
		  		<select style="width:200px;" id="column.columns.id" name="column.columns.id">
                	<option value="">请选择</option>
                	[#macro getColumnOptoin pc perfix="　" index=1]
                		[#if columns?size lte 1][#return/][/#if]
                		[#list index..columns?size-1 as c_index]
                			[#assign c=columns[c_index]]
                			[#if (c.columns.id)?? && c.columns.id == pc.id]
                				[#assign hasSub = hasSubColumn(c, c_index)]
                				[#assign hasNextLevel = hasNextLevelColumn(c, c_index)]
                				[#assign pperfix = perfix]
                				[#assign subPerfix = perfix]
                				[#if hasNextLevel]
                					[#assign pperfix=pperfix+'├']
                					[#assign subPerfix=subPerfix+'│']
                				[#else]
                					[#assign pperfix=pperfix+'└']
                					[#assign subPerfix=subPerfix+'　']
                				[/#if]
                				<option [#if column.id?? && c.id=column.id]disabled=disabled[/#if] value="${c.id}" [#if column.columns??][#if column.columns.id==c.id]selected[/#if][#else][#if nodeid??][#if nodeid==c.id]selected[/#if][/#if][/#if]>${pperfix}${c.name}</option>
                				[#if hasSub]
                					[@getColumnOptoin pc=c perfix=subPerfix index=c_index/]
                				[/#if]
                			[/#if]
                		[/#list]
                	[/#macro]
                	
                	[#function hasSubColumn c index]
	                	[#list index..columns?size-1 as c_index]
                			[#assign cc=columns[c_index]]
		                	[#if (cc.columns.id)?? && cc.columns.id == c.id ]
		                	[#return true]
	                		[/#if]
	                	[/#list]
	                	[#return false]
                	[/#function]
                	
                	[#function hasNextLevelColumn c index]
	                	[#list index..columns?size-1 as c_index]
                			[#assign cc=columns[c_index]]
		                	[#if (cc.columns.id)?? && (c.id != cc.id) && (cc.columns.id == c.columns.id) ]
		                	[#return true]
		                	[/#if]
	                	[/#list]
	                	[#return false]
                	[/#function]
                	[#assign c=columns[0]]
                	<option  [#if column.id?? && c.id=column.id]disabled=disabled[/#if] value="${c.id}" [#if column.columns??][#if column.columns.id==c.id]selected[/#if][#else][#if nodeid??][#if nodeid==c.id]selected[/#if][/#if][/#if]>※${c.name!}</option>
                	[@getColumnOptoin pc=columns[0]/]
                	[#--
                    [#list columns?sort_by("orders") as item]
	                    <option [#if column.id?? && item.id=column.id]disabled=disabled[/#if] value="${item.id}" [#if column.columns??][#if column.columns.id==item.id]selected[/#if][#else][#if nodeid??][#if nodeid==item.id]selected[/#if][/#if][/#if]>
	                    	[#if item_index=0]
	                    		※
	                    	[#else]
	                    	&nbsp;&nbsp;&nbsp;
	                    		[#if item.orders?length/2>2]
		                    		[#list 3..item.orders?length/2 as item2]
		                    			│
		                    		[/#list]
	                    		[/#if]
	                    		[#assign sameLevel=0]
	                    		[#if columns?last.id!=item.id]
		                    		[#list (item_index+1)..(columns?size-1) as item3]
		                    			[#if (columns[item3].columns)?? && (columns[item3].columns.id=item.columns.id)]
		                    				[#assign sameLevel=1]
		                    				[#break]
		                    			[/#if]
		                    		[/#list]
		                    		[#if sameLevel=1]
		                    			├
		                    		[#else]
		                    			└
		                    		[/#if]
		                    	[#else]
		                    		└
	                    		[/#if]
	                    	[/#if]
	                    	${item.name}
	                    </option>
                    [/#list]
                    --]
                </select>
			[/@]
			[@b.select label="访问权限" name="column.access.id" empty="请选择..." value=(column.access.id)!  style="width:200px;" items=accesss option="id,name"/]
			[@b.radios label="是否继承流程" name="column.doesImpl" value=column.doesImpl items="1:是,0:否" onclick="changeWf(this.value)" class="sfjclc" /]
			[@b.select label="关联流程" name="column.workflow.id" empty="请选择..." value=(column.workflow.id)!  style="width:200px;" items=workflows option="id,name" 
				class="gllc" /]
			[@b.select label="内容来源" name="column.contentSource.id" empty="请选择..." value=(column.contentSource.id)!  style="width:200px;" items=contents option="id,name" class="nrly" /]
			[@b.field label="关联栏目" class="gllm" ]
				<textarea cols="50"  name="columnNames"  rows="5" readonly="true" id="columnNames" style="float: left;">[#if column.relationColumns?exists && column.relationColumns?size>0][#list column.relationColumns as rcs]${rcs.name!},[/#list][/#if]</textarea>
				<input style="float: left;"  type="button" onClick="listRelationColumns(this);" value="选择关联栏目"/>
			[/@]
			[#--
			[@b.select label="栏目显示模板" name="column.contentTemplate.id" empty="请选择..." value=(column.contentTemplate.id)!  style="width:200px;" items=templates option="id,name"
			 class="lmxsmb" /]
			[@b.select label="信息显示模板" name="column.columnTemplate.id" empty="请选择..." value=(column.columnTemplate.id)!  style="width:200px;" items=templates option="id,name"
			 class="xxxsmb" /]
			
			[@b.select label="扩展属性来源" name="column.extProperty.id" empty="请选择..." value=(column.extProperty.id)!  style="width:200px;" items=extpropertys option="id,name"
			 class="kzsxly" /]
			[@b.field label="扩展属性" class="kzsx" ]
				<textarea cols="50"  name="propertyNames"  rows="5" readonly="true" id="propertyNames" style="float: left;">[#if column.relationColumns?exists && column.relationColumns?size>0][#list column.extpropertys as res]${res.id},[/#list][/#if]</textarea>
				<input style="margin:2px;"  type="button" onClick="listtprs(this);" value="选择关联栏目" style="float: left;"/>
			[/@]
			--]
			[@b.textfield id="column.url" name="column.url" label="URL" value="${column.name!}" maxlength="300" comment="长度不超过300个字符"
			 class="url" /]
			 [@b.select label="访问入口" name="column.entry.id" value=(column.entry.id)!0 items=entrys empty="请选择..."/]
			[#--
			[@b.field label="访问入口" class="fwrk" ]
		  		<select style="width:200px;" class="entrySelect" name="entrySelect">
		  			<option value="">请选择...</option>
		  			<option [#if column.entry??&&column.entry='!index']selected[/#if] value="!index">首页</option>
		  			<option [#if column.entry??&&column.entry='!list']selected[/#if] value="!list">信息列表</option>
		  			<option [#if column.entry??&&column.entry='!list?img=1']selected[/#if] value="!list?img=1">图片列表</option>
		  			<option [#if column.entry??&&column.entry='!list?single=1']selected[/#if] value="!list?single=1">单页信息</option>
		  			<option [#if column.entry??&&column.entry='!hyzx']selected[/#if] value="!hyzx">会员中心</option>
		  			<option [#if column.entry??&&column.entry='!zpxxList']selected[/#if] value="!zpxxList">招聘信息</option>
		  			<option [#if column.entry??&&column.entry='!rcssList']selected[/#if] value="!rcssList">人才库</option>
		  			<option [#if column.entry??&&column.entry='!studentInfo']selected[/#if] value="!studentInfo">学生简历</option>
		  			<option [#if column.entry??&&column.entry='!studentDa']selected[/#if] value="!studentDa">学生档案</option>
		  			<option [#if column.entry??&&column.entry='!apply']selected[/#if] value="!apply">投递情况</option>
		  			<option [#if column.entry??&&column.entry='!wyzx']selected[/#if] value="!wyzx">我要咨询</option>
		  			<option [#if column.entry??&&column.entry='!wdzx']selected[/#if] value="!wdzx">我的咨询</option>
		  			<option [#if column.entry??&&column.entry='!rdzx']selected[/#if] value="!rdzx">热点咨询</option>
		  			<option [#if column.entry??&&column.entry='!wzdc']selected[/#if] value="!wzdc">网站调查</option>
                	<option value="自定义">自定义</option>
                </select>
                <div>
                	<input style="display: none;width:300px;" name="column.entry" value="${column.entry!}" maxlength="200"/>
                </div>
			[/@]
			 --]
			[@b.radios label="新闻类型" name="column.newsType.id" value=(column.newsType.id)!  style="width:200px;" items=newsTypes option="id,name"
			 class="xwlx" /]
			
			[@b.radios label="前台菜单是否显示" name="column.visible" value=column.visible items="1:显示,0:不显示"/]
			[@b.radios label="是否启用" name="column.enabled" value=column.enabled items="1:启用,0:禁用"/]
			[@b.formfoot]
				<input type="hidden" id="columnIds" name="columnIds" value="[#list column.relationColumns as rcs]${rcs.id},[/#list]"/>
				<input type="hidden" id="propertyIds" name="propertyIds" value="[#list column.extpropertys as res]${res.id},[/#list]"/>
				<input type="hidden" name="column.site.id" value="[#if column.site??]${(column.site.id)!}[#elseif siteId??]${siteId!}[/#if]" />
				<input type="hidden" name="column.id" value="${column.id!}" />
				<input type="hidden" name="oldParentId" value="${(column.columns.id)!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;
				[@b.submit value="action.submit"  onsubmit="checkSelect()"/]
			[/@]
[/@]
<script>
	function checkSelect(form){
		if(document.getElementById("column.columns.id").value==""){
			alert("必须选择上级栏目！");
			return false;
		}
	}
	function typeChange(selectObj){
		xxfb = ".sfjclc,.gllc,.nrly,.kzsxly,.kzsx,.xwlx";
        if(selectObj==16){
        	//信息发布
            $("tr").filter(xxfb).show();
            $("tr").filter(".url").hide();
            changeKzsx();
        	changeWf();
        }else if(selectObj==161){
        	$("tr").filter(xxfb).hide();
            $("tr").filter(".lmxsmb,.fwrk").show();
        }else if(selectObj==18){
        	$("tr").filter(xxfb).hide();
            $("tr").filter(".fwrk").show();
        }else if(selectObj==141){
        	$("tr").filter(xxfb).hide();
            $("tr").filter(".fwrk").show();
            document.getElementById("column.columns.id").value ="";
        }else if(selectObj==142){
        	$("tr").filter(xxfb).hide();
            $("tr").filter(".fwrk").show();
        }
    }
	typeChange($name("column.type.id").val());
	
    function changeWf(){
    	var value = $("[name='column.doesImpl']:checked").val();
    	if(value==1){
    		$("tr").filter(".gllc").hide();
    	}else{
    		$("tr").filter(".gllc").show();
    	}
    }
	
    function changeKzsx(){
    	var value = $("[name='column.extProperty.id']").val();
    	if(value==26){
    		$("tr").filter(".kzsx").hide();
    	}else{
    		$("tr").filter(".kzsx").show();
    	}
    }
    
     $(function (){
        var es = $(".entrySelect");
        var entry = "${column.entry!}";
        es.find("option").each(function (){
            if(this.value == entry){
                this.selected=true;
            }
        })
        es.change(function (){
            var entryinput = $("input[name='column.entry']");
            if(this.value == "自定义"){
                entryinput.show();
            }else{
                entryinput.hide();
                entryinput.val(this.value);
            }
        });
        if(entry != "" && es.val() == ""){
            es.val("自定义");
            es.change();
        }
        $name("column.contentSource.id").change(function (){
        	if(this.value == "21"){
        		$(".gllm").show();
        	}else{
        		$(".gllm").hide();
        	}
        }).change();
    })
    
    function listRelationColumns(ele) {
		jQuery(ele).colorbox(
		{
			iframe : true,
			width : "800px",
			height : "600px",
			href : "${base}/cms/column-manager.action?idValues=columnIds&nameValues=columnNames"
		});
	}
</script>
[@b.foot/]