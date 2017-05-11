function vDoCheck(name){
    if(window.c_validator){
        c_validator.doCheck(name);
    }
}

/**
 *  fileds
 *  r:必填
 *  mx:最多字符
 *  space:提示框向右偏移像素数
 *  focus:当验证值与显示不一致时，指定提示元素name
 *
 */
function validator(fileds){
    window.c_validator = this;
    this.errormsg = {
        r:"不能为空",
        sr:"请选择"
    };
    this.fs = {
      "alpha":{reg:/^[a-zA-Z\.\-]*$/, msg:"格式不正确！"},// 字母和点减号
      "gh":{reg:/^[a-zA-Z\d]*$/, msg:"请输入字母或数字！"},// 工号
      "alphanum":{reg:/^\w+$/, msg:"格式不正确！"},  // 字符
      "unsigned":{reg:/^\d+$/, msg:"请输入正整数！"}, // 正整数
      "positiveInteger":{reg:/^[0-9]*[1-9][0-9]*$/, msg:"格式不正确！"}, // 可以0开头数字，包括以零开头的数字
      "integer":{reg:/^[\+\-]?\d*$/, msg:"请输入一个整数！"},// 整数可以有+-号
      "real":{reg:/^[\+\-]?\d*\.?\d*$/, msg:"请输入一个实数！"},// 实数
      "unsignedReal":{reg:/^\d*\.?\d*$/, msg:"格式不正确！"},// 无符号实数
      "ration":{reg:/^\d*\.?\d*\%?$/, msg:"格式不正确！"},// 可以是百分数
      "email":{reg:/^[\w-\.]+\@[\w\.-]+\.[a-z]{2,4}$/, msg:"请输入正确的邮箱格式！"},// 邮箱
      "phone":{reg:/^([\d]{3,4}-)?[\d]{6,12}(\*[\d]{1,4})?$/, msg:"电话号码格式不正确！<br>如：021-99999999*2222"},// 固定电话
      "sfz":{reg:new v_checkSfz(), msg:"身份证号码应为15、18位或者17位+X！"},// 身份证号
      "mphone":{reg:/^[\d]{11}$/, msg:"请输入正确的手机号码！"},// 手机号
      "zip":{reg:/^[\d]{6}$/, msg:"请输入正确的邮编号码！"},// 邮政编码
      "lxdh":{reg:/^(([\d]{3,4}-)?[\d]{6,12}(\*[\d]{1,4})?)$|^[\d]{11}$/, msg:"电话号码格式不正确！<br>如：021-99999999或13000000000"},// 固定电话手机号
      "money":{reg:/^[\d]+(\.\d{1,2})?$/, msg:"请输入正确的金额！如：12.98"},
      "score":{reg:/^\d+$/, msg:"请输入正确的分数！如：81"},
      "year":{reg:/^19[\d]{2}$|^20[\d]{2}$/, msg:"请输入正确的年份格式1900-2099！"},
      "month":{reg:/^[1-9]$|^1[012]$/, msg:"请输入正确的月份！<br/>如：1-12"},
      "salaryField":{reg:/^[a][0][1-9]$|^[a][1234][\d]$|^[a][5][0]$/, msg:"请输入正确的格式！<br/>如：a01"}, // a01-a50
      "gs":{reg:/^([1-9]+[\d]*(\.[05])?$)|^0.5$/, msg:"请输入非0的0.5的整倍数！<br/>如：0.5，1.0，1.5"},
      "hour":{reg:/^0$|^(0)?[1-9]$|^1[0-9]$|^2[0-3]$/, msg:"请输入小时数0-23"},
      "minute":{reg:/^0$|^(0)?[1-9]$|^[1-5][0-9]$/, msg:"请输入分钟数0-59"},
      "resource":{reg:/^([a-zA-Z\d]+[/])?[a-zA-Z\d]+$/, msg:"请输入正确的资源，如abc/edf！"},// 资源
      "entry":{reg:/^([a-zA-Z\d]+[/])?[-a-zA-Z\d]+\.action(\?.*)?$/, msg:"请输入正确的入口，如abc/edf.action！"},// 入口
      "dictCode":{reg:/^([A-Z\d]+[_])*[A-Z\d]+$/, msg:"请输入正确的字典代码，由大写字母、数字和下划线组成，如ABC_DEF_G01！"},
      "ipfw":{reg:/^[\d\.\-,]*$/, msg:"IP范围用-分隔，多个条件用,号分隔<br>如：如192.168.0.12-192.168.0.200,172.28.0.10"},// 入口
      "zzjgdm":{reg:/^[\d]{8}-[\d]$/, msg:"请输入正确的机构代码<br>如：65235468-9"},
      "numbers":{reg:/^([\d]+,)*[\d]+$/, msg:"请输入一个或多个数字，以','隔开."}
    };
    this.divs={};
    this.showTips = new ShowTips();
    this.showTip = v_showTip;
    this.fileds = fileds;
    this.getEntityName = function (){
        var fileds = this.fileds;
        for(var name in fileds){
            if(name.indexOf(".") > 0){
                return name.substring(0, name.indexOf("."));
            }
        }
    }
    this.entityName = this.getEntityName();
    this.check = v_check;
    this.doCheck = v_doCheck;
    this.init = v_init;
    this.exec = v_exec;

    this.init();
}

