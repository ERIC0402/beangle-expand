[#ftl]
[@b.head/]
<script type="text/javascript">
	bg.ui.load("tabletree");
	defaultColumn=1;
</script>
[@b.grid  items=columns var="column" sortable="false"]
[@b.gridbar title="栏目列表"]
	bar.addItem("确认选择", "setColumn(true)");
[/@]
	[@b.row]
		<tr [#if column??] id="${column.orders}"[/#if]>
		[@b.boxcol /]
		[@b.col width="40%" property="name" title="名称"]
		<div class="tier${column.depth}" align="left">
		[#if !(column.node)]
			<a href="#" class="doc"/>
		[#else]
			<a href="#" class="folder_open" id="${column.id}_folder" onclick="toggleRows(this)" >   </a>
		[/#if]
			${column.name!}
		</div>
		[/@]
		[@b.col width="25%" property="orders" title="排序"/]
		[@b.col width="20%" property="type.name" title="栏目类型"/]
		[@b.col width="15%" property="enabled" width="10%" title="是否启用"]
			[#if column.enabled]
				启用
			[#else]
				禁用
			[/#if]
		[/@]
		</tr>
	[/@]
[/@]
<script type="text/javascript">
   //展开所有菜单
   displayAllRowsFor(1);
   
 	var columns = new Array();
 	[#list columns as item]
 		columns[${item_index}]={'id':'${item.id}','name':'${item.name}'};
 	[/#list]
    function setColumn(flag){
        var columnIds = document.getElementsByName("column.id");
        var id="";
        var name="";
      
        for(var i=0;i<columnIds.length;i++){
            if(columnIds[i].checked){
            	if(id!=""){
            		id+=",";
            		name+=",";
            	}
             	id += columnIds[i].value;
             	name +=columns[i].name;
             	
            }
        }
        parent.document.getElementById("${idValues!}").value=id;
        parent.document.getElementById("${nameValues!}").value=name;
        parent.jQuery.colorbox.close();
    }
  </script>
[@b.foot/]