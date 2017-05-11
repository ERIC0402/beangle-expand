[#ftl]
[#import "/template/list/comm.ftl" as lc/]
[@b.head]
[/@]
<script type="text/javascript" src="${base}/static/scripts/wenjuan/DiaoChaWenJuan.js"></script>
<link rel="stylesheet" type="text/css" href="${base}/static/scripts/wenjuan/DiaoChaWenJuan.css"/>
[@b.toolbar title=c.formTitle("问卷", wenJuan)]bar.addBack();[/@]
[@b.form action="!save" theme="list"]
	<div style="padding:0 5px 0 0">
		<input name="wenJuan.wjmc" value="${wenJuan.wjmc!'请输入问卷的标题'}" style="text-align: center; width: 100%; font-size: 28px;"/>
		<textarea name="wenJuan.sm" cols="50" rows="3" style="width: 100%;">${wenJuan.sm!'请填写关于此问卷的说明'}</textarea>
	</div>
	<div>
		<input type="button" value="从文本添加题目" onclick="dcwj.addWebBenTiMu()"/>
		[#list tmlxs as v]
		[#if v.code != "WJ_TI_MU_LX_JI_DIAN"]
		<input type="button" value="${v.name}" onclick="dcwj.addWjtm('${v.code}')"/>
		[/#if]
		[/#list]
	</div>
	<div class="templateDiv" style="display: none">
		<div class="dcwjHideIframe">
			<iframe></iframe>
		</div>
	</div>
	<div>
	<div class="tmEditorDivs">
	[#include "wjtm.ftl"/]
	</div>
	</div>
	[@lc.table]
	[@b.formfoot]
		<input type="hidden" name="wenJuan.id" value="${wenJuan.id!}" />
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
	[/@]
	[/@]
[/@]
<script type="text/javascript">
	var dcwj = new DiaoChaWenJuan("${wenJuan.id}");
</script>
[@b.foot/]
