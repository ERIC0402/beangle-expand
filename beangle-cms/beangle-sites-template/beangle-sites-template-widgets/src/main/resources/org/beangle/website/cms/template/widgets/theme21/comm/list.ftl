<#include "/widgetComm.ftl"/>
<@wv.position/>
<div class="list_box_3">
    <h3 class="title_5">
        <span><span class="span_1">|</span>${(column.name)!'栏目名称'}</span>
    </h3>
    <#if contents??>
    <ul class="list_box_5">
        <#list contents as v>
        <li> <span>[${(v.publishDate)?string("yyyy-MM-dd")}]</span><a href="<@url.content cc=v/>" target="_blank"><font>&rsaquo;</font> <@wv.contentTitle cc=v/></a> </li>
        </#list>
    </ul>
    <@wv.columnPageBar c=column/>
    <#else>
    <ul class="list_box_5">        
        <#list 1..20 as v>
        <li> <span>[2011-07-21]</span><a href="#" target="_blank"><font>&rsaquo;</font> 文档标题文档标题文档标题文档标题文档标题文档标题</a> </li>
        </#list>
    </ul>
    <div class="turn_page">
        <table align="right">
            <tr>
                <td><a href="#">首页</a> <  1 <a href="#">2</a> 3 4 5 ....下一页 末页</td>
                <td> 2/20页 </td>
                <td> 到 </td>
                <td><input type="text" class="ip_3">
                </td>
                <td> 页 </td>
                <td><input type="submit" class="ip_2" value="确 定">
                </td>
            </tr>
        </table>
    </div>
    </#if>
    <div style="clear:both"></div>
</div>
<div style="display:none">&nbsp<a>a</a></div>









