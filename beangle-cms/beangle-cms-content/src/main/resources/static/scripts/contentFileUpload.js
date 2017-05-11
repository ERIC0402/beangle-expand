var start = 0;
var end = 0;
function huanhang() {
	var textBox = document.getElementById("richText");
	var pre = textBox.value.substr(0, start);
	var post = textBox.value.substr(end);
	textBox.value = pre + "[BR]" + post;
}
function shsj() {
	var textBox = document.getElementById("richText");
	var pre = textBox.value.substr(0, start);
	var post = textBox.value.substr(end);
	textBox.value = pre + "[IN]" + post;
}
function jc() {
	var textBox = document.getElementById("richText");
	var pre = textBox.value.substr(0, start);
	var post = textBox.value.substr(end);
	textBox.value = pre + "[B][/B]" + post;
}
function savePos(textBox) {
	// 如果是Firefox(1.5)的话，方法很简单
	if (typeof (textBox.selectionStart) == "number") {
		start = textBox.selectionStart;
		end = textBox.selectionEnd;
	}
	// 下面是IE(6.0)的方法，麻烦得很，还要计算上'\n'
	else if (document.selection) {
		var range = document.selection.createRange();
		if (range.parentElement().id == textBox.id) {
			// create a selection of the whole textarea
			var range_all = document.body.createTextRange();
			range_all.moveToElementText(textBox);
			// 两个range，一个是已经选择的text(range)，一个是整个textarea(range_all)
			// range_all.compareEndPoints()比较两个端点，如果range_all比range更往左(further
			// to the left)，则 //返回小于0的值，则range_all往右移一点，直到两个range的start相同。
			// calculate selection start point by moving beginning of range_all
			// to beginning of range
			for (start = 0; range_all.compareEndPoints("StartToStart", range) < 0; start++)
				range_all.moveStart('character', 1);
			// get number of line breaks from textarea start to selection start
			// and add them to start
			// 计算一下\n
			for ( var i = 0; i <= start; i++) {
				if (textBox.value.charAt(i) == '\n')
					start++;
			}
			// create a selection of the whole textarea
			var range_all = document.body.createTextRange();
			range_all.moveToElementText(textBox);
			// calculate selection end point by moving beginning of range_all to
			// end of range
			for (end = 0; range_all.compareEndPoints('StartToEnd', range) < 0; end++)
				range_all.moveStart('character', 1);
			// get number of line breaks from textarea start to selection end
			// and add them to end
			for ( var i = 0; i <= end; i++) {
				if (textBox.value.charAt(i) == '\n')
					end++;
			}
		}
	}
	document.getElementById("start").value = start;
	document.getElementById("end").value = end;
}

function check(form){
	check(form,null)
}

function check(form,confirmMsg){
	if($("#newsType").val()=='225'){
       if($("#titleImage").val() == ''){
           alert("此栏目是图片新闻，请插入标题图片！");
           return false;
       }
    }
	
	if(confirmMsg != null && confirmMsg != ""){
		if(!confirm(confirmMsg)){
    		return false;
    	}
	}
	
    var fileIds = "";
    var select = $("#fileList1 option").each(function(){
    	if(this.value!=null&&this.value!=""){
    		if(fileIds!=""){
    			fileIds += ","+this.value;
    		}else{
    			fileIds = this.value;
    		}
    	}
    });
    $("#annexIds").val(fileIds);
//    $(":button").each(function(){this.disabled=true});
	bg.form.submit(form,form.action,form.target,null,true);
}

function change(value){
    if(value == 1){
        details.style.display="none";
        details1.style.display="none";
        details2.style.display="none";
        tti.style.display="none";
        rt.style.display="none";
        url.style.display="";
    }else if(value == 2 ){
        details.style.display="";
        details1.style.display="";
        details2.style.display="";
        tti.style.display="";
        url.style.display="none";
        rt.style.display="none";
    }else{
        details.style.display="none";
        details1.style.display="none";
        details2.style.display="none";
        tti.style.display="none";
        rt.style.display="";
        url.style.display="none";
    }
}

function selectAnnex() {
	jQuery().colorbox(
	{
		iframe : true,
		width : "800px",
		height : "600px",
		href : base+"/cms/annex.action?selectId=fileList1"
	});
}

function handle1() {
	if (!confirm("确定删除吗？")) {
		return false;
	}
	$("#fileList1 option:selected").remove();
	alert("文件删除成功");
}

