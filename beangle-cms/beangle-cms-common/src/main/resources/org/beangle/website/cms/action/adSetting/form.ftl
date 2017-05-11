[#ftl]
[#import "/template/list/utils.ftl" as lu/]
[@b.head/]
[@b.toolbar title="广告设置" entityId=adSetting.id!0]bar.addBack();[/@]
[@b.form action="!save" title="广告设置信息" theme="list"]
	[@b.textfield label="名称" name="adSetting.name" value="${adSetting.name!}" style="width:200px;" required="true" maxlength="100" /]
	[@b.select label="位置" items=positions value=(adSetting.position.id)! name="adSetting.position.id" empty="..."/]
	[@b.field label="内容"]
		[@lu.ckeditor id="adSettingContent" name="adSetting.content" value=adSetting.content!/]
	[/@]
	[@b.radios label="common.status"  name="adSetting.enabled" value=adSetting.enabled items="1:common.enabled,0:common.disabled"/]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="adSetting.id" value="${adSetting.id!}" />
	[/@]
[/@]
<script type="text/javascript">
</script>
[@b.foot/]