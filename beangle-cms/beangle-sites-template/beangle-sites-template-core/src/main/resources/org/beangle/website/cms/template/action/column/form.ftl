[#ftl]
[@b.head/]
[@b.toolbar title="栏目管理"]bar.addBack();[/@]
[@b.tabs]
[@b.tab label="栏目管理"]
[@b.form action="!save" title="基本信息" theme="list"]
[@b.textfield id="fileName" name="column.name" label="栏目名称" value="${column.name!}" required="true" maxlength="100" comment="长度不超过100个字符"/]
[@b.select label="栏目类型" required="true" id="column.type.id" name="column.type.id" empty="请选择..." value=(column.type.id)!  style="width:200px;" items=types option="id,name" onchange="typeChange()"/]
[@b.field label="上级栏目" required="true"]
<select style="width:200px;" id="column.columns.id" name="column.columns.id">
	<option value="">请选择</option>
	[#macro getColumnOptoin pc perfix="　" index=1]
	[#if columns?size lte 1][#return/][/#if]
	[#list index..columns?size-1 as c_index]
	[#assign c=columns[c_index]]
	[#if c.type.id == 142 || c.type.id == 141]
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
	[/#if] <option [#if column.id?? && c.id=column.id]disabled=disabled[/#if] value="${c.id}" [#if column.columns??][#if column.columns.id==c.id]selected[/#if][#else][#if nodeid??][#if nodeid==c.id]selected[/#if][/#if][/#if]>${pperfix}${c.name}</option>
	[#if hasSub]
	[@getColumnOptoin pc=c perfix=subPerfix index=c_index/]
	[/#if]
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
	[#assign c=columns[0]] <option  [#if column.id?? && c.id=column.id]disabled=disabled[/#if] value="${c.id}" [#if column.columns??][#if column.columns.id==c.id]selected[/#if][#else][#if nodeid??][#if nodeid==c.id]selected[/#if][/#if][/#if]>※${c.name!}</option>
	[@getColumnOptoin pc=columns[0]/]
</select>
[/@]
[@b.select label="访问权限" name="column.access.id" empty="请选择..." value=(column.access.id)!  style="width:200px;" items=accesss option="id,name"/]
<div id="sfjclc" style="display:none;">
	[@b.radios label="是否继承流程" name="column.doesImpl" value=column.doesImpl items="1:是,0:否" onclick="changeWf(this.value)"/]
</div>
<div id="gllc" style="display:none;">
	[@b.select label="关联流程" name="column.workflow.id" empty="请选择..." value=(column.workflow.id)!  style="width:200px;" items=workflows option="id,name"/]
</div>
<div id="nrly" style="display:none;">
	[@b.select label="内容来源" name="column.contentSource.id" empty="请选择..." value=(column.contentSource.id)!  style="width:200px;" items=contents option="id,name"/]
</div>
<div id="gllm" style="display:none;">
	[@b.field label="关联栏目"] 	<textarea cols="50"  name="columnNames"  rows="1" readonly="true" id="columnNames">[#if column.relationColumns?exists && column.relationColumns?size>0][#list column.relationColumns as rcs]${rcs.name!},[/#list][/#if]</textarea>
	<input style="margin:auto"  type="button" onClick="listRelationColumns(this);" value="选择关联栏目"/>
	[/@]
</div>
<li id="lmxsmb" style="display:none;">
	<label class="title">栏目显示模板：</label>
	<select name="column.columnTemplate.id">
		<option value="">请选择...</option>
		[#list templates! as v] <option value="${v.id}" typeid="${v.type.id}" [#if v.id == (column.columnTemplate.id)!0]selected[/#if]>${v.name}</option>
		[/#list]
	</select>
</li>
<li id="xxxsmb" style="display:none;">
	<label class="title">信息显示模板：</label>
	<select name="column.contentTemplate.id">
		<option value="">请选择...</option>
		[#list templates! as v] [#if v.type.id = 263]<option value="${v.id}" typeid="${v.type.id}" [#if v.id == (column.contentTemplate.id)!0]selected[/#if]>${v.name}</option>[/#if]
		[/#list]
	</select>
</li>
<div id="kzsxly" style="display:none;">
	[@b.select label="扩展属性来源" name="column.extProperty.id" empty="请选择..." value=(column.extProperty.id)!  style="width:200px;" items=extpropertys option="id,name"/]
</div>
<div id="kzsx" style="display:none;">
	[@b.field label="扩展属性"] 	<textarea cols="50"  name="propertyNames"  rows="2" readonly="true" id="propertyNames">[#if column.relationColumns?exists && column.relationColumns?size>0][#list column.extpropertys as res]${res.id},[/#list][/#if]</textarea>
	<input style="margin:auto"  type="button" onClick="listtprs(this);" value="选择关联栏目"/>
	[/@]
</div>
<div id="url" style="display:none;">
	[@b.textfield id="column.url" name="column.url" label="URL" value="${column.url!}" maxlength="300" comment="长度不超过300个字符"/]
</div>
<div id="fwrk-" style="display:none;">
	[@b.field label="访问入口"]
	<select style="width:200px;" id="entrySelect" name="entrySelect">
		<option value="">请选择...</option>
		<option [#if column.entry??&&column.entry='!index']selected[/#if] value="!index">首页</option>
		<option [#if column.entry??&&column.entry='!list']selected[/#if] value="!list">信息列表</option>
		<option [#if column.entry??&&column.entry='!list?img=1']selected[/#if] value="!list?img=1">图片列表</option>
		<option [#if column.entry??&&column.entry='!list?single=1']selected[/#if] value="!list?single=1">单页信息</option>
		<option [#if column.entry??&&column.entry='!studentInfo']selected[/#if] value="!studentInfo">学生简历</option>
		<option [#if column.entry??&&column.entry='!studentDa']selected[/#if] value="!studentDa">学生档案</option>
		<option [#if column.entry??&&column.entry='!zpxxList']selected[/#if] value="!zpxxList">招聘信息</option>
		<option [#if column.entry??&&column.entry='!apply']selected[/#if] value="!apply">投递情况</option>
		<option [#if column.entry??&&column.entry='!wyzx']selected[/#if] value="!wyzx">我要咨询</option>
		<option [#if column.entry??&&column.entry='!wdzx']selected[/#if] value="!wdzx">我的咨询</option>
		<option [#if column.entry??&&column.entry='!rdzx']selected[/#if] value="!rdzx">热点咨询</option>
		<option [#if column.entry??&&column.entry='!wzdc']selected[/#if] value="!wzdc">网站调查</option>
		<option value="自定义">自定义</option>
	</select>
	<div>
		<input style="display: none;" name="column.entry" value="${column.entry!}" size="50" maxlength="200"/>
	</div>
	[/@]
</div>
<div id="xwlx" style="display:none;">
	[@b.select label="新闻类型" name="column.newsType.id" value=(column.newsType.id)!  style="width:200px;" items=newsTypes option="id,name"/]
</div>
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
[/@]
[/@]
<script>
	function checkSelect(form) {
		if(document.getElementById("column.columns.id").value == "") {
			alert("必须选择上级栏目！");
			return false;
		}
	}

	typeChange();
	function typeChange() {
		var selectObj = document.getElementById("column.type.id").value;
		filterTemplate(selectObj);
		if(selectObj == 16) {
			$("#sfjclc,#gllc,#nrly,#gllm,#xxxsmb,#lmxsmb,#kzsxly,#kzsx,#fwrk,#xwlx").show();
			$("#url").hide();
		}
		if(selectObj == 161) {//首页
			$("#sfjclc,#gllc,#nrly,#gllm,#xxxsmb,#kzsxly,#kzsx,#xwlx").hide();
			$("#lmxsmb,#fwrk").show();
		}
		if(selectObj == 18) {
			$("#sfjclc,#gllc,#nrly,#gllm,#xxxsmb,#lmxsmb,#kzsxly,#kzsx,#xwlx").hide();
			$("#fwrk,#url").show();
		}
		if(selectObj == 141) {
			$("#sfjclc,#gllc,#nrly,#gllm,#xxxsmb,#lmxsmb,#kzsxly,#kzsx,#xwlx").hide();
			$("#fwrk").show();
			document.getElementById("column.columns.id").value = "";
		}
		if(selectObj == 142) {
			$("#sfjclc,#gllc,#nrly,#gllm,#xxxsmb,#lmxsmb,#kzsxly,#kzsx,#xwlx").hide();
			$("#fwrk").show();
		}
		var val = 0;
		$("[name=column\\.doesImpl]").each(function() {
			if(this.checked) {
				val = this.value;
			}
		});
		changeWf(val);
	}

	function changeWf(value) {
		if(value == 1) {
			$("#gllc").hide();
		} else {
			$("#gllc").show();
		}
	}

	//根据栏目类型过滤模板
	function filterTemplate(typeid) {
		var types = {
			"161" : "261",
			"16" : "262,263",
			"17" : "321,264"
		};
		if(types[typeid] == undefined){
			return;
		}
		var ids = types[typeid].split(",");
		$("[name='column.contentTemplate.id'], [name='column.columnTemplate.id']").find("option").each(function() {
			var option = $(this);
			var hastype = false;
			var typeid = option.attr("typeid");
			for(var i = 0; i < ids.length; i++){
				if(typeid == ids[i]){
					hastype = true;
				}
			}
			if(hastype){
				option.show();
			}else{
				option.hide();
			}
		});
	}
	$(function() {
		var es = $("#entrySelect");
		var entry = "${column.entry!}";
		es.find("option").each(function() {
			if(this.value == entry) {
				this.selected = true;
			}
		})
		es.change(function() {
			var entryinput = $("input[name='column.entry']");
			if(this.value == "自定义") {
				entryinput.show();
			} else {
				entryinput.hide();
				entryinput.val(this.value);
			}
		});
		if(entry != "" && es.val() == "") {
			es.val("自定义");
			es.change();
		}
	})
	function listRelationColumns(ele) {

		jQuery(ele).colorbox({
			iframe : true,
			width : "800px",
			height : "600px",
			href : "${base}/cms/column-manager.action?idValues=columnIds&nameValues=columnNames"
		});
	}
</script>
[@b.foot/]