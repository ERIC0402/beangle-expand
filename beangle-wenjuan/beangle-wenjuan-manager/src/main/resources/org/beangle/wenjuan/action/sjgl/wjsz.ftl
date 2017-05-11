[#ftl]
[#import "/template/list/comm.ftl" as lc/]
[@b.head][/@]
<script src="${base}/static/scripts/comm/DictTreeUtil.js"></script>
[@b.toolbar title="随机问题设置"]bar.addBack();[/@]
[@b.form action="!saveWjsz" theme="list"]
<div style="padding: 10px; margin: 5px;" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
	<div style="margin: 5px 0;">
		<input type="button" value="添加一行" onclick="addLine()"/>
	</div>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<thead>
			<th width="20%">题库</th>
			<th width="30%">题目分类</th>
			<th width="10%">题目类型</th>
			<th width="10%">题目数量</th>
			<th width="10%">题目难度</th>
			<th width="10%">每题分数</th>
			<th width="10%">操作</th>
		</thead>
		<tbody id="tbody">
			[#list wjszs as v]
				[@templateTr wjsz=v index=v_index/]
			[/#list]
		</tbody>
	</table>
	<div>
		说明：题目数量的输入框后显示的数量是题库中可使用的题目数。
	</div>
</div>
	[@lc.table]
		[@b.formfoot]
			<input type="hidden" name="wenJuan.id" value="${wenJuan.id}" />
			[@b.redirectParams/]
			[@b.submit value="action.submit" onsubmit="validateForm" /]
		[/@]
	[/@]
[/@]
<table style="display: none">
	<tbody id="templateDiv">
		[@templateTr wjsz=wjsz!/]
	</tbody>
</table>
[#macro templateTr wjsz index='{i}']
<tr>
		<td>
			<input type="hidden" name="wjsz" value="${index}"/>
			<input type="hidden" name="wjsz${index}.sswj.id" value="${wenJuan.id}" />
			<select name="wjsz${index}.tk.id" onchange="setTmfl(this)">
				[#list tikus as v]
					<option value="${v.id}" tmflid="${(v.tkfl.id)!}" [#if v.id == (wjsz.tk.id)!0]selected[/#if] title="${v.tkmc}">[@c.substring str=v.tkmc!'' mx=15/]</option>
				[/#list]
			</select>
		</td>
		<td><select name="wjsz${index}.tmfl.id" val="${(wjsz.tmfl.id)!}"></select></td>
		<td><select name="wjsz${index}.tmlx.id">
					<option value="">请选择</option>
				[#list tmlxs as v]
					[#if v.name != "节点" && v.name != "网格题"]
					<option value="${v.id}" [#if v.id == (wjsz.tmlx.id)!0]selected[/#if]>${v.name}</option>
					[/#if]
				[/#list]</select></td>
		<td align="center"><input name="wjsz${index}.tmsl" style="width: 50px;" value="${wjsz.tmsl!}" [#if (wjsz.id)??]title="题库中可使用的题目数${wjsz.tiMus?size}"[/#if]/></td>
		<td><select name="wjsz${index}.tmnd.id">
				<option value="">请选择</option>
				[#list tmnds as v]
					<option value="${v.id}" [#if v.id == (wjsz.tmnd.id)!0]selected[/#if]>${v.name}</option>
				[/#list]</select></td>
		<td align="center"><input name="wjsz${index}.mtfs" style="width: 50px;" value="${wjsz.mtfs!}"/></td>
		<td align="center"><input type="button" value="删除" onclick="delLine(this)"/></td>
	</tr>
[/#macro]
<script language="JavaScript">
	var wjszno = "${wjszs?size}"*1;
	function addLine(){
		var html = $("#templateDiv").html();
		html = html.replace(/\{i\}/g, wjszno++);
		var tr = $(html);
		tr.hide();
		tr.appendTo($("#tbody")).fadeIn();
		var ptr = tr.prev("tr");
		var tiku = ptr.find("[name$='.tk.id']").val();
		var tk = tr.find("[name$='.tk.id']");
		tk.val(tiku);
		setTmfl(tk.get(0));
	}
	
	function delLine(input){
		var tr = $(input).parent().parent();
		tr.fadeOut("fast", function (){
			$(this).remove();
		});
	}
	
	var wjtmfl = new Array();
	//[#list tmfls as v]	
	wjtmfl.push({id:"${v.id}", name:"${v.name}", pid: "${(v.parent.id)!}"});
	//[/#list]
	function setTmfl(select){
		var tk = $(select);
		
		var tmflid = tk.find("option:selected").attr("tmflid");
		
		var tmflSelect = tk.parent().parent().find("[name$='.tmfl.id']");
		
		tmflSelect.find("option").remove();
		
		var dt = new DictTreeUtil();
		dt.init(wjtmfl, tmflSelect);
		dt.addOptoinByPid(tmflid)
		dt.select.prepend("<option value=''>请选择</option>");
		
		if($(tmflSelect).attr("val")==""){
			
			$(tmflSelect).find("option[value='']").attr("selected","true");
		}
		
		
	}
	
	$(function (){
		var tks = $("#tbody").find("[name$='.tk.id']");
		tks.each(function (){
			setTmfl(this);
		});
		$("#tbody").find("select").each(function (){
			var val = $(this).attr("val")
			if(val){
				this.value = val;
			}
		});
		if(tks.length == 0){
			addLine();
		}
	});
	
	function validateForm(){
		var tmsl = $("#tbody").find("[name$='.tmsl']");
		if(tmsl.length == 0){
			//throw new Error("请添加题目设置！");
		}
		tmsl.each(function (){
			if(!/^\d{1,4}$/.test(this.value)){
				this.focus();
				throw new Error("题目数量不能为空或格式错误！");
			}
		});
		var mtfs = $("#tbody").find("[name$='.mtfs']");
		mtfs.each(function (){
			if(!/^(100|[1-9]?\d(\.\d{0,2})?)$/.test(this.value)){
				this.focus();
				throw new Error("题目分数不能为空或格式错误！");
			}
		});
		return null;
	}
</script>
[@b.foot/]
