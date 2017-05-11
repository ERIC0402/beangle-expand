[#ftl]
[#import "/org/beangle/website/cms/template/action/widget.ftl" as w/]
[@b.head/]
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="${base}/static/scripts/uploadify/swfobject.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/upload.js"></script>
[@b.toolbar title="组件"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="组件基本信息"]
		[@b.form action="!save" title="组件" theme="list"]
			[@b.textfield name="widget.name" label="common.name" value="${widget.name!}" required="true" maxlength="100" size=60/]
			[@b.select label="类型" name="widget.type.id"  value=(widget.type.id)!0  items=types/]
			[@b.textfield name="widget.className" label="类名" value="${widget.className!}" required="true" maxlength="100" size=80/]
			[@b.field label="预览图" ]
			 	[@w.img widget=widget width=190 height=160/]
			[/@]
			[@b.select2 label="关联模板组" name1st="groups"  name2nd="groupIds" items1st=groups items2nd=widget.groups/]
			[@b.radios label="common.status"  name="widget.enabled" value=widget.enabled items="1:激活,0:冻结"/]
			[@b.formfoot]
				<input type="hidden" name="widget.id" value="${widget.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
			[/@]
		[/@]
	[/@]
[/@]
[@b.foot/]