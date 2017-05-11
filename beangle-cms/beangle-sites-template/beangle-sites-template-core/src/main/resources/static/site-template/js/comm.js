jQuery.extend({
    hasClass:function (a){
        var c = this.attr("class")+"";
        if(c.indexOf(a) >= 0){
            return true;
        }else{
            return false;
        }
    }
});
jQuery.fn.extend({
    style:function (a,n){
        if(!n){
            n="style"
        }
        var style = this.attr(n);
        if(style == undefined){
            return ""
        }
        var styles = style.split(";");
        for(var i = 0; i < styles.length; i++){
            var s = styles[i].split(":");
            if(s[0].indexOf(a) >=0){
                return s[1];
            }
        }
        return "";
    }
});

if(!c){
    var c = {};

}
c.onprogress = function (){
    return $("<div>正在处理，请稍候...</div>").dialog({
        title: '友情提示',
        show: 'explode',
        hide: 'explode',
        modal: true
    });
}
function $name(name){
    return $("[name='"+name+"']");
}

function removeSelectedOption(select){
    var options = select.options;
    for (var i=options.length-1; i>=0; i--){
        if (options[i].selected){
            options[i] = null;
        }
    }
}

function getSelectedOpetionValue(select, multi){
    var val = "";
    var options = select.options;
    for (var i=options.length-1; i>=0; i--){        
        if (options[i].selected){
             if (val != "")
            val = val + ",";
            val = val + options[i].value;
        }
    }
    if(!multi){
        if(val == "" || val.indexOf(",") > 0){
            alert("先选择一条记录");
            return "";
        }
    }
    return val;
}


function getAllOptionValue(select)
{
    var val = "";
    var options = select.options;
    for (var i=0; i<options.length; i++){
        if (val != "")
            val = val + ",";
        val = val + options[i].value;
    }
    return val;
}

function selectToText(select, input, par){            
    $(select).change(function (){           
        var title = input.value;
        if(title == ""){
            title = this.options[this.selectedIndex].text;
            if(par && title.indexOf(par) > 0){
                title = title.substring(title.indexOf(")")+1);
            }
            $(input).val(title);
        }
    });
}

function searchContent(form, allSite){
    if(allSite){
        form["allSite"].value = "true";
    }
    var key = form.keyWord.value;
    key=key.replace(/[  _\?%]/g, "");
    form.keyWord.value = key;
    if(key == ""){
        alert("请输入搜索条件！");
        form.keyWord.focus();
        return false;
    }
    form.submit();
    return false;
}