[#ftl]
[@b.head/]
[#import "/org/beangle/wenjuan/action/zuJuan/WenJuan.ftl" as wj]
<script type="text/javascript" src="${base}/static/scripts/comm/jquery.blockUI.js"></script>

[@b.grid  items=zxkss var="zxks" sortable="false" ]
	[@b.gridbar]
		try{c.closeProgress();}catch(e){};
		bar.addItem("计算成绩",action.openCurrent("jscj",null,null,true,true,null,"false"));
		[#if userid?? && userid == 1]
		bar.addItem("修改成绩", "zxksglXgcj()");
		bar.addItem("刷新", action.method("refreshZxks"));
		[/#if]
		bar.addItem("${b.text("action.info")}",action.info());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("${b.text("action.export")}",
			action.exportData("pici.nf:年份,pici.bh:批次,wjjg.wj.wjmc:问卷名称"
			+",user.name:学员学籍号,user.fullname:学员姓名"
			+",wjjg.kssj:开考时间,wjjg.jssj:交卷时间,wjjg.score:分数",null,"&fileName=成绩信息"));
		[#if zxksid??]
		bar.addItem("返回考试安排","fanhuikasp()","${base}/static/themes/default/icons/16x16/actions/backward.png");
		[/#if]
	[/@]
	<script type="text/javascript">
	
		function fanhuikasp(){
			$("[href*='${base}/zyk/wenjuan/zyk-ksap.action']").click();
		}
		
		function zxksglXgcj(){
			var ids = bg.input.getCheckBoxValues("zxks.id");
			if(ids == ""){
				alert("请选择学生！");
				return;
			}
			var div = $("#dialog-modal");
			div.find("input").val("");
			div.dialog({
				height: 200,
				modal: true,
				buttons:{
					"确定":function (){
						var score = $(this).find(".dmscore").val();
						if(!/^[+-]?(\d+(\.\d*)?|\.\d+)([Ee]\d+)?$/.test(score)){
							alert("请输入正确的分数");
							return;
						}
						zxksglSaveXgcj($(this));
						$(this).dialog("close");
					//	$("#zxkslist").load('${base}/wenjuan/zxksgl!search.action');
					},
					"取消":function (){
						$(this).dialog("close");
					}
				}
			});
		}
		
		function zxksglSaveXgcj(div){
			var ids =  "";
			$("[name='zxks.id']:checked").each(function (){
				if(ids != "")ids+=",";
				ids+=$(this).val();
			
			});
			
			var data = {};
			data.ids = ids;
			data.score = div.find(".dmscore").val() * 1;
			$.post("${base}/wenjuan/zxksgl!saveSzfs.action", data, function (){
				$("[name='zxks.id']:checked").each(function (){
					var tr = $(this).parent().parent();
					var className = ".scoreLabel";
					var label = tr.find(className);
					var hgcj = label.attr("hgcj") * 1;
					var score = data.score * 1;
					if(score > hgcj){
						label.css("color", "black");
					}else{
						label.css("color", "red");
					}
					label.html(data.score);
				});
				$("[name='zxks.idbox']:checked").click();
				$("[name='zxks.id']:checked").click();
			});
		}
	</script>
	<div id="dialog-modal" style="display: none">
		<div>
			设置分数：<input name="score" class="dmscore"/>
		</div>
	</div>
	[@b.row]
		[@b.boxcol/]
		[#if zyk_mode??]
		[@b.col width="20%" title="考试名称" property="ksap.name"/]
		[@b.col width="10%" title="学员编号" property="user.name"/]
		[@b.col width="10%" title="学员姓名"  property="user.fullname"][/@]
		[@b.col width="10%" title="班级"  property="user.adminClass.name"][/@]
		[@b.col width="10%" title="学号" property="user.bjxh"/]
		
		[@b.col width="25%" title="考试时间" ]
			${(zxks.wjjg.startTime)!}-${(zxks.wjjg.endTime?string("HH:mm"))!}
		
		[/@]
		
		[@b.col width="5%" title="分数" property="wjjg.score"  align="center" ][@wj.showScore wjjg=zxks.wjjg!/][/@]
		[@b.col width="10%" title="状态" property="sfwcdj"  align="center"]${zxks.finished?string("<font color='green'>已交卷</font>", "<font color='red'>未交卷</font>")}[/@]
		[#else]
		[@b.col width="50%" title="考试名称" property="ksap.name"/]
		[@b.col width="15%" title="编号" property="user.name"/]
		[@b.col width="15%" title="学员姓名"  property="user.fullname"][/@]
		[@b.col width="25%" title="考试时间" ]
			${(zxks.wjjg.startTime)!}-${(zxks.wjjg.endTime?string("HH:mm"))!}
		
		[/@]
		[@b.col width="10%" title="分数" property="wjjg.score"  align="center" ][@wj.showScore wjjg=zxks.wjjg!/][/@]
		[@b.col width="10%" title="状态" property="sfwcdj"  align="center"]${zxks.finished?string("<font color='green'>已交卷</font>", "<font color='red'>未交卷</font>")}[/@]
		[/#if]
	[/@]
[/@]
[@b.foot/]