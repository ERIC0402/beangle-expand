<#include "/widgetComm.ftl"/>
<#if contents??>
<div class="list_box_3">
    <div class="BlankLine3"></div>
    <h2 class="title_4">${(column.name)}<div class="line_1"></div></h2>
    <ul class="list_box_4">
        <#list contents as v>
        <li>
            <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                    <td rowspan="2" class="pic_box_2 td_2"><img src="<@url.getTitleImage cc=v/>"></td>
                    <td class="td_1"><h4><@wv.contentTitle cc=v/></h4>
						${(v.content.abstracts)!}</td>
                </tr>
                <#if !config.hideDetail??>
                <tr><Td class="td_2"><div  class="more_2"><a href="<@url.content cc=v/>">More</a></div></Td></tr>
                </#if>
            </table>
        </li>
        </#list>
        <#assign perfix><@url.columnPerfix c=column/></#assign>
        <#assign suffix>.htm</#assign>
        <@wv.pageBarEn perfix=perfix  suffix=suffix/>
    </ul>
</div>
<#else>
<div class="list_box_3">
    <div class="BlankLine3"></div>
    <h2 class="title_4">SEIEE Today <div class="line_1"></div></h2>

    <ul class="list_box_4">
        <#list 1..8 as v>
        <li>
            <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                    <td rowspan="2" class="pic_box_2 td_2"><img src="${frontbase}/css/front/theme61/style_01/temp_41.jpg"></td>
                    <td class="td_1"><h4>Seminar Rooms</h4>
                        School of International Education of SJTU is a special school responsible for admission, registration and management of international students coming from all parts of the world. </td>
                </tr>
                <#if !config.hideDetail??>
                <tr><Td class="td_2"><div  class="more_2"><a href="#">More</a></div></Td></tr>
                </#if>
            </table>
        </li>
        </#list>
    </ul>
    <div class="turn_page">
        <table align="right">
            <tr>
                <td class="td_1"><a href="#">First</a> </td><td><  1 <a href="#">2</a> 3 4 5 ....</td><td class="td_1"><a href="#">Next</a></td><td class="td_1"> <a href="#">End</a></td>
                <td> 2/20Page </td>
                <td> to </td>
                <td><input type="text" class="ip_3">
                </td>
                <td> Page </td>
                <td><div class="ip_2_box"><input type="submit" class="ip_2" value="GO"></div>
                </td>
            </tr>
        </table>
    </div>
</div>
</#if>
<script type="text/javascript">
    $(function (){
        //autoImgSize($(".pic_box_2 img"));
    })
</script>