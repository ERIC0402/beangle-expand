<#include "/org/beangle/website/cms/template/action/comm.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${base}/static/site-template/css/style_comm.css" rel="stylesheet" type="text/css"> 
        <link href="${base}/static/site-template/css/toolBar.css" rel="stylesheet" type="text/css"> 
        <link href="${base}/static/site-template/css/default.css" rel="stylesheet" type="text/css"> 
        <link href="${base}/static/site-template/css/messages.css" rel="stylesheet" type="text/css"> 
        <script type="text/javascript">var base = "${base}";</script>
        <script type="text/javascript" src="${base}/static/site-template/js/jquery-latest.js"></script>
    	<link href="${base}/static/site-template/js/validator2/Validator2.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="${base}/static/site-template/js/validator2/Validator2.js"></script>
      
        <link rel="stylesheet" media="screen" type="text/css" href="${base}/static/site-template/js/colorpicker/css/colorpicker.css" />
        <link rel="stylesheet" media="screen" type="text/css" href="${base}/static/site-template/js/colorpicker/css/layout.css" />
        <script type="text/javascript" src="${base}/static/site-template/js/colorpicker/js/colorpicker.js"></script>

    </head>
    <body>
        <#if colors??>
        <div id="msges"><@getMessage/></div>
        <form action="site.action">
            <input name="method" value="saveColor" type="hidden"/>
            <input name="siteId" value="${site.id}" type="hidden"/>
            <input name="type" value="" type="hidden"/>
            <table width="98%" class="list_box1">
                <#list colors as v>
                <tr>
                    <td class="list_box1_title2">${v[0]}:</td>
                    <td align="left" valign="middle">
                        <div style="float: left; padding: 7px;"><input name="color.${v[1]}" value="${v[2]!}" style="width: 50px;"/></div>
                        <div class="colorSelector" style="float: left"><div style="background-color: #${v[2]!'ffffff'}"></div></div>
                        <#if v[3]??><font color="#aaa">&nbsp;&nbsp;(${v[3]})</font></#if>
                    </td>
                </tr>
                </#list>
                <tr align="center">
                    <td colspan="4">
                        <input type="button" value="提交" name="button1" onClick="save(this.form)" class="ip1" />
                        <input type="button" value="恢复默认" name="button2" onClick="save2(this.form)" class="ip2" />
                    </td>
                </tr>
            </table>
        </form>
        <script type="text/javascript">
            $(function (){
                if($("#msges").html() != ""){
                    window.top.location.reload();
                }
                $('.colorSelector').each(function (){
                    var v = $(this);
                    var color = v.prev("div").find("input").val();
                    v.ColorPicker({
                        color:color,
                        left:36,
                        top:-36,
                        onShow: function (colpkr) {
                            $(colpkr).fadeIn(500);
                            return false;
                        },
                        onHide: function (colpkr) {
                            $(colpkr).fadeOut(500);
                            return false;
                        },
                        onChange: function (hsb, hex, rgb) {
                            v.find("div").css('backgroundColor', '#' + hex);
                            v.prev("div").find("input").val(hex);
                        },
                        onSubmit: function(hsb, hex, rgb, el) {
                            $(el).ColorPickerHide();
                        }
                    });
                });
            });
            
            function save(form){
                form.submit();
            }
            
            function save2(form){
                if(confirm("是否撤消所有修改，还原到系统默认值？")){
                    form["type"].value = "reset";
                    form.submit();
                }
            }
        </script>
        <#else>
        配色模板不存在
        </#if>
    </body>
</html>