<#include "/widgetComm.ftl"/>
<div class="c_Right_2">
    <div class="list_box_3 border_right_1">
        <div class="position_bar"> 

            <div class="p_bg_3">
                <div class="p_bg_2"></div>
                <@wv.position/>
            </div>

            <div class="article_box">
                <h2 class="title_5">院长致辞</h2>
                <hr size="1" class="line_2"></hr>
                <div class="article_content">
                    <#if teacher??>		              
                    <table width="100%">
                        <tr>
                            <td valign="top" width="30%" align="center"><img class="pic_box_3" src="${base}/${teacher.src!'img/nopic.gif'}"></td>
                            <td valign="top" class="list_box_6">
                                <ul>
                                    <li>姓名：${teacher.xm!'未知'}</li>
                                    <li>工号：${teacher.gh!'未知'}</li>
                                    <li>出生年月：${teacher.csrq!'未知'}</li>
                                    <li>性别：${teacher.xb!'未知'}</li>
                                    <li>民族：${teacher.mz!'未知'}</li>
                                    <li>博导/硕导：${teacher.bd_sd!'未知'}</li>
                                    <li>电子邮箱：${teacher.dzyx!'未知'}</li>
                                </ul>
                            </td>
                        </tr>
                    </table>

                    <div class="BlankLine2"></div>
                    <div class="list_box_7">
                        <div class="p_bg_3">
                            <div class="p_bg_2"></div>
                            <div class="p_bg"><span class="f1"><strong>&middot;个人简历</strong></span></div>
                            <div class="text_box">${teacher.grjl!'未知'}</div>
                        </div>
                    </div>
                    <!--<div class="BlankLine2"></div>  
                      <div class="list_box_7">
                              <div class="p_bg_3">
                                        <div class="p_bg_2"></div>
                                        <div class="p_bg"><span class="f1"><strong>&middot;个人简历</strong></span></div>
                                         <div class="text_box"> 遵循教育部本科教学优秀评价标准组织教学工作，深化教学改革。物理系在承担全校大面积工科物理理论、实验以及本专业教学的同时，依托全国工科物理教学基地这个平台，在教学体系、教学模式、教学方法、教学手段等方面进行了深层次的探索和实践。2001年先后获国家级和省市级教学成果一等奖各1项，出版教材7部，（其中获奖教材3部），并连续3年被学校评为优良教学状态系。 </div>
                              </div>
                      </div>-->

                    <#else>
                    <table width="100%">
                        <tbody><tr>
                                <td valign="top" width="30%" align="center"><img class="pic_box_3" src="${base}/css/front/theme1/style_01/temp_5.jpg"></td>
                                <td valign="top" class="list_box_6"><ul>
                                        <li>姓名：某某某</li>
                                        <li>工号：119</li>
                                        <li>出生年月：1965年9月4日</li>
                                        <li>性别：男</li>
                                        <li>民族：汉</li>
                                        <li>博导/硕导：硕导</li>
                                        <li>学历：本科</li>
                                        <li>电子邮箱：double@hotmail.com</li>
                                    </ul></td>
                            </tr>
                        </tbody></table>
                    <div class="BlankLine2"></div>
                    <div class="list_box_7">
                        <div class="p_bg_3">
                            <div class="p_bg_2"></div>
                            <div class="p_bg"><span class="f1"><strong>·个人简历</strong></span></div>
                            <div class="text_box"> 遵循教育部本科教学优秀评价标准组织教学工作，深化教学改革。物理系在承担全校大面积工科物理理论、实验以及本专业教学的同时，依托全国工科物理教学基地这个平台，在教学体系、教学模式、教学方法、教学手段等方面进行了深层次的探索和实践。2001年先后获国家级和省市级教学成果一等奖各1项，出版教材7部，（其中获奖教材3部），并连续3年被学校评为优良教学状态系。 </div></div>
                        <div class="BlankLine2"></div>
                        <div class="list_box_7">
                            <div class="p_bg_3">
                                <div class="p_bg_2"></div>
                                <div class="p_bg"><span class="f1"><strong>·个人简历</strong></span></div>
                                <div class="text_box"> 遵循教育部本科教学优秀评价标准组织教学工作，深化教学改革。物理系在承担全校大面积工科物理理论、实验以及本专业教学的同时，依托全国工科物理教学基地这个平台，在教学体系、教学模式、教学方法、教学手段等方面进行了深层次的探索和实践。2001年先后获国家级和省市级教学成果一等奖各1项，出版教材7部，（其中获奖教材3部），并连续3年被学校评为优良教学状态系。 </div></div>
                        </div>
                    </div>
                    </#if>
                </div>
            </div> 


            <div style="display:none">&nbsp<a>a</a></div>
