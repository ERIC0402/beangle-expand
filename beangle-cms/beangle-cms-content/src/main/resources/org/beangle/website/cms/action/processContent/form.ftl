[#ftl]
[@b.head/]
<script>
	var isRedirect = ${isRedirect!(0)};
	if(isRedirect==1){
		alert("保存成功！");
		parent.close2();
	}
</script>
[@b.tabs]
	[@b.tab label="内容信息-${column.name!}【${(column.site.name)!}】"]
		[@b.form action="!save" theme="list"]
			[#include "../contentEditForm.ftl"/]
			[@b.formfoot]
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;
				<input type="button" value="${b.text("action.submit")}" onClick="check(this.form);"/>
			[/@]
		[/@]
	[/@]
[/@]
[@b.foot/]