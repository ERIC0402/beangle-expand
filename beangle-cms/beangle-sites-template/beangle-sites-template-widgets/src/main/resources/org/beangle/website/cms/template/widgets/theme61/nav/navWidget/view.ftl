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
  <div class="nav_box"  style="border-top-left-radius: 15px 15px; border-top-right-radius: 15px 15px; ">
    <table width="100%" cellpadding="0" cellspacing="0">
      <tr>
        <td>
        	<ul class="dropdown">
            <li class="first_li first_li_line"><a class="first_a" href="<@url.index site=site!/>"><span>Home</span> </a> </li>
            <#if columns??>
            <#list columns as v>
            <#if v.orders?length == 6>
            <li class="first_li first_li_line"><a style="padding: 0 ${config.space!29}px;"  class="first_a" href="<#if v.type.id == 142>#<#else><@url.column c=v/></#if>"><span>${v.name}</span></a>
                <ul class="sub_menu">
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
            <li class="first_li first_li_line"><a class="first_a" href="#"> <span>About Us</span></a>
              <ul class="sub_menu">
                <li><a href="#">院长致辞 </a></li>
                <li> <a href="#">学院简介</a> </li>
                <li><a href="#">院史沿革</a></li>
                <li><a href="#">学院领导</a></li>
                <li><a href="#">组织机构</a></li>
              </ul>
            </li>
            <li class="first_li first_li_line"><a class="first_a" href="#"><span>Admission</span></a>
              <ul class="sub_menu">
                <li><a href="#">院长致辞 </a></li>
                <li> <a href="#">学院简介</a> </li>
                <li><a href="#">院史沿革</a></li>
                <li><a href="#">学院领导</a></li>
                <li><a href="#">组织机构</a></li>
              </ul>
            </li>
            <li class="first_li first_li_line"><a class="first_a" href="#"><span>Research</span></a> </li>
            <li class="first_li first_li_line"><a class="first_a" href="#"><span>Academics</span></a> </li>
            <li class="first_li first_li_line"><a class="first_a" href="#"><span>Faculty</span></a> </li>
            <li class="first_li"><a class="first_a" href="#"><span>Join Us</span></a> </li>
            </#if>
          </ul>
         </td>
      </tr>
    </table>
  </div>
  <div class="nav_box_bottom"></div>
</div>