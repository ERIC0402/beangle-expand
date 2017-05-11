<#include "/widgetComm.ftl"/>
<div class="c_Right_2">
    <div class="list_box_3 border_right_1">
        <div class="position_bar">
            <div class="p_bg_3">
                <div class="p_bg_2"></div>
                <@wv.position/>
            </div>
            <div class="BlankLine3"></div>

            <h2 class="title_4">${(cc.name!'教师列表')!}</h2>
            <#if cc??>
            <ul class="list_box_4">
                <#list teacherList as t>
                <li>
                    <table width="100%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td valign="top" class="pic_box_2"><a href="<@url.detail c=cc v=t/>"><img src="${frontbase}/${t.src!'img/nopic.gif'}"></a></td>
                            <td valign="top"><a href="<@url.detail c=cc v=t/>"><h3>&middot;${t.xm!}:${t.jszw!}</a></h3>  
                                电话:${t.bgsdh!}<br>
                                办公地点:${t.bgsdz!}<br>
                                Email:${t.dzyx!}</td>
                        </tr>
                    </table>
                </li>
                </#list>
            </ul>
            <#assign perfix><@url.columnPerfix c=cc/></#assign>
            <#assign suffix>.htm</#assign>
            <@wv.pageBar perfix=perfix  suffix=suffix/>
            <#else>
            <ul>
                <#list 1..8 as v>
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
                <table align="right">
                    <tr>
                        <td><a href="#">首页</a> <  1 <a href="#">2</a> 3 4 5 ....下一页 末页</td>
                        <td> 2/20页 </td>
                        <td> 到 </td>
                        <td><input type="text" class="ip_3">
                        </td>
                        <td> 页 </td>
                        <td><input type="submit" class="ip_2" value="确 定">
                        </td>
                    </tr>
                </table>
            </div>
            </#if>
        </div>
    </div>
</div>
