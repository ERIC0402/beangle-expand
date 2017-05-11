<#if !config.hidePosition??>
<@wv.position/>
</#if>
<#assign titleStyle = config.titleStyle!'1'/>
<#if titleStyle == '1'>
<h2 class="title_4">${(column.name)!'栏目名称'}</h2>
<#elseif titleStyle == '2'>
<h3 class="title_1">
    <div class="div_2"><span><span class="span_1">|</span> ${(column.name)!'栏目名称'} </span></div>
</h3>
<#elseif titleStyle == '3'>
<h3 class="title_5_2">
    <span><span class="span_1">|</span> ${(column.name)!'栏目名称'} </span>
</h3>
<hr class="line_2">
<#elseif titleStyle == '4'>
<h3 class="title_5">
    <span><span class="span_1">|</span> ${(column.name)!'栏目名称'} </span>
</h3>
<#elseif titleStyle == '5'>
<div class="BlankLine3"></div>
<h2 class="title_4"> ${(column.name)!'栏目名称'}<div class="line_1"></div></h2>
</#if>