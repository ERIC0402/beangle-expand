[#ftl]
[@b.head/]
<script src="${base}/static/scripts/comm/DictTreeUtil.js"></script>
		[@b.form name="tiMuSearchForm"  action="!tiMuSearch" target="tiMulist" title="ui.searchForm" theme="search"]
			[@b.textfields names="tiMu.tmmc;题目名称"/]
			[@b.field label="所属题库" ]
				<select name="tiMu.sstk.id" id="tkId" onchange="setTmfl(this)">
					<option value="">...</option>
					[#list tiKuList as v]
						<option value="${v.id}" tmflid="${(v.tkfl.id)!}" [#if v.id == (tiKuId)!0]selected[/#if]>${v.tkmc}</option>
					[/#list]
				</select>
			[/@]
			[@b.select empty="..." name="tiMu.tmfl.id" id="tmfl" label="题目分类" option="id,name"/]
			[@b.select items=tmlxs empty="..." name="tiMu.tmlx.id" label="题目类型" option="id,name"/]
		[/@]
		[@b.div id="tiMulist" href="!tiMuSearch" /]
<script>
	var tmfl = new Array();
	//[#list tmfls as v]	
	tmfl.push({id:"${v.id}", name:"${v.name}", pid: "${(v.parent.id)!}"});
	//[/#list]
	function setTmfl(select){
		var tk ;
		if(select != undefined){
			tk = $(select);
		}else{
			tk = $("#tkId");
		}
		var tmflid = tk.find("option:selected").attr("tmflid");
		var tmflSelect = $("#tmfl");
		if(tmflid != null){
			tmflSelect.find("option").remove();
			var dt = new DictTreeUtil();
			dt.init(tmfl, tmflSelect);
			dt.addOptoinByPid(tmflid);
		}else{
			tmflSelect.find("option").remove();
		}
		dt.addDefault("...");
	}
</script>
[@b.foot/]
