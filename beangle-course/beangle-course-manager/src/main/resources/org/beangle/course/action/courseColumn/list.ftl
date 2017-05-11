[#ftl]
[@b.head/]
<script type="text/javascript">
	bg.ui.load("tabletree");
	defaultColumn=1;
</script>
[@b.grid  items=courseColumns var="courseColumn" sortable="false"]
[@b.gridbar title="菜单列表"]
	bar.addItem("${b.text("action.new")}",action.add());
	bar.addItem("${b.text("action.edit")}",action.edit());
	bar.addItem("${b.text("action.delete")}",action.remove());
[/@]
	[@b.row]
		<tr [#if courseColumn??]id="${(courseColumn.code)!}"[/#if]>
		[@b.boxcol /]
		[@b.col property="name" title="序号" width="15%"]
		<div class="tier${(courseColumn.depth)!}" align="left">
		[#if (courseColumn.children?size==0)]
			<a href="#" class="doc"/>
		[#else]
			<a href="#" class="folder_open" id="${courseColumn.code}_folder" onclick="toggleRows(this)" ></a>
		[/#if]
			${(courseColumn.code)!}
		</div>
		[/@]
		[@b.col property="name" width="25%" title="栏目名称"/]
		[@b.col property="columnType.name" width="20%" title="栏目类型"/]
		[@b.col property="courseField" width="25%" title="课程字段"/]
		[@b.col width="8%" title="状态" ]
			[@c.sfyx courseColumn.enabled/]
		[/@]
		[@b.col width="10%" title="操作"]
			[@b.a href="!edit?courseColumnId=${(courseColumn.id)!}"]编辑[/@]
		[/@]
		</tr>
	[/@]
[/@]
<script type="text/javascript">
   //展开所有菜单
   displayAllRowsFor(1);
</script>
[@b.foot/]