[#ftl]
[#import "/org/beangle/wenjuan/action/zuJuan/WenJuan.ftl" as wj/]
[#list wjtms as wjtm]
<div class="tmEditorDiv tmEditorDiv${wjtm.id}">
	<div class="btnBar">
		<input type="hidden" name="wjtm.id" value="${wjtm.id}" />
		<input type="button" value="上移" onclick="dcwj.up('${wjtm.id}')"/>
		<input type="button" value="下移" onclick="dcwj.down('${wjtm.id}')"/>
		<input type="button" value="编辑" onclick="dcwj.editWjtm('${wjtm.id}')"/>
		<input type="button" value="删除" onclick="dcwj.delWjtm('${wjtm.id}', this)"/>
	</div>
[@wj.showTm tm=wjtm.sstm/]
</div>
[/#list]