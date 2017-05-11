<#include "/widgetComm.ftl"/>
<div class="list_box_3">
    <div class="BlankLine3"></div>
    <h2 class="title_4">${(cc.name)!'SEIEE Today'}<div class="line_1"></div></h2>
    <#if cc??>
    <#if englishList??>
    <ul class="list_box_4">
        <#list englishList as english>
        <li>
            <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                    <td rowspan="2" class="pic_box_2_2 td_2"><img src="${frontbase}/${english.img!'img/nopic.gif'}" width="200" height="192"></td>
                    <td class="td_1 teacherInfo">
                        <h3><strong>Name：</strong>${english.name!}</h3>
                        <p><strong>Title：</strong>${english.title!}</p>
                        <p><strong>Office Phone：</strong>${english.officePhone!}</p>
                        <p><strong>Email：</strong>${english.email!}</p>
                        <p><strong>Research Field：</strong>${english.researchField!}</p>
                    </td>
                </tr>
                <tr><Td class="td_2"><div  class="more_2"><a href="<@url.detail c=cc v=english/>">Detailed</a></div></Td></tr>
            </table>
        </li>
        </#list>
    </ul>
    <#assign perfix><@url.columnPerfix c=cc/></#assign>
    <#assign suffix>.htm</#assign>
    <@wv.pageBarEn perfix=perfix  suffix=suffix/>
    </#if>
    <#else>
    <ul class="list_box_4">
        <#list 1..4 as v>
        <li>
            <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                    <td rowspan="2" class="pic_box_2_2 td_2"><img width="200" height="192" src="${base}/css/front/theme61/style_01/photo_3.jpg"></td>
                    <td class="td_1"><h3>Seminar Rooms</h3>
                        School of International Education of SJTU is a special school responsible for admission, registration and management of international students coming from all parts of the world. </td>
                </tr>
                <tr><Td class="td_2"><div  class="more_2"><a href="#">More</a></div></Td></tr>
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
    </#if> 
</div>
<script type="text/javascript">
    $(function (){
        autoImgSize($(".pic_box_2_2 img"));
    })
</script>