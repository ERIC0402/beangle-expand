<#macro imgUrl url>${base}/common/file.action?method=downFile&folder=${url!}</#macro>

<#macro getMessage><@s.actionmessage theme="beangle"/><@s.actionerror theme="beangle"/></#macro>