[#ftl]
[@b.head/]
[@b.tabs]
	[@b.tab label="发布"]
		[@b.form action="!save" theme="list"]
			[#include "../contentEditForm.ftl"/]
			[@b.formfoot]
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;
				<input type="button" value="${b.text("action.submit")}" onClick="check(this.form);"/>
				&nbsp;&nbsp;
				<input type="button" value="发布" onClick="sub(this.form);"/>
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
    	form.action= action + "!savePublish.action?publish.state=1";
    	check(form,"发布后网站上将会看到该信息，您是否确定要发布？");
    }
  </script>
[@b.foot/]