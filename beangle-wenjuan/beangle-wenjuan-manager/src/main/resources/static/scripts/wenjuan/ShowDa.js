function ShowDa() {
	this.acode = "A".charCodeAt(0);
	this.showAll = function(fieldsets) {
		for ( var i = 0; i < fieldsets.length; i++) {
			var fieldset = $(fieldsets.get(i));
			var tmlxcode = fieldset.find(".tmlxcode").html();
			switch (tmlxcode) {
			case "WJ_TI_MU_LX_DAN_XUAN":
			case "WJ_TI_MU_LX_PAN_DUAN":
			case "WJ_TI_MU_LX_DUO_XUAN":
				this.showDanXuan(fieldset);
				break;
			case "WJ_TI_MU_LX_PAI_XU":
				this.showPaiXu(fieldset);
				break;
			case "WJ_TI_MU_LX_JIAN_DA":
				this.showJianDa(fieldset);
				break;
			case "WJ_TI_MU_LX_TIAN_KON":
				this.showTianKon(fieldset);
				break;
			}
		}
	}
	
	this.getZqda = function(fieldset){
		var zqda = fieldset.find(".zqda").html();
		return zqda;
	}
	
	this.showDanXuan = function(fieldset){
		var zqda = this.getZqda(fieldset);
		var ipts = fieldset.find("input");
		for(var i = 0; i < ipts.length; i++){
			var ipt = $(ipts[i]);
			//var px = String.fromCharCode(this.acode+i);
			var px = ipt.attr("px");
			if(zqda.indexOf(px) >=0){
				ipt.attr("checked", true);
				ipt.parent().css("color", "red");
			}
		}
	}
	
	this.showPaiXu = function(fieldset){
		var zqda = this.getZqda(fieldset);
		var selectDiv = fieldset.find("select");
		selectDiv.css("color", "red");
		if(zqda != null){
			var os = fieldset.find("li label");
			for(var i = 0; i < zqda.length; i++){
				var x = zqda.charCodeAt(i) - this.acode;
				var text = os[x].innerHTML;
				selectDiv.append("<option>"+text+"</option>");
			}
		}
	}
	
	this.showTianKon = function(fieldset){
		var zqda = this.getZqda(fieldset).split("，");
		var ipts = fieldset.find("input").css("color", "red");
		for(var i = 0; i < ipts.length && i < zqda.length; i++){
			 ipts[i].value = zqda[i];
		}
		
	}
	
	this.showJianDa = function(fieldset){
		var zqda = this.getZqda(fieldset);
		fieldset.find("textarea").css("color", "red").val(zqda);
	}
}