<#include "/widgetComm.ftl"/>
<div> 
    <div class="p_bg_2"></div>
    <@wv.position/>
</div>
<div class="list_box_3">
    <h3 class="title_5_2">
        <span><span class="span_1">|</span>${(column.name)!'栏目名称'}</span>
    </h3>
    <hr class="line_2">
    <div class="list_box_8">
        <#if (contents!?size>0)>
        <#list contents as v>
        <#if v_index gt 0 && v_index%3==0>
        <div class="line_2"></div>
        </#if>
        <div class="pic_box_3"><a href="<@url.content cc=v/>"><img src="<@url.getTitleImage cc=v/>" style="height:145px;width:192px;"><br>
                <div><@wv.contentTitle cc=v/></div></a>
        </div>
        </#list>
        <div class="line_2"></div>
        <#assign perfix><@url.columnPerfix c=column/></#assign>
        <#assign suffix>.htm</#assign>
        <@wv.pageBar perfix=perfix  suffix=suffix/>
        <#else>
        <#if !column??>
        <#list 1..5 as v>
        <div class="pic_box_3"><a href="#"><img style="width:192;height:145;" src="${base}/css/front/theme41/style_01/temp_3.jpg"><br>
                <div>交大摩根士丹利共建金融本早稻 本早稻本早稻上海。</div>
            </a></div>
        </#list> 
        <div class="line_2"></div>
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
        </#if>

    </div>


