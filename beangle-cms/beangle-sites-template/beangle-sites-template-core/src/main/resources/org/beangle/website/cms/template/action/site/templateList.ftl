[#ftl]
[@b.head/]
<link href="${base}/static/site-template/css/template/template.css" rel="stylesheet" type="text/css">
[@b.grid  items=sites var="site"]
	[@b.gridbar]
		bar.addItem("全部发布",action.method('publishAll'));
		[#if !step??]bar.addItem("后退",action.method('index'));[/#if]
	[/@]
	<div class="templateBox">
		[#list templates as v]
			<div class="item">
				<div class="imgbox">
					<img width="150" height="160" class="autoSize" src="${base}/common/file.action?method=downFile&folder=${v.img!'empty.jpg'}"/>
				</div>
				<div>${v.name!}<font color="#aaa"></font></div>
				<div>[@b.a href="site!configTemplate?templateId=${v.id}&siteId=${site.id}" target="_blank"]编辑[/@]</div>
			</div>
		[/#list]
	</div>
[/@]
[@b.foot/]