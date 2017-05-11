<#include "/widgetComm.ftl"/>
<script type="text/javascript">
    $(function (){
        $(".list_box_2_2 ul li").last().addClass("boder-bottom-none");
    })
</script>
<div class="c_Left_Bottom_Right">
    <div class="list_box_2_2">
        <h3 class="title_1">
            <div class="div_2"><span><span class="span_1">|</span>${config.title!'最新消息列表'}</span><a href="<#if column??><@url.column c=column /><#else>#</#if>" target="_blank"><font>&gt;</font> 更 多</a></div>
        </h3>
        <ul>
            <li style="display: none"><div><table>

                        <#assign lastDate=''>         
                        <#list contents! as item>
                        <#if lastDate!=item.publishDate?string("yyyy-MM-dd")>
                    </table>
                </div>
            </li>
            <li>
                <div class="date_box_1"><span>${item.publishDate?string("M")}月</span>
                    <div>${item.publishDate?string("dd")}</div>
                </div>
                <div class="text_box_1">
                    <table width="100%">
                        </#if>                        
                        <#assign lastDate=item.publishDate?string("yyyy-MM-dd")>
                        <#assign hasNext=false/>
                        <#if contents[item_index+1]?? && contents[item_index+1].publishDate?string("yyyy-MM-dd") == lastDate>
                        <#assign hasNext=true/>
                        </#if>
                        <tr>
                            <td valign="top" width="15"  class="rsaquo <#if hasNext>td_3</#if>">&rsaquo;</td>
                            <td class="td_2 <#if hasNext>td_3</#if>"><a href="<@url.content cc=item/>" target="_blank"><@wv.contentTitle cc=item/></a></td>
                        </tr>
                        </#list>            
                    </table>
                </div>
            </li>
        </ul>       
        &nbsp;
    </div>
</div>