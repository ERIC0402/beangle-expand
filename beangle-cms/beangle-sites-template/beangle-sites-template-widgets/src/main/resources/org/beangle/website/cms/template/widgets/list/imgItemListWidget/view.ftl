<#include "/widgetComm.ftl"/>
<div class="c_Right_2">
    <div class="list_box_3 border_right_1">
        <div class="position_bar">
            <#include "/comm/positionAndTitle.ftl"/>
            <#if contents??>
            <ul class="list_box_4 list_box_4_2">
                <#list contents as v>
                <li>
                    <table width="100%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td valign="top" class="pic_box_2" width="240">
                                <a href="<@url.content cc=v/>">
                                    <img class="autoSize" src="<@url.getTitleImage cc=v/>">
                                </a>
                            </td>
                            <td valign="top" style="word-wrap:break-all;">
                                <h3>&middot; 
                                    <#if !config.noDetail??><a href="<@url.content cc=v/>" title="<@wv.contentTitle cc=v/>"></#if>
                                        <#if (v.content.title!)?length gt 50>
                                        ${v.content.title?substring(0,50)}...
                                        <#else>
                                        <@wv.contentTitle cc=v/>
                                        </#if>
                                        <#if !config.noDetail??></a></#if>
                                </h3>
                                <#if (v.content.abstracts!)?length gt 120>
                                ${v.content.abstracts?substring(0,120)}
                                <#else>
                                ${v.content.abstracts!}
                                </#if>
                            </td>
                        </tr>
                    </table>
                </li>
                </#list>
            </ul>
            <#assign perfix><@url.columnPerfix c=column/></#assign>
            <#assign suffix>.htm</#assign>
            <@wv.pageBar perfix=perfix  suffix=suffix/>
            <#else>
            <ul class="list_box_4 list_box_4_2">
                <#list 1..3 as v>
                <li>
                    <table width="100%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td valign="top" class="pic_box_2"><img src="${frontbase}/css/front/theme1/style_01/photo_3.jpg"></td>
                            <td valign="top"><h3>&middot; 洪发华：院长助理、主任</h3>
                                岗位职责：办学资源的核算、分配统计，年薪发放；办学经费的分配预算、及使用审批；公共用房管理、调配，教职工住房购房奖励；院机关设备的添置、调拨、报废预审；院专家办管理及使用调配；学院车辆管理；学院网站管理；学院工程教育中心工作。 <br>
                                电话：34205006 、 34205015 <br>
                                办公地点：电院三号楼-215 <br>
                                Email： fhhong@sjtu.edu.cn </td>
                        </tr>
                    </table>
                </li>
                </#list>
            </ul>
            <div class="turn_page">
                <#include "/comm/pageBar.ftl"/>
            </div>
            </#if>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function (){
        autoImgSize($(".autoSize"));
    })
</script>
