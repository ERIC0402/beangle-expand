/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function uploadimg(options){
    
//    var scriptData = {
//        dir:options.dir,
//        ofile:options.ofile,
//        zipImg:true
//    };
    options.zipImg = true;
    $('#' + options.upBtn).uploadify({
        fileDesc : '图片(*.jpg;*.jpeg)',
        fileExt : '*.jpg;*.jpeg;*.gif;*.png',
        multi        : false,
        auto      : true  ,
        scriptData:options,
        onComplete : function(event, ID, fileObj, response, data) {
            $name(options.imgNmae).val(response);
            $("#" + options.showImg).attr("src", base + "/" + response).css("display","");
            options.ofile=response;
            $('#' + options.upBtn).uploadifySettings('scriptData',options);
        }
    });
}
