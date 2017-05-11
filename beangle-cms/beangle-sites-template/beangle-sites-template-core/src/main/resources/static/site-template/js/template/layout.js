function initLayout(){
    //tab初始化
    $("#layoutTabs").tabs();
    //编辑区tab初始化
    $("#editLayoutFormDiv").tabs();
    //表单初始化
    $('#layoutUpdateForm').ajaxForm(); 

    //格式化html
    do_js_beautify("templateLayoutTextarea");
    //初始化部件容器
    $("#layoutRoot div").each(function (){
        var v = $(this);
        v.attr("oclazz",v.attr("class"));
    });
    //标记浮动层及其父节点
    $("#layoutRoot div").each(function (){
        var div = $(this);
        if(div.css("float") == "left"){
            var div = $(this).addClass("floatLayout").parent();
            div.addClass("floatLayouts"); 
        }
    });
    $("#layoutRoot div[id^='layout']").each(function (){
        var v = $(this);
        if(v.attr("style") == undefined){
            v.attr("style","");
        }
        v.wrap($("<div style='"+v.attr("style")+"'></div>"));
        v.attr("ostyle",v.attr("style"));
        v.attr("style","");
        var editLayoutDiv = $("#editLayoutDiv .editLayoutDiv").clone();
        editLayoutDiv.attr("parentId", this.id);
        v.wrap(editLayoutDiv);
        if(!v.hasClass("floatLayout")){
            var toolbar = $("#editLayoutDiv .toolBar3").clone();
            if(v.hasClass("floatLayout")){
                toolbar.find(".edit").css("display", "none");
            }
            v.before(toolbar);
            toolbar.show();
        }
        if(!v.hasClass("floatLayouts")){
            var emptyDiv = $("#editLayoutDiv .emptyDiv").clone();
            v.after(emptyDiv);
        }
    });
    $("#layoutRoot").attr("parentId","layoutRoot").addClass("editLayoutDiv").append($("#editLayoutDiv .emptyDiv").clone());
    $(".emptyDiv" ).droppable({
        accept: ".litem",
        hoverClass: "ui-state-active",
        drop: function( event, ui ) {
            ui = $(ui.draggable[0]);
            var form = getLayoutForm(1);
            form["parentId"].value = $(this).parent(".editLayoutDiv").attr("parentId");
            form["data"].value = ui.attr("data");
            submitAjaxForm(form);
        }
    });
}
    
function saveLayout(content){
    var form = getLayoutForm(0);
    form.content.value = content;
    submitAjaxForm(form);
}
    
function editLayout(edit){
    var form = getLayoutForm(2);
    form["parentId"].value = $(edit).parents(".editLayoutDiv").attr("parentId");        
    var div = $("#" + form["parentId"].value);
    initEditLayoutForm("editLayoutFormSizes","size", div);
    initEditLayoutForm("editLayoutFormStyles","style", div);
    initEditLayoutForm("editLayoutFormClazzs","clazz", div);       
    $("#editLayoutFormDiv").dialog({
        minWidth:500,
        modal: true, 
        hide: 'fade',
        show: 'fade',
        title: '布局编辑',
        buttons: {
            "提交": function() {
                $(this).dialog("close"); 
                submitAjaxForm(form);
            } 
        }
    }); 
    $("#editLayoutFormDiv").tabs("option", "selected", 0);
    var div = $("#" + form["parentId"].value);
    if(div.hasClass("floatLayouts")){
            
    }else{
            
}
}
/**
 * 初始化布局编辑
 */
function initEditLayoutForm(divId,name, div){
        
    if(div.hasClass("floatLayouts")){
        div = div = div.children().children().children(".floatLayout").not(".clearDiv");
    }
    var fdiv = $("#" + divId);        
        
    fdiv.empty();
    for(var i = 0; i < div.length; i++){
        var c = $(div[i]);
        var value = "";
        if("size" == name){
            var width = c.style("width", "ostyle");
            var type = "px"
            if(width.indexOf("%") > 0){
                type = "percent";
                value = width.substr(0, width.length - 1);
            }else if(width == ""){
                type = "percent";
                value = 100;
            }else{
                value = width.substr(0, width.length - 2);
            }
            setTimeout(function (){
                $("#" + type).attr("checked","checked");
            }, 500);
        }else if("style" == name){
            value = c.find("div").attr("style");
        }else{
            value = c.find("div").attr("class");
        }
        if(value == undefined){
            value = "";
        }
        var p = "<p>列" + (i+1)+"：";
        p += "<input name='" +name+ "' value='"+value+"'/>";
        p += "</p>";
        fdiv.append($(p));
    }
}
    
function removeLayout(del){
    var form = getLayoutForm(3);
    form["parentId"].value = $(del).parents(".editLayoutDiv").attr("parentId");
    submitAjaxForm(form);
}
    
function getLayoutForm(type){
    var form = document["layoutUpdateForm"];
    form["type"].value = type;
    return form;
}
    
function submitAjaxForm(form){
    //        alert($(form).formSerialize());
    var dialog = c.onprogress();
    var options = {  
        success:    function(data, status, xhr) {     
            if(xhr.responseText == "error"){
                $("<div>3秒后页面将刷新</div>").dialog({
                    title: '出错了',
                    show: 'explode',
                    hide: 'explode',
                    modal: true
                });
                setTimeout(function (){
                    location.reload()
                }, 3000);
            }else{
                dialog.dialog("close");
                //                    alert(xhr.responseText)
                $("#layoutEditView").html(xhr.responseText);
                $("#templateLayoutTextarea").val(xhr.responseText)
                initLayout();
            }
        }
    }; 
    $(form).ajaxSubmit(options);
}