<#include "/widgetComm.ftl"/>
<#if column??>
<div class="list_box_3">
    <div class="BlankLine3"></div>
    <h2 class="title_4">${column.name!}<div class="line_1"></div></h2>
    <div class="list_box_5">
        <ul>
            <#list contents as v>
            <li><a href="<@url.content cc=v/>"><font>></font><@wv.contentTitle cc=v/></a><span>[${(v.publishDate)?string("yyyy-MM-dd")}]</span></li>
            </#list>
        </ul>
         <#assign perfix><@url.columnPerfix c=column/></#assign>
            <#assign suffix>.htm</#assign>
        <@wv.pageBarEn perfix=perfix  suffix=suffix/>
    </div>
    <div class="BlankLine1"></div>
</div>
<#else>
<div class="list_box_3">
    <div class="BlankLine3"></div>
    <h2 class="title_4">Seacher <div class="line_1"></div></h2>
    <div class="list_box_5">
        <ul>
            <li>    <a href="#"><font>></font> Professor Liu Niancai Gave a Report at UNESCO Global Forum</a>                                   <span>[2011-05-25]</span></li>

            <li>    <a href="#"><font>></font> Professor Liu Niancai Gave a Report at UNESCO Global Forum</a>                                   <span>[2011-05-25]</span></li>

            <li>    <a href="#"><font>></font> Professor Liu Niancai Gave a Report at UNESCO Global Forum</a>                                   <span>[2011-05-25]</span></li>

            <li>    <a href="#"><font>></font> Professor Liu Niancai Gave a Report at UNESCO Global Forum</a>                                   <span>[2011-05-25]</span></li>

            <li>    <a href="#"><font>></font> Professor Liu Niancai Gave a Report at UNESCO Global Forum</a>                                   <span>[2011-05-25]</span></li>

        </ul>
    </div>
    <div class="BlankLine1"></div>
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