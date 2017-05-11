[#ftl]
  <div class="div_list_5">
   <h2 class="h2_style_5"> <span>${(courseColumn.name)!}</span></h2>
   <div class="BlankLine1"></div>
   <div style="padding:0px 10px">
    <div class="right_text_1">
      	[@b.grid  items=courseContents var="courseContent"]
		[@b.row]
			[@b.col width="20%" property="courseResource.zybt" title="资源名称" /]
			[@b.col width="20%" property="courseResource.zyfl.name" title="内容分类" /]
			[@b.col width="15%" property="kczylx.name" title="资源类型" /]
			[@b.col width="30%" property="courseSection.name" title="所属章节" /]
		[/@]
	[/@]
    </div>
    <div class="BlankLine1"></div>
    </div>
  </div>

