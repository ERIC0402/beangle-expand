[#ftl]
[@b.head/]
[@b.toolbar title="留言管理"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="留言管理"]
		[@b.form action="!save" title="留言信息" theme="list"]
			[@b.textfield name="onlineMess.person" label="留言人" value="${onlineMess.person!}" required="true" maxlength="30"/]
			[@b.datepicker name="onlineMess.time" label="留言时间" value=onlineMess.time format="datetime" required="true"/]
			[@b.textfield name="onlineMess.linkEmail" label="联系邮箱" value="${onlineMess.linkEmail!}" required="true" maxlength="100"/]
			[@b.textfield name="onlineMess.linkPhone" label="联系电话" value="${onlineMess.linkPhone!}" required="true" maxlength="30"/]
			[@b.textfield name="onlineMess.title" label="主题" value="${onlineMess.title!}" required="true" maxlength="100"/]
			[@b.textarea label="留言内容" cols="50" rows="6" name="onlineMess.content" value="${onlineMess.content!}" maxlength="1000"/]
			[@b.field label="处理人"]
				<select id="clr" name="clr" style="width:155px;">
					<option value="">直接回复</option>
					[#list users?sort_by("fullname") as item]
					<option value="${item.id!}">${item.fullname!}</option>
					[/#list]
				</select>
				<input id="ffbtn" type="reset"  name="reset1" value="分发" class="ip1" onclick="fenfa(this.form)" style="display: none"/>
			[/@]
			[@b.field label="处理人意见"]
				[#if clryjs?size==0]无[/#if]
                [#list clryjs as v]
                	<div style="padding-left:125px;line-height:25px;">
	                	${(v.user.fullname)!}：
	                
		                [#if v.views??]
		                	${v.views?replace("\n","<br>")}
		                [/#if]
		                ([#if v.state]已处理[#else]未处理[/#if])
                	</div>
                [/#list]
			[/@]
			[@b.radios label="是否热点"  name="onlineMess.rd" value=onlineMess.rd items="1:是,0:否"/]
			[@b.radios label="是否显示"  name="onlineMess.visible" value=onlineMess.visible items="1:显示,0:不显示"/]
			[@b.textarea label="回复内容" cols="50" rows="6" name="onlineMess.backContent" value="${onlineMess.backContent!}" maxlength="1000"/]
			[@b.formfoot]
				<input type="hidden" name="onlineMess.id" value="${onlineMess.id!}" />
				<input type="hidden" name="onlineMess.ids" value="${onlineMess.id!}" />
				[@b.redirectParams/]
				[@b.submit value="action.submit"/]
				&nbsp;&nbsp;
				<input type="button" value="删除" onClick="remove(this.form);"/>
			[/@]
		[/@]
	[/@]
[/@]
  <script language="javascript" >
	  function fenfa(form){
	        form.action = "online-mess.action?method=fenfa";
	        bg.form.submit(form,form.action,form.target,null,true);
	    }
	    function remove(form){
	    	if(!confirm("删除后所有相关记录都将会删除，您是否确定要删除？")){
	    		return false;
	    	}
	        form.action = "online-mess.action?method=remove";
	        bg.form.submit(form,form.action,form.target,null,true);
	    }
	    $(function (){
	        var clr = $("#clr");
	        clr.change(function (){
	            var s = this.value == "" ? "none" : "";
	            $("#ffbtn").css({display:s});
	        });
	    })
  </script>
[@b.foot/]