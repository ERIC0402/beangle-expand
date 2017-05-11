[#ftl]
[#import "/template/list/comm.ftl" as lc/]
[@b.head]
[/@]
<script src="${base}/static/scripts/wenjuan/WenBenTiMu.js"></script>
<script src="${base}/static/scripts/comm/DictTreeUtil.js"></script>
<link rel="stylesheet" href="${base}/static/wenjuan/css/main.css" />
[#if notoobar??]
[#else]
[@b.toolbar title="使用文本添加题目"]bar.addBack();[/@]
[/#if]
[@b.form action="!saveWbtj" theme="list"]
<div id="dialog-modal" style="display: none">
	<div>
		设置分数：<input name="score"/>
	</div>
</div>
<div style="display: none">
	<div id="settingdiv" style="background:#fff;">
		<ul>
			<li><input type="checkbox" id="delNum" checked="checked"/><label for="delNum">删除题目前的数字+空格</label></li>
			<li><input type="checkbox" id="zhhtml" checked="checked"/><label for="zhhtml">替换html标记</label></li>
		</ul>
	</div>
</div>
<div style="padding: 10px; margin: 5px;" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
	<div>
		
		<a href="#" id="settinga" style="float: right;color:#2d6fb5; cursor:pointer;">选项</a>
		
		[#if tikus??][@c.required/]
			<label>所属题库：</label>
			[@c.select id="tkId" data=tikus name="sstk.id" onchange="dt.tiKuChanged(this)" noOption=true; v]
				<option value="${v.id}" tmflid="${(v.tkfl.id)!}" [#if v.id == (tiKuId)!0]selected[/#if] title="${v.tkmc!}">[@c.substring str=v.tkmc!'' mx=20/]</option>
			[/@]
		[/#if]
		[#if tmfls??][@c.required/]<label>题目分类：</label>[@c.select data=[] name="tmfl.id"/][/#if]
	</div>
	<div>
		<a class="wbtjtm_helpa" href="${base}/static/wenjuan/wbtjsysm.html" target="wbtjtm_helpa" style="float: right;color:#2d6fb5; cursor:pointer;">使用说明</a>
		<input type="button" value="格式化" onclick="format()"/>
		<label>题目配置：</label>
		[#list ["单选题","多选题","填空题","简答题","排序题", "网格题"] as v]
		<input type="button" value="${v}" onclick="wb.addConfig('${v}')"/>
		[/#list]
	</div>
	<textarea style="width: 100%" rows="10" id="textInput" name="textInput">${textInput!}</textarea>
	<div id="preview" class="wjpreview">
	</div>
</div>
	[@lc.table]
		[@b.formfoot]
			<input type="hidden" name="wenJuanId" value="${wenJuanId!}" />
			[@b.redirectParams/]
			[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" onsubmit="validateForm" /]
		[/@]
	[/@]
[/@]
<script language="JavaScript">
	var wb = new WenBenTiMu();
	$(function (){
		wb.textarea = $("#textInput");
		wb.div = $("#preview");
		$("#textInput").keyup(function (){
		
			wb.preview($("#preview"), this.value);
		}).keyup();
		$("#settinga").click(function (){
			jQuery().colorbox({inline : true,href : "#settingdiv"});
		});
	});
	
	function format(){
		wb.formatText();
	}
	
	function setZqda(){
		var div = $("#dialog-modal");
		div.find("input").val("");
		div.dialog({
			height: 140,
			modal: true,
			buttons:{
				"确定":function (){
					var zqda = div.find("input").val();
					if(zqda != null){
						zqda = zqda.toUpperCase();
						wb.addContent('[正确答案：'+zqda+']');
					}
					$(this).dialog("close");
				},
				"取消":function (){
					$(this).dialog("close");
				}
			}
		});
	}
	
	function validateForm(){
		format();
		var tk = $name("tiku.id");
		if(tk.length > 0 && tk.val() == ""){
			throw new Error("请选择题库！");
		}
		var tmfl = $name("tmfl.id");
		if(tmfl.length > 0 && tmfl.val() == ""){
			throw new Error("请选择题目分类！");
		}
		if(wb.getTiMuCount() == 0){
			alert("您还有没添加题目。");
			return false;
		}
		return true;
	}
	//[#if tmfls??]
	var tmfl = new Array();
	/**[#list tmfls as v]*/	tmfl.push({id:"${v.id}", name:"${v.name}", pid: "${(v.parent.id)!}"});/**[/#list]*/
	var dt = new DictTreeUtil();
	dt.init(tmfl, $name("tmfl.id"));
	dt.tiKuChanged();
	//[/#if]
</script>
[@b.foot/]
