<#macro pageBar perfix="" suffix="">
<#if page??>
<style type="text/css">
    .turn_page a{padding: 3px 5px;}
</style>
<div class="turn_page">
    <table align="right">
        <tr>
            <td>
                <#if page.pageNo != 1>
                <a href="${perfix}1-${page.pageSize}${suffix}" class="pageBtn ip_2">首页</a>
                <a href="${perfix}${page.previousPageNo}-${page.pageSize}${suffix}" class="pageBtn ip_2">上一页</a>
                <#else>
                <font color="#777777">首页&nbsp;&nbsp;上一页</font>
                </#if>
                <#list (page.pageNo-3)..(page.pageNo+3) as v>
                <#if v gt 0 && v lte page.maxPageNo>
                <#if v == page.pageNo>
                <font color="#777777">${v}</font>
                <#else>
                <a href="${perfix}${v}-${page.pageSize}${suffix}" class="pageBtn ip_2">${v}</a>
                </#if>
                </#if>
                </#list>
                <#if page.pageNo != page.maxPageNo>
                <a href="${perfix}${page.nextPageNo}-${page.pageSize}${suffix}" class="pageBtn ip_2">下一页</a>
                <a href="${perfix}${page.maxPageNo}-${page.pageSize}${suffix}" class="pageBtn ip_2">末页</a>
                <#else>
                <font color="#777777">&nbsp;下一页&nbsp;&nbsp;末页</font>
                </#if>
                <font color="#777777">${page.pageNo}&nbsp;/&nbsp;${page.maxPageNo}&nbsp;页</font>
            </td>
            <td> 到 </td>
            <td><input id="pageNo" type="text" class="ip_3" onkeyup="if(event.keyCode == 13){gotoPage()}">
            </td>
            <td> 页 </td>
            <td><input onclick="gotoPage()" type="button" class="ip_2" value="跳 转">
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    function gotoPage(){
        var pageNo = $("#pageNo").val();
        if(!/\d+/.test(pageNo)){
            alert("请输入一个整数！");
            return;
        }
        var maxNo = '${page.maxPageNo}' * 1;
        if(pageNo <= 0 || pageNo > maxNo){
            alert("请输入 ${page.maxPageNo} 以内的整数");
            return;
        }
        window.location.href = "${perfix}"+pageNo+"-${page.pageSize}${suffix}";
    }
</script>
</#if>
</#macro>

<#macro columnPageBar c>
<#assign perfix><@url.columnPerfix c=c!/></#assign>
<#assign suffix>.htm</#assign>
<@wv.pageBar perfix=perfix suffix=suffix/>
</#macro>

<#macro pageBarEn perfix="" suffix="">
<#if page??>
<style>
    .turn_page a{padding: 3px 5px;}
</style>
<div class="turn_page">
    <table align="right">
        <tr>
            <td>
                <#if page.pageNo != 1>
                <a href="${perfix}1-${page.pageSize}${suffix}" class="pageBtn">First</a>
                <a href="${perfix}${page.previousPageNo}-${page.pageSize}${suffix}" class="pageBtn">Prev</a>
                <#else>
                <font color="#777777">First&nbsp;&nbsp;Prev</font>
                </#if>
                <#list (page.pageNo-3)..(page.pageNo+3) as v>
                <#if v gt 0 && v lte page.maxPageNo>
                <#if v == page.pageNo>
                <font color="#777777">${v}</font>
                <#else>
                <a href="${perfix}${v}-${page.pageSize}${suffix}" class="pageBtn">${v}</a>
                </#if>
                </#if>
                </#list>
                <#if page.pageNo != page.maxPageNo>
                <a href="${perfix}${page.nextPageNo}-${page.pageSize}${suffix}" class="pageBtn">Next</a>
                <a href="${perfix}${page.maxPageNo}-${page.pageSize}${suffix}" class="pageBtn">End</a>
                <#else>
                <font color="#777777">&nbsp;Next&nbsp;&nbsp;End</font>
                </#if>
                <font color="#777777">${page.pageNo}&nbsp;/&nbsp;${page.maxPageNo}&nbsp;Page</font>
            </td>
            <td>
                Go to Page&nbsp;&nbsp;<input id="pageNo" type="text" class="ip_3" onkeyup="if(event.keyCode == 13){gotoPage()}">&nbsp;<input onclick="gotoPage()" type="button" class="ip_2" value="Go">
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    function gotoPage(){
        var pageNo = $("#pageNo").val();
        if(!/\d+/.test(pageNo)){
            alert("Please input a integer！");
            return;
        }
        var maxNo = '${page.maxPageNo}' * 1;
        if(pageNo <= 0 || pageNo > maxNo){
            alert("Please input a integer little than ${page.maxPageNo} ");
            return;
        }
        window.location.href = "${perfix}"+pageNo+"-${page.pageSize}${suffix}";
    }
</script>
</#if>
</#macro>

<#macro showPosition c>
<#if c.orders?length gte 6 && c.columns?? && c.columns.name != 'Home'><@showPosition c=c.columns!/><a href="<@url.column c=c/>">${c.name}</a>&nbsp;&gt;&nbsp;</#if>
</#macro>

<#macro position>
<#if !column??>
<div class="p_bg_3"><div class="p_bg_2"></div><div class="p_bg"><span>当前位置：<a href="/yjsjwb/list/258-1-20.htm">管理人员</a>&nbsp;&gt;&nbsp;主管院长</span></div></div>
<#else>
<div class="p_bg_3">
    <div class="p_bg_2"></div>
    <div class="p_bg"><span>当前位置：<@showPosition c=column.columns/>${column.name}</span></div>
</div>
</#if>
</#macro>

<#macro positionEn>
<#if !column??>
<div class="p_bg"><span>Home > About US > Visit SEIEE > Directions</span></div>
<#else>
<div class="p_bg_3">
    <div class="p_bg_2"></div>
    <div class="p_bg"><span>Home > <@showPosition c=column.columns/>${column.name}</span></div>
</div>
</#if>
</#macro>

<#macro showContentDetail cc>
<#if cc.content.detail??>
           	${(cc.content.detail)?replace('[IN]','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;')?replace('[B]','<B>')?replace('[/B]','</B>')?replace('[BR]','<br>')}
</#if>
</#macro>
<#macro contentTitle cc mx=999><#if cc.doesTop><b></#if><#if ((cc.content.title)!)?length gt mx>${(cc.content.title)?substring(0,mx)}...<#else>${(cc.content.title)!}</#if><#if cc.doesTop></b></#if></#macro>
<#--
<#macro contentTitle cc mx=999><#if cc.doesTop><b></#if>${(cc.content.title)!}<#if cc.doesTop></b></#if></#macro>
-->
<#macro textarea c>${c?replace('\n','<br>')}</#macro>
<#macro readTime><div style="text-align: right; color: #666666">访问数量：${(column_content.content.readTimes)!999}</div></#macro>
<#macro readTimeEn><div style="text-align: right; color: #666666">Visits：${(column_content.content.readTimes)!999}</div></#macro>
<#macro fileBase url>${base}/file/</#macro>

                                