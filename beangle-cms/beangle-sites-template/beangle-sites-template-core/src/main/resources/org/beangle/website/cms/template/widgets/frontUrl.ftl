<#-- 分页链接的前缀 -->
<#macro columnPerfix c>${base}/${(site.basePath)!}/list/${c.id}-</#macro>

<#-- 栏目的链接：信息、单页、自定义模板、自定义链接 -->
<#--
<#if c.name == '首页'>
	${base}/${(site.basePath)!}/index.htm
<#else>
	<#if c.type.id == 18>
		${c.url!'#'}
	<#else>
		${base}
		<#if c.access?? && c.access.id == 23>/user</#if>
		/${(site.basePath)!}/
		list/${c.id?c}-${pageNo}-${pageSize}.htm
	</#if>
</#if>
 -->
<#macro column c istop=false pageNo=1 pageSize=20><#if (c.name!'') == '首页'>${base}/${(site.basePath)!}/index.htm<#else><#if c.type.id == 18>${c.url!'#'}<#else>${base}<#if c.access?? && c.access.id == 23>/user</#if>/${(site.basePath)!}/list/${c.id?c}-${pageNo}-${pageSize}.htm</#if></#if></#macro>

<#macro index site>${base}/${site.basePath!}/index.htm</#macro>
<#macro content cc><#if cc.content.contentStyle.id==1>${(cc.content.url)!'#'}<#else>${base}<#if (cc.column.access?? && cc.column.access.id == 23) || (cc.content.access?? && cc.content.access.id == 23)>/user</#if>/${cc.column.site.basePath!}/info/${cc.id}.htm</#if></#macro>
<#macro module c tid>${base}/${c.site.basePath!}/module/${c.id}-${tid}-${(page.pageNo)!1}-${(page.pageSize)!20}.htm</#macro>
<#macro detail c v>${base}/${c.site.basePath!}/detail/${c.id}_${v.id}.htm</#macro>

<#macro getTitleImage cc><#if cc.content.titleImage??>${base}/contentImg/${(cc.content.titleImage.id)!}.htm<#else>${base}/static/img/nopic.gif</#if></#macro>

<#macro searchPerfix>${base}/${(site.basePath)!}/search-</#macro>