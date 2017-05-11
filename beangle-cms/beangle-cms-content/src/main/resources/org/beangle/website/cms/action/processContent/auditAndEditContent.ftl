[#ftl]
[@b.head/]
[@b.toolbar title=""]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="审核"]
		[@b.form action="!save" theme="list"]
			[#include "../contentEditForm.ftl"/]
			[@b.formfoot]
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;
				<input type="button" value="${b.text("action.submit")}" onClick="check(this.form);"/>
				&nbsp;&nbsp;
				<input type="button" value="通过" onClick="sub(this.form);"/>
			[/@]
		[/@]
	[/@]
[/@]
<script>
	function sub(form){
		var action = form.action;
		var last1=action.lastIndexOf("!");
		if(-1!=last1){
			action=action.substring(0,last1);
		}
    	form.action= action + "!saveAudit.action?audit.state=1";
    	check(form,"您是否确定要审核通过该信息？");
    }
  </script>
[@b.foot/]