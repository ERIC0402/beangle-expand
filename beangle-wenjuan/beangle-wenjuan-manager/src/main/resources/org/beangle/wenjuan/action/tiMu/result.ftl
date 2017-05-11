[#ftl/]
[@b.head/]
[@b.toolbar title="导入结果"]
    bar.addPrint();
    bar.addBack();
[/@]
<table class="infoTable" align="center" width="100%">
   <tr><td colspan="2" align="center">导入结果</td></tr>
   <tr><td class="title">总计：</td><td>${(importer.success)!}</td></tr>
   <tr><td class="title">新增：</td><td>${(count)!}</td></tr>
   <tr><td class="title">更新：</td><td>${(ucount)!}</td></tr>
   <tr><td class="title">失败：</td><td>${(importer.fail)!}</td></tr>

</table>
[#if importResult.errs??&&importResult.errs?size>0]
	[@b.grid items=importResult.errs var="message"]
	    [@b.row]
	        [@b.col title="错误序号" width="10%"]${message_index + 1}[/@]
	        [@b.col title="行号" property="index" width="10%"]${(message.index + 2)!}[/@]
	        [@b.col title="错误内容" width="40%"][#if message.message?starts_with("error")]${b.text(message.message)}[#else]${message.message}[/#if][/@]
	        [@b.col title="错误值"][#list message.values as value]${value?default("")}[/#list][/@]
	    [/@]
	    <div style="text-align:center">请将所有的输入数据(包括日期,数字等)在导入文件中以文字格式存放。</div>
	[/@]
[/#if]
[@b.foot/]
