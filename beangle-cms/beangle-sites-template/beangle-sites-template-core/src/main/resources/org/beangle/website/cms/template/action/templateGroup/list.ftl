[#ftl]
[#include "/pages/beangle/security/status.ftl"/]
[@b.head/]
<link href="${base}/static/site-template/css/template/template.css" rel="stylesheet" type="text/css">
[@b.grid  items=templateGroups var="templateGroup"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
	[/@]
	<div class="titleBox">
		[#list templateGroups as templateGroup]
			<div class="item">
				<div class="imgbox">
					<img width="190" height="160" class="autoSize" src="${base}/common/file.action?method=downFile&folder=${templateGroup.img!}"/>
				</div>
				<div>${templateGroup.name!}<font color="#aaa"></font></div>
				<div>([@enableInfo templateGroup.enabled/])[@b.a href="template-group!edit?templateGroup.id=${templateGroup.id}"]编辑[/@]</div>
			</div>
		[/#list]
		<div style="clear:both"></div>
	</div>
[/@]
[@b.foot/]