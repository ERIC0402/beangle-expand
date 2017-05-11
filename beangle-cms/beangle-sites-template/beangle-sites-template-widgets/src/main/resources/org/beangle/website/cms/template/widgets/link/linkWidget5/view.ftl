<#include "/widgetComm.ftl"/>
<script type="text/javascript">
    // JavaScript Document
    $(document).ready(function(){
        $('.s4 dt:first').addClass('active');
        $('.s4 dd:first').css('display','block');
        setInterval(autoroll, offset);
        hookThumb();
    });
    var i=-1; //第i+1个tab开始
    var offset = '${config.time!5}000' * 1; //轮换时间
    var running = true;
    function autoroll(){
        if(!running){
            return;
        }
        n = $('.s4 dt').length-1;
        i++;
        if(i > n){
            i = 0;
        }
        slide(i);
    }
    function slide(i){
        $('.s4 dt').eq(i).addClass('active').siblings().removeClass('active');
        $('.s4 dd').eq(i).css('display','block').siblings('.s4 dd').css('display','none');
    }

    function hookThumb() {
        $('.s4 dt, .list_box_2').hover(function() {
            running = false;
            if($(this).attr("class") == 'list_box_2'){
                i = $(".active").prevAll().length;
            }else{
                i = $(this).prevAll().length;
            }
            slide(i);
        }, function() {
            running = true;
        });
    }

</script>
<script>           
    function changeaaa(a){  
        window.location=a;       
    }    
</script>
<#if (column1)??||(column2)??||(column3)??||(column4)??||(column5)??>
<dl class="s4">
    <div class="div_1">
        <#if column1??>
        <dt onclick="changeaaa('<@url.column c=column1 />')">
            <#if config.name1="">无</#if>${(config.name1)!}
        </dt>
        </#if>
        <#if column2??>
        <dt onclick="changeaaa('<@url.column c=column2 />')">
            <#if config.name2="">无</#if>${(config.name2)!}
        </dt>
        </#if>
        <#if column3??>
        <dt onclick="changeaaa('<@url.column c=column3 />')">
            <#if config.name3="">无</#if>${(config.name3)!}
        </dt>
        </#if>
        <#if column4??>
        <dt onclick="changeaaa('<@url.column c=column4 />')">
            <#if config.name4="">无</#if>${(config.name4)!}
        </dt>
        </#if>
        <#if column5??>
        <dt onclick="changeaaa('<@url.column c=column5 />')">
            <#if config.name5="">无</#if>${(config.name5)!}
        </dt>
        </#if>
    </div>
    <#if column1??>
    <dd>
        <div class="list_box_2">
            <ul>
                <#list contents1 as item>
                <#if ((config.num1?number) &gt; item_index)>
                <li><a href="<@url.content cc=item/>">
                        <font>
                            &rsaquo;
                        </font>
                        <@wv.contentTitle cc=item/>
                    </a>
                    <span>
                        <#if config.showDate??>
                        (${item.publishDate?string("yyyy-MM-dd")})
                        </#if>
                    </span>
                </li>
                </#if>
                </#list>
            </ul>
            <div class="more_1"><a href="<@url.column c=column1 />">&raquo; <font size="-2">更多</font></a>
            </div>
    </dd>
    </#if>
    <#if column2??>
    <dd>
        <div class="list_box_2">
            <ul>
                <#list contents2 as item>
                <#if ((config.num2?number) &gt; item_index)>
                <li><a href="<@url.content cc=item/>">
                        <font>
                            &rsaquo;
                        </font>
                        <@wv.contentTitle cc=item/>
                    </a>
                    <span>
                        <#if config.showDate??>
                        (${item.publishDate?string("yyyy-MM-dd")})
                        </#if>
                    </span>
                </li>
                </#if>
                </#list>
            </ul>
            <div class="more_1"><a href="<@url.column c=column2 />">&raquo; <font size="-2">更多</font></a>
            </div>
    </dd>
    </#if>
    <#if column3??>
    <dd>
        <div class="list_box_2">
            <ul>
                <#list contents3 as item>
                <#if ((config.num3?number) &gt; item_index)>
                <li>
                    <a href="<@url.content cc=item/>">
                        <font>
                            &rsaquo;
                        </font>
                        <@wv.contentTitle cc=item/>
                    </a>
                    <span>
                        <#if config.showDate??>
                        (${item.publishDate?string("yyyy-MM-dd")})
                        </#if>
                    </span>
                </li>
                </#if>
                </#list>
            </ul>
            <div class="more_1"><a href="<@url.column c=column3 />">&raquo; <font size="-2">更多</font></a>
            </div>
    </dd>
    </#if>
    <#if column4??>
    <dd>
        <div class="list_box_2">
            <ul>
                <#list contents4 as item>
                <#if ((config.num4?number) &gt; item_index)>
                <li>
                    <a href="<@url.content cc=item/>">
                        <font>
                            &rsaquo;
                        </font>
                        <@wv.contentTitle cc=item/>
                    </a>
                    <span>
                        <#if config.showDate??>
                        (${item.publishDate?string("yyyy-MM-dd")})
                        </#if>
                    </span>
                </li>
                </#if>
                </#list>
            </ul>
            <div class="more_1"><a href="<@url.column c=column4 />">&raquo; <font size="-2">更多</font></a>
            </div>
    </dd>
    </#if>
    <#if column5??>
    <dd>
        <div class="list_box_2">
            <ul>
                <#list contents5 as item>
                <#if ((config.num5?number) &gt; item_index)>
                <li>
                    <a href="<@url.content cc=item/>">
                        <font>
                            &rsaquo;
                        </font>
                        <@wv.contentTitle cc=item/>
                    </a>
                    <span>
                        <#if config.showDate??>
                        (${item.publishDate?string("yyyy-MM-dd")})
                        </#if>
                    </span>
                </li>
                </#if>
                </#list>
            </ul>
            <div class="more_1"><a href="<@url.column c=column5 />">&raquo; <font size="-2">更多</font></a>
            </div>
    </dd>
    </#if>
</dl>
<#else>
<dl class="s4">
    <div class="div_1">
        <dt>招生</dt>
        <dt>培养</dt>
        <dt>学位</dt>
    </div>
    <dd>
        <div class="list_box_2">
            <ul>
                <li><a href="#"><font>&rsaquo;</font> 4月1日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
            </ul>
            <div class="more_1"><#if column??><a href="<@url.column c=column />"><#else><a href="#"></#if>&raquo; 更多</a>
            </div>
        </div>
    </dd>
    <dd>
        <div class="list_box_2">
            <ul>
                <li><a href="#"><font>&rsaquo;</font> 4月2日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
            </ul>
            <div class="more_1"><#if column??><a href="<@url.column c=column />"><#else><a href="#"></#if>&raquo; 更多</a>
            </div>
        </div>
    </dd>
    <dd>
        <div class="list_box_2">
            <ul>
                <li><a href="#"><font>&rsaquo;</font> 4月3日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
                <li><a href="#"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
            </ul>
            <div class="more_1"><#if column??><a href="<@url.column c=column />"><#else><a href="#"></#if>&raquo; 更多</a>
            </div>
        </div>
    </dd>
</dl>
</#if>