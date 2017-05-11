[#ftl]
[@b.head][/@]
<script type="text/javascript" src="${base}/static/scripts/wenjuan/wjtm.js"></script>
[@b.form title="编辑题目" action="!saveWjtm" theme="form"]
	[#--
	[@b.select label="题目类型" name="timu.tmlx.id" value=(timu.tmlx.id)!  required="true" option="id,name" empty="请选择..."]
		[#list tmlxs as v]
		<option value="${v.id}" code="${v.code}"[#if v.id==((timu.tmlx.id)!0)]selected="selected"[/#if]>${v.name}</option>
		[/#list]
	[/@]
	--]
	<input name="timu.tmlx.id" value="${(timu.tmlx.id)}" type="hidden"/>
	[@b.textfield label="题目名称" name="timu.tmmc" value=timu.tmmc required="true" maxlength="100" style="width:370px;"/]
	[@b.textarea label="题目内容" name="timu.tmnr" value=(timu.tmnr)!/]
	[#if timu.hasxx]
		[@b.field label="题目选项" class="xxField"]
			<table id="xxtable" width="100%" border="1" cellpadding="0" cellspacing="0">
				<thead>
					<th>选项内容</th>
					<th style="width: 220px;">操作</th>
				</thead>
				<tbody>
					[#list timu.tmxxs?sort_by("px") as xx]
						[@xxtr xx=xx i=xx_index/]
					[/#list]
					[#if timu.tmxxs?size lt 2]
						[#list timu.tmxxs?size..1 as v]
							[@xxtr i=v/]
						[/#list]
					[/#if]
				</tbody>
			</table>
			<textarea style="display: none" class="templatexxtr">
				[#macro xxtr xx={} i="{i}"]
				<tr class="xxtr xxtr${i}">
					<td>
						<input name="xxxh" value="${i}" type="hidden" class="xxxh"/>
						<input name="xx${i}.id" value="${xx.id!}" type="hidden"/>
						<input name="xx${i}.px" value="${i}" type="hidden" class="xxpx"/>
						<input name="xx${i}.xxnr" value="${xx.xxnr!}" class="xxnr" style="width: 300px;"/>
					</td>
					<td align="center">
						<input type="button" value="上移" onclick="wjtm.upxx($('.xxtr${i}'));"/>
						<input type="button" value="下移" onclick="wjtm.downxx($('.xxtr${i}'));"/>
						<input type="button" value="删除" onclick="wjtm.delxx($('.xxtr${i}'));"/>
						<input type="button" value="添加" onclick="wjtm.addxx($('.xxtr${i}'));"/>
					</td>
				</tr>
				[/#macro]
				[@xxtr i='{i}'/]
			</textarea>
		[/@]
	[#elseif ((timu.tmlx.code)!'') == "WJ_TI_MU_LX_PAI_XU"]
		
	[/#if]
	[#--
	[@b.field label="关联题目选项" comment="当前面题目选中某些选项时才出现此题"]
		[@c.select data=timus value=(wjtm.gltmxx.id)!0 id="timuSelect" nameKey="tmmc"/][@c.select name="wjtm.gltmxx.id"/]
	[/@]
	--]
	[@b.field label="题目设置"]
		[@fc.switch label="是否必填" name="wjtm.bdt" value=wjtm.bdt!false/]
	[/@]
	[@b.formfoot]
		<input type="hidden" name="wjtm.id" value="${(wjtm.id)!}"/>
		<input type="hidden" name="wjtm.sswj.id" value="${(wjtm.sswj.id)}"/>
		<input type="hidden" name="timu.id" value="${(timu.id)!}"/>
		[@b.redirectParams/]
		[@b.submit value="action.submit" onsubmit="validateEditWjtmForm"/]
	[/@]
[/@b.form]
<script type="text/javascript">
	function validateEditWjtmForm(){
		if(bg.ui.form.empty($(".xxtr .xxnr"))){
			alert("选项内容不能为空！");
			return false;
		}
	}
	$(function (){
		$("#timuSelect").change(function (){
			var xxs = new Array();
//			[#list timus as tm]
//				[#list tm.tmxxs as xx]
				xxs.push({id : "${xx.id}", xxnr : "${xx.xxnr}", tmid:"${xx.sstm.id}"});
//				[/#list]
//			[/#list]
			var xxselect = $name("wjtm.gltmxx.id");
			xxselect.find("option[value!='']").remove();
			for(var i in xxs){
				var xx = xxs[i];
				if(xx.tmid == this.value){
					xxselect.append($("<option>").val(xx.id).html(xx.xxnr));
				}
			}
			xxselect.val("");
		}).change();
		$name("wjtm.gltmxx.id").val("${(wjtm.gltmxx.id)!}");
	});
	var wjtm = new WenJuanTiMu();
</script>
[@b.foot nofooter=1/]
