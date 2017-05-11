[#ftl]
[@b.head]
[/@]
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>

[@b.toolbar title=c.formTitle("问卷题目", wjtm)]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="问卷题目信息"]
[@b.form action="!save" theme="list"]
	<fieldset>
		<legend>基本信息</legend>
		<ol>
			[@b.textfield name="wjtm.px" label="排序" value="${wjtm.px!}" style="width:50px;" maxlength="3" check="match('integer').range(0,999)"/]
			[@b.textfield name="wjtm.score" label="分数" value="${wjtm.score!}" style="width:50px;" maxlength="3" check="match('number').range(0,99)"/]
			[@b.textfield name="wjtm.sstm.tmmc" label="题目" value=(wjtm.sstm.tmmc)! style="width:600px;" maxlength="100" /] 
		</ol>
	</fieldset>
	[@b.formfoot]
		<input type="hidden" name="wjtm.id" value="${wjtm.id!}" />
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
	[/@]
[/@]
	[/@]
[/@]
<script language="JavaScript">
	jQuery("#groupIds").chosen();
</script>
[@b.foot/]
