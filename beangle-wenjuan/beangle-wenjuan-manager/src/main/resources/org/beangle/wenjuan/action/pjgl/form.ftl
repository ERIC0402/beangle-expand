[#ftl]
[@b.head]
[/@]
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
[#assign wenJuanPj=wenJuan/]
[@b.toolbar title=c.formTitle("问卷", wenJuanPj)]bar.addBack();[/@]
[@b.form action="!save" theme="list" title="基本信息"]
	[@b.textfield name="wenJuanPj.wjmc" label="名称" value="${wenJuanPj.wjmc!}" style="width:300px;" maxlength="100" required="true" comment="最多输入不超过100个字"/]
	[@b.textarea label="说明" cols="50" rows="3" name="wenJuanPj.sm" value=(wenJuanPj.sm)! maxlength="1000"/] 
	[@b.textfield name="wenJuanPj.xxScore" label="选项分数" value="${wenJuanPj.xxScore!}"style="width:150px;" maxlength="20" comment="如：5，4，3，2，1"/]
	[@b.formfoot]
		<input type="hidden" name="wenJuanPj.id" value="${wenJuanPj.id!}" />
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" onsubmit="validateForm"/]
	[/@]
[/@]
<script type="text/javascript">
	function validateForm(){
		return true;
	}
</script>
[@b.foot/]
