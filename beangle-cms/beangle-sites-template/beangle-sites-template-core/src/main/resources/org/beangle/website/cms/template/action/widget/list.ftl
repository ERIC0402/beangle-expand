[#ftl]
[#include "/pages/beangle/security/status.ftl"/]
[#import "/org/beangle/website/cms/template/action/widget.ftl" as w/]
[@b.head/]
<link href="${base}/static/site-template/css/template/template.css" rel="stylesheet" type="text/css">
[@b.grid  items=widgets var="widget"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
	[/@]	
	<div class="titleBox">
		[#list widgets as widget]
			<div class="item">
				<div class="imgbox">
					[@w.img widget=widget width=190 height=160/]
				</div>
				<div>${widget.name!}</div>
				<div><font color="#aaa">${widget.type.name!}</font></div>
				<div>([@enableInfo widget.enabled/])[@b.a href="widget!edit?widget.id=${widget.id}"]编辑[/@]</div>
			</div>
		[/#list]
	</div>
[/@]
[@b.foot/]