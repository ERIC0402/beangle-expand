<script type="text/javascript">
    $(function (){
        $("#${toolBarId!'radio2'}").attr("checked",true);
        $( "#radio" ).buttonset();
        $("#radio [name='toolBar']").click(function (){
            var v = $(this);
            var href = v.attr("href");
            if(href == undefined){
                return;
            }
            if(v.attr("target")){
                window.open(href, "_blank");
            }else{
                location.href = href;
            }
        })
    })
</script>
<div class="toolBar">
    <div id="radio"> 
        <input type="radio" checked="checked" id="radio2" name="toolBar"
               href="${base}/cms/template/template!editLayout.action?templateId=${(template.id)!}"/>
        <label for="radio2">布局</label> 
        <input type="radio" id="radio3" name="toolBar"
               href="${base}/cms/template/template!editContent.action?templateId=${(template.id)!}"/>
        <label for="radio3">组件</label> 
<!--        <input type="radio" id="radio4" name="toolBar"/>
        <label for="radio4">主题</label> 
        <input type="radio" id="radio5" name="toolBar"/>
        <label for="radio5">页面背景</label> 
        <input type="radio" id="radio6" name="toolBar"/>
        <label for="radio6">自定义样式</label> -->
    </div>
</div>