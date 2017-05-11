[#ftl]
[@b.head/]
[#import "/org/beangle/wenjuan/action/zuJuan/WenJuan.ftl" as wj]
<link rel="stylesheet" href="${base}/static/wenjuan/css/main.css" />
<style type="text/css">
	.showOneBtn, .msgdiv{display: none;}
	.wjtoolbar, .pagediv{text-align: center}
	.wjtoolbar input{margin: 0 20px;}
	.progressvalue, .msgdiv{float:left; margin:0 5px;}
	.ui-progressbar .ui-progressbar-value { background-image: url(${base}/static/scripts/jquery-ui/images/pbar-ani.gif); }
	.ui-progressbar{height:1em; margin: 5px;}
	.zqdadiv{display: none;}
	.pervTmBtn, .nextTmBtn{font-size: 30px!important; margin: 0 20px;}
	.pageBtn{width:30px; margin: 5px;}
	.xxdiv input{vertical-align:middle;}
	.xxdiv li label{vertical-align:middle;display:inline-block; cursor:pointer;}
</style>
<script src="${base}/static/scripts/wenjuan/DaJuan.js"></script>
[@b.toolbar title="${toolbarTitle!'在线考试'}"][#if errormsg?? || lxms??][#if !noback??]bar.addBack();[/#if][/#if][/@]
[#if errormsg??]
	[@c.errormsgTab errormsg=errormsg/]
[#else]
		[@b.form title="${(ksap.name)!'考试内容'}" action="!save" theme="list" id="ksapdjForm" name="ksapdjForm"  notable="true"]
		<tr><td>
			<p class="tmlx">
				<div style="float: left;">
				<input type="button" class="showAllBtn" value="显示全部" onclick="dj.showAll()"/>
				<input type="button" class="showOneBtn" value="单个显示" onclick="dj.showOne()"/>
				<input type="button" value="检查进度" onclick="dj.checkProgress()"/>
				</div>
				<div id="progressbar" style="float: left; width: 300px;"></div>
				<div id="progressvalue" class="progressvalue" ></div>
				<div class="msgdiv">自动保存中……</div>
				[#if lxms??]
				<div style="padding: 0 10px;">成绩：<label class="djcjqk"></label>（答对数量/总题目数）<label class="zxlx_zp"></label></div>
				[/#if]
					[#if lxms??]
					[#else]
						<div style="float: right;">
							[#if (ksap.time)??]
							<label style="font-style: italic; color: #555555;">考试总时长：${ksap.time}分钟</label>
							[/#if]
							<label > 剩余时间：</label><label class="leftTime" leftTime="${(endTime?string('yyyy/MM/dd HH:mm:ss'))!}" now="${now?string('yyyy/MM/dd HH:mm:ss')}"></label>
						</div>
					[/#if]
				<div style="clear: both;"></div>
			</p>
			<div class="ui-tabs ui-widget ui-widget-content ui-corner-all wjpreview">
					[#if lxms??]
						[#global zxks_lxms = true/]
						[#global showzqda = true/]
						
							[#list wjjgtms as jgtm]
								[@wj.showTm tm=jgtm.tm sortxx='px' /]
								[#assign tmbtns]${tmbtns!}<input class="pageBtn" type="button" value="${jgtm_index+1}" onclick="dj.showOne(this)"/>[/#assign]
							[/#list]
					[#else]
						[#global hasbqd = true/]
							[#list wjjgtms as jgtm]
								[@wj.showTm tm=jgtm.tm /]
								[#assign tmbtns]${tmbtns!}<input class="pageBtn" type="button" value="${jgtm_index+1}" onclick="dj.showOne(this)"/>[/#assign]
							[/#list]
					[/#if]
			</div>
			<div class="ui-tabs ui-widget ui-widget-content ui-corner-all wjpreview wjtoolbar pagediv">
				<input type="button" class="pervTmBtn" value="上一题" onclick="dj.prevTiMu()"/>
				<input type="button" class="nextTmBtn" value="下一题" onclick="dj.nextTiMu()"/>
			</div>
			<div class="ui-tabs ui-widget ui-widget-content ui-corner-all wjpreview pagediv">
				<p>
					${tmbtns!}
				</p>
				<div>
					<label style="font-weight: bolder;">按钮颜色说明：</label><label style="background-color: #D4D0C8; color: #fff">灰色</label>表示未答题，<label style="background-color: #3B9B3A; color: #fff">绿色</label>表示已答题[#if !lxms??]，<label style="background-color: #E9C147; color: #fff">黄色</label>表示不确定的题目[/#if]。
				</div>
			</div>
			[@b.formfoot]
				[#if lxms??]
				[#else]
						<div style="text-align: center;">
							<input type="hidden" name="ksap.id" value="${(ksap.id)!}" />
							<input type="hidden" name="completedj" value="" class="completedj"/>
							<input type="hidden" name="savedj" value="" class="savedj"/>
							[@b.submit value="保存" onsubmit="saveForm"/]
							[@b.submit value="交卷" onsubmit="validateForm" style="margin-left:100px;"/]
						</div>
				[/#if]
			[/@]
			</td></tr>
		[/@]
	<div style="display: none">
	<iframe id="autoSaveFrame" name="autoSaveFrame"></iframe>
	</div>
	<script type="text/javascript">
		var dj = new DaJuan();
		var timediff = null;
		var timeup = false;
		$(function(){
			dj.init();
		});
		//[#if !lxms??]
		if(window.wjshowLeftTimeInterval){
			clearInterval(window.djCheckProgressInterval);
		}
		window.wjshowLeftTimeInterval = setInterval("showLeftTime()",1000);
		function showLeftTime(){
			var leftTimeLabel = $(".leftTime");
			if(leftTimeLabel.length == 0){//没有剩余时间时，清除定时
				clearInterval(wjshowLeftTimeInterval);
			}
			var now = new Date();
			if(timediff == null){//计算本地与服务器时差
				var snow = leftTimeLabel.attr("now");
				snow = new Date(snow);
				timediff = snow.getTime() - now.getTime();
			}
			var leftTime = leftTimeLabel.attr("leftTime");
			leftTime = new Date(leftTime);
			var time = leftTime.getTime() - now.getTime() - timediff;
			time /=1000;
			if(time <= 0){
				timeup = true;
				bg.form.submit('ksapdjForm',null,null,validateForm);
				leftTimeLabel.html("0秒");
				return;
			}
			var str = "";
			if(time / 60/60 > 1){
				str += parseInt(time / 60/60)  + "小时";
				time %= 60*60;
			}
			if(time / 60 > 1){
				str += parseInt(time / 60) + "分钟";
				time %= 60;
			}
			str += parseInt(time) + "秒";
			leftTimeLabel.html(str);
		}
		
		function saveForm(){
			$(".savedj").val("1");
			return true;
		}
		
		function validateForm(){
			dj.checkProgress();
			if(!timeup){
				if(dj.progressValue < 100){
					var da = confirm("问卷还没全部答完，是否继续？");
					if(!da){
						return false;
					}
				}
				if(!confirm("交卷后不能再答题，是否继续？")){
						return false;
				}
				if(!confirm("交卷后不能再答题，是否继续？")){
						return false;
				}
			}
			$(".completedj").val("1");
			clearInterval(wjautoSaveInterval);
			clearInterval(wjshowLeftTimeInterval);
			return true;
		}
		if(window.wjautoSaveInterval){
			clearInterval(window.djCheckProgressInterval);
		}
		window.wjautoSaveInterval = setInterval("autoSave()", 100000);
		function autoSave(){
			var form = $("#ksapdjForm");
			if(form.length == 0){
				clearInterval(wjautoSaveInterval);
			}
			bg.form.submit('ksapdjForm', null, "autoSaveFrame", null, true, true);
			form.find(":button, :submit").attr("disabled", false);
			var msgdiv = $(".msgdiv");
			msgdiv.show();
			msgdiv.fadeOut(3000);
		}
		function showDa(){
			var wjjgtms = new Array();
			//[#list wjjg.wjjgtms as jgtm]
			wjjgtms.push({tmid: "${jgtm.tm.id}", xx:[[#list jgtm.tmxxs as xx][#if xx_index gt 0],[/#if]${xx.id}[/#list]], nr:"${(jgtm.nr?js_string)!}"});
			//[/#list]
			dj.showDa(wjjgtms);
		}
		showDa();
		//[/#if]
	</script>
[/#if]
[@b.foot/]
