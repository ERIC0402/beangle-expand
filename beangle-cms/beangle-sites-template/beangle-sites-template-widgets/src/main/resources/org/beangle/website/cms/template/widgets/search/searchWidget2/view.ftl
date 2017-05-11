<#include "/widgetComm.ftl"/>
<div style="overflow: hidden">
    <div id="layout50" style="float:left;width:21%;">
        <div style="" class="">
            <div class="c_Left_2">
                <h3 class="title_3"><font>&middot;</font>搜索结果</h3>
            </div>
        </div>
    </div>
    <div id="layout51" style="float:left;width:79%;">
        <div style="" class="">
            <div class="c_Right_2">
                <div class="list_box_3">
                    <h3 class="title_1">
                        <div class="div_2"><span><span class="span_1">|</span> 搜索结果 </span></div>
                    </h3>
                    <#if contents??>
                    <ul class="list_box_5">
                        <#list contents as v>
                        <li> <span>[${v.publishDate?date}]</span><a href="<@url.content cc=v/>" target="_blank"><font>&rsaquo;</font> <@wv.contentTitle cc=v/></a>  </li>
                        </#list>
                    </ul>
                    <#assign perfix><@url.searchPerfix/></#assign>
                    <#assign suffix>.htm?keyWord=${keyWord}&allSite=${(Parameter['allSite'][0])!}</#assign>
                    <@wv.pageBar perfix=perfix  suffix=suffix/>
                    <#else>       
                    <ul class="list_box_5">
                        <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科。</a> </li>
                        <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科。</a> </li>
                        <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科。</a> </li>
                        <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科。</a> </li>
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
    </div>
</div>