function handleother() {
	var fileList1 = document.getElementById('fileList1');
	var fileOpList1 = document.getElementById('fileOpList1');
	var oEditor = CKEDITOR.instances.editor1;
	var fileid = fileList1.value;

	if (fileOpList1.value != "3" && fileid == '') {
		alert("请选择文件");
		return false;
	}

	var selectfilename = fileList1.options[fileList1.selectedIndex].text;
	var suffilename = selectfilename
			.substring(selectfilename.lastIndexOf('.') + 1);
	var suff = suffilename.toUpperCase();
	var mysuff = new Array();
	mysuff[0] = "BMP";
	mysuff[1] = "GIF";
	mysuff[2] = "JPEG";
	mysuff[3] = "JPG";
	mysuff[4] = "PSD";
	mysuff[5] = "PNG";
	mysuff[6] = "SVG";
	mysuff[7] = "PCX";
	mysuff[8] = "DXF";
	mysuff[9] = "WMF";
	mysuff[10] = "EMF";
	mysuff[11] = "EPS";
	mysuff[12] = "TGA";
	var mysuff2 = new Array();
	mysuff2[0] = "SWF";
	mysuff2[1] = "FLV";
	var mysuff3 = new Array();
	mysuff3[0] = "ASF";
	var mysuff4 = new Array();
	mysuff4[0] = "MP3";

	var str = selectfilename.substring(0, selectfilename.lastIndexOf('.'))
	switch (fileOpList1.value) {
	case '0':

		var strhref = window.prompt("请输入显示名称", str);
		if (strhref == null) {
		} else if (strhref.replace(/^\s+|\s+$/g, "") == "")
			alert("名称不能为空");
		else
			var s = "<a style='color:blue;' href='" + base
					+ "/cms/file-utils!downFileById.action?fileId=" + fileid
					+ "'>" + strhref + "</a>";
		// tinyMCE.execCommand("mceInsertContent", false, s);
		oEditor.insertHtml(s);
		break;
	case '1':
		var bool = false;
		for ( var i = 0; i < mysuff.length; i++) {
			if (mysuff[i] == suff) {
				bool = true;
			}
		}

		if (bool) {
			var s = "<img width='650' class='' src='"
					+ base
					+ "/cms/file-utils!downFileById.action?isShowImage=1&fileId="
					+ fileid + "'/>";
			// tinyMCE.execCommand("mceInsertContent", false, s);
			oEditor.insertHtml(s);
		} else {
			alert("请选择图片");
		}

		break;
	case '2':
		var bool = false;
		for ( var i = 0; i < mysuff.length; i++) {
			if (mysuff[i] == suff) {
				bool = true;
			}
		}

		if (bool) {
			$("#titleImage").val(fileid);
			$("#showImage")
					.html(
							"<img id='showImage' src='"
									+ base
									+ "/cms/file-utils!downFileById.action?isShowImage=1&fileId="
									+ fileid + "&t=" + (new Date().getTime()) + "' width='80px' height='65px'/>");
		} else {
			alert("请选择图片");
		}

		break;
	case '3':
		$("#titleImage").val("");
		$("#showImage").html("&nbsp;");
		break;
	case '4':
		handle1();
		break;
	case '5':
		var bool = false;
		for ( var i = 0; i < mysuff2.length; i++) {

			if (mysuff2[i] == suff) {
				bool = true;
			}
		}

		if (bool) {
			var url = "";
			if (suff == "SWF") {
				url = base + '/cms/file-utils!downFileById.action?fileId='
						+ fileid;
			} else if (suff == "FLV") {
				url = base + "/sites/player.swf?movUrl=" + base
						+ "/cms/file-utils!downFileById.action?fileId="
						+ fileid;
			}
			var hml = '<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0"><param name="quality" value="high" /><param name="movie" value="'
					+ url
					+ '" /><embed pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" src="'
					+ url
					+ '" type="application/x-shockwave-flash"></embed></object>';
			// tinyMCE.execCommand("mceInsertContent", false, hml);
			oEditor.insertHtml(hml);
		} else {
			alert("请选择FLASH");
		}

		break;
	case '6':
		var bool = false;
		for ( var i = 0; i < mysuff3.length; i++) {

			if (mysuff3[i] == suff) {
				bool = true;
			}
		}
		if (bool) {
			var url = base + '/cms/file-utils!downFileById.action?fileId='
					+ fileid;
			var hml = '<center><p><object align="center" canhavechildren="true" canhavehtml="true" classid="CLSID:22D6F312-B0F6-11D0-94AB-0080C74C7E95" height="500" id="movieplayer" standby="Loading Microsoft Windows Media Player components..." type="application/x-oleobject" width="600"><param name="AudioStream" value="-1" /><param name="filename" value="'
					+ url
					+ '" /><param name="AutoSize" value="0" /><param name="ShowPositionControls" value="-1" /><param name="ShowStatusBar" value="-1" /><param name="AutoStart" value="1" /><embed autostart="true" height="500" type="video/mpeg" width="600"></embed></object></p><font color="red">*</font> 如无法在线观看，请<a href="'
					+ url
					+ '" style="color: blue;cursor:pointer;">下载</a>后观看</center>';

			// tinyMCE.execCommand("mceInsertContent", false, hml);
			oEditor.insertHtml(hml);
		} else {
			alert("请选择ASF格式视频");
		}
		break;
	case '7':
		var bool = false;
		for ( var i = 0; i < mysuff4.length; i++) {
			if (mysuff4[i] == suff) {
				bool = true;
			}
		}
		if (bool) {
			var url = base + '/cms/file-utils!downFileById.action?fileId='
					+ fileid;
			var hml = '<center><p><object align="center" canhavechildren="true" canhavehtml="true" classid="CLSID:22D6F312-B0F6-11D0-94AB-0080C74C7E95" height="50" id="movieplayer" standby="Loading Microsoft Windows Media Player components..." type="application/x-oleobject" width="600"><param name="AudioStream" value="-1" /><param name="filename" value="'
					+ url
					+ '" /><param name="AutoSize" value="0" /><param name="ShowPositionControls" value="-1" /><param name="ShowStatusBar" value="-1" /><param name="AutoStart" value="1" /><embed autostart="true" height="500" type="audio/mpeg" width="600"></embed></object></p><font color="red">*</font> 如无法在线观看，请<a href="'
					+ url
					+ '" style="color: blue;cursor:pointer;">下载</a>后观看</center>';

			// tinyMCE.execCommand("mceInsertContent", false, hml);
			oEditor.insertHtml(hml);
		} else {
			alert("请选择MP3格式音频");
		}
		break;
	default:
		alert("请选择操作");
		break;
	}
}
