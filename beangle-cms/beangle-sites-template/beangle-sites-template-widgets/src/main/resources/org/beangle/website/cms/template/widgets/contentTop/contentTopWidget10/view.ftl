<#include "/widgetComm.ftl"/>
<div class="list_box_8">
    <@wv.position/>
    <h3 class="title_5">
        <span><span class="span_1">|</span> ${(column.name)!'图片列表'}</span>
    </h3>
    <#if contents??>
    <div class="list_box_8">
        <#list contents as v>
        <div class="pic_box_3">
            <a href="<@url.content cc=v/>"  target="_Blank"><img style="width:192px; height:145px;" src="<@url.getTitleImage cc=v/>"/><div><@wv.contentTitle cc=v/></div></a> 
        </div>
        <#if ((v_index+1)%3)=0>
        <div class="line_2"></div>
        <#else>
        <#if !(v_has_next)>
        <div class="line_2"></div>
        </#if>
        </#if>
        </#list>
    </div>
    <span>
        <#assign perfix><@url.columnPerfix c=column/></#assign>
        <#assign suffix>.htm</#assign>
        <@wv.pageBar perfix=perfix  suffix=suffix/>
    </span>
    <#else>
    <div class="list_box_8">
        <#list 1..9 as v>
        <div class="pic_box_3">
            <a href="#"  target="_Blank"><img style="width:192px; height:145px;" src="/img/nopic.gif"/><div>研究生教务办主任：罗金才（兼负责仪器系研究生教务工作）</div></a> 
        </div>
        </#list>

        <div class="line_2"></div>
    </div>
    <div class="turn_page">
        <table align="right">
            <tr>
                <td>
                    <font color="#777777">首页&nbsp;&nbsp;上一页</font>
                    <font color="#777777">1</font>
                    <font color="#777777">&nbsp;下一页&nbsp;&nbsp;末页</font>
                    <font color="#777777">1&nbsp;/&nbsp;1&nbsp;页</font>
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
    </#if>