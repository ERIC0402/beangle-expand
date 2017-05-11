[#ftl]
<div id="left_menu_3_box" class="left_menu_p">
    <div id="left_menu_3" >
      <div id="left_menu_div">
      	[#list courseColumns?sort_by("code")  as courseColumn]
	        <h2 class="menu_header_2"><span>${(courseColumn.name)!}</span></h2>
	        <ul class="menu_ul_2">
	        	[#list courseColumn.children as children]
	        		[#if course.courseLmzh.kclms?seq_contains(children)&&children.enabled]
			        	<li>
				        	[@b.a href="!${(children.url)!}?courseId=${course.id!}&courseColumnId=${(children.id)!}" title="${(children.name)!}" target="right_box"]
				        		${(children.name)!}
							[/@]
						</li>
					[/#if]
	        	[/#list]
	        	[#if courseColumn.name=='课程资源']
					[#list kczylxs as kczylx]
		        		<li>
		        		[@b.a href="!courseContent?courseId=${course.id!}&courseColumnId=${(courseColumn.id)!}&kczylx.id=${(kczylx.id)!}" target="right_box"]
		        			${(kczylx.name)!}
		        		 	<strong class="f_6">(${kczyCountMap[kczylx.id?string]?default("0")})</strong>
		        		[/@]
		        		</li>
		        	[/#list]
				[/#if]
	        </ul>
        [/#list]
        </div>
    </div>
    
     <div class="BlankLine1"></div>
      <div class="div_list_6">
        <h2><span>相关课程</span></h2>
        <ul class="menu_ul_2">
        [#list course.relatedCourses as relatedCourse]
        	<li><a href="/course/course-home!index.action?courseId=${(relatedCourse.courseRelated.id)!}" target="_blank">${(relatedCourse.courseRelated.name)!}</a></li>
          [/#list]
        </ul>
 	 </div>
</div>