[#ftl]
[#include "/org/beangle/website/cms/template/action/comm.ftl"/]
<style>
	.templateUl li {
		float: left;
		background: none !important;
	}
	li.ui-selected {
		background: #F39814!important;
		color: white;
	}
</style>
[@b.tab label="选择模板组"]
[@b.form action="${actionUrl!'!save'}" theme="list"]
<fieldset>
	<legend>
		选择模板组
	</legend>
	<ol>
		<div id="templateGroupTd">
			<ul class="templateGroupUl templateUl">
				[#list templateGroups as v]
				<li templateGroupId="${v.id}" style="text-align: center; width: 100px; margin: 5px;padding:10px;list-style-type: none;">
					<div>
						<div style="width: 100px; height: 80px;">
							<img class="autoSize" src="[@imgUrl url=v.img!/]" width="100" height="80"/>
						</div>
						<div>
							${v.name!}
						</div>
					</div>
				</li>
				[/#list]
			</ul>
			<div style="clear: both"></div>
		</div>
	</ol>
</fieldset>
<fieldset>
	<legend>
		选择首页默认模板
	</legend>
	<ol id="indexTemplateTd">
		<ul class="indexTemplateUl templateUl"></ul>
	</ol>
</fieldset>
<fieldset>
	<legend>
		选择列表默认模板
	</legend>
	<ol id="listTemplateTd">
		<ul class="listTemplateUl templateUl"></ul>
	</ol>
</fieldset>
<fieldset>
	<legend>
		选择详细页面默认模板
	</legend>
	<ol id="detailTemplateTd">
		<ul class="detailTemplateUl templateUl"></ul>
	</ol>
</fieldset>
<fieldset>
	<legend>
		搜索页面模板
	</legend>
	<ol id="searchTemplateTd">
		<ul class="searchTemplateUl templateUl"></ul>
	</ol>
</fieldset>
[@b.formfoot]
<input type="hidden" name="site.id" value="${site.id!}" />
<input type="hidden" name="site.templateGroup.id" value="${(site.templateGroup.id)!}" />
<input type="hidden" name="site.indexTemplate.id" value="${(site.indexTemplate.id)!}" />
<input type="hidden" name="site.listTemplate.id" value="${(site.listTemplate.id)!}" />
<input type="hidden" name="site.detailTemplate.id" value="${(site.detailTemplate.id)!}" />
<input type="hidden" name="site.searchTemplate.id" value="${(site.searchTemplate.id)!}" />
[@b.redirectParams/]
[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
[/@]
<div class="clone" style="display:none">
	<li templateId="" style="text-align: center; width: 100px; float: left; margin: 5px;padding:5px;list-style-type: none;">
		<div>
			<div style="width: 100px; height: 80px;">
				<img class="autoSize" src="" width="100%" height="100%"/>
			</div>
			<div class="name" style="padding: 5px;"></div>
		</div>
	</li>
</div>
[/@]
[/@]
<script language="javascript" >
	$(function() {
		$(".templateGroupUl").selectable({
			selected : function(event, ui) {
				var tgid = $(ui.selected).attr("templateGroupId");
				$name("site.templateGroup.id").val(tgid);
				initTemplates(tgid);
			}
		});
		$("li[templateGroupId='${(site.templateGroup.id)!}']").addClass("ui-selected");
		initTemplates('${(site.templateGroup.id)!}', true);
		initSelectable(".indexTemplateUl", "site.indexTemplate.id")
		initSelectable(".listTemplateUl", "site.listTemplate.id")
		initSelectable(".detailTemplateUl", "site.detailTemplate.id")
		initSelectable(".searchTemplateUl", "site.searchTemplate.id")
	});
	function initSelectable(ul, name) {
		$(ul).selectable({
			selected : function(event, ui) {
				var tid = $(ui.selected).attr("templateId");
				$name(name).val(tid);
				vDoCheck(name);
			}
		});
	}

	function initTemplates(tgid, isInit) {
		if(tgid == '') {
			return;
		}
		var dialog = c.onprogress();
		var url = "${base}/cms/template/site!findTemplate.action?templateGroupId=" + tgid;
		url += "&n=" + new Date().getTime();
		$.getJSON(url, function(data) {
			$(".indexTemplateUl, .listTemplateUl, .detailTemplateUl, .searchTemplateUl").empty();
			if(!isInit) {
				$name("site.indexTemplate.id").val("");
				$name("site.listTemplate.id").val("");
				$name("site.detailTemplate.id").val("");
				$name("site.searchTemplate.id").val("");
			}
			for(var i in data) {
				i = data[i];
				var config = {
					"261" : {
						"ul" : ".indexTemplateUl",
						"name" : "site.indexTemplate.id"
					},
					"262" : {
						"ul" : ".listTemplateUl",
						"name" : "site.listTemplate.id"
					},
					"263" : {
						"ul" : ".detailTemplateUl",
						"name" : "site.detailTemplate.id"
					},
					"321" : {
						"ul" : ".searchTemplateUl",
						"name" : "site.searchTemplate.id"
					}
				};
				if(!config[i.typeId]) {
					continue;
				}
				var ul = config[i.typeId].ul;
				var name = config[i.typeId].name;
				var c = $(".clone li").clone();
				if(i.id == $name(name).val()) {
					c.addClass("ui-selected");
				}
				c.attr("templateId", i.id);
				c.find("img").attr("src", i.img);
				c.find(".name").html(i.name);
				$(ul).append(c);
			}
			dialog.dialog("close");
		});
	}
</script>