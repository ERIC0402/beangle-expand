<#include "/widgetComm.ftl"/>
<script type="text/javascript">
    $(function() {
        $('.nav_box').corner("top 15px");

        $('.s3').cycle({
            fx:     'turnLeft',
            speed:  'slow',
            timeout: ${config.time!10}000
        });
        focusBox('PicFocus');
    });
    $(function (){
        autoImgSize($(".autoSize"));
    })
</script>
<div class="list_box_6">
    <div class="p_1 bg_1">
        <h3 class="title_1_1">
            <div class="div_2"><span><span class="span_2"></span> ${config.title!'Faculty'} </span></div>
        </h3>
        <div class="BlankLine3"></div>
        <div class="photo_box_1">
            <div class="s3">
                <#if englishs??>
                <#macro getColumnByEnglish e>
                <#list columns as c>
                <#if c.name! == e.department>
                <#nested c/>
                <#return/>
                </#if>
                </#list>
                </#macro>
                <#list englishs as v>
                <div> 
                    <a href="<@getColumnByEnglish e=v;c><@url.detail c=c v=v/></@getColumnByEnglish>">
                        <#assign img = v.img!'NULL'/>
                        <h4 class="pic_box_4"><img class="autoSize" width="100" height="100" src="${base}/<#if img == 'NULL'>img/nopic.gif<#else>${img}</#if>" ></h4>
                        <h4>${v.name!}</h4>
                        ${v.title!}<br>
                        <#list fileds as w>
                        ${v[w[0]]!}<br>
                        </#list>
                    </a> 
                </div>
                </#list>
                <#else>
                <#list 1..4 as v>
                <div> <a href="#">
                        <h4 class="pic_box_4"><img src="${frontbase}/css/front/theme61/style_01/photo_1.jpg" ></h4>
                        <h4> Assistant Prof.Kai Xu</h4>
                        Scholar of New Centruy Excellent  
                        Talents in Universitu <br>
                        PH.D.,Columbia University 
                        Medical Robotics and Service 
                        Robotics</a> </div>
                </#list>
                </#if>
            </div>
        </div>
    </div>
</div>