<#include "/widgetComm.ftl"/>
<#setting locale='en'>
<div class="BlankLine1"></div>
<div class="box_1 ">
    <div class="box_1_left_shadow">
        <div class="list_box_1">
            <div class="p_1">
                <h3 class="title_1"><font></font><span>${config.title!'Events'}</span></h3>
                <#if column??>
                <#if contents??>
                <div class="p_2">
                    <ul class="list_2">
                        <#list contents as item>
                        <li>
                            <table>
                                <tr>
                                    <td><div class="week_1">${(item.publishDate)?string("EEE")}</div>
                                        <div class="date_3">${(item.publishDate)?string("MMM.dd")}</div></td>
                                    <td><div class="text_box_3"><a href="<@url.content cc=item/>"><@wv.contentTitle cc=item/></a></div>
                                        <div class="position_1">${((item.content.abstracts)!)?replace('\n','<br>')}</div></td>
                                </tr>
                            </table>
                        </li>
                        </#list>
                        </#if>
                </div>
                <div class="more_1"><a href="<@url.column c=column />">More &raquo;</a></div>
                <#else>
                <div class="p_2">
                    <ul class="list_2">
                        <li>
                            <table>
                                <tr>
                                    <td><div class="week_1">sun.</div>
                                        <div class="date_3">Oct.24</div></td>
                                    <td><div class="text_box_3"><a href="#">Life in occupied Palestine Palestine Palestine</a></div>
                                        <div class="time_1">7:00-8:00 PM</div>
                                        <div class="position_1">Jiao Tong Library</div></td>
                                </tr>
                            </table>
                        </li>
                        <li>
                            <table>
                                <tr>
                                    <td><div class="week_1">sun.</div>
                                        <div class="date_3">Oct.24</div></td>
                                    <td><div class="text_box_3"><a href="#">Life in occupied Palestine </a></div>
                                        <div class="time_1">7:00-8:00 PM</div>
                                        <div class="position_1">Jiao Tong Library</div></td>
                                </tr>
                            </table>
                        </li>
                        <li>
                            <table>
                                <tr>
                                    <td><div class="week_1">sun.</div>
                                        <div class="date_3">Oct.24</div></td>
                                    <td><div class="text_box_3"><a href="#">Life in occupied Palestine </a></div>
                                        <div class="time_1">7:00-8:00 PM</div>
                                        <div class="position_1">Jiao Tong Library</div></td>
                                </tr>
                            </table>
                        </li>
                    </ul>
                </div>
                <div class="more_1"><a href="#">More &raquo;</a></div>
                </#if>
            </div>
        </div>
    </div>
    <div class="box_1_bottom_shadow"></div>
</div>
