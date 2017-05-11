[#ftl]
<link rel="stylesheet" href="${base}/static/scripts/jquery-treeview/jquery.treeview.css" />
<script type="text/javascript" src="${base}/static/scripts/jquery-treeview/lib/jquery.cookie.js"></script>
<script type="text/javascript" src="${base}/static/scripts/jquery-treeview/jquery.treeview.js"></script>

[#macro columnTree datas formAction target]
<script>
    $(function (){
        $("#navigation").treeview({
            	collapsed: true
		});
        $("#navigation li div").eq(0).click();
    });
</script>
<style>
	.selectDiv{
			background-color:rgb(225, 236, 255);
			background-repeat:no-repeat;
			border: 2px solid #dddddd; 
			height: 490px;
			min-height:300px; 
			overflow-x: scroll; 
			overflow-y: scroll;
			padding: 3px; 
			line-height:15px;
		}
		.selectDiv ul li a{
			white-space:nowrap;
		}
		.selectDiv ul{
			background-color:rgb(225, 236, 255);
			background-repeat:no-repeat;
		}
</style>
	<div id="myColumns" name="myColumns">
		[#if (datas??&&datas?size>0)]
			<div class="selectDiv">
		    	<ul id="navigation">
					[#list datas as v]
						[#if v.orders?length == 2]
							<li>
								[@b.a href="!${formAction!}?column.id=${v.id}&enabledRefresh=0" target="${target!}"]${v.name!}[/@]
								<ul>
									[@showColumn pc=v datas=datas formAction=formAction target=target/]
								</ul>
							</li>
						[/#if]
					[/#list]
				</ul>
			</div>
		[/#if]
	</div>
[/#macro]

[#macro showColumn pc datas formAction target]
	[#list datas as v]				
		[#if v.columns?? &&  v.columns.id == pc.id]
			<li>
				[@b.a href="!${formAction!}?column.id=${v.id}&enabledRefresh=0" target="${target!}"]${v.name!}[/@]
				[#if v.node]
					<ul>
						[@showColumn pc=v datas=datas formAction=formAction target=target/]
					</ul>
				[/#if]
			</li>
		[/#if]
	[/#list]
[/#macro]

[#macro siteList formAction isColumnManager=false]
	<script type="text/javascript">
		function refreshcolumns(){
			[#if isColumnManager]
				var siteId = $("#siteId").val();
				bg.Go('${base}/cms/column!listandform.action?siteId='+siteId,'columnlist')
			[#else]
				bg.form.submit('siteSearchForm');
			[/#if]
			return false;
		}
	</script>
	[@b.form action="${formAction}" name="siteSearchForm" id="siteSearchForm" target="myColumns"]
		<table class="search-widget">
			<tr [#if hideSites??]style="display:none"[/#if]>
				<td align="center">
					<a style="cursor:pointer;" onclick="refreshcolumns();return false;">刷新</a>
					<select id="siteId" name="siteId" onchange="refreshcolumns();return false;" style="width:120px;">
						[#list sites?sort_by("code") as item]
							<option title="${item.name}" value="${item.id}" >${item.name}</option>
						[/#list]
					</select>
				</td>
			</tr>
		</table>
   [/@]
[/#macro]