[#ftl]
[@b.head/]
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
[#assign formTitle]在线考试-[#if ksap.id??]修改[#else]添加[/#if][/#assign]
[@b.toolbar title=formTitle]bar.addBack();[/@]
[@b.form id="ksapForm" name="ksapForm" action="!save" title="基本信息" theme="list"]
    [@b.textfield label="在线考试名称" name="ksap.name"  value="${(ksap.name)!}" required="true" maxlength="20" comment="最多输入20个字"/]
    [@b.field label="问卷" required="true"]
    <select title="问卷" name="ksap.wenJuan.id" id="wenJuanId" style="width:160px;" >
         <option value="" >请选择...</option>
		[#list wenJuans as v]
		    <option value="${v.id}" [#if ksap.wenJuan??&&ksap.wenJuan.id==v.id]selected="selected"[/#if] title="${v.wjmc}">[@c.substring str=v.wjmc!'' mx=20/]</option>
		[/#list]
	</select>
    [/@]
	[@b.field label="类别" required="true"]
		<select title="类别" name="ksap.category.id" id="categoryId" style="width:160px;"  onchange="typechange(this)">
			<option value="" >请选择...</option>
			[#list categorys as item]
				<option value="${item.id}" [#if ksap.category??&&ksap.category.id==item.id]selected="selected"[/#if]>${item.name!}</option>
			[/#list]
		</select>
	[/@]
	<div id="type">
	[@b.field label="类型" ]
		<select id="type"  name="ksap.type.id" style="width:160px;" >
			<option value="" >请选择...</option>
			[#if types??]
				[#list types as item]
					<option value="${item.id}"  [#if ksap.type??&&ksap.type.id==item.id]selected="selected"[/#if]>${item.name!}</option>
				[/#list]
			[/#if]
		</select>
	[/@]
	</div>
    [@b.startend label="开始时间-结束时间" name="ksap.startTime,ksap.endTime" required="true,true" start=ksap.startTime end=ksap.endTime format="yyyy-MM-dd HH:mm" /]
    [@b.textfield label="时间（分钟）" name="ksap.time"  value="${(ksap.time)!}" required="true" check="match('integer')"  maxlength="10" comment="最多输入10个字。单位：分钟"/]
	[@b.textfield label="IP段限制" name="ksap.ipLimit"  value="${ksap.ipLimit!}"  maxlength="200" comment="最多输入200个字符。格式如：192.168.0.1-192.168.0.100"/]
	[@b.radios label="状态"  name="ksap.enabled" value=ksap.enabled items="1:有效,0:无效"/]
	[@b.formfoot]
		<input type="hidden" name="ksap.id" value="${ksap.id!}" />
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" onsubmit="checkKsapForm()"/]
	[/@]
[/@]
<script type="text/javascript">
	function typechange(select){
        var category = $(select);
        var id = category.find("option:selected").text();
        if(id=="考试"){
			$("#type").show();
		}else if(id=="评教"){
			$("#type").hide();
		}
	}
	
	var categoryinit = "${(ksap.category.id)!}";
	if(categoryinit != ""){
		typechange($("#categoryId"));
	}
	
	function checkKsapForm(){
		var fields = {};
		fields["#wenJuanId"] = new Validator2Str().require().getConfig();
		fields["#categoryId"] = new Validator2Str().require().getConfig();
		var vform = new validator2(fields, "#ksapForm");
		var res=vform.exec();
		return res;
	}

	jQuery("#xueYuanIds").chosen();

</script>
[@b.foot/]