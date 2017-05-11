[#ftl]
[@b.head/]
[@b.toolbar title="课程" entityId=course.id!0]bar.addBack();[/@]
[#include "../course/nav.ftl"/]
[@b.form action="!save"  title="课程资源信息" theme="list"]
	[@b.select label="课程章节" name="courseSection.id" value=(courseContent.courseSection.id)! style="width:200px;"  items=courseSections option="id,description" empty="请选择..." required="true"/]
	[#if courseContent.id??]
	[#else]
	[@b.field label="课程资源"]
	<table width="600px" border="1" cellspacing="0" cellpadding="0">
	    <thead>
			<tr>
			    <th>序号</th>
			    <th>资源名称</th>
			    <th>资源类型</th>
			    <th>操作</th>
			</tr>
	    </thead>
	    <tbody id="tbody">
	    </tbody>
	</table>
	<input type="button" value="添加资源" onclick="searchResource()"/>
	<table style="display: none">
		<tbody id="templateDiv">
			<tr>
				<td align="center">
					<input name="resourceIndex" value="" class="resourceIndex" type="hidden"/>
					<label class="seqno"></label>
				</td>
				<td align="center"><input name="resourceId{i}" class="resourceId" type="hidden"/><label class="zybt"></label></td>
				<td align="center"><label class="zylx"></label></td>
				<td align="center"><input type="button" value="删除" onclick="delLine(this)"/></td>
			</tr>
		</tbody>
	</table>
[/@]
[/#if]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="courseId" value="${course.id!}" />
		<input type="hidden" name="courseSectionOldId" value="${(courseSection.id)!}" />
		<input type="hidden" name="courseContentId" value="${(courseContent.id)!}" />
	[/@]
[/@]
<script language="JavaScript">
	function searchResource(){
		jQuery().colorbox(
		{
			iframe : true,
			width : "100%",
			height : "100%",
			href : "${base}/zyk/core/resource-select.action?method=index"
		});
	}
	
	var resourceLine = 0;
	function addResource(data){
		var maxNum = 0;
		$('input[name="resourceIndex"]').each(function(){
			var nn = $(this).val();
		   	if(nn!=''&&nn>maxNum){
	    	 	maxNum =nn;
	   		}
		});  
		
		resourceLine =maxNum;
		    
		for(var i in data){
			var d = data[i];
			
			//过滤重复题目
			var hastmid = false;
			$(".resourceId").each(function (){
				if(d.id == this.value){
					hastmid = true;
				}
			});
			if(hastmid){
				continue;
			}
			resourceLine++;
			var html = $("#templateDiv").html();
			var tbody = $("#tbody").html();
			html = html.replace(/\{i\}/ig, resourceLine);
			var tr = $(html);
			tr.find(".seqno").html(resourceLine);
			tr.find(".zybt").html(d.zybt);
			tr.find(".zylx").html(d.zylx);
			tr.find(".resourceId").val(d.id);
			tr.find(".resourceIndex").val(resourceLine);
			$("#tbody").append(tr);
		}
	}
	function delLine(btn){
		var tr = $(btn).parent().parent();
		tr.fadeOut(function (){
			tr.remove();
		});
	}
</script>
[@b.foot/]