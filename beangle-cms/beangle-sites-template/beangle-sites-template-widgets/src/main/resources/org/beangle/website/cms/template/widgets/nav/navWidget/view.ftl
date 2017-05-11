<#include "/widgetComm.ftl"/>
<script type="text/javascript">
    $(function(){
        $(".sub_menu").each(function (){
            var v = $(this);
            if(v.find("li").length == 0){
                v.remove();
            }
        });
        $("ul.dropdown .first_li").hover(function(){
            $(this).addClass("hover");
            $('a:first',this).addClass("hover_a");
            $('ul:first',this).css('visibility', 'visible');
        }, function(){
            $(this).removeClass("hover");
            $('a:first',this).removeClass("hover_a");
            $('ul:first',this).css('visibility', 'hidden');
        });
        $("ul.dropdown li ul li:has(ul)").find("a:first").append(" &raquo; ");
    });
</script>
<div class="nav">
    <div class="nav_box">

        <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <Td>
                    <ul class="dropdown">
                        <li class="first_li"><a class="<#if !topColumn??>current_hit</#if> first_a" href="<@url.index site=site!/>">首 页</a> </li>
                        <#if columns??>
                        <#list columns as v>
                        <#if v.orders?length == 6>
                        <li class="first_li"><a style="padding: 0 ${config.space!18}px;"  class="<#if topColumn?? && topColumn.orders?length gt 5 && topColumn.orders[0..5] == v.orders[0..5]>current_hit</#if> first_a" href="<@url.column c=v/>" <#if v.type.id == 18>target="_blank"</#if>>${v.name}</a>
                            <ul class="sub_menu" style="visibility: hidden; ">
                                <#list columns as w>
                                <#if w.columns.id == v.id>
                                <li><a href="<@url.column c=w/>">${w.name} </a></li>
                                </#if>
                                </#list>
                            </ul>
                        </li>
                        </#if>
                        </#list>
                        <#else>
                        <li class="first_li"><a class="first_a" href="#">学院概况</a>
                            <ul class="sub_menu" style="visibility: hidden; ">
                                <li><a href="#">院长致辞 </a></li>
                                <li> <a href="#">学院简介</a> </li>
                                <li><a href="#">院史沿革</a></li>
                                <li><a href="#">学院领导</a></li>
                                <li><a href="#">组织机构</a></li>
                            </ul>
                        </li>
                        <li class="first_li"><a class="first_a" href="#">师资队伍</a>
                            <ul class="sub_menu" style="visibility: hidden; ">
                                <li><a href="#">院长致辞 </a></li>
                                <li> <a href="#">学院简介</a> </li>
                                <li><a href="#">院史沿革</a></li>
                                <li><a href="#">学院领导</a></li>
                                <li><a href="#">组织机构</a></li>
                            </ul>
                        </li>
                        <li class="first_li"><a class="first_a" href="#">人才培养</a> </li>
                        <li class="first_li"><a class="first_a" href="#">科学研究</a> </li>
                        <li class="first_li"><a class="first_a" href="#">学科基地</a> </li>
                        <li class="first_li"><a class="first_a" href="#">办事指南</a></li>
                        <li class="first_li"><a class="first_a" href="#">院系所办</a></li>
                        </#if>
                    </ul>
                </td>
            </tr>
        </table>
    </div>

</div>