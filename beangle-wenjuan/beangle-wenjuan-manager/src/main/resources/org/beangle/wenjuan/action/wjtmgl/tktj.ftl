[#ftl]
[@b.head]
[/@]
[@b.toolbar title="从题库添加题目"]bar.addBack();[/@]
[@b.form action="!saveTktj" theme="list"]
<div style="padding: 10px; margin: 5px;" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
	<div style="margin: 5px 0;">
		<input type="button" value="添加题目" onclick="searchTiMu()"/>
		<input type="button" value="自动排序" onclick="autoPx()"/>
		[#if wjfl != "WEN_JUAN_FL_PJ"]
		<input style="width: 80px;" id="scoreAll"/><input type="button" value="批量设置分数" onclick="batchScore()"/>
		[/#if]
	</div>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<thead>
			<th width="10%">排序</th>
			<th>题目</th>
			<th width="10%">[#if wjfl == "WEN_JUAN_FL_PJ"]子题目[/#if]分数</th>
			[#if wjfl == "WEN_JUAN_FL_PJ"]
			<th width="20%">选项分数</th>
			[/#if]
			<th width="10%">操作</th>
		</thead>
		<tbody id="tbody">
		</tbody>
	</table>
</div>
	[@b.formfoot]
		<input type="hidden" name="wenJuanId" value="${wenJuanId}" />
		[@b.redirectParams/]
		[@b.submit value="action.submit" onsubmit="validateForm" /]
	[/@]
[/@]
<table style="display: none">
	<tbody id="templateDiv">
		<tr>
			<td align="center">
				<input name="timu" value="{i}" type="hidden"/>
				<input name="timu{i}.sswj.id" type="hidden" value="${wenJuanId}" />
				<input name="timu{i}.px" style="width: 80px;" class="tmpx"/></td>
			<td><input name="timu{i}.sstm.id" class="tmid" type="hidden"/><label class="tmmc"></label></td>
			<td align="center"><input name="timu{i}.score" class="tmscore" style="width: 98%;"/></td>
			[#if wjfl == "WEN_JUAN_FL_PJ"]
			<td align="center"><input name="timu{i}.xxScore" class="xxScore" style="width: 98%;"/></td>
			[/#if]
			<td align="center"><input type="button" value="删除" onclick="delLine(this)"/></td>
		</tr>
	</tbody>
</table>
<script language="JavaScript">
	function searchTiMu(){
		jQuery().colorbox(
		{
			iframe : true,
			width : "1000px",
			height : "600px",
			href : "${base}/wenjuan/select.action?method=tiMu"
		});
	}
	var tiMuLine = 0;
	function addTiMu(data){
		for(var i in data){
			var d = data[i];
			//过滤重复题目
			var hastmid = false;
			$(".tmid").each(function (){
				if(d.id == this.value){
					hastmid = true;
				}
			});
			if(hastmid){
				continue;
			}
			var html = $("#templateDiv").html();
			html = html.replace(/\{i\}/ig, tiMuLine++);
			var tr = $(html);
			tr.find(".tmmc").html(d.tmmc);
			tr.find(".tmid").val(d.id);
			$("#tbody").append(tr);
		}
	}
	
	function delLine(btn){
		var tr = $(btn).parent().parent();
		tr.fadeOut(function (){
			tr.remove();
		});
	}
	
	function autoPx(){
		var px = 0;
		$("#tbody  .tmpx").each(function (){
			if(this.value == ""){
				this.value = ++px;
			}else{
				px = this.value * 1;
			}
		});
	}
	
	function batchScore(){
		var score = $("#scoreAll").val();
		if(!/^\d+\.?\d*$/.test(score)){
			alert("请输入正确的分数");
			return false;
		}
		$(".tmscore").val(score);
	}
	
	function validateForm(){
		if($("#tbody > tr").length == 0){
			alert("您还有没添加题目。");
			return false;
		}
		bg.form.checkAll($("#tbody .tmpx"), /^\d{1,4}$/, "题目排序不能为空或格式错误！");
		bg.form.checkAll($("#tbody .tmscore"), /^(100|[1-9]?\d(\.\d{2})?)$/, "题目分数不能为空或格式错误！");
		if($("#tbody .xxScore").length > 0){
			bg.form.checkAll($("#tbody .xxScore"), /^(\d+\.?\d*[，,])*(\d+\.?\d*)$/, "选项分数不能为空或格式错误！");
		}
		return true;
	}
</script>
[@b.foot/]
