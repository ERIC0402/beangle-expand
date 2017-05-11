[#ftl]
[@b.head/]
	[@b.form name="zxksSearchForm"  action="!search" target="zxkslist" title="ui.searchForm" theme="search"]
		[@b.textfields names="zxks.ksap.name;考试名称,zxks.user.bjxh;编号,zxks.user.fullname;学员姓名"/]
		[@b.select name="zxks.finished" label="common.status" items={'true':'已交卷','false':'未交接'}  empty="状态"/]
		<input type="hidden" name="zxksid" value="${zxksid!}"/>
		<input type="hidden" name="ksapname" value="${(ksap.name)!}" />
	[/@]
	[@b.div id="zxkslist" href="!search?zxksid=${zxksid!}" /]
[@b.foot/]

<script>
		var zxksid=$('input[name="zxksid"]').val();
		var ksapname=$('input[name="ksapname"]').val();
		if(zxksid!=""&&ksapname!=""){
			$('input[name="zxks.ksap.name"]').attr("value",ksapname);
			$('input[name="zxks.ksap.name"]').removeClass();
		}
</script>