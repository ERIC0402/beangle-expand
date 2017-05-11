[#ftl]
[#assign tmpx = 0/]

[#macro showWenJuan wenJuan tiMus=[] showda=false sortxx="rand"]
	[#if wenJuan.wjszs?size gt 0]
		<div class="ui-tabs ui-widget ui-widget-content ui-corner-all wjpreview">
			[#list wenJuan.wjszs as wjsz]
				[#if wjsz.tiMus??]
					[#list wjsz.tiMus as tm]
						[@wj.showTm tm=tm showda=showda sortxx=sortxx/]
						<input type="hidden" name="tmid" value="${tm.id}" />
						<input type="hidden" name="wjsz${tm.id}" value="${wjsz.id}" />
					[/#list]
				[/#if]
			[/#list]
		</div>
		[#if tiMus?size gt 0]
		<p class="tmlx">固定题目</p>
		[/#if]
	[/#if]
	[#if tiMus?size gt 0]
		<div class="ui-tabs ui-widget ui-widget-content ui-corner-all wjpreview">
			[#list tiMus as tm]
				[#if tm.sstm??][#assign ttm = tm.sstm/][#else][#assign ttm = tm/][/#if]
				[@wj.showTm tm=ttm showda=showda sortxx=sortxx/]
			[/#list]
		</div>
	[/#if]
[/#macro]

[#macro showTm tm showda=false sortxx="rand"]
	[#assign sortxx0][#if tm.xxsfgd??&&tm.xxsfgd]px[#else]${sortxx!}[/#if][/#assign]
	[#assign tmpx = tmpx+1/]
	<div id="tmdiv${tm.id}" class="tmdiv">
		<div style="font-size: 14px; font-weight: bold;">
			<label class="pxLabel" style="width: 21px; text-align: right; float: left; padding-right: 3px;">${tmpx}.</label>
			<div style="padding-left: 40px;">【${(tm.tmlx.name)!}】[#if tm.tmlx?? && tm.tmlx.code == "WJ_TI_MU_LX_TIAN_KON"][@showTianKon tm/][#else]${tm.tmmc!}[/#if]</div>
			<div style="clear: both;"></div>
		</div>
		<div style="padding-left: 40px;" class="xxdiv">
			[#if tm.tmnr??]<div>${tm.tmnr}</div>[/#if]
			[#if showzqda?? || zxks_lxms??]
			<label class="zqda">${tm.zqda!}</label>
			<label class="tmlxcode">${tm.tmlx.code!}</label>
			[/#if]
			
			[#if !(tm.tmlx??) || tm.tmlx.code == "WJ_TI_MU_LX_DAN_XUAN" || tm.tmlx.code == "WJ_TI_MU_LX_PAN_DUAN"]
				[@showRadio tm sortxx0/]
			[#elseif tm.tmlx.code == "WJ_TI_MU_LX_DUO_XUAN"]
				[@showCheckBox tm sortxx0/]
			[#elseif tm.tmlx.code == "WJ_TI_MU_LX_PAI_XU"]
				[@showPaiXu tm sortxx0/]
			[#elseif tm.tmlx.code == "WJ_TI_MU_LX_JIAN_DA"]
				[@showJianDa tm/]
			[#elseif tm.tmlx.code == "WJ_TI_MU_LX_WANG_GE"]
				[@showWangGe tm/]
			[/#if]
			[#nested/]
			[#if zxks_lxms?? && tm.tmlx.code != "WJ_TI_MU_LX_WANG_GE"]
			<div class="tm_info_div">
				<hr/>
				
				<input value="检查答案" onclick="dj.checkOneDa(this)" type="button" class="jcda_btn"/>
				<label class="djsfzq">&nbsp;</label>
				[#if showzqda??]
				<div class="zqdadiv">
				<hr />
					正确答案：<label class="zqdaLabel">${tm.zqda!}</label>
				</div>
				[/#if]
			</div>
			[/#if]
			
			[#if hasbqd??]
			<hr />
			<div class="bqddiv">
				<input class="bqd" type="checkbox" value="1" id="bqd${tmpx}" onclick="dj.checkProgress()"/><label for="bqd${tmpx}" style="font-style: italic; color: red; font-weight: bold;">如果您对这个题目没有把握，可以点击这里做个记号。</label>
			</div>
			[/#if]
		</div>
	</div>
[/#macro]

[#macro showRadio tm sortxx]
[@showRadioOrCheckBox tm=tm type="radio" sortxx=sortxx/]
[/#macro]

[#macro showCheckBox tm sortxx]
[@showRadioOrCheckBox tm=tm type="checkbox" sortxx=sortxx/]
[/#macro]

[#macro showRadioOrCheckBox tm type sortxx]
[#if tm.tmxxs??]
[#list tm.tmxxs?sort_by(sortxx) as xx]
[@showInputxx tm=tm xx=xx index=xx_index type=type/]
[/#list]
[/#if]
[/#macro]

[#macro showInputxx tm xx index type]
[#assign inputid = "xx${tm.id}${index}"/]
<li>
	<input type="${type}" name="xx${tm.id}" id="${inputid}" value="${xx.id}" px="${(xx.px)!}"/>
	<label for="${inputid}">[#if showzqda??]${(xx.px)!}.&nbsp;[/#if]${xx.xxnr}</label>
</li>
[/#macro]


[#macro showPaiXu tm]
<div style="float:left;">
[#list tm.tmxxs as xx]
[#assign inputid = "xx${tm.id}${xx_index}"/]
<li onclick="dj.addOrRemoveOption(this)">
	<input type="checkbox" id="${inputid}" value="${xx.px}" class="pxcheckbox${tm.id}"/>
	<label for="${inputid}">[#if showzqda??]${(xx.px)!}[/#if]${xx.xxnr}</label>
</li>
[/#list]
</div>
<select class="pxselect" multiple="multiple" style="float:left; width:150px; height:150px; margin-left:20px;"></select>
<div style="float:left; width:100px;">
	<input type="button" value="移到最前" onclick="dj.moveOption(this, 'first')"/>
	<input type="button" value="上移一位" onclick="dj.moveOption(this, 'up')"/>
	<input type="button" value="下移一位" onclick="dj.moveOption(this, 'down')"/>
	<input type="button" value="移到最后" onclick="dj.moveOption(this, 'last')"/>
</div>
<input type="hidden" name="xx${tm.id}" class="pxda"/>
<div style="clear: both"></div>
[/#macro]

[#macro showTianKon tm]
[#list tm.tmmc?split("（") as x]
${x}
[#if x_has_next]
（<input name="xx${tm.id}" style="width:80px;"/>
[/#if]
[/#list]
[/#macro]

[#macro showJianDa tm]
<li>
	<textarea name="xx${tm.id}" rows="5" cols="100"></textarea>
</li>
[/#macro]

[#macro showWangGe tm]
<li>
	<table class="ctable">
		<thead>
			<th></th>
			[#list tm.tmxxs?sort_by("px") as tmxx]
				<th>${(tmxx.xxnr)!}</th>
			[/#list]
		</thead>
	
		<tbody>
			[#list tm.wgztms?sort_by("px") as item]
				<tr>
					<td class="title">${(item.tmmc)!}</td>
					[#list tm.tmxxs?sort_by("px") as tmxx]
						<td align="center">
							<input type="radio" name="tmxx${(item.id)!}" value="${tmxx.id!}">
							<input type="hidden" name="tmxxpx${(item.id)!}${(tmxx.id)!}" value="${tmxx.px!}">
						</td>
					[/#list]
				</tr>
			[/#list]
		</tbody>
	</table>
</li>
[/#macro]

[#macro showScore wjjg]
	[#if wjjg.score??]
		<label [#if wjjg.score lt wjjg.wj.hgcj!]style="color:red"[/#if] class="scoreLabel">${wjjg.score!}</label>
	[/#if]
[/#macro]
