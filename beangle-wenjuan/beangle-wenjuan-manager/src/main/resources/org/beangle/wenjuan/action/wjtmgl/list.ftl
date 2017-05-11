[#ftl]
[@b.head/]
[@b.grid  items=wjtms var="wjtm" sortable="false"]
	[@b.gridbar]
		bar.addItem("从文本添加",action.method("wbtj"));
		bar.addItem("从题库添加",action.method("tktj"));
		//bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		[#if wenJuan.wjfl.code == "WEN_JUAN_FL_SJ"]
		bar.addItem("设置分数","sztmfs()");
		bar.addItem("返回试卷管理",action.goTo("index", "${base}/wenjuan/sjgl.action"),"${base}/static/themes/default/icons/16x16/actions/backward.png");
		[#else]
		bar.addItem("设置题目分数","sztmfs()");
		bar.addItem("设置选项分数","szxxfs()");
		bar.addItem("返回评教管理",action.goTo("index", "${base}/wenjuan/pjgl.action"),"${base}/static/themes/default/icons/16x16/actions/backward.png");
		[/#if]
	[/@]
	
	<input type="hidden" name="wenJuan.id" value="${wenJuan.id}"/>
	<div id="dialog-modal" style="display: none">
		<div>
			设置分数：<input name="score" class="dmscore"/>
		</div>
		<div class="xxfssmdiv">
			<div>选项分数用逗号隔开</div>
			<div>如：5,4,3,2,1</div>
		</div>
	</div>
	[@b.row]
		[@b.boxcol/]
		[@b.col width="5%" property="px" title="排序" class="wjtmpx"/]
		[@b.col width="30%" property="sstm.tmmc" title="题目"]
			${(wjtm.sstm.tmmc)!}
			[#if (wjtm.sstm.tmlx)?? && wjtm.sstm.tmlx.code == "WJ_TI_MU_LX_WANG_GE"]
				[#list wjtm.sstm.wgztms?sort_by("px") as v]
					<p>${v_index+1}.&nbsp;${v.tmmc!}</p>
				[/#list]
			[/#if]
		[/@]
		[#if wenJuan.wjfl.code == "WEN_JUAN_FL_KS"]
		[@b.col width="5%" property="score" title="分数" class="tmScoreTd"/]
		[@b.col width="5%" property="sstm.zqda" title="答案"]
			[@c.substring str=wjtm.sstm.zqda! mx=4/]
		[/@]
		[@b.col width="45%" property="wjzf" title="选项"]
			[#list wjtm.sstm.tmxxs?sort_by('px') as v]
				[#if v_index gt 0]，[/#if]${v.px}.${v.xxnr!}
			[/#list]
		[/@]
		[#else]
		[@b.col width="10%" property="score" title="子题目分数" class="tmScoreTd"/]
		[@b.col width="15%" property="xxScore" title="选项分数" class="xxScoreTd"/]
		[@b.col width="30%" property="wjzf" title="选项"]
			[#list wjtm.sstm.tmxxs?sort_by('px') as v]
				[#if v_index gt 0]，[/#if]${v.px}.${v.xxnr!}
			[/#list]
		[/@]
		[/#if]
		[@b.col width="10%" property="enabled" title="操作" align="center"]
			<a href="#" style="float:left; margin-left: 15px;[#if wjtm_index == 0]display: none;[/#if]" onclick="upLine(this)" class="upLinea">
				<img class="toolbar-icon" title="上移" src="${b.theme.iconurl('actions/go-up.png')}" alt="上移"/></a>
			<a href="#" style="float: right; margin-right: 15px;[#if wjtm.id == (wjtms?last).id]display: none;[/#if]" onclick="downLine(this)" class="downLinea">
				<img class="toolbar-icon" title="下移" src="${b.theme.iconurl('actions/go-down.png')}" alt="下移"/></a>
		[/@]
	[/@]
[/@]
<script type="text/javascript">
	
	function sztmfs(){
		szfs("tm");
	}
	
	function szxxfs(){
		szfs("xx");
	}
	
		function szfs(type){
			var ids = bg.input.getCheckBoxValues("wjtm.id");
			if(ids == ""){
				alert("请选择题目！");
				return;
			}
			var div = $("#dialog-modal");
			div.find("input").val("");
			if(type == "tm"){
				div.find(".xxfssmdiv").hide();
			}else{
				div.find(".xxfssmdiv").show();
			}
			div.dialog({
				height: 200,
				modal: true,
				buttons:{
					"确定":function (){
						var score = $(this).find(".dmscore").val();
						if(type == "tm"){
							if(!/^[+-]?(\d+(\.\d*)?|\.\d+)([Ee]\d+)?$/.test(score)){
								alert("请输入正确的分数");
								return;
							}
						}else{
							if(!/^(\d+\.?\d*[，,])*(\d+\.?\d*)$/.test(score)){
								alert("请输入正确的选项分数。\n如：5,4,3,2,1");
								return;
							}
						}
						saveSzfs($(this), type);
						$(this).dialog("close");
					},
					"取消":function (){
						$(this).dialog("close");
					}
				}
			});
		}
		
		function saveSzfs(div, type){
			var ids = bg.input.getCheckBoxValues("wjtm.id");
			var data = {};
			data.ids = ids;
			data.wenJuanId = "${wenJuan.id}";
			data.score = div.find(".dmscore").val();
			if(type == "tm"){
				data.score = data.score*1;
			}
			data.type = type;
			$.post("${base}/wenjuan/wjtmgl!saveSzfs.action", data, function (){
				$("[name='wjtm.id']:checked").each(function (){
					var tr = $(this).parent().parent();
					var className = ".tmScoreTd";
					if(type == "xx"){
						className = ".xxScoreTd";
					}
					tr.find(className).html(data.score);
				});
				$("[name='wjtm.idbox']:checked").click();
				$("[name='wjtm.id']:checked").click();
			});
		}
		
		function upLine(a){
			var tr = $(a).parent().parent();
			var prevtr = tr.prev("tr");
			changePx(tr, prevtr, function (){
				tr.insertBefore(prevtr);
			});
		}
		
		function downLine(a){
			var tr = $(a).parent().parent();
			var nexttr = tr.next("tr");
			changePx(tr, nexttr, function (){
				tr.insertAfter(nexttr);
			});
		}
		
		function changePx(tr1, tr2, onsuccess){
			var ids = getTmId(tr1) + "," + getTmId(tr2);
			$.post("${base}/wenjuan/wjtmgl!changePx.action", {ids:ids}, function (){
				onsuccess();
				resetUpDown(tr1);
				resetUpDown(tr2);
				var px = tr1.find(".wjtmpx").html();
				tr1.find(".wjtmpx").html(tr2.find(".wjtmpx").html());
				tr2.find(".wjtmpx").html(px);
			});
		}
		
		function getTmId(tr){
			var id = tr.find("[name='wjtm.id']").val();
			return id;
		}
		
		function resetUpDown(tr){
				tr.find(".upLinea").css("display", tr.prev("tr").length == 0 ? "none" : "");
				tr.find(".downLinea").css("display", tr.next("tr").length == 0 ? "none" : "");
		}
	</script>
[@b.foot/]