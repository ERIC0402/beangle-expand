<#include "/comm.ftl">
<div style="display:none">&nbsp<a>a</a></div>
<div class="list_box_8a">
    <h4 class="title_2"> <a href="<#if column??><@url.column c=column /><#else>#</#if>" target="_Blank">${config.title!'热点信息'}</a></h4>
    <#if contents??>
    <#list contents as item>
    <#if item_index==0>
    <div class="pic_box_1"><img src="<@url.getTitleImage cc=item/>"></div>
    <div class="text_box_1">
        <h4><a href="<@url.content cc=item/>" ><@wv.contentTitle cc=item/></a></h4>
        <div><a href="<@url.content cc=item/>" target="_Blank">${(item.content.abstracts)!}</a> </div>
    </div>
    <div class="BlankLine1"></div>
    </#if>
    </#list>
    <ul>
        <#list contents as item>
        <#if item_index != 0>
        <li><a href="<@url.content cc=item/>" target="_Blank"><font>&rsaquo;&nbsp;</font><@wv.contentTitle cc=item/></a><span>(${(item.publishDate)?string("yyyy-MM-dd")})</span></li>
        </#if>
        </#list>
    </ul> 
    <#else>
    <div class="pic_box_1"><#if config.hiddenPic??><img src="${frontbase}/${config.img!'css/front/theme41/style_01/temp_1.jpg'}"></#if></div>
    <div class="text_box_1">
        <h4>电院2011年硕士研究生招收复试工作方案</h4>
        <div><a href="#" target="_Blank"> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a> </div>
    </div>
    <div class="BlankLine1"></div>
    <ul>
        <li><a href="#" target="_Blank"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
        <li><a href="#" target="_Blank"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
        <li><a href="#" target="_Blank"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
        <li><a href="#" target="_Blank"><font>&rsaquo;</font> 4月9日下午，上海交通大学电气工程系54届校友一行，在电气工程与自动化实验中心主任、同为交大54届毕业生...</a><span><#if config.showDate??>(2011-01-24)</#if></span></li>
    </ul>
    </#if>
</div>
<div class="BlankLine2"></div>