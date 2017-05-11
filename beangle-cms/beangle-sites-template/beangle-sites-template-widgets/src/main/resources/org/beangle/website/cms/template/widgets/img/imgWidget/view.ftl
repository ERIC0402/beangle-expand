<script type="text/javascript">
    $(function() {	
        $('.s5').cycle({
            fx:     'fade',
            speed:  'slow',
            timeout: "${config['time']!10}000"
        });
    });

</script>
<#if config.topBlank??>
<div class="BlankLine2"></div>
</#if>
<div class="banner">
    <div class="s5">
        <#assign hasimg = false/>
        <#list 1..5 as v>
        <#if config['img' + v]?? &&  config['img' + v] != ''>
        <#assign hasimg = true/>
        <#assign url = config['url' + v]!/>
        <#if url == ''>
        <#assign url = '#'/>
        </#if>
        <div><a href="${url}" <#if url != '#'>target="_blank"</#if>><img width="100%" src="${base}/${config['img' + v]!'css/front/theme1/style_01/banner_pic.jpg'}"></a></div>
        </#if>
        </#list>
        <#if !hasimg>
        <div><a href="#" target="_blank"><img width="100%" src="${base}/css/front/theme1/style_01/banner_pic.jpg"></a></div>
        </#if>
    </div>
</div>
<#if config.bottomLine??>
<div class="line_1"></div>
</#if>
<div class="BlankLine1"></div>