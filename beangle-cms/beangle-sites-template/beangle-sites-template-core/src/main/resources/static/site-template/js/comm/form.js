function FormUtils(){
    this.findTeachers = function (idsId, selectId){
        
        var iframe = $("#selectTeachersIframe");
        if(iframe.length == 0){
            iframe = $('<iframe id="selectTeachersIframe" frameborder="0"></iframe>');
            iframe.appendTo($("body"));
        }        
        var action = base + "/common/select!findTeachers.action?";
        if(idsId){
            action += "&idsId=" + idsId;
        }
        if(selectId){
            action += "&selectId=" + selectId;
        }
        iframe.attr("src", action);
        iframe.dialog({
            width:800,
            height:600,
            modal: true,
            resizable : false
        });
        iframe.css("width", "775px");
        iframe.css("height", "675px");
    };
    this.closeFindTeachers = function (){
        $("#selectTeachersIframe").dialog("close");
    }
    
    
    this.findEnglish = function (idsId, selectId){
        var name = "findEnglish";
        var action = base + "/common/select!findEnglish.action?";
        this.find(name, action, idsId, selectId);
    };
    
    this.closeFindEnglish = function (){
        $("#findEnglish").dialog("close");
    }
    
    this.find = function (name, action, idsId, selectId){
        
        var iframe = $("#" + name);
        if(iframe.length == 0){
            iframe = $('<iframe id="'+name+'" frameborder="0"></iframe>');
            iframe.appendTo($("body"));
        }        
        if(idsId){
            action += "&idsId=" + idsId;
        }
        if(selectId){
            action += "&selectId=" + selectId;
        }
        iframe.attr("src", action);
        iframe.dialog({
            width:800,
            height:600,
            modal: true,
            resizable : false
        });
        iframe.css("width", "775px");
        iframe.css("height", "675px");
    };
    
    this.editTeacher = function (selectId){
        var select = document.getElementById(selectId);
        var id = getSelectedOpetionValue(select, false);
        if(id != ""){
            window.open(base + "/module/teachers.action?id=" + id);
        }
    }
}
var $fu = new FormUtils();