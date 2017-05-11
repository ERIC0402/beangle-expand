function WenJuanTiMu(){
	this.xxtable = $("#xxtable");//选项表格
	this.trClass = ".xxtr";//选项行
	this.xxpxClass = ".xxpx";//选项排序
	this.xxxhClass = ".xxxh";//选项序号
	this.templatexxtrClass = ".templatexxtr";//模板选项行
	this.delmsglt2 = "至少应该保留2个选项！";
	
	this.upxx = function(tr){
		var ptr = tr.prev(this.trClass);
		if(ptr.length == 0){
			return;
		}
		tr.insertBefore(ptr);
		this.px();
	}
	
	this.downxx = function(tr){
		var ntr = tr.next(this.trClass);
		if(ntr.length == 0){
			return;
		}
		tr.insertAfter(ntr);
		this.px();
	}
	
	this.delxx = function(tr){
		if(this.xxtable.find(this.trClass).length == 2){
			alert(this.delmsglt2);
			return;
		}
		tr.remove();
		this.px();
	}
	
	this.addxx = function(tr){
		var xh = this.getNextXh();
		var str = $(this.templatexxtrClass).val();
		str = str.replace(/\{i\}/g, xh);
		var ntr = $(str);
		ntr.insertAfter(tr);
	}
	
	this.getNextXh = function(){
		var max = 0;
		var v = this;
		this.getTrs().each(function (){
			var xh = $(this).find(v.xxxhClass).val();
			xh = parseInt(xh);
			if(xh > max){
				max = xh;
			}
		});
		return ++max;
	}
	
	this.getTrs = function (){
		return this.xxtable.find(this.trClass);
	}
	
	this.px = function (){
		var px = 0;
		var v = this;
		this.getTrs().each(function (){
			$(this).find(v.xxpxClass).val(px++);
		});
	}
}