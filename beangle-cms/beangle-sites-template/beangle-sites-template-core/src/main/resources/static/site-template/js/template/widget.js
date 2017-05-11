function initContent() {
	// tab初始化
	$("#contentTabs").tabs();
	$(".contentMode").html($("#templateLayout").html());
	// 标记浮动层及其父节点
	$(".contentMode div[style*='float']").each(function() {
		var div = $(this).addClass("floatLayout").parent();
		div.addClass("floatLayouts");
	});
	// 为容器加一个空的Div
	$(".contentMode div[id^='layout']").not(".clearDiv,.floatLayouts").each(function() {
		var v = $(this);
		if (v.find("div[id^='layout']").length > 0) {
			return;
		}
		var emptyDiv = $("#editContentDiv .emptyDiv").clone();
		emptyDiv.attr("parentId", this.id);
		v.append(emptyDiv);
	});
	// 查询模板的控件
	var url = action + "?method=findWidgetConfig&templateId=" + $name("templateId").val();
	if (typeof (siteId) != "undefined") {
		url += "&siteId=" + siteId;
	}
	$.getJSON(url + "&now=" + new Date().getTime(), function(json) {
		for ( var v in json) {
			v = json[v];
			var div = $("#editContentDiv .editContentDiv").clone();
			var t = $("<div style='overflow:hidden'></div>");
			$("#" + v.position).children("div").first().append(div);
			var tb = $("#editContentDiv .toolBar2").clone();
			tb.attr("widgetConfigId", v.id);
			tb.attr("title", v.className);
			tb.attr("config", v.config);
			div.append(tb);
			div.append(t);
			var url = action + "?method=viewWidget";
			if (v.sid != undefined) {
				url += "&sid=" + v.sid;
				tb.attr("swidgetConfigId", v.sid);
			}
			if (typeof (siteId) != "undefined") {
				url += "&siteId=" + siteId;
			}
			var params = {};
			params.clazzName = v.className;
			params.config = v.config;
			loadDiv(t, url, params);
			// t.load(url);
			toolBarHover(div, tb);
		}
	});
	// 使空的Div可以让组件放下
	$(".emptyDiv").droppable({
		accept : ".widget",
		hoverClass : "ui-state-active",
		drop : function(event, ui) {
			ui = $(ui.draggable[0]);
			var form = getWidgetConfigForm(1);
			form["parentId"].value = $(this).attr("parentId");
			form["data"].value = ui.attr("data");
			submitAjaxForm(form);
		}
	});
}
function loadDiv(div, url, params) {
	url += "&t=" + new Date().getTime();
	$.post(url, params, function(data) {
		if (typeof (data) == "object") {
			if(data.innerHTML){
				data = data.innerHTML;
			}
		}
//		alert($(data).html())
		div.append(data);
		$("#layoutRoot a").each(function() {
			this.href = "#";
			this.target = "";
		})
		div.parent().parent().next(".emptyDiv").remove();
	}, "html");
}
function toolBarHover(div, tb) {
	div.hover(function() {
		tb.show();
	}, function() {
		tb.hide();
	});
}
function editWdigetConfig(edit) {
	var form = getWidgetConfigForm(2);
	var widgetConfigId = $(edit).parent().attr("widgetConfigId");
	form["widgetConfigId"].value = widgetConfigId;
	var swidgetConfigId = $(edit).parent().attr("swidgetConfigId");
	if (swidgetConfigId != undefined) {
		form["swidgetConfigId"].value = swidgetConfigId;
	}
	$("#widgetConfigFormContent").html("正在加载...");
	$("#editWidgetConfigFormDiv").dialog({
		minWidth : 800,
		modal : true,
		hide : 'fade',
		show : 'fade',
		title : '组件属性',
		buttons : {
			"保存" : function() {
				if (window.v == undefined || v.exec == undefined || v.exec()) {
					v = {};
					$(this).dialog("close");
					submitAjaxForm(form);
				}
			}
		},
		close : function(event, ui) {
			v = {};
			$(".info").hide();
		}
	});
	var url = action + "?method=editConfig&widgetConfigId=" + widgetConfigId;
	if (typeof (swidgetConfigId) != "undefined") {
		url += "&swidgetConfigId=" + swidgetConfigId;
	}
	if (typeof (siteId) != "undefined") {
		url += "&siteId=" + siteId;
	}
	url += "&t=" + new Date().getTime();
	$("#widgetConfigFormContent").load(url, null, function() {
		if (typeof (siteId) == "undefined") {
			$(".list_box1 .hidden").show();
		}
	});
}
function removeContent(btn) {
	var form = getWidgetConfigForm(3);
	form["widgetConfigId"].value = $(btn).parent().attr("widgetConfigId");
	submitAjaxForm(form);
}
function getWidgetConfigForm(type) {
	var form = document["wdigetConfigForm"];
	form["type"].value = type;
	return form;
}
function submitAjaxForm(form) {
	var dialog = c.onprogress();
	var options = {
		success : function(data, status, xhr) {
			if (xhr.responseText == "error") {
				$("<div>3秒后页面将刷新</div>").dialog({
					title : '出错了',
					show : 'fade',
					hide : 'fade',
					modal : true
				});
				setTimeout(function() {
					location.reload();
				}, 3000);
			} else {
				dialog.dialog("close");
				initContent();
			}
		}
	};
	$(form).ajaxSubmit(options);
}
function publishTemplate(templateId, siteId) {
	var url = "site.action?method=publish&templateId=" + templateId + "&siteId=" + siteId;
	url += "&t=" + new Date().getTime();
	$.get(url, function() {
		$("#resultDiv").dialog({
			minWidth : 400,
			modal : true,
			hide : 'fade',
			show : 'fade',
			title : '',
			buttons : {
				"确定" : function() {
					if (window.v == undefined || window.v.exec == undefined || v.exec()) {
						$(this).dialog("close");
					}
				}
			}
		});
	});
}
function uniteWidgetConfig(id) {
	var url = action + "?method=uniteWidgetConfig";
	$.post(url, "widgetConfigId=" + id, function(data) {
		if (data.length < 100) {
			alert(data);
		} else {
			$("#widgetBoxTabs2").html(data);
		}
	});
}