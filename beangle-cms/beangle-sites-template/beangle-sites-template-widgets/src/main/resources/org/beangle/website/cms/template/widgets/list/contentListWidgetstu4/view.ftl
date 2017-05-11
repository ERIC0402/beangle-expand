<#include "/widgetComm.ftl"/>
<#if !config.hideTopBlank??>
<div class="BlankLine3"></div>
</#if>
<div class="list_box_3">
          <h3 class="title_5_2">
            <span><#if contents??>当前位置:搜索&nbsp;"${keyWord!}"<#else>搜索栏目中无信息</#if></span>
          </h3>
 <hr class="line_2">
<h2 class="title_4" align="center">搜索结果</h2>
<ul class="list_box_5_2">
                        <#if contents??>
                        
                            <#list contents as v>
                            <#if !v_has_next>
				                <li style="border-bottom:none">
				            <#else>
				                <li> 
				           </#if>
                           <span>[${v.publishDate?date}]</span>
                            <a href="<@url.content cc=v/>" target="_blank">
                            <font>&rsaquo;</font><@wv.contentTitle cc=v/></a></li>
                            </#list>
       
                            </ul> 
                        <#assign perfix><@url.searchPerfix/></#assign>
                        <#assign suffix>.htm?keyWord=${keyWord}</#assign>
                        <@wv.pageBar perfix=perfix  suffix=suffix/>
                        <#else>
                        <#if !site??>
                        <li><span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（

以下简称学院）的前身可溯源电机专科。</a> </li>
            <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院

）的前身可溯源电机专科。</a> </li>
            <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院

）的前身可溯源电机专科。</a> </li>
            <li> <span>[2011.5.25]</span><a href="#"><font>&rsaquo;</font> 上海交通大学—上海交通大学电子信息与电气工程学院（以下简称学院

）的前身可溯源电机专科。</a> </li>
          
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
          </#if>
          
        </div>



        
        
        