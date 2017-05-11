<#include "/widgetComm.ftl"/>
<@wv.position/>
<div class="list_box_3">
	<h3 class="title_5">
    	<span><span class="span_1">|</span>搜索结果</span>
    </h3>
        	<#if contents??>
            	<ul class="list_box_5">
                	<#list contents as v>
                    	<li> <span>[${(v.publishDate)?string("yyyy-MM-dd")}]</span><a href="<@url.content cc=v/>" target="_blank"><font>&rsaquo;</font> <@wv.contentTitle cc=v/></a> </li>
                    </#list>
                </ul>
                <div style="clear:both"></div>
                	<#if contents?size &gt; 0>
		           		<#assign perfix><@url.searchPerfix/></#assign>
		            	<#assign suffix>.htm?keyWord=${keyWord}</#assign>
		            	<@wv.pageBar perfix=perfix  suffix=suffix/>
		            </#if>
		     <#else>
		     	没有搜索到任何相关信息
             </#if>
</div>
 <div style="display:none">&nbsp<a>a</a></div>









