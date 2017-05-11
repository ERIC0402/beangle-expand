[#ftl]
[@b.head]
[/@]
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>

[@b.toolbar title=c.formTitle("问卷", wenJuan)]bar.addBack();[/@]
[@b.form action="!save" theme="list" title="基本信息"]
	[@b.textfield name="wenJuan.wjmc" label="名称" value="${wenJuan.wjmc!}"style="width:300px;" maxlength="100" required="true" comment="最多输入不超过100个字"/]
	[@b.select label="类型" required="true" name="wenJuan.wjlx.id" empty="请选择" value=(wenJuan.wjlx.id)!  style="width:200px;" items=wjlxs option="id,name"/]
	[@b.textarea label="说明" cols="50" rows="3" name="wenJuan.sm" value=(wenJuan.sm)! maxlength="1000"/] 
	[@b.textfield name="wenJuan.hgcj" label="合格成绩" value="${wenJuan.hgcj!}"style="width:100px;" maxlength="3" required="true"   check="match('integer').range(0,999)" comment="请输入非负数"/]
	[@b.textfield name="wenJuan.wjzf" label="总分" value="${wenJuan.wjzf!}"style="width:100px;" maxlength="3"  required="true"  check="match('integer').range(0,999)" comment="请输入非负数"/]
	[@b.formfoot]
		<input type="hidden" name="wenJuan.id" value="${wenJuan.id!}" />
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" onsubmit="validateForm"/]
	[/@]
[/@]
<script type="text/javascript">
	function validateForm(){
		var hgcj = $name("wenJuan.hgcj").val() * 1;
		var wjzf = $name("wenJuan.wjzf").val() * 1;
		if(hgcj > wjzf){
			alert("合格成绩不能大于总分。");
			return false;
		}
		return true;
	}
</script>
[@b.foot/]
