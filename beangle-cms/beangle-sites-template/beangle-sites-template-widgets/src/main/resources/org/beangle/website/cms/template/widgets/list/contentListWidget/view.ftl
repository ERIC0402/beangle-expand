<#include "/widgetComm.ftl"/>
<style type="text/javascript">
    .pageBtn{
        padding: 2px 5px; border: #2775D8 1px solid;
    }
</style>
<div class="c_Right_2">
    <div class="list_box_3 border_right_1">
        <div class="position_bar">
            <@wv.position/>
            <#if contents??>
            <div class="BlankLine3"></div>
            <h2 class="title_4">${column.name}</h2>
            <ul class="list_box_5">
                <#list contents as v>
                <li <#if v_index + 1 == contents?size>class="boder-bottom-none"</#if>> <span>[${v.publishDate?date}]</span><a href="<@url.content cc=v/>" target="_blank"><font>&rsaquo;</font>&nbsp;&nbsp;<@wv.contentTitle cc=v/></a>  </li>
                </#list>
            </ul>
            <#assign perfix><@url.columnPerfix c=column/></#assign>
            <#assign suffix>.htm</#assign>
            <@wv.pageBar perfix=perfix  suffix=suffix/>
            <#else>
            <div class="p_bg_3">
                <div class="p_bg_2"></div>
                <div class="p_bg"><span>当前位置：学院概况 > 院长致辞</span></div>
            </div>
            <div class="BlankLine3"></div>
            <h2 class="title_4">列表模板</h2>
            <ul class="list_box_5">
                <#list 1..4 as v>
                <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font>&nbsp;&nbsp;上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科溯源电机专溯源电机专溯源电机专。</a>  </li>
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
        </div>
    </div>
</div>
