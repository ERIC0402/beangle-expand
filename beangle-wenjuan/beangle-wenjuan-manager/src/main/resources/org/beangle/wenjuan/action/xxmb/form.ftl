[#ftl]
[@b.head/]
<script src="${base}/static/scripts/wenjuan/WenBenTiMu.js"></script>
<style type="text/css">
	.preview p{margin: 0; padding: 0;}
</style>
[@b.toolbar title=c.formTitle("选项模板", xxmb)]bar.addBack();[/@]
		[@b.form action="!save" title="选项模板" theme="list"]
			[#--
				[@b.field label="模板类型" required="true" ]
					<select  name="xxmb.mblx.id" style="width:160px;" >
						[#list template as item]
							<option value="${item.id}" [#if xxmb.mblx??&&xxmb.mblx.id==item.id]selected="selected"[/#if]>${item.name!}</option>
						[/#list]
					</select>
				[/@]
			--]
			[@b.textfield name="xxmb.mbmc" label="选项名称" value="${xxmb.mbmc!}" required="true" maxlength="100" comment="长度不超过100个字"/]
			[@b.textarea label="选项内容" cols="50" rows="5" id="xxnr" name="xxmb.xxnr" value="${xxmb.xxnr!}" required="true" comment="选项内容以换行作为分隔，每个换行表示一个选项"/]
			[@b.field]
				<div id="preview" class="preview">
				</div>
			[/@]
			[@b.radios label="是否有效"  name="xxmb.enabled" value=xxmb.zt items="1:有效,0:无效"/]
			
			[@b.formfoot]
				<input type="hidden" name="xxmb.id" value="${xxmb.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
			[/@]
		[/@]
<script type="text/javascript">
	var wb = new WenBenTiMu();
	$(function (){
		$("#xxnr").keyup(function (){
			if(this.value == ""){
				return;
			}
			var os = this.value.split("\n");
			var index = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"];
			var preview = $("#preview");
			preview.html("");
			if(os.length>index.length){
				alert("超过选项可添加的最大限制，请删除一行");
				return;
			}
			for(var i = 0; i < os.length; i++){
				var p = $("<p>");
				p.html(index[i] + "." + os[i]);
				preview.append(p);
			}
			//wb.preview($("#preview"), this.value);
		});
		$("#xxnr").keyup();
	});
</script>
[@b.foot/]