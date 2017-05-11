[#ftl]
[@b.head/]
<link href="${base}/static/themes/${b.theme.ui}/homePage.css" rel="stylesheet" type="text/css" />
[@b.tabs]
	[@b.tab label="审核"]
		[@b.form action="!save" theme="list"]
			[#include "../audit.ftl"/]
      		<div class="button_box">
	      		<label class="title" for="abstracts">审核意见：</label>
					<textarea id="abstracts" title="审核意见" cols="50" rows="3" name="abstracts"></textarea>
				<label class="comment">长度不超过200字符</label>
	      	</div>
	      	<div class="button_box">
				<input type="button" value="通过" onClick="save(this.form);"/>
				<input type="button" value="不通过" onClick="notPass(this.form);"/>
				<input type="button" value="退回" onClick="back(this.form);"/>
				<input type="button" value="修改" onClick="edit(this.form);"/>
			</div>
		[/@]
	[/@]
[/@]
<script>
	var action = "${base}/cms/process-content!saveAudit.action";
    function save(form){
    	if(!confirm("您是否确定要审核通过该信息？")){
    		return false;
    	}
    	$(":button").each(function(){this.disabled=true});
    	form.action = action + "?audit.state=1";
        form.submit();
    }
    function back(form){
    	if($("#abstracts").val()==""){
    		alert("退回请填写审核意见！");
    		return;
    	}
    	if(!confirm("您是否确定要退回该信息？")){
    		return false;
    	}
    	$(":button").each(function(){this.disabled=true});
    	form.action = action + "?audit.state=0";
        form.submit();
    }
    function notPass(form){
    	if($("#abstracts").val()==""){
    		alert("不通过请填写审核意见！");
    		return;
    	}
    	if(!confirm("您是否确定要审核不通过该信息？")){
    		return false;
    	}
    	$(":button").each(function(){this.disabled=true});
    	form.action = action + "?audit.state=2";
        form.submit();
    }
    function edit(form){
   		$(":button").each(function(){this.disabled=true});
    	form.action = "${base}/cms/process-content!auditAndEditContent.action";
        bg.form.submit(form,form.action,form.target,null,true);
    }
  </script>
[@b.foot/]