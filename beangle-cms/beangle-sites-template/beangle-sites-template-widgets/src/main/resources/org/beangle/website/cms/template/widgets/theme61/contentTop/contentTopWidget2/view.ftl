<#include "/widgetComm.ftl"/>
<#setting locale='en'>
<div class="BlankLine1"></div>
<div class="box_1 box_1_p">
    <div class="box_1_left_shadow">
        <div class="list_box_1">
            <div class="p_1">
                <#if column??>
                <h3 class="title_1"><font></font><span>${column.name!}</span></h3>
                <#if contents??>
                <div class="p_2">
                    <ul class="list_1">
                        <#list contents as item>
                        <li>
                            <div class="date_1">${(item.publishDate)?string("dd-MM-yyyy")}</div>
                            <div class="text_box_2"><a href="<@url.content cc=item/>"><@wv.contentTitle cc=item/> </a></div>
                        </li>	
                        </#list>
                    </ul>
                    <div class="date_2"><span>Today</span>${today?string("MMM dd,yyyy")}</div>

                </div>
                <div class="more_1"><a href="<@url.column c=column />">More &raquo;</a></div>
                </#if>
                <#else>
                <h3 class="title_1"><font></font><span>News</span></h3>
                <div class="p_2">
                    <ul class="list_1">
                        <#list 1..4 as v>
                        <li>
                            <div class="date_1">02-04-2011</div>
                            <div class="text_box_2"><a href="#">Gift from alumnus enables Humanities Center to widen its reach </a></div>
                        </li>
                        </#list>
                    </ul>
                    <div class="date_2"><span>Today</span> June 6,2001</div>
                </div>
                <div class="more_1"><a href="#">More &raquo;</a></div>
                </#if>
            </div>
        </div>
    </div>
    <div class="box_1_bottom_shadow"></div>
</div>