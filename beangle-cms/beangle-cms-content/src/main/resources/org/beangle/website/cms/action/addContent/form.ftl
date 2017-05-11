[#ftl]
[@b.head/]
<script>
	var isAdd = ${isAdd!(0)};
	var isRedirect = ${isRedirect!(0)};
	if(isAdd==1 && isRedirect==1){
		if(!confirm("保存成功，您是否要继续起草信息？")){
			parent.close2();
		}else{
			parent.refresh();
		}
	}else if(isAdd==0 && isRedirect==1){
		alert("保存成功！");
		parent.close2();
	}
</script>
[@b.tabs]
	[@b.tab label="内容信息-${column.name!}【${(column.site.name)!}】"]
		[@b.form action="!save" theme="list"]
			<input type="hidden" name="isAdd" id="isAdd" value="${isAdd!}"  />
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