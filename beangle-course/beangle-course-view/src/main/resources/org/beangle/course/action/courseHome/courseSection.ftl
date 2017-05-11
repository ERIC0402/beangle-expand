[#ftl]
[@b.head/]
<script type="text/javascript">
	bg.ui.load("tabletree");
	defaultColumn=1;
</script>
  <div class="div_list_5">
   <h2 class="h2_style_5"> <span>${(courseColumn.name)!}</span></h2>
   <div class="BlankLine1"></div>
   <div style="padding:0px 10px">
   <div class="right_text_1">
    
  <table width="100%" class="table_style_1">
	<tbody>
	<tr class="gridhead">
	<th width="40%">章节</th>
	<th width="60%">相关资源</th>
	</tr>
	[#list courseSections?sort_by("code") as courseSection]
	<tr class="grayStyle" id="${courseSection.code}">
			<td	style="display:none">
				<input type="checkbox" id="checkbox_${courseSection_index}"  value="${courseSection.id}">
			</td>
			<td>
			<div class="tier${courseSection.depth}">
				[#if courseSection.children?size==0]
				<a href="#" class="doc"></a>[#rt]
				[#else]
				<a href="#" class="folder_open" id="${courseSection.code}_folder" onclick="toggleRows(this);"></a>[#rt]
				[/#if]
				 ${(courseSection.code)!} ${(courseSection.name)!} 
			</div>
			</td>
			<td>
				[#list courseSection.courseContentSet as courseContent]
					[#list courseContent.courseResource.resourceFiles as resourceFile]
						${(resourceFile.fileName)!}   ${(courseContent.courseResource.zylx.name)!}
						[#if !resourceFile_has_next]<br>[/#if]
					[/#list]
				[/#list]
			</td>
		</tr>
		[/#list]
		</tbody>
	</table>
	<script type="text/javascript">
	   //展开所有菜单
	   displayAllRowsFor(1);
	</script>
	
    </div>
    <div class="BlankLine1"></div>
    </div>
  </div>

