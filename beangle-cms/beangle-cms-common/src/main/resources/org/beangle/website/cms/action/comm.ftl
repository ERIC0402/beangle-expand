[#ftl]
[#assign scopes={'1':'公开','0':'私有'}/]
[#assign scopeNames={'1':'public','0':'private'}/]
[#macro resourceScope scope]
	<img style="padding-bottom:5px;vertical-align:middle;" src="${b.theme.iconurl('status/'+ scopeNames[scope?string] + '.png')}"/>${scopes[scope?string]}
[/#macro]

[#macro downLoadFile filePath fileName]
	<a href="${base}/common/file.action?method=downFile&folder=${filePath!}&fileName=${fileName?url('utf-8')}" target="_blank">
		<img style="padding-bottom:5px;vertical-align:middle;" title="点击下载" src="${b.theme.iconurl('actions/download.png')}"/>
	</a>
[/#macro]