function v_init(){
    var fileds = this.fileds;
    for(var name in fileds){
        this.showTip(name)
    }
}
function v_showTip(name){
    var v = this;
    var filed = this.fileds[name];
    filed.name = name;
    var input = $("[name='" + name+"']");
    if(input.length == 0 || (input.attr("type") != undefined && input.attr("type").toUpperCase() == "RADIO")){//单选框没有提示信息
        return;
    }
    if(filed["space"] == undefined){
        filed.space = 15;
    }
    if(filed["mx"]){
        if(input[0].tagName.toLowerCase() == "textarea"){
            input.keyup(function (){
                var value = input.val();
                if(value.length > filed["mx"]){
                    input.val(value.substring(0, filed["mx"]));
                }
            });
        }
        if(!filed["l"]){
            filed["l"] = "最多输入"+filed["mx"]+"个字符";
        }
    }
    var div = v.showTips.getTipsDiv("");
    v.divs[name] = div;
    if(input.length == 0){
        filed.checked = true;
    }
    v.showTips.setPosistion(input, div);
    input.bind("focus", function(){
        div.css("z-index",99999);
        var cont = div.find(".cont");
        if(filed["l"]){
            cont.html("提示：" + filed["l"]);
            cont.removeClass("error");
            var css = {};
            if(filed["width"]){
                css = {width: filed["width"]};
            }
            v.showTips.setPosistion(filed, div, css);
            div.fadeIn("fast");
        }else{
            div.fadeOut("fast");
        }
    });
    input.bind("blur", function(){
        v.doCheck(name)
    });
// var idname = name.substring(0, name.indexOf(".")) + ".id";
// var id = $("[name='" + idname+"']").val() + "";
// if(id != ""){
// input.blur();
// }
}
/**
 * 主验证方法 r:是否必填 f:验证格式 mn:最小字符数 mx:最大字符数 reg:自定义验证表达式 msg:自定义验证表达式提示信息
 */
