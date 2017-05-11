function DaJuan() {
	
	this.index = 0;
	this.tmdiv = $(".wjpreview");
	this.pagediv = $(".pagediv");
	this.wjtoolbar = $(".wjtoolbar");
	this.showAllBtn = $(".showAllBtn");
	this.showOneBtn = $(".showOneBtn");
	this.progress = $( "#progressbar" );
	this.progressValue = 0;
	this.kjsfstmsl = 0;
	
	this.init = function (){
		var _this = this;
		this.progress.progressbar({
			value: 0
		});
		setTimeout(function (){
			_this.checkProgress();
		}, 1);
		this.showOne();
		this.setKjsfstmsl();
	}
	
	this.setKjsfstmsl = function (){
		this.kjsfstmsl = 0;
		var v = this;
		this.findTmdiv().each(function (){
			var tmlx = v.getTmlx($(this));
			if(v.isXzt(tmlx)){
				v.kjsfstmsl++;
			}
		});
	}
	
	this.isXzt = function(tmlx){
		return tmlx == "WJ_TI_MU_LX_DAN_XUAN" || tmlx == "WJ_TI_MU_LX_PAN_DUAN" || tmlx == "WJ_TI_MU_LX_DUO_XUAN";
	}
	
	this.findTmdiv = function (){
		return this.tmdiv.find(".tmdiv");
	}

	this.showAll = function() {
		this.tmdiv.find(".tmdiv").show();
		this.showAllBtn.hide();
		this.showOneBtn.show();
		this.wjtoolbar.hide();
	}

	this.showOne = function(btn) {
		var index = this.index;
		if (btn != undefined) {
			if(typeof(btn.onclick) != "undefined"){
				index = btn.value - 1;
			}else{
				index = btn;
			}
		}
		this.index = index;
		this.tmdiv.find(".tmdiv").hide();
		this.tmdiv.find(".tmdiv").eq(index).fadeIn();
		this.showAllBtn.show();
		this.showOneBtn.hide();
		this.wjtoolbar.show();
		this.checkProgress();
	}
	
	this.prevTiMu = function(){
		if(this.index <= 0){
			return;
		}
		this.showOne(this.index-1);
	}
	
	this.nextTiMu = function(){
		if(this.index >= this.tmdiv.find(".tmdiv").length - 1){
			this.checkProgress();
			$(".tmdiv").filter("")
			return;
		}
		this.showOne(this.index+1);
	}
	
	this.colorBtn = function(){
		var btns = this.pagediv.find(".pageBtn");
		btns.css("color", "");
		btns.css("background-color", "");
		btns.eq(this.index).css("color", "red");
		var tmdivs = this.tmdiv.find(".tmdiv");
		for(var i = 0; i < tmdivs.length; i++){
			var tmdiv = $(tmdivs.get(i));
			var btn = $(btns.get(i));
			if(tmdiv.attr("hashd") == "true"){
				btn.css("background-color", "#3B9B3A");
			}
			if(tmdiv.attr("bqd") == "true"){
				btn.css("background-color", "#E9C147");
			}
		}
	}
	
	this.checkProgress = function(){
		var tmdivs = this.tmdiv.find(".tmdiv");
//		$(".pervTmBtn, .nextTmBtn").css("color", "#000");
		$(".pervTmBtn, .nextTmBtn").attr("disabled", false);
		if(this.index == 0){
//			$(".pervTmBtn").css("color", "#eee");
			$(".pervTmBtn").attr("disabled", true);
		}
		if(this.index >= tmdivs.length - 1){
//			$(".nextTmBtn").css("color", "#eee");
			$(".nextTmBtn").attr("disabled", true);
		}
		var num = 0; 
		for(var i = 0; i < tmdivs.length; i++){
			if(this.hashd($(tmdivs.get(i)))){
				num++;
			}
		}
		this.progressValue = parseInt(num/tmdivs.length*100);
		if(this.progressValue == 100){
			this.showzqda();
		}
		$("#progressvalue").html(this.progressValue + "%");
		this.progress.progressbar("value", this.progressValue);
		this.colorBtn();
		var djcjqk = $(".djcjqk");
		var zqsl = tmdivs.filter("[sfhdzq='true']").length * 1;
		if(djcjqk.length > 0){
			var str = zqsl + "/" + this.kjsfstmsl;
			djcjqk.html(str);
		}
	}
	
	this.hashd = function(tmdiv){
		if(tmdiv.find(".bqd").filter(":checked").length > 0){
			tmdiv.attr("bqd", true);
		}else{
			tmdiv.attr("bqd", false);
		}
		var has = false;
		if(tmdiv.find("input:checked").not(".bqd").length > 0){
			has = true;
		}
		if(!has){
			tmdiv.find(":text, textarea").each(function (){
				if(this.value != ""){
					has =  true;
				}
			});
		}
		tmdiv.attr("hashd", has);
		return has;
	}
	
	this.addOrRemoveOption = function(li){
		li = $(li);
		var ipt = li.find("input");
		var tmdiv = li.parent().parent();
		var select = tmdiv.find(".pxselect");
		var o = select.find("[value='"+ipt.val()+"']");
		if(ipt.attr("checked")){
			if(o.length > 0){
				return;
			}
			var option = $("<option>");
			option.val(ipt.val());
			option.text(li.find("label").text());
			select.append(option);
		}else{
			o.remove();
		}
		this.setPaiXuDa(tmdiv);
	}
	
	this.moveOption = function(btn, method){
		btn = $(btn);
		var tmdiv = btn.parent().parent();
		var select = tmdiv.find(".pxselect");
		var os = select.find(":selected");
		if(method == "first"){
			select.prepend(os);
		}else if(method == "up"){
			var pre = os.prev("option");
			os.insertBefore(pre);
		}else if(method == "down"){
			var next = os.next("option");
			os.insertAfter(next);
		}else if(method == "last"){
			select.append(os);
		}
		this.setPaiXuDa(tmdiv);
	}
	
	this.setPaiXuDa = function(tmdiv){
		var pxda = tmdiv.find(".pxda");
		var os = tmdiv.find(".pxselect > option");
		var da = "";
		for(var i = 0; i < os.length; i++){
			var o = os[i];
			da += o.value;
		}
		pxda.val(da);
	}
	
	this.showDa = function (wjjgtms){
		for(var i = 0; i < wjjgtms.length; i++){
			var wjtm = wjjgtms[i];
			var name = "xx" + wjtm.tmid;
			if(wjtm.xx.length > 0){
				for(var j = 0; j < wjtm.xx.length; j++){
					var xx = wjtm.xx[j];
					$("[name='"+name+"'][value='"+xx+"']").click();
				}
			}
			if(wjtm.nr != ""){
				var nr = wjtm.nr;
				var e = $name(name);
				var tagName = e.get(0).tagName;
				switch(tagName){
					case "TEXTAREA":
						 e.html(nr);
						 break;
					case "INPUT":
						var type = e.get(0).type;
						if(type == "text"){
							var nrs = nr.split("，");
							for(var j = 0; j < nrs.length; j++){
								$name(name).eq(j).val(nrs[j]);
							}
						}else{
							for(var j = 0; j < nr.length; j++){
								$(".pxcheckbox"+wjtm.tmid+"[value='"+nr.charAt(j)+"']").click();
							}
						}
						break;
				}
			}
		}	
	}
	
	this.showzqda = function (){
		this.tmdiv.find(".zqdadiv").fadeIn();
	}
	
	this.showOneDa = function (btn){
		var tmdiv = $(btn).parent().parent().parent();
		var xxda = this.getXxda(tmdiv);
		if(xxda == ""){
			alert("答完题才能看答案！");
			return;
		}
		tmdiv.find(".zqdadiv").show();
		var da = tmdiv.find(".zqdaLabel").html();
		
		if(da == xxda){
			tmdiv.attr("sfhdzq", "true");
			tmdiv.find(".djsfzq").html("<font>恭喜你答对了</font>");
		}else{
			tmdiv.attr("sfhdzq", "false");
			tmdiv.find(".djsfzq").html("<font color='red'>答错了~</font>");
		}
		this.checkProgress();
	}
	
	this.checkOneDa = function (btn){
		var tmdiv = $(btn).parent().parent().parent();
		var xxda = this.getXxda(tmdiv);
		if(xxda == ""){
			alert("请先答题再检查答案！");
			return;
		}
		$(btn).parent().find(".rtm_div").show();
		$(btn).hide();//只能检查一次答案
		var da = tmdiv.find(".zqda").html();
		
		if(this.isXzt(this.getTmlx(tmdiv))){
			if(da == xxda){
				tmdiv.attr("sfhdzq", "true");
				tmdiv.find(".djsfzq").html("<font color='green'>恭喜你答对了。</font>");
			}else{
				tmdiv.attr("sfhdzq", "false");
				tmdiv.find(".djsfzq").html("<font color='red'>答错了，革命尚未成功，同学仍需努力哟。</font>");
			}
		}
		this.showOneDa(btn);
		this.checkProgress();
		if(this.progressValue == 100){
			var tmdivs = this.tmdiv.find(".tmdiv");
			var zqsl = tmdivs.filter("[sfhdzq='true']").length * 1;
			var fenshu = Math.round(zqsl / this.kjsfstmsl *100);
			//评级 80以上优秀 60-79 合格 60以下 不及格
			var zongping = "本次练习的成绩：" +fenshu;
			var title = "";
			if(fenshu > 80)title="辛苦了，你太棒了。";
			else if(fenshu > 60)title="辛苦了，很不错，期待更好的成绩。";
			else title="太可惜了没有合格，再努力一点点，期待你的进步。";
			$( "<div>" ).html(zongping).appendTo($("body")).dialog({
				width:500,
				title:title,
				hide: "explode"
			});
		}
	}
	this.getTmlx = function (tmdiv){
		return tmdiv.find(".tmlxcode").html();
	}
	this.getXxda = function(tmdiv){
		var xxda = "";
		var tmlx = this.getTmlx(tmdiv);
		if(tmlx == "WJ_TI_MU_LX_DAN_XUAN" || tmlx == "WJ_TI_MU_LX_PAN_DUAN" || tmlx == "WJ_TI_MU_LX_DUO_XUAN"){
			tmdiv.find(":checked").each(function (){
				xxda += $(this).attr("px");
			});
		}else if(tmlx == "WJ_TI_MU_LX_PAI_XU"){
			xxda = tmdiv.find(".pxda").val();
		}else if(tmlx == "WJ_TI_MU_LX_TIAN_KON"){
			tmdiv.find(":text").each(function (){
				if(xxda != ""){
					xxda += "，";
				}
				xxda+=this.value;
			});
		}else if(tmlx == "WJ_TI_MU_LX_JIAN_DA"){
			xxda = tmdiv.find("textarea").val();
			xxda = xxda.replace(/(^\s*)|(\s*$)/g, "");
		}
		return xxda;
	}
}