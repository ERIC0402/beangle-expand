<#include "/widgetComm.ftl"/>
<div class="content content_bg_1">
    <div style="float:left;width:20%;">
        <div style="" class="">
            <div class="c_Left_2">
                <h3 class="title_3"><font>&middot;</font>搜索结果</h3>
            </div>
        </div>
    </div>
    <div style="float:left;width:80%;">
        <div style="" class="">
            <div class="c_Right_2">
                <div class="list_box_3 border_right_1">
                    <div class="position_bar">
                        <div class="p_bg_3">
                            <div class="p_bg_2"></div>
                            <div class="p_bg"><span>搜索&nbsp;"${keyWord!}"</span></div>
                        </div>
                        <div class="BlankLine3"></div>
                        <h2 class="title_4">搜索结果</h2>
                        <#if contents??>
                        <ul class="list_box_5">
                            <#list contents as v>
                            <li> <span>[${v.publishDate?date}]</span><a href="<@url.content cc=v/>" target="_blank"><font>&rsaquo;</font> <@wv.contentTitle cc=v/></a>  </li>
                            </#list>
                        </ul>               
                        <#assign perfix><@url.searchPerfix/></#assign>
                        <#assign suffix>.htm?keyWord=${keyWord}</#assign>
                        <@wv.pageBar perfix=perfix  suffix=suffix/>
                        <#else>
                        <ul class="list_box_5">
                            <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科溯源电机专溯源电机专溯源电机专。</a>  </li>
                            <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科。</a>  </li>
                            <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科。</a>  </li>
                            <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院）的前身可溯源电机专科。</a>  </li>
                        </ul>         
                        <div class="turn_page">
                            <table align="right">
                                <tr>
                                    <td><a href="#">首页</a> <a>上一页</a>  1 <a href="#">2</a> 3 4 5 下一页 末页</td>
                                    <td> 2/20页 </td>
                                </tr>
                            </table>
                        </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="clearDiv">
    </div>
</div>