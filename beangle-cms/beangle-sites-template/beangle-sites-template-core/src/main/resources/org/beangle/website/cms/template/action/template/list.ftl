[#ftl]
[#include "/pages/beangle/security/status.ftl"/]
[@b.head/]
<link href="${base}/static/site-template/css/template/template.css" rel="stylesheet" type="text/css">

[@b.grid  items=templates var="template"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		bar.addItem("设计", "openOne('${b.url('template!editLayout')}')");
		bar.addItem("预览","openOne('${base}/cms/template/template!viewContent.action')");
		
		function openOne(url){
			var form=action.getForm();
			var isMulti = false;
			var selectId = bg.input.getCheckBoxValues("template.id");
			if(""==selectId){
				alert(isMulti?"请选择一个或多个进行操作":"请选择一个进行操作");
				return;
			}
			if(!isMulti && (selectId.indexOf(",")>0)){
				alert("请仅选择一个");
				return;
			}
			window.open(url + "?template.id=" + selectId);
		}
		
	[/@]	
	[@b.row]
		[@b.boxcol/]
		[@b.col width="20%" property="name" title="名称" /]
		[@b.col width="20%" property="type.name" title="类型" /]
		[@b.col width="30%" property="remark" title="备注" /]
		[@b.col width="20%" property="group.name" title="模板组" /]
		[@b.col width="10%" property="enabled" title="状态"][@enableInfo template.enabled/][/@]
	[/@]
[/@]
[@b.foot/]