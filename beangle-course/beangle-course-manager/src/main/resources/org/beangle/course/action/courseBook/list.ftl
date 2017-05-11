[#ftl]
[@b.head/]
[@b.toolbar title="课程" entityId=course.id!0]bar.addBack();[/@]
[#include "../course/nav.ftl"/]
[@b.grid  items=courseBooks var="courseBook"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="33%" property="name" title="教材名称" /]
		[@b.col width="33%" property="author" title="作者" /]
		[@b.col width="33%" property="publishingHouse" title="出版社" /]
	[/@]
[/@]
[@b.foot/]