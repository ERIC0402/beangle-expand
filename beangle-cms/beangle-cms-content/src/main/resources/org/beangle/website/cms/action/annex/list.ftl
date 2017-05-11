[#ftl]
[@b.head/]
[#include "../comm.ftl"/]
[@b.grid  items=annexs var="annex" sortable="true"]
	[@b.gridbar]
		[#if isSelect??&&isSelect=1]
			bar.addItem("确认选择", "setAnnex(true)");
		[#else]
			bar.addItem("${b.text("action.new")}",action.add());
			bar.addItem("${b.text("action.modify")}",action.edit());
			bar.addItem("${b.text("action.delete")}",action.remove());
		[/#if]
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="40%" property="fileName" title="文件名" /]
		[@b.col width="20%" property="fileSize" title="文件大小" align="center"]
			[#if annex.fileSize??]
				[#if (annex.fileSize / (1024*1024)) gt 1]
					${annex.fileSize / (1024*1024)}MB
				[#elseif (annex.fileSize / 1024) gt 1]
					${annex.fileSize / (1024)}KB
				[#else]
					${annex.fileSize}B
				[/#if]
			[/#if]
		[/@]
		[@b.col width="20%" property="time" title="上传时间" align="center" ]
			${(annex.time?string("yyyy-MM-dd"))!}
		[/@]
		[#if isSelect??&&isSelect=1]
			[@b.col width="20%" property="open" title="是否公开" align="center"]
				[#if annex.open]
					[@resourceScope "1"/]
				[#else]
					[@resourceScope "0"/]
				[/#if]
			[/@]
		[#else]
			[@b.col width="10%" property="open" title="是否公开" align="center"]
				[#if annex.open]
					[@resourceScope "1"/]
				[#else]
					[@resourceScope "0"/]
				[/#if]
			[/@]
			[@b.col width="10%" title="操作" align="center"]
				[@downLoadFile "${annex.filePath!}" "${annex.fileName!}"/]
			[/@]
		[/#if]
	[/@]
[/@]
<script>
	var annexs = new Array();
 	[#list annexs as item]
 		annexs[${item_index}]={'id':'${item.id}','name':'${item.fileName}'};
 	[/#list]
    function setAnnex(flag){
        var annexIds = document.getElementsByName("annex.id");
      
        for(var i=0;i<annexIds.length;i++){
            if(annexIds[i].checked){
             	parent.$("#${selectId!}").append("<option selected value='"+annexIds[i].value+"'>"+annexs[i].name+"</option>");
            }
        }
        parent.jQuery.colorbox.close();
    }
</script>
[@b.foot/]