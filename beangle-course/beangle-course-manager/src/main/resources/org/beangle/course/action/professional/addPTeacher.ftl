[#ftl]
[@b.head/]
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/ajax-chosen.js"></script>

[@b.toolbar title="专业教师"]bar.addBack();[/@]
[@b.form action="!pTeacherSave" title="专业信息" theme="list"]
	[@b.field label="专业教师"]
        <select data-placeholder="选择专业教师"  id="zyjs"  name="zyjsId" multiple="true" style="width:400px;">
			<option value=""></option>
			[#list faculties as item]
				<option value="${(item.id)!}">[${(item.name)!}]${(item.fullname)!}</option>
			[/#list]
		</select>
	[/@]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="professional.id" value="${(professional.id)!}" />
	[/@]
[/@]
<script language="JavaScript">
jQuery(document).ready(function(){
	jQuery("#zyjs").ajaxChosen(
		{
			method: 'post',
			url : "${b.url('!searchFaculty')}?pageNo=1&pageSize=20"
		}
		, function (data) {
			var items = {};
			var dataObj = eval("(" + data + ")");
			jQuery.each(dataObj.faculties, function (i, faculty) {
				items[faculty.id] = faculty.name;
			});
			return items;
		}
	);
})
</script>
[@b.foot/]