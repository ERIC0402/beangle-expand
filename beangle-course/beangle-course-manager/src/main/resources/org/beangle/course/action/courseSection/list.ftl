[#ftl]
[@b.head/]
<script type="text/javascript">
	bg.ui.load("tabletree");
	defaultColumn=1;
</script>
[@b.toolbar title="课程" entityId=course.id!0]bar.addBack();[/@]
[#include "../course/nav.ftl"/]
[@b.grid  items=courseSections var="courseSection" sortable="false"]
[@b.gridbar title="菜单列表"]
	bar.addItem("${b.text("action.new")}",action.add());
	bar.addItem("${b.text("action.edit")}",action.edit());
	bar.addItem("${b.text("action.delete")}",action.remove());
[/@]
	[@b.row]
		<tr [#if courseSection??]id="${(courseSection.code)!}"[/#if]>
		[@b.boxcol /]
		[@b.col property="name" title="序号" width="15%"]
		<div class="tier${courseSection.depth}" align="left">
		[#if (courseSection.children?size==0)]
			<a href="#" class="doc"/>
		[#else]
			<a href="#" class="folder_open" id="${courseSection.code}_folder" onclick="toggleRows(this)" ></a>
		[/#if]
			${(courseSection.code)!}
		</div>
		[/@]
		[@b.col property="name" width="35%" title="章节名称"/]
		[@b.col  width="45%" title="相关资源文件"]
			[#list courseSection.courseContentSet as courseContent]
				[#list courseContent.courseResource.resourceFiles as resourceFile]
					<a href="#">${(courseContent.courseResource.zybt)!} ${(resourceFile.fileName)!}   ${(courseContent.courseResource.zylx.name)!}</a>
					[#if resourceFile_has_next]<br>[/#if]
				[/#list]
			[/#list]
		[/@]
		[@b.col width="10%" title="操作"]
			[@b.a href="!edit?courseId=${(course.id)!}&courseSectionId=${(courseSection.id)!}"]编辑[/@]
		[/@]
		</tr>
	[/@]
[/@]
<script type="text/javascript">
   //展开所有菜单
   displayAllRowsFor(1);
</script>
[@b.foot/]