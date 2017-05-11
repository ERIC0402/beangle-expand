[#ftl]
[#import "/template/list/utils.ftl" as lu/]
[@lc.group title="基本信息"]
	[@b.textfield name="tiMu.tmmc" id="tmmc" label="题目名称" required="true" value="${tiMu.tmmc!}" style="width:500px;" maxlength="100" comment="题目问答处用“（）”标识"/]
	[#--[@b.textarea label="题干" cols="50" rows="3" name="tiMu.tmnr" value="${tiMu.tmnr!}" maxlength="500" comment="长度不超过500个字"/]
	--]
	[@b.field label="题干"]
		[@lu.ckeditor id="y"  name="tiMu.tmnr" value="${(tiMu.tmnr?string)!}"/]
		[/@]
	[#if !noTiku??]
	[@b.field label="所属题库" required="true"]
		<select title="所属题库" name="tiMu.sstk.id" id="tkIdform" onchange="setTmflForm(this)" style="width:300px">
			<option value="">请选择</option>
			[#list tiKuList as v]
				<option value="${v.id}" tmflid="${(v.tkfl.id)!}" [#if v.id == (tiMu.sstk.id)!0]selected[/#if] title="${v.tkmc}">[@c.substring str=v.tkmc!'' mx=20/]</option>
			[/#list]
		</select>
	[/@]
	[@b.select empty="..." name="tiMu.tmfl.id" val="${(tiMu.tmfl.id)!}" id="tmflform" label="题目分类" option="id,name"/]
	[/#if]
	[@b.field label="题目类型" required="true"]
		<select title="题目类型" name="tiMu.tmlx.id" id="tmlxId" style="width:160px;"  onchange="typechange(this)">
			<option value="" >请选择...</option>
			[#list tmlxs as item]
				<option value="${item.id}" [#if tiMu.tmlx??&&tiMu.tmlx.id==item.id]selected="selected"[/#if]>${item.name!}</option>
			[/#list]
		</select>
	[/@]
	[@b.radios empty="请选择..." name="tiMu.tmnd.id" value="${(tiMu.tmnd.id)!5361}" label="题目难度" items=tmnds option="id,name"/]
	[@b.radios empty="请选择..." name="tiMu.syfw.id" value="${(tiMu.syfw.id)!5420}" label="适用范围" items=syfws option="id,name"/]
	[#--
	[@b.field label="上级题目" id="sjtm" ]
		<select id="sjtm"  name="tiMu.sjtm.id" style="width:160px;" onchange="sjtmchange(this)">
			<option value="" >请选择...</option>
			[#if sjtms??]
				[#list sjtms as item]
					<option value="${item.id}" sstkId="${(item.sstk.id)!}" tmlxId="${(item.tmlx.name)!}" [#if tiMu.sjtm??&&tiMu.sjtm.id==item.id]selected="selected"[/#if]>${item.tmmc!}</option>
				[/#list]
			[/#if]
		</select>
	[/@]
	--]
		[@b.field label="相关图片" ]	
		 	<img id="albumImg" src="${base}/common/file.action?method=downFile&folder=${tiMu.pic!}" style="display: none"/>
	        <div style="margin:3px 0;">
	            <input id="file_upload" name="file_upload" type="file" />
	        </div>
		[/@]
		[@b.radios name="tiMu.enabled" label="是否有效" value=tiMu.enabled items="1:有效,0:无效"/]
		[/@lc.group]
		[@lc.group title="选项相关" id="xxxg"]
			[@b.field label="子题目" required="true" id="wgztm" style="display:none"]
				<textarea cols="50" rows="10" id="wgztmnr" name="tiMu.wgztm" title="子题目">${(tiMu.wgztm)!}</textarea>
		[/@]
		[#--
			[@b.field label="选项排列方式" required="true" class="ytmxx"]
				<select name="tiMu.xxplfs.id" id="xxplfsId" title="选项排列方式" style="width:160px;">
					<option value="">请选择</option>
					[#list xxplfses as item]
						<option value="${item.id}" [#if tiMu.xxplfs??&&tiMu.xxplfs.id==item.id]selected="selected"[/#if]>${item.name!}</option>
					[/#list]
				</select>
			[/@]
		--]
		[@b.radios id="xxsfgd" name="tiMu.xxsfgd" label="选项顺序是否固定" value=tiMu.enabled items="1:是,0:否" class="ytmxx"/]
		[@b.field label="选项模板"  class="ytmxx"]
			<select  name="tiMu.xxmb.id" id="xxmbId" style="width:160px;"  onchange="xxmbchange(this)">
				<option value="">无</option>
				[#list xxmbs as item]
					<option value="${item.id}" mbxxnr="${(item.xxnr)!}" [#if tiMu.xxmb??&&tiMu.xxmb.id==item.id]selected="selected"[/#if]>${item.mbmc!}</option>
				[/#list]
			</select>
		[/@]
		[@b.field label="选项内容" required="true" class="ytmxx"]
			<textarea cols="50" rows="4" id="xxnr" name="tiMu.xxnr" title="选项内容">${(tiMu.xxnr)!}</textarea>
			<div>选项内容以换行作为分隔，每个换行表示一个选项</div>
			<div id="preview" class="preview">
				<p>&nbsp</p>
			</div>
		[/@]
		[@b.textarea label="题目答案" cols="50" rows="3" name="tiMu.zqda" value="${tiMu.zqda!}" comment="" maxlength="5000"/]
		[#--[@b.textarea label="试题解析" cols="50" rows="3" name="tiMu.stjx" value="${tiMu.stjx!}" maxlength="500" comment="长度不超过500个字"/]
		--]
		[@b.field label="试题解析"]
		[@lu.ckeditor id="x"  name="tiMu.stjx" value="${(tiMu.stjx?string)!}"/]
		[/@]
		
[/@lc.group]
<script type="text/javascript">
	function typechange(select){
        var tmlx = $(select);
        var id = tmlx.find("option:selected").text();
        if(id=="普通节点"){
			$("#sjtm").hide();
			$("#xxxg").hide();
		}else if(id=="网格题"){
			$("#sjtm").hide();
			$("#xxxg").show();
			$("#wgztm").show();
			$("#zqda").hide();
		}else if(id=="填空题" || id=="简答题"){
			$("#sjtm").show();
			$("#xxxg").show();
			$(".ytmxx").hide();
			$("#wgztm").hide();
			$("#zqda").show();
		}else{
			$("#sjtm").show();
			$("#xxxg").show();
			$(".ytmxx").show();
			$("#wgztm").hide();
			$("#zqda").show();
		}
		
		if(id == "填空题"){
			$("#zqdats").html("如有多个答案的请用“，”隔开");
		}else{
			$("#zqdats").html("");
		}
		
		if(id == "判断题"){
			$("[name='tiMu.xxsfgd'][value='1']").click();
		}else{
			$("[name='tiMu.xxsfgd'][value='0']").click();
		}
	}
	
	var tmlxinit = "${(tiMu.tmlx.id)!}";
	if(tmlxinit != ""){
		typechange($("#tmlxId"));
	}
	
	function sjtmchange(select){
        var sjtm = $(select);
        var tmlxId = sjtm.find("option:selected").attr("tmlxId");
        if(tmlxId=="网格题"){
			$("#xxxg").hide();
			$("#zqda").hide();
		}else{
			$("#xxxg").show();
			$("#zqda").show();
		}
	}
	
	function xxmbchange(select){
        var xxmb = $(select);
        var val = xxmb.val();
        if(val != ""){
        	$("#xxnr").val(xxmb.find("option:selected").attr("mbxxnr"));
        }else{
        	$("#xxnr").val("");
        }
        $("#xxnr").keyup();
	}
	
	$(function (){
		$("#xxnr").keyup(function (){
			var preview = $("#preview");
			preview.html("");
			if(this.value==""){
				return;
			}
			var os = this.value.split("\n");
			var index = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"];
			if(os.length>index.length){
				alert("超过选项可添加的最大限制，请删除一行");
				return;
			}
			for(var i = 0; i < os.length; i++){
				var p = $("<p>");
				p.html(index[i] + "." + os[i]);
				preview.append(p);
			}
		});
	});
	
	$("#xxnr").keyup();
	//[#if !noTiku??]
	var tmflform = new Array();
	//[#list tmfls as v]	
	tmflform.push({id:"${v.id}", name:"${v.name}", pid: "${(v.parent.id)!}"});
	//[/#list]
	function setTmflForm(select){
		var tk ;
		if(select != undefined){
			tk = $(select);
		}else{
			tk = $("#tkIdform");
		}
		var tmflid = tk.find("option:selected").attr("tmflid");
		var tmflSelect = $("#tmflform");
		
		if(tmflid != null){
			tmflSelect.find("option").remove();
			var dt = new DictTreeUtil();
			dt.init(tmflform, tmflSelect);
			dt.addOptoinByPid(tmflid);
		}else{
			tmflSelect.find("option").remove();
		}
		dt.addDefault("请选择");
		dt.select.val("");
		$("#sjtm").find("option").filter("[value!='']").hide();
		$("#sjtm").find("[sstkId='"+tk.val()+"']").show();
	}
	
	var tkIdform = '${(tiMu.sstk.id)!}';
	if(tkIdform != ""){
		setTmflForm($("#tkIdform"));
	}
	//[/#if]
	
	$("[name='tiMu.tmfl.id']").each(function (){
		var val = $(this).attr("val");
		if(val){
			this.value = val;
		}
	});
	
	$(function (){
        uploadimg({
            dir:"TIMU/${currentDate?string('yyyyMM')}",
            ofile:"${tiMu.pic!}",
            upBtn:"file_upload",
            imgNmae:"tiMu.pic",
            showImg:"albumImg",
            width:300
        });
    });

    
//     [#if tiMu.pic??]
    $("#albumImg").show();
//     [/#if]
</script>

<script type="text/javascript">
	function submit2(form, tmethod){
		tmethod && $("#tmethod").val(tmethod);
		bg.ui.load("validity2");
	    var a_fields = {};
	    addMorConidtion(a_fields);
	    //a_fields["#sectionId"] = new Validator2Str().require().getConfig();
	    a_fields["#tmlxId"] = new Validator2Str().require().getConfig();
	    a_fields["#tmmc"] = new Validator2Str().require().getConfig();
		var tmlx = $("#tmlxId").find("option:selected").text();
		var tmlxId = $("#sjtm").find("option:selected").attr("tmlxId");
		if(tmlxId != "网格题"){
			if(tmlx!="普通节点"){
				if(tmlx!="填空题" && tmlx!="简答题"){
			        //$("#xxplfsId").require();
	    			a_fields["#xxnr"] = new Validator2Str().require().getConfig();
		        }
		        if(tmlx=="网格题"){
	    			a_fields["#wgztmnr"] = new Validator2Str().require().getConfig();
		        }
		        //$("#zqda").require();
			}
		}
		var vform = new validator2(a_fields, "#" + form.id);
		res =vform.exec();
	    if(false == res) {
	        return false;
	    }
	        
		bg.form.submit(form,form.action,form.target,null,true);
	}
	
	function addMorConidtion(a_fields){
	
	}
	
</script>