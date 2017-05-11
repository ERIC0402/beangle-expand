[#ftl]
[@b.head/]
[@b.grid  items=tiMus var="tiMu"]
	[@b.gridbar]
		bar.addItem("选择题目", "addTiMu()");
		bar.addItem("选择题目并关闭", "addTiMuAndClose()");
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="40%" property="tmmc" title="题目名称"/]
		[@b.col width="20%" property="tmfl.name" title="题目分类"/]
		[@b.col width="10%" property="tmlx.name" title="题目类型" /]
		[@b.col width="30%" property="sstk.tkmc" title="所属题库" /]
	[/@]
[/@]
<div style="display: none">
	<div id="msg">添加成功。</div>
</div>
<script type="text/javascript">
 	var tiMus = new Array();
    [#list tiMus as tiMu]
     	tiMus[${tiMu.id}]={'id':'${tiMu.id}','tmmc':'${tiMu.tmmc!}'};
    [/#list]
    
    function addTiMu(){
    	var data = new Array();
    	$("[name='tiMu.id']:checked").each(function (){
    		data.push(tiMus[this.value]);
    	});
    	parent.addTiMu(data);
    	showmsg();
    }
	
	function addTiMuAndClose(){
		addTiMu();
		parent.jQuery.colorbox.close();
	}
    
    function showmsg(){
    	var val = this.value;
		var os = {
				show: { effect: 'drop', direction: "down" },
				hide: "explode",
				position: "center",
				title:"提示信息",
				resizable:false
			};
		$( "#msg" ).dialog(os);
		setTimeout(function() {
			$( "#msg" ).dialog( "close" );
			}, 1000 );
    }
  </script>
[@b.foot/]