[#ftl]
[@b.head/]
[#macro shortEnableInfo enabled]
[#if enabled]<img height="15" width="15" src="${b.theme.iconurl('actions/activate.png')}" alt="activate" title="${b.text("action.activate")}"/>[#else]<img height="15" width="15" src="${b.theme.iconurl('actions/freeze.png')}" alt="freezen" title="${b.text("action.freeze")}"/>[/#if]
[/#macro]
<script type="text/javascript">
	bg.ui.load("tabletree");
	defaultColumn=1;
</script>
<script type="text/javascript">
	function getIds(){
		return(getCheckBoxValue(document.getElementsByName("columnId")));
	}
	function save(){
		if($("#groupId").val()=="" || $("#groupId").val() == null){
			alert("请选择角色");
			return;
		}
		document.authorityForm.action="${b.url('!save')}";
		if(confirm("您是确定要授权？")){
			bg.form.submit('authorityForm');
		}
	}
	function checkResource(ele){
		columnDivId=ele.id;
		var tempTarget ;
		tempTarget = document.getElementById(columnDivId);
		if(tempTarget!=null||tempTarget!='undefined'){
			var stats = tempTarget.checked;
			var num=0;
			var tempId = columnDivId+'_'+num;
			while(tempTarget!=null){
				num++;
				tempTarget.checked = stats;
				tempTarget = document.getElementById(tempId);
				tempId = columnDivId+'_'+num;
			}
		}
	}
</script>
<table width="90%" align="center">
<tr>
<td valign="top">
[@b.toolbar]
	bar.setTitle('栏目权限');
	bar.addItem("${b.text("action.spread")}","displayAllRowsFor(1);",'${b.theme.iconurl('tree/plus.png')}');
	bar.addItem("${b.text("action.collapse")}","collapseAllRowsFor(1);",'${b.theme.iconurl('tree/minus.png')}');
	bar.addItem("${b.text("action.save")}",save,'save.png');
[/@]
[#if group??]
[@b.form name="authorityForm" action="!edit"]
<table width="100%" class="searchTable" id="meunAuthorityTable">
	<tr>
		<td>
		站点:<select name="site.id" onchange="bg.form.submit('authorityForm');return false;" style="width:150px">
				 [#list sites?sort_by("code")! as item]
				  	<option value="${item.id}" [#if item.id=site.id]selected="selected"[/#if]>${item.name}</option>
				 [/#list]
			</select>
		</td>
		<td class="title">
		角色:<select id="groupId" name="group.id" style="width:150px;" onchange="bg.form.submit('authorityForm');return false;">
			[#list site.groups as item]
			<option value="${item.id}" [#if item.id=group.id]selected="selected"[/#if]>${item.name}</option>
			[/#list]
			</select>
		</td>
	</tr>
</table>

<table width="100%" class="gridtable">
	<tbody>
	<tr class="gridhead">
	<th width="5%"><input type="checkbox" onclick="treeToggleAll(this,checkResource)"/></th>
	<th width="30%">${b.text("common.name")}</th>
	<th width="30%">排序</th>
	<th width="20%">栏目类型</th>
	<th width="10%">${b.text("common.status")}</th>
	</tr>
	[#macro i18nTitle(entity)][#if locale.language?index_of("en")!=-1][#if entity.engTitle!?trim==""]${entity.title!}[#else]${entity.engTitle!}[/#if][#else][#if entity.title!?trim!=""]${entity.title!}[#else]${entity.engTitle!}[/#if][/#if][/#macro]
	[#list columns?sort_by("orders") as column]

	<tr class="grayStyle" id="${column.orders!}">
		<td	class="gridselect">
			<input type="checkbox" id="checkbox_${column_index}" onclick="treeToggle(this,checkResource)"  name="columnId" [#if (selectedColumnIds??)&&(selectedColumnIds?seq_contains(column.id))]checked="checked"[/#if] value="${column.id}">
		</td>
		<td>
		<div class="tier${column.depth}">
			[#if !column.node]
			<a href="#" class="doc"></a>[#rt]
			[#else]
			<a href="#" class="folder_open" id="${column.orders}_folder" onclick="toggleRows(this);"></a>[#rt]
			[/#if]
			 ${column.name!}
		</div>
		</td>
		<td >&nbsp;${column.orders!}</td>
		<td>
			&nbsp;${(column.type.name)!}
		</td>
		<td align="center">[@shortEnableInfo column.enabled/]</td>
	</tr>
	[/#list]
	</tbody>
</table>
[/@]
[/#if]
	</tr>
</table>
[@b.foot/]