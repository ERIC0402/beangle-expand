[#ftl]
[#include "/pages/beangle/security/status.ftl"/]
[@b.head/]
<link href="${base}/static/site-template/css/template/template.css" rel="stylesheet" type="text/css">

[@b.grid  items=templateLayouts var="templateLayout"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
	[/@]	
	<div class="titleBox">
		[#list templateLayouts as templateLayout]
			<div class="item">
				<div class="imgbox">
					<img width="190" height="160" class="autoSize" src="${base}/common/file.action?method=downFile&folder=${templateLayout.img!}"/>
				</div>
				<div>${templateLayout.name!}<font color="#aaa"></font></div>
				<div>([@enableInfo templateLayout.enabled/])[@b.a href="template-layout!edit?templateLayout.id=${templateLayout.id}"]编辑[/@]</div>
			</div>
		[/#list]
	</div>
[/@]
[@b.foot/]