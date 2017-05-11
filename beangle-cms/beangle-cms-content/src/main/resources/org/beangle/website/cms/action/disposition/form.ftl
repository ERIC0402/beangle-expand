[#ftl]
[@b.head/]
[@b.toolbar title="留言处理"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="留言处理"]
		[@b.form action="!save" title="留言信息" theme="list"]
			[@b.field label="留言人"]
				${(disposition.onlineMess.person)!}
			[/@]
			[@b.field label="留言时间"]
				${(disposition.onlineMess.time?string("yyyy-MM-dd"))!}
			[/@]
			[@b.field label="联系邮箱"]
				${(disposition.onlineMess.linkEmail)!}
			[/@]
			[@b.field label="联系电话"]
				${(disposition.onlineMess.linkPhone)!}
			[/@]
			[@b.field label="主题"]
				${(disposition.onlineMess.title)!}
			[/@]
			[@b.field label="留言内容"]
				[#if disposition.onlineMess.content??]
					${(disposition.onlineMess.content?replace("\n","<br>"))!}
				[/#if]
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
			[@b.textarea label="处理意见" cols="50" rows="6" name="disposition.views" value="${disposition.views!}" maxlength="1000" required="true"/]
			[@b.formfoot]
				<input type="hidden" name="disposition.id" value="${disposition.id!}" />
				[@b.redirectParams/]
				[@b.submit value="action.submit"/]
			[/@]
		[/@]
	[/@]
[/@]
[@b.foot/]