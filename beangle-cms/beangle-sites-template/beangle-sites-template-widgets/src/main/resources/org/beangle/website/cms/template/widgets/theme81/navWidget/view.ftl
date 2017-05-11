<#include "/widgetComm.ftl"/>
<div class="nav_bg">
    <div class="nav">
        <div class="nav_box">
            <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                    <Td>
                        <ul class="dropdown">
                            <li class="first_li home_box"><a class="<#if !topColumn??>home_now current_hit<#else>home_bg</#if> first_a" href="<@url.index site=site!/>">首 页</a> </li>
                            <#if columns??>
                            <#assign num=1>
                            <#assign count=0>
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
</div>
<script type="text/javascript">
    $(function (){    
        $('.home_now').corner("tl");
        $('.home_bg').corner("tl");
        $('.last_now').corner("tr");
        $('.nav_box').corner("top 10px");
    });
</script>