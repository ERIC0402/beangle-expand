[#ftl]
[@b.head/]
[#include "../comm.ftl"/]
[@b.grid  items=linksTypes var="linksType" sortable="true"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("链接管理",action.openNew("wscan","${base}/cms/link.action"),"${base}/static/themes/default/icons/16x16/actions/go-next.png");
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="25%" property="typeName" title="类型名称" /]
		[@b.col width="25%" property="typeCode" title="类型代码" /]
		[@b.col width="25%" property="site.name" title="所属站点" /]
		[@b.col width="25%" title="状态" property="enabled" align="center"]
			[#if linksType.enabled]启用[#else]禁用[/#if]
		[/@]
	[/@]
[/@]
[@b.foot/]