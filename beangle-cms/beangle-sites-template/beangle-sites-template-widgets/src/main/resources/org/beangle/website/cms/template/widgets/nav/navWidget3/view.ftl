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
        <ul class="dropdown">
            <li class="first_li"><a class="<#if !topColumn??>current_hit</#if> first_a" href="<@url.index site=site!/>">首 页</a> </li>
            <#if columns??>
            <#list columns as v>
            <#if v.orders?length == 6>
            <li class="first_li"><a style="padding: 0 ${config.space!29}px;"  style="padding: 0 ${config.space!29}px;" class="<#if topColumn?? && topColumn.orders[0..5] == v.orders[0..5]>current_hit</#if> first_a" href="<@url.column c=v/>">${v.name}</a>
                <ul class="sub_menu" style="visibility: hidden;">
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
            <li class="first_li"><a class="first_a" href="#"> 工作动态</a>
                  <ul class="sub_menu">
                    <li><a href="#">院长致辞 </a></li>
                    <li> <a href="#">学院简介</a> </li>
                    <li><a href="#">院史沿革</a></li>
                    <li><a href="#">学院领导</a></li>
                    <li><a href="#">组织机构</a></li>
                  </ul>
                </li>
                <li class="first_li"><a class="first_a" href="#">党务工作</a>
                  <ul class="sub_menu">
                    <li><a href="#">院长致辞 </a></li>
                    <li> <a href="#">学院简介</a> </li>
                    <li><a href="#">院史沿革</a></li>
                    <li><a href="#">学院领导</a></li>
                    <li><a href="#">组织机构</a></li>
                  </ul>
                </li>
                <li class="first_li"><a class="first_a" href="#">服务指南</a> </li>
                <li class="first_li"><a class="first_a" href="#">信息发布</a> </li>
            </#if>
        </ul>
    </div>
</div>