function v_check(name){
    var filed = this.fileds[name];
    var input = $("[name='" + name + "']");
    var value = "";
    if(input.length == 0 || (input.attr("type") != undefined && input.attr("type").toUpperCase() == "RADIO")){
        value = $(":radio[name='" + name + "'][checked]").val();
        if(value == undefined){
            var msg = "show:"+filed["l"] + "不能为空！";
            return msg;
        }
    }else{
        value = input.val();
    }
    var checkFmt = true;
    if(filed["r"]){
        if(/^[\s　]*$/.test(input.val())){
            if(input.attr("tagName") == "SELECT"){
                return this.errormsg["sr"];
            }else{
                return this.errormsg["r"];
            }
        }
    }else{
        if(value == ""){
            checkFmt = false;
        }
    }
    if(filed["mn"]){
        if(value.length < filed["mn"] *1){
            return "字符长度不能小于" + filed["mn"];
        }
    }
    if(filed["mx"]){
        if(value.length > filed["mx"] *1){
            return "字符长度不能大于" + filed["mx"] + "<br>当前" + value.length + "个字符";
        }
    }
    if(filed["reg"] && checkFmt){
        var reg = filed["reg"];
        if(!reg.exec(value)){
            var msg = filed["msg"];
            if(!msg){
                msg = "格式错误！";
            }
            return msg;
        }
    }
    if(filed["f"] && checkFmt){
        var f = this.fs[filed["f"]];
        if(f){
            if(!f["reg"].exec(value)){
                return f["msg"];
            }
        }
    }
    return "";
}
function v_doCheck(name){
    var v = this;
    var filed = v.fileds[name];
    if(!filed){
        return
    }
    var div = v.divs[name];
    var cont = div.find(".cont");
    var input = $("[name='" + name+"']");
    v.showTips.setPosistion(filed, div);
    var msg = v.check(name);
    if(msg == ""){
        filed.checked = true;
        cont.removeClass("error");
        div.fadeOut("fast");
    }else{
        v.showTips.setPosistion(input, div);
        div.css("z-index",100000);
        div.show();
        filed.checked = false;
        cont.addClass("error");
        cont.html(msg);
    }
}
function v_exec(){
    var v = this;
    var fileds = this.fileds;
    var pass = true;
    var msgs = "";
    for(var name in fileds){
        var msg = v.check(name);
        if(msg != ""){
            pass = false;
        }
        if(msg.indexOf("show:", 0) == 0){
            msgs += msg.substr(5, msg.length) + "\n";
        }
        var input = $("[name='" + name + "']");
        input.blur();
    }
    if(msgs != "" || !pass){
        msgs += "表单填写有误，请检查后再提交！";
        if(c_validator.reset){
            c_validator.reset = false;
        }else{
            alert(msgs);
        }
    }
    return pass;
}

function ShowTips(){
    this.setPosistion = function (filed, div, css){
        try{
            var input = null;
            if(filed.fs){
                input = $("#"+filed.fs);
            }else{
                input = $name(filed.name);;
            }
            var s = "x:" + (input.offset().top - 5) + "   y:" + (input.offset().left + input.width() + 50);
            s += "\n" + div.css("position");
            div.css("position","absolute");
            $("#r").html(s);
            var top = input.offset().top;
            if(!$.browser.msie || $.browser.version == '8.0' || $.browser.version == '9.0'){
                top -=5;
            }
            if(css != undefined){
                div.css(css);
            }
            div.css("top", top);
            div.css("left", input.offset().left + input.width() + filed.space);
        }catch(e){}
    }

    this.getTipsDiv = function (msg){
        var s = '<div style="DISPLAY: none" class="info"><div class="arr"></div><div class="info-pop-t"><b class="cr-l"></b><b class="cr-r"></b></div><div class="info-pop-c"><div class="cont">';
        s += msg;
        s += '</div></div><div class="info-pop-b"><b class="cr-l"></b><b class="cr-r"></b></div></div>';
        var div = $(s);
        $("body").append(div);
        return div;
    }
}
function v_checkSfz(){
    this.exec = function (value){
        if(!/^[\d]{15}$|^[\d]{18}$|^[\d]{17}[X]$/.exec(value)){
            return false;
        }
        return true;
    }
}
$(function (){
    $("textarea").keyup(function (){
        var v = $(this);
        var mx =  parseInt(v.attr("mx"));
        if(mx != undefined){
            if(v.val().length > mx){
                v.val(v.val().substr(0, mx));
            }
        }
    });
    $("[type='reset']").click(function (){
        if(c_validator){
            c_validator.reset = true;
            window.setTimeout("c_validator.exec()", 100);
        }
    });
});
