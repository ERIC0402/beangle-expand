<#include "/widgetComm.ftl"/>
<div class="c_Right_2">
    <div class="BlankLine1"></div>
    <div class="position_bar">
    <@wv.position/>
    </div>
    <#if contents??>
    <h3 class="title_1">
        <div class="div_2"><span><span class="span_1">|</span> ${column.name} </span></div>
    </h3>
    <ul class="list_box_5">
        <#list contents as v>
        <li <#if v_index + 1 == contents?size>class="boder-bottom-none"</#if>> <span>[${v.publishDate?date}]</span><a href="<@url.content cc=v/>" target="_blank" title="<@wv.contentTitle cc=v/>"><font>&rsaquo;</font>&nbsp;&nbsp;<@wv.contentTitle cc=v/></a>  </li>
        </#list>
    </ul>
    <#assign perfix><@url.columnPerfix c=column/></#assign>
    <#assign suffix>.htm</#assign>
    <@wv.pageBar perfix=perfix  suffix=suffix/>
    <div class="BlankLine1"></div>
    <#else>
    <h3 class="title_1">
        <div class="div_2"><span><span class="span_1">|</span> 信息列表2 </span></div>
    </h3>
    <ul class="list_box_5">
        <#list 1..4 as v>
        <li <#if v_index + 1 == 4>class="boder-bottom-none"</#if>> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font>&nbsp;&nbsp;信息列表2</a> </li>
        </#list>
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
    </ul>
    <div class="BlankLine1"></div>
    <div class="BlankLine4"></div>
    </#if>
</div>
