<#include "/widgetComm.ftl"/>
<div class="list_box_3">
    <#if english??>
    <div class="BlankLine3"></div>
    <h2 class="title_4"><#if english.department??>${english.department!}<#else><#if site??>SEIEE Today </#if></#if><div class="line_1"></div></h2>
    <div class="list_box_7">

        <table width="100%">
            <tr>
                <td valign="top" width="35%" align="center"><img height="224" class="pic_box_5" src="${base}/${english.img!'img/nopic.gif'}"></td>
                <td valign="top" class="box_6">
                    <ul>
                        <li><strong>Name：</strong>${english.name!}</li>
                        <li><strong>Title：</strong>${english.title!}</li>
                        <li><strong>Office：</strong>${english.office!}</li>
                        <li><strong>Office Phone：</strong>${english.officePhone!}</li>
                        <li><strong>Email：</strong>${english.email!}</li>
                        <li><strong>Website：</strong>${english.website!}</li>
                    </ul>
                </td>
            </tr>
        </table>

        <h4>Research Field</h4>
        <div class="box_7">
            ${english.researchField!}
        </div>

        <h4>Education</h4>
        <div class="box_7">
            ${english.education!}
        </div>

        <h4>Work experience</h4>
        <div class="box_7">
            ${english.workExperience!}
        </div>

        <h4>Research</h4>
        <div class="box_7">
            ${english.research!}
        </div>

        <h4>Awards and Honors</h4>
        <div class="box_7">
            ${english.awardsAndHonors!}
        </div>

        <h4>Teaching</h4>
        <div class="box_7">
            ${english.teaching!}
        </div>

        <h4>Publications</h4>
        <div class="box_7">
            ${english.publications!}
        </div>

        <h4>Others</h4>
        <div class="box_7">
            ${english.others!}
        </div>
        <#else>
        <#if site??>
        <table width="100%">
            <tr>
                <td valign="top" width="35%" align="center"><img class="pic_box_5" src="${base}/img/nopic.gif"></td>
                <td valign="top" class="box_6"><ul>
                        <li><strong>Name：</strong>某某某</li>
                        <li><strong>Job number：</strong>119</li>
                        <!--<li><strong>Date of birth：</strong>1965年9月4日</li>
                        <li><strong> ：</strong>男</li>
                        <li><strong>Nation：</strong>汉</li>
                        <li><strong>PhD supervisor/Master Instructor：</strong>硕导</li>-->
                        <li><strong>Education：</strong>本科</li>
                        <li><strong>Email：</strong>double@hotmail.com</li>
                    </ul></td>
            </tr>
        </table>

        <h4>Profile</h4>
        <div class="box_7">
            School of International Education of SJTU is a special school responsible for admission, registration and management of international students coming from all parts of the world.
        </div>

        <h4>Profile</h4>
        <div class="box_7">
            School of International Education of SJTU is a special school responsible for admission, registration and management of international students coming from all parts of the world.
        </div>

        <h4>Profile</h4>
        <div class="box_7">
            School of International Education of SJTU is a special school responsible for admission, registration and management of international students coming from all parts of the world.
        </div>
    </div>
    </#if>
    </#if>
    <div class="BlankLine1"></div>

</div>