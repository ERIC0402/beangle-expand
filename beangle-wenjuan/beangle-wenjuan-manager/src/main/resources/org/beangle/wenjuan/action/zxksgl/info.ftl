[#ftl]
[@b.head/]
[#import "/org/beangle/wenjuan/action/zuJuan/WenJuan.ftl" as wj]
<link rel="stylesheet" href="${base}/static/wenjuan/css/main.css" />
<style type="text/css">
	.showOneBtn{display: none;}
	.wjtoolbar{text-align: center}
	.wjtoolbar input{margin: 0 20px;}
	.progressvalue{float:left; margin-left: 5px;}
	.ui-progressbar .ui-progressbar-value { background-image: url(${base}/static/scripts/jquery-ui/images/pbar-ani.gif); }
	.ui-progressbar{height:1em; margin: 5px;}
</style>
<script src="${base}/static/scripts/wenjuan/DaJuan.js"></script>
[@b.toolbar title="试卷详细情况"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="试卷内容"]
		[@b.form action="!save" theme="list" id="zxksdjForm" name="zxksdjForm"]
			<p class="tmlx">
				<div style="float: left;">
				<input type="button" value="完成度" onclick="dj.checkProgress()"/>
				</div>
				<div id="progressbar" style="float: left; width: 300px;"></div>
				<div id="progressvalue" class="progressvalue" ></div>
				<div style="clear: both;"></div>
			</p>
			<div class="ui-tabs ui-widget ui-widget-content ui-corner-all wjpreview" style="min-height: 300px;">
					[#assign wjjg = zxks.wjjg/]
					[#global showzqda = true/]
					[#global shortxx = true/]
					[#list wjjg.wjjgtms as jgtm]
						[@wj.showTm tm=jgtm.tm  sortxx="px" /]
						[#assign tmbtns]${tmbtns!}<input class="pageBtn" type="button" value="${jgtm_index+1}" onclick="dj.showOne(this)"/>[/#assign]
					[/#list]
			</div>
			<div class="ui-tabs ui-widget ui-widget-content ui-corner-all wjpreview wjtoolbar pagediv">
				<input type="button" value="上一题" onclick="dj.prevTiMu()"/>
				<input type="button" value="下一题" onclick="dj.nextTiMu()"/>
				<p>
					${tmbtns!}
				</p>
			</div>
		[/@]
	[/@]
[/@]
<div style="display: none">
<iframe id="autoSaveDiv"></iframe>
</div>
<script type="text/javascript">

	var dj = new DaJuan();
	$(function(){
		dj.init();
		dj.showAll();
	});
	
	function showDa(){
		var wjjgtms = new Array();
		[#list wjjg.wjjgtms as jgtm]
		wjjgtms.push({tmid: "${jgtm.tm.id}", xx:[[#list jgtm.tmxxs as xx][#if xx_index gt 0],[/#if]${xx.id}[/#list]], nr:"${(jgtm.nr?js_string)!}"});
		[/#list]
		dj.showDa(wjjgtms);
	}
	showDa();
	$("#zxksdjForm").find("input, textarea").attr("disabled", true);
</script>
[@b.foot/]
