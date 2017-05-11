<script type="text/javascript">
    $(function() {
        $(".selectLayoutOl" ).selectable({
            selected: function(event, ui) {
                $("#templateLayoutId").val($(ui.selected).attr("layoutId")); 
            }
        });
    });
    function submitLayoutForm(form){
        var layoutId = form["templateLayoutId"].value;
        if(layoutId == null){
            alert("请选择一个布局模板");
            return;
        }
        form.submit();
    }
</script>
<ol class="selectLayoutOl"> 
    <#list layouts as v>
    <li class="ui-state-default" layoutId="${v.id}">
        <img src="<@imgUrl url=v.img/>"/>
        <p>${v.name!}</p>
    </li> 
    </#list>
</ol> 
<div class="clearDiv"></div>
<form action="${base}/cms/template.action" method="post">
    <input type="hidden" name="method" value="saveLayout"/>
    <input type="hidden" name="templateLayoutId" id="templateLayoutId"/>
    <input type="hidden" name="template.id" value="${template.id}"/>
    <div><button onclick="submitLayoutForm(this.form)">确定</button></div>
</form>

