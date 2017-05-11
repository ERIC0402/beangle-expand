[#ftl]
<div class="BlankLine1"></div>
  <div class="div_list_5">
    <h2 class="h2_style_5"> <span>相关课程</span><a href="#">更多</a> </h2>
   <div class="BlankLine2"></div>
   
   <div class="picbox2">
      <DIV id=control><A id="goL_2" href="javascript:void(0);">&nbsp;</A> <A id="goR_2" href="javascript:void(0);">&nbsp;</A></DIV>
      <DIV id=marquee2>
        <UL class="ul_style_2_4">
         [#list course.relatedCourses as relatedCourse]
          <LI>
      		 <div class="pic_box">
	      		 <a href="/course/course-home!index.action?courseId=${(relatedCourse.courseRelated.id)!}" target="_blank">
	      		 	<img src="${base}/common/file.action?method=downFile&folder=${(relatedCourse.courseRelated.previewImage)!}">
	      		 </a>
      		 </div>
             <a class="a_title_1" href="/course/course-home!index.action?courseId=${(relatedCourse.courseRelated.id)!}" target="_blank">${(relatedCourse.courseRelated.name)!}</a>
          </LI>
          [/#list]
        </UL>
      </DIV>
    </div>
    <div class="BlankLine1"></div>
  </div>