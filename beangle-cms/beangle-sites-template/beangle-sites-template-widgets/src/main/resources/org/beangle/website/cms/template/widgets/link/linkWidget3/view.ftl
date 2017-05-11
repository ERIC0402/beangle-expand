<script>           
    function change(){           
        var s=document.getElementById("linkOne");           
        window.open(s.value);           
    }    
</script>
<select id="linkOne" class="s_1" onchange="change()">
    <option>-------${(siteType.typeName)!'快速通道'}-------</option>
    <#if links??>
    <#list links as v>
    <option value="${v.url}">${v.name}</option>
    </#list>
    </#if>
</select>
<div style="display:none">&nbsp<a>a</a></div>