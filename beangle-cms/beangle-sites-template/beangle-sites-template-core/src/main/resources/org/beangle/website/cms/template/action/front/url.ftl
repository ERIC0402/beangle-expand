<#-- 分页链接的前缀 -->
<#macro columnPerfix c>${base}/${(site.basePath)!}/list/${c.id}-</#macro>

<#-- 栏目的链接：信息、单页、自定义模板、自定义链接 -->
<#macro column c istop=false pageNo=1 pageSize=20><#if c.name??><#if c.name == '首页'>${base}/${(site.basePath)!}/index.htm<#return></#if><#if c.type.id == 18>${c.url!'#'}<#else>${base}/${(site.basePath)!}/<#if c.type.id != 18>list/${c.id?c}-${pageNo}-${pageSize}.htm<#else>${c.entry!}</#if></#if><#else>#</#if></#macro>

<#macro index site>${base}/${site.basePath!}/index.htm</#macro>
<#macro content cc><#if cc.content.contentStyle.id==1>${(cc.content.url)!'#'}<#else>${base}/${cc.columns.site.basePath!}/info/${cc.id}.htm</#if></#macro>
<#macro detail c v>${base}/${c.site.basePath!}/detail/${c.id}_${v.id}.htm</#macro>

<#macro getTitleImage cc><#if cc.content.titleImage??>${base}/contentImg/${(cc.content.titleImage.id)!}.htm<#else>${base}/img/nopic.gif</#if></#macro>

<#macro searchPerfix>${base}/${(site.basePath)!}/search-</#macro>