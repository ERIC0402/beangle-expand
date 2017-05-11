[#ftl]
<link href="${base}/static/base/style/upload_v3.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
[@b.head/]
[@b.tabs]
	[@b.tab label="批次"]
	<div class="container_25 clearfix">
	<div class="grid_25">
		<ol class="upload_step upload_step_1" id="move_msg">
		<li class="step_1">填写批次信息</li>
		<li class="step_2">设置课程</li>
		<li class="step_3">导入学员</li>
		</ol>
	</div>
    </div>
		[@b.form action="!save" title="批次信息" theme="list"]
			[@b.textfield label="年份" name="wjjg.nf"  value="${wjjg.nf!}" check="match('integer')" required="true" maxlength="4" comment="请输入4位年份"/]
			[@b.field label="院系"]
				<select data-placeholder="请选择院系" tabindex="1" id="yuanXiId" name="wjjg.yuanXi.id"  style="width:400px;">
					<option value=""></option>
					[#list yuanXis?sort_by("xymc") as item]
						<option value="${(item.id)!}" [#if wjjg.yuanXi??][#if wjjg.yuanXi.id=item.id]selected="selected"[/#if][/#if]>${(item.xymc)!}</option>
					[/#list]
                </select>
			[/@]
			[@b.select label="类别" required="true" id="wjjg.lb.id" name="wjjg.lb.id"  value=(wjjg.lb.id)!  style="width:154px;" items=lbs empty="请选择..." option="id,name" onchange="typechange()"/]
			[@b.textfield label="批次编号" name="wjjg.bh"  value="${wjjg.bh!}" required="true" maxlength="20" comment="最多输入20个字符"/]
			[@b.startend label="学习时间" name="wjjg.xxkssj,wjjg.xxjssj" required="true,true" start=wjjg.xxkssj end=wjjg.xxjssj format="date"/]
			[@b.radios label="状态"  name="wjjg.sfyx" value=wjjg.sfyx items="1:启用,0:禁用"/]
			[@b.formfoot]
				<input type="hidden" name="wjjg.id" value="${wjjg.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
			[/@]
		[/@]
	[/@]
[/@]
<script language="JavaScript">
	jQuery("#yuanXiId").chosen();
</script>
[@b.foot/]