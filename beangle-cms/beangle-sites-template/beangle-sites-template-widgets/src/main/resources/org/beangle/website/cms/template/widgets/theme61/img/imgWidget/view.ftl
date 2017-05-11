<#include "/widgetComm.ftl"/>
<script type="text/javascript" src="${frontbase}/css/front/theme61/js/jquery.dropdownPlain.js"></script>
<script type="text/javascript">
    function focusBox(o){
        if(!o) return;
        var w=598, $o=$('.'+o),i=0,l=0;arr= [],t= null,
        $infoLi = $o.find('.banner_info li'), len= $infoLi.length*2,
        $ul=$o.find('.banner_pic>ul');
        $ul.append($ul.html()).css({'width':len*w, 'left': -len*w/2});
        $infoLi.eq(0).addClass('current');
        //add banner_pages element
        arr.push('<div class="banner_pages"><ul>');
        <#list 1..5 as v>
                <#list 1..5 as w>
                	<#assign hasconfig = false/>
                    <#if (config["order" + w]?number)!0 == v>
        arr.push('<li<#if v==1> class="current"</#if>><span><img style="width:100px; height:60px;" src=${base}/${config["img" + w]!"img/nopic.gif"}></span></li>');
                	<#assign hasconfig = true/>
                   </#if>
               </#list>
               <#if !hasconfig>
               arr.push('<li<#if v==1> class="current"</#if>><span><img style="width:100px; height:60px;" src=${frontbase}css/front/theme61/style_01/temp_${v}.jpg></span></li>');
               </#if>
        </#list>
        arr.push('</ul></div>');
        ////$o.append(arr.join(''));
        $o.prepend(arr.join(''));
        var $pagesLi = $o.find('.banner_pages li');
        //mouse
        $pagesLi.children('span').click(function(){
            var p = $pagesLi.index($o.find('.banner_pages li.current'));
            i = $pagesLi.children('span').index($(this));
            if(i==p) return;
            l = parseInt($ul.css('left')) + w*(p-i);
            addCurrent(i,l);
            return false;
        })
        $o.children('a.btn_prev').click(function(){
            i = $pagesLi.index($o.find('.banner_pages li.current'));
            (i==0)? i=(len/2-1):i--;
            l = parseInt($ul.css('left')) + w;
            addCurrent(i,l);
            return false;
        })
        $o.children('a.btn_next').click(function(){
            i = ($pagesLi.index($o.find('.banner_pages li.current'))+1)%(len/2);
            l = parseInt($ul.css('left')) - w;
            addCurrent(i,l);
            return false;
        })
        //auto focus
        intervalTime = '${config.time!6}' * 1000;
        t = setInterval(init,intervalTime);
        $o.hover(function(){
            clearInterval(t);
        }, function(){
            t = setInterval(init,intervalTime);
        });
        function init(){
            $o.children('a.btn_next').trigger('click');
        }
        //add focus
        function addCurrent(i,l){
            if($ul.is(':animated')) return;
            $ul.animate({'left':l},500,function(){
                $o.children('.banner_count').text(i+1);
                $infoLi.not($infoLi.eq(i).addClass('current')).removeClass('current');
                $pagesLi.not($pagesLi.eq(i).addClass('current')).removeClass('current');
                if(l== (1-len)*w){
                    $ul.css({'left': (1-len/2)*w});
                }else if(l== 0){
                    $ul.css({'left': -len*w/2});
                }
            });
        }
    }
    $(function(){
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
<div class="BlankLine2"></div>
<div class="banner">
    <div class="box_3">         
        <div class="box_3_left_shadow">  
            <div class="banner_box PicFocus"> <a class="btn_prev" title="上一个" href="#">Previous</a> <a class="btn_next" title="下一个" href="#">Next</a>
                <div class="banner_pic">
                    <ul>
                        <#list 1..5 as v>
                        <#list 1..5 as w>
                        <#if (config["order" + w]?number)!0 == v>
                        <li><a href="${config['title' + w]!'#'}" target="_blank"><img style="width:598px; height:322px;" src="${base}/${config['img' + w]!'css/front/theme61/style_01/temp_1_b.jpg'}" alt="" /></a></li>
                        </#if>
                        </#list>
                        </#list>
                    </ul>
                </div>
                <div class="banner_info">
                    <ul>
                        <#list 1..5 as v>
                        <#list 1..5 as w>
                        <#if (config["order" + w]?number)!0 == v>
                        <li>
                            <h4><a href="${config['address' + w]!'#'}" target="_blank">${config['title' + w]!'The east gate features traditional Chinese architectural style with vermeil walls and green glazed tiles.'}
                                </a></h4>
                        </li>
                        </#if>
                        </#list>
                        </#list>
                    </ul>
                </div>
                <div class="banner_count">1</div>
                <div class="banner_info_bg"> </div>
            </div></div>
    </div>
</div>