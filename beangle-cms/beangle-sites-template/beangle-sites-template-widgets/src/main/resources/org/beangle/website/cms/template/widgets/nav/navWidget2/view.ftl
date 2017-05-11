<#include "/widgetComm.ftl"/>
<#assign num=1>
<#assign count=0>
<script type="text/javascript" src="${base}/js/jquery.corner.js"></script>
<script type="text/javascript" src="${base}/js/jquery.dropdownPlain.js"></script>
<script type="text/javascript">
    $('.home_now').corner("tl");
    $('.last_now').corner("tr");
    $('.nav_box').corner("top 10px");
    $('.list_box_1_bg').corner("10px");
    $('.span_2').corner("top 5px");
    $('.title_2_a').corner("top 5px");
    $('.li_2').corner("3px");

    $(function() {
        $('.s4').cycle({
            fx:     'fade',
            speed:  'slow',
            timeout: 4000,
            pager:  '.nav3'
        });
    });

    $(function (){
        $(".main_bg").css('background','url(${base}/${config.img!"css/front/theme41/style_01/top_bg.jpg"}) no-repeat top');
    });
</script>

<div class="nav">
    <div class="nav_box">
        <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <Td>
                    <ul class="dropdown">
                        <li class="first_li"><a class="home_now <#if !topColumn??>current_hit</#if> first_a" href="<@url.index site=site!/>">首 页</a> </li>
                        <#if columns??>
                        <#list columns as v>
                        <#if v.orders?length == 6>
                        <#assign num=num+1>
                        <li class="first_li_line"></li>
                        <li class="first_li"><a style="padding: 0 ${config.space!29}px;"  class="<#if topColumn?? && topColumn.orders[0..5] == v.orders[0..5]>current_hit</#if> first_a" href="<@url.column c=v/>">${v.name}</a>
                            <#assign count=0>
                            <#list columns as w>
                            <#if w.columns.id == v.id>
                            <#assign count=count+1>
                            <#if count==1>
                            <ul class="sub_menu">
                                </#if>
                                <li><a href="<@url.column c=w/>">${w.name} </a></li>
                                </#if>
                                </#list>
                                <#if count!=0></ul></#if>
                        </li>
                        </#if>
                        </#list>
                        <#else>
                        <li class="first_li_line"></li>
                        <li class="first_li "><a class="first_a" href="second_1.html">组织结构</a>
                            <ul class="sub_menu">
                                <li><a href="#">院长致辞 </a></li>
                                <li> <a href="#">学院简介</a> </li>
                                <li><a href="#">院史沿革</a></li>
                                <li><a href="#">学院领导</a></li>
                                <li><a href="#">组织机构</a></li>
                            </ul>
                        </li>
                        <li class="first_li_line"></li>
                        <li class="first_li "><a class="first_a" href="#"> 思政工作</a>
                            <ul class="sub_menu">
                                <li><a href="#">院长致辞 </a></li>
                                <li> <a href="#">学院简介</a> </li>
                                <li><a href="#">院史沿革</a></li>
                                <li><a href="#">学院领导</a></li>
                                <li><a href="#">组织机构</a></li>
                            </ul>
                        </li>
                        <li class="first_li_line"></li>
                        <li class="first_li "><a class="first_a" href="#">党建德育</a> </li>
                        <li class="first_li_line"></li>
                        <li class="first_li "><a class="first_a" href="#">学生事务</a> </li>
                        <li class="first_li_line"></li>
                        <li class="first_li "><a class="first_a" href="#">职业发展</a> </li>
                        <li class="first_li_line"></li>
                        <li class="first_li "><a class="first_a" href="#">办事指南</a> </li>
                        <li class="first_li_line"></li>
                        <li class="first_li last_box "><a class="first_a  last_now last_bg" href="#">文件下载</a> </li>
                        </#if>
                    </ul>
                </Td>
            </tr>
        </table>
    </div>
</div>
