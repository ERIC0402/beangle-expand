function DiaoChaWenJuan(wenJuanId){
	
	this.wenJuanId = wenJuanId;
	this.dcwjHideIframe = null;
	
	this.getUrl = function(method, param){
		return base + "/wenjuan/dcwj!" + method + ".action?wenJuan.id=" + this.wenJuanId + "&" + param;
	}
	
	this.getDcwjHideIframe = function (){
		if(this.dcwjHideIframe == null){
			this.dcwjHideIframe = $(".dcwjHideIframe");
		}
		return this.dcwjHideIframe;
	}
	
	this.addWjtm = function(code){
		this.editWjtm(null, code);
	}
	
	this.editWjtm = function(id, code){
		if(id == null){
			id = "";
		}
		if(code == undefined){
			code = "";
		}
		var title =  id == "" ? "添加题目" : "修改题目";
		this.showIframe({title:title, src: this.getUrl("editWjtm", "wjtm.id=" + id + "&code=" + code)});
	}
	
	this.addWebBenTiMu = function(){
		this.showIframe({title:"文本添加题目", src: this.getUrl("wbtj", "wenJuanId=" + this.wenJuanId), width:1000, height:800});
	}
	
	this.showIframe = function (config){
		if(!config.width)config.width = 800;
		if(!config.height)config.height = 500;
		var iframes = $(".dcwjHideIframe");
		while(iframes.length > 1){
			iframes.last().remove();
			iframes = $(".dcwjHideIframe");
		}
		$(".dcwjHideIframe").dialog({
			title: config.title,
			width: config.width, height:config.height,
            modal: true
        }).find("iframe").attr("src", config.src).css("height", config.height -80).css("width", config.width - 50);
		$(".dcwjHideIframe").find("iframe").get(0).contentWindow.document.body.innerHTML = "页面加载中...";
	}
	
	this.refresh = function(){
		this.getDcwjHideIframe().dialog("close");
		var v = this;
		$(".tmEditorDivs").load(this.getUrl("wjtm"), function (){
			v.init();
		});
	}
	
	this.resetPx = function (){
		var px = 1;
		var param = {};
		$(".tmEditorDiv").each(function (){
			$(".pxLabel", this).html(px++ + ".");
			param["px" + $("[name='wjtm.id']", this).val()] = px;
		});
		$.post(this.getUrl("resetPx"), param);
	}
	
	this.up = function(id){
		var div = this.getDiv(id);
		var pdiv = div.prev(".tmEditorDiv");
		if(pdiv.length == 0){
			return;
		}
		div.insertBefore(pdiv);
		this.resetPx();
	}
	
	this.down = function(id){
		var div = this.getDiv(id);
		var pdiv = div.next(".tmEditorDiv");
		if(pdiv.length == 0){
			return;
		}
		div.insertAfter(pdiv);
		this.resetPx();
	}
	
	this.getDiv = function(id){
		return $(".tmEditorDiv" + id);
	}
	
	this.delWjtm = function (id, btn){
		var sure = confirm("是否删这个题目？");
		if(!sure){
			return;
		}
		$.post(this.getUrl("delWjtm","wjtm.id=" + id));
		$(btn).parents(".tmEditorDiv").remove();
		this.resetPx();
	}
	
	this.init = function (){
		var v = this;
		$(".tmEditorDiv").hover(function (){
			var div = $(this);
			div.addClass("tmEditorDivHover");
			div.find(".btnBar").show();
		}, function (){
			var div = $(this);
			div.removeClass("tmEditorDivHover");
			div.find(".btnBar").hide();
		});
		$(".tmEditorDivs").sortable && $(".tmEditorDivs").sortable({
			stop : function (){
				v.resetPx();
			}
		}).disableSelection();
		bg.ui.comm.addPlaceholder($name("wenJuanDc.wjmc"), "请输入问卷的标题");
		bg.ui.comm.addPlaceholder($name("wenJuanDc.sm"), "请填写关于此问卷的说明");
	}
	this.init();
}