$(function() {
	fixA();
	fixContentImg();
	// fixContentTable();
});

function searchContent(form, allSite) {
	if (allSite) {
		form["allSite"].value = "true";
	}
	var key = form.keyWord.value;
	key = key.replace(/[  _\?%]/g, "");
	form.keyWord.value = key;
	if (key == "") {
		alert("请输入搜索条件！");
		form.keyWord.focus();
		return false;
	}
	form.submit();
	return false;
}
function fixA() {

	$("a").each(
			function() {
				try {
					var a = $(this);
					var href = a.attr("href");
					if (!href) {
						return;
					}
					var pathname = window.location.pathname + "1";
					pathname = pathname.substring(0,
							pathname.indexOf("/", 1) + 1);
					var host = window.location.host;
					if (href.indexOf("http") < 0 || href.indexOf(host) >= 0
							|| herf.indexOf(pathname) >= 0) {
						a.attr("target", "");
					} else {
						a.attr("target", "_blank");
					}
				} catch (e) {
				}
			});
}
function fixContentImg() {
	$(".c_Right_2 img").each(function() {
		if (this.width > 600) {
			this.width = 600;
		}
	});
}

function fixContentTable() {
	$(".c_Right_2 table").each(function() {
		if (this.width > 600) {
			this.width = 600;
			$(this).css("width", "600px");
		}
	});
}