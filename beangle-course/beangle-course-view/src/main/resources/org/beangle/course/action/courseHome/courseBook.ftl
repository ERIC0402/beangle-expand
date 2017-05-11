[#ftl]
  <div class="div_list_5">
   <h2 class="h2_style_5"> <span>${(courseColumn.name)!}</span></h2>
   <div class="BlankLine1"></div>
   <div style="padding:0px 10px">
    <div class="right_text_1">
      	[@b.grid  items=courseBooks var="courseBook"]
		[@b.row]
			[@b.col width="33%" property="name" title="教材名称" /]
			[@b.col width="33%" property="author" title="作者" /]
			[@b.col width="33%" property="publishingHouse" title="出版社" /]
		[/@]
	[/@]
    </div>
    <div class="BlankLine1"></div>
    </div>
  </div>

