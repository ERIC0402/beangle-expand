[#ftl]
[@b.head/]
[@b.toolbar title="链接类型管理"]bar.addBack();[/@]
		[@b.form action="!save" title="链接类型信息" theme="list"]
			[@b.textfield name="linksType.typeName" label="类型名称" value="${linksType.typeName!}" required="true" maxlength="30" comment="长度不超过30个字符"/]
			[@b.textfield name="linksType.typeCode" label="类型代码" value="${linksType.typeCode!}" maxlength="30" comment="长度不超过30个字符"/]
			[@b.select name="linksType.site.id" label="所属站点" value=(linksType.site.id)! required="true" empty="请选择..." items=sites option="id,name"/]
			[@b.radios label="是否启用"  name="linksType.enabled" value=linksType.enabled items="1:启用,0:禁用"/]
			
			[@b.formfoot]
				<input type="hidden" name="linksType.id" value="${linksType.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;
				[@b.submit value="action.submit"/]
			[/@]
		[/@]
[@b.foot/]