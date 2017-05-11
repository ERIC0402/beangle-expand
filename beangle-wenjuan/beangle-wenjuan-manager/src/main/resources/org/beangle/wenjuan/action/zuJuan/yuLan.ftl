[#ftl]
[#import "/org/beangle/wenjuan/action/zuJuan/WenJuan.ftl" as wj]
[#import "/template/list/comm.ftl" as lc]
[@b.head]
[/@]
<link rel="stylesheet" href="${base}/static/wenjuan/css/main.css" />
<script src="${base}/static/scripts/wenjuan/ShowDa.js"></script>
[@b.toolbar title="问卷预览"]bar.addBack();[/@]
[@b.form action="!faBu" theme="list"]

	[#if wjlx != "WEN_JUAN_FL_PJ"]
	[#else]
	[/#if]
	
	[#if wenJuan.wjszs?size gt 0]
	<p class="tmlx">随机题目
		<input type="button" value="刷新随机题目" onclick="shuaXin(this.form)"/>
		<input type="button" value="转为固定问卷" onclick="guDingTiMu(this.form)"/>
	</p>
	[/#if]
	
	[@lc.table]
		[@wj.showWenJuan wenJuan=wenJuan tiMus=tiMus showda=true /]
		[@b.formfoot]
			<input type="hidden" name="wenJuan.id" value="${wenJuan.id}" />
			[@b.redirectParams/]
			[#--
			[#if wjlx != "WEN_JUAN_FL_PJ"]
			<input type="button" value="显示答案" onclick="showda();" />
			[/#if]
			--]
			[#if !wenJuan.enabled]
			[@b.submit value="发布" onsubmit="validateForm" /]
			[#else]
			[/#if]
		[/@]
	[/@]
[/@]
<script type="text/javascript">

	$(function (){
			showda();
	});

	function showda(){
		var da = new ShowDa();
		da.showAll($(".wjpreview fieldset"));
	}
	function shuaXin(form){
		form.action = form.action.replace("faBu", "yuLan");
		bg.form.submit(form.id, null, null, null, null, true);
	}
	
	function guDingTiMu(form){
		form.action = form.action.replace("faBu", "guDingTiMu");
		bg.form.submit(form.id, null, null, null, null, true);
	}

	function validateForm(){
		return true;
	}
</script>
[@b.foot/]
