[#ftl]
[#import "/template/list/comm.ftl" as lc/]
[@b.head]
[/@]
<script src="${base}/static/scripts/uploadify/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/> 

<script src="${base}/static/scripts/comm/DictTreeUtil.js"></script>
<script src="${base}/static/scripts/wenjuan/WenBenTiMu.js"></script>
<style type="text/css">
	.preview p{margin: 0; padding: 0;}
</style>
[@b.toolbar title=c.formTitle("题目", tiMu)]bar.addBack();[/@]
		[@b.form action="!save" theme="list"]
				[#include "/org/beangle/wenjuan/action/tiMu/tiMuFields.ftl"]
				[@lc.group]
					[@b.formfoot]
					<input type="hidden" name="tiMu.pic" value="${tiMu.pic!}"/>
					<input type="hidden" name="oldTMPIC" value="${tiMu.pic!}"/>
					<input type="hidden" name="tiMu.id" value="${tiMu.id!}" />
					[@b.redirectParams/]
					<input type="button" value="保存" onClick="submit2(this.form);"/>
					[/@]
				[/@lc.group]
		[/@]
<script type="text/javascript">
	function submit2(form){
		bg.ui.load("validity2");
	    var a_fields = {};
	    a_fields["#tmmc"] = new Validator2Str().require().getConfig();
	    a_fields["#tmlxId"] = new Validator2Str().require().getConfig();
	    a_fields["#tkIdform"] = new Validator2Str().require().getConfig();
	    a_fields["#tmflform"] = new Validator2Str().require().getConfig();
		var tmlx = $("#tmlxId").find("option:selected").text();
		var tmlxId = $("#sjtm").find("option:selected").attr("tmlxId");
		if(tmlxId != "网格题"){
			if(tmlx!="普通节点"){
				if(tmlx!="填空题" && tmlx!="简答题"){
			        //$("#xxplfsId").require();
	    			a_fields["#xxnr"] = new Validator2Str().require().getConfig();
		        }
		        if(tmlx=="网格题"){
	    			a_fields["#wgztmnr"] = new Validator2Str().require().getConfig();
		        }
		        //$("#zqda").require();
			}
		}
		var vform = new validator2(a_fields, "#" + form.id);
		res =vform.exec();
	    if(false == res) {
	        return false;
	    }
	        
		bg.form.submit(form,form.action,form.target,null,true);
	}
</script>
[@b.foot/]
