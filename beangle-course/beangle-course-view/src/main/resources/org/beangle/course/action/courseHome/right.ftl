[#ftl]
[@b.head/]
      <div class="div_list_5">
       <h2 class="h2_style_5"> <span>${(introductionColumn.name)!}</span>
       [@b.a href="!courseInfoContent?courseId=${course.id!}&courseColumnId=${(introductionColumn.id)!}" 
				title="${(introductionColumn.name)!}" target="right_box"]更多[/@]
       </h2>
       <div class="BlankLine1"></div>
       <div style="padding:0px 10px">
        <div class="left_pic_2"> <img width="210" height="170" src="${base}/common/file.action?method=downFile&folder=${(course.previewImage)!}"> </div>
        <div class="right_text_2">
          <h2>${(course.name)!}</h2>
          	${(introductionContent)!}
			[@b.a href="!courseInfoContent?courseId=${course.id!}&courseColumnId=${(introductionColumn.id)!}" 
				title="${(introductionColumn.name)!}" target="right_box"]详细[/@]
		 </h2>
        </div>
        <div class="BlankLine1"></div>
        </div>
      </div>
      
	  [#include "right/recommendResource.ftl"/]
      [#include "right/hotResource.ftl"/]
	  [#include "right/relatedCourse.ftl"/]
