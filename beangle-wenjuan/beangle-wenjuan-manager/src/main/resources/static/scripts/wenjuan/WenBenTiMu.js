function WenBenTiMu() {
	
	this.div = null;
	this.index = 0;
	this.tmcount = 0;
	this.textarea = null;
	this.timus = null;
	this.timu = function (){
			this.title;
			this.config;
			this.xx = new Array();
	}
	
	this.getTiMuCount = function (){
		return this.timus.length;
	}
	
	this.preview = function(div, text) {
		this.tmcount = 0;
		this.div = div;
		div.html("");
		this.timus = this.format(text);
			
		for(var i in this.timus){
			var tm = this.timus[i];
			
			this.addTiMu(tm, i);
		}
	}
	
	this.replaceAll = function(str, src, target){
		str = str.replace(new RegExp (src, "g"), target);
		return str;
	}
	
	this.format = function (text){
		text+="\n\n#end";
		var lines = text.split(/\n/);
		
		var tms = new Array();
		var tm, lastLine, lineIndex = 0, hasaz = false;//是否有a-z
		
		for(var i in lines){
			var line = lines[i];
			
			line = line.replace(/(^\s*)|(\s*$)/g, "");//去除首尾空格
			
			if(line == ""){//如果碰到空行，就认为是新题目重置行号
				if(lastLine == ""){//忽略重复空行
					continue;
				}
				lineIndex = 0;
				lastLine = line;
				continue;
			}else{//记录行号
				lineIndex++;
			}
			//如果题目选项有标识（A-Z）
			//行数大于2，不是选项（不是以A-Z开头），认为是新题目
			var xxreg = /^[（(]*[A-Za-z][)）]*/;
			
			if(lineIndex > 2 && !line.match(xxreg) && hasaz || "#end" == line){
				lineIndex = 1;
			}
			//创建新题目，并初始化
			if(lineIndex == 1){
				
				//保存上一题目
				
				if(tm){
					//如果没有配置项目，自动添加，题目类型默认为单选题
					if(tm.config == null){
						var config = tm.xx.length != 2 ? "[题目类型：单选题]" : "[题目类型：判断题]" ;
						tm.config = config;
					}
					var config = tm.config;
					//从题目查找答案
					if(this.getConfig("正确答案", config) == null){
						var r = tm.title.match(/[A-Za-z]+$/);
						
						
						if(r){
							//删除题目中的答案
							var zqda = r[0];
							zqda = zqda.toUpperCase();
							tm.title = tm.title.replace(zqda,"");
							config += "[正确答案：" + zqda + "]";
						}
					}
					//如果答案有多个，且题目类型为单选题，那么自动转为多选题
					//默认为单选题
					if(this.getConfig("题目类型", config) == null){
						if(tm.xx.length == 2){
							config = "[题目类型：判断题]" + config;
						}else{
							config = "[题目类型：单选题]" + config;
						}
					}
					if(this.getConfig("题目类型", config) == "填空题"){
						tm.title = tm.title.replace(/\(/g, "（");
						tm.title = tm.title.replace(/\)/g, "）");
					}
					var zqda = this.getConfig("正确答案", config);
					if(zqda != null && zqda.length > 1 ){
						if(config.indexOf("[题目类型：单选题]") >= 0){
							config=config.replace("[题目类型：单选题]", "[题目类型：多选题]")
						}else if(this.getConfig("题目类型", config) == null){
							config += "[题目类型：多选题]";
						}
					}
					tm.config = config;
					tms.push(tm);
				}
				if(line == "#end"){
					break;
				}
				//创建新题目
				tm = new this.timu();
				//去除题目前的数字序号
				if($("#delNum").attr("checked")){//去除题目前的数字
					line = line.replace(/^\d+[．\.\s、]+/, "");
				}
				
				tm.title = line;
				hasaz = false;
			}
			if(line.indexOf("[") == 0 && line.lastIndexOf("]") == line.length - 1){
				//配置项目
				tm.config = line;
			}else if(lineIndex > 1){
				if(line.match(xxreg)){
					hasaz = true;
				}
				//处理选项在同一行情况
				//选项规则[A-Z]\.
				
				var r = line.match(/[A-Z]\./ig);
				
				if(r != null && r.length > 1 && 1==2){
					
					var leftStr = line;
					
					var oa = new Array();
					for(var i = r.length - 1 ; i >=0; i--){
						var rs = r[i];
					
						var os = leftStr.substring(leftStr.lastIndexOf(rs));
						replace(/\)/g, "）");
						os = os.replace(/^[(（]*[a-zA-Z][)）]*[\.。．、\s]+/, "");
						if($("#zhhtml").attr("checked")){//去除html
							os=os.replace(/</g,"&lt;");
							os=os.replace(/>/g,"&gt;")
						}
						
						oa.push(os);
						leftStr = leftStr.substring(0, leftStr.lastIndexOf(rs) - rs.length);
					}
					for(var i = oa.length -1 ; i >= 0; i--){
						tm.xx.push(oa[i]);
					}
				}else{
					
					line = line.replace(/^[(（]*[a-zA-Z][)）]*[\.。．、\s]+/, "");
					if($("#zhhtml").attr("checked")){//去除html
						line=line.replace(/</g,"&lt;");
						line=line.replace(/>/g,"&gt;")
					}
					tm.xx.push(line);
				}
			}
			lastLine = line;
		}
		return tms;
	}
	
	this.addTiMu = function(tm, index) {
		var config = tm.config;
		var xxtype = this.getConfig("题目类型", config, "单选题");
		var zqda = this.getConfig("正确答案", config);
		var g = $("<fieldset></fieldset>");
		var title = tm.title;
		var titleLabel = $("<legend></legend>");
		if(xxtype == "填空题"){
			title = this.getTianKong(title, zqda);
		}else{
		}
		titleLabel.html((parseInt(index)+1) + ". " + title);
		g.append(titleLabel);

		if(xxtype == "判断题" || xxtype == "单选题" || xxtype == "多选题"){
			var xxGroup = $("<ol type='A'></ol>")
			var a = "A".charCodeAt(0);
			for (var i = 0; i < tm.xx.length; i++) {
				var px = String.fromCharCode(a++);
				var xx = tm.xx[i];
				var xx = this.getxx(xx, xxtype, index, i);
				xxGroup.append(xx);
				if(zqda != null && zqda.indexOf(px) >=0){
					xx.css("color", "red");
					xx.find("input").attr("checked", true);
				}
			}
			g.append(xxGroup);
		}else{
			var content = null;
			switch(xxtype){
				case "简答题":
					content =  $("<li><textarea rows=5  style='width:500px; color:red;'>"+zqda+"</textarea></li>");
					break;
				case "排序题":
					content = this.getPaiXu(tm, index);
					break;
				case "网格题":
					content = this.getWanGe(tm, index);
					break;
			}
			if(content != null){
				g.append(content);
			}
		}
		this.div.append(g);
	}
	
	this.getTianKong = function (title, zqda){
		if(zqda == null){
			zqda = "";
		}
		var index = -1, lastIndex = -1, num=0;
		var ntitle = "";
		zqda = zqda.split("，");
		while((index = title.indexOf("（", lastIndex+1)) >= 0){
			if(index > 0){
				ntitle += title.substring(lastIndex+1, index+1).replace(/(^\s*)|(\s*$)/g, "");
			}
			var value = "";
			if(zqda.length > num){
				value = zqda[num];
			}
			num++;
			ntitle += "<input class='tianKong' value='"+value+"'/>";
			lastIndex = index;
		}
		ntitle += title.substring(lastIndex+1).replace(/(^\s*)|(\s*$)/g, "");;
		return ntitle;
	}
	
	this.getPaiXu = function(tm, index){
		var div = $("<div style='float:left'>");
		var xxdiv = $("<div style='float:left'>");
		for(var i = 0; i < tm.xx.length; i++){
			xxdiv.append(this.getxx(tm.xx[i], "排序题", index, i));
		}
		var selectDiv =  $("<select multiple style='float:left; width:150px; height: 150px;margin-left:20px;color:red;'>");
		var zqda = this.getConfig("正确答案", tm.config);
		if(zqda != null){
			var a = "A".charCodeAt(0);
			var os = xxdiv.find("label");
			for(var i = 0; i < zqda.length; i++){
				var x = zqda.charCodeAt(i) - a;
				var text = os[x].innerHTML;
				selectDiv.append("<option>"+text+"</option>");
			}
		}
		var btndiv = $("<div style='float:left; width:100px;'>");
		btndiv.append("<input type='button' value='移到最前'/>")
		btndiv.append("<input type='button' value='上移一位'/>")
		btndiv.append("<input type='button' value='下移一痊'/>")
		btndiv.append("<input type='button' value='移到最后'/>")
		div.append(xxdiv);
		div.append(selectDiv);
		div.append(btndiv);
		return div;
	}
	this.getWanGe = function(tm, index){
		var tmxx = this.getConfig("题目选项", tm.config);
		if(tmxx == null || tm.xx.length == 0){
			return;
		}
		var li = $("<li>");
		var table = $('<table class="ctable">');
		tmxx = this.replaceAll(tmxx, "、", "，");
		tmxx = tmxx.split("，");
		var thead = $("<thead>");
		thead.append($("<th>"));
		for(var i = 0; i < tmxx.length; i++){
			var xx = tmxx[i];
			var th = $("<th>").html(xx);
			thead.append(th);
		}
		table.append(thead);
		for(var i = 0; i < tm.xx.length; i++){
			var xx = tm.xx[i];
			var tr = $("<tr>");
			tr.append($("<td class='title'>").html(xx));
			for(var j = 0; j < tmxx.length; j++){
				tr.append($("<td align='center'><input type='radio' name='tm"+index+i+"'/></td>"));
			}
			table.append(tr);
		}
		li.append(table);
		return li;
	}
	
	this.getConfig = function(type, config, dvalue){
		if(!dvalue){
			dvalue = null;
		}
		if(config == null){
			return dvalue;
		}
		var xxtype = config.match("\\["+type+"：[^\\]]*\\]");
		if(xxtype == null){
			return dvalue;
		}
		if(xxtype.length == 0){
			xxtype = dvalue;
		}else{
			xxtype = xxtype[0].split("：")[1];
			xxtype = xxtype.substring(0, xxtype.length - 1);
		}
		return xxtype;
	}
	
	this.getxx = function(xx, xxtype, index, xxindex){
		var xxli = $("<li style='list-style-type:upper-alpha;'></li>");
		xx = this.subxx(xx, ".");
		xx = this.subxx(xx, "。");
		xx = this.subxx(xx, "．");
		var inputType = null;
		switch(xxtype){
			case "判断题":
			case "单选题":
				inputType = "radio";
				break;
			case "多选题":
			case "排序题":
				inputType = "checkbox";
				break;
		}
		if(inputType != null){
			var input = $("<input id='tm"+index+"_" + xxindex +"' type='"+inputType+"' name='tm"+index+"' /><label for='tm"+index+"_" + xxindex +"'>"+xx+"</label>");
			xxli.append(input);
			return xxli;
		}
		return null;
	}
	
	this.subxx = function(xx, sub){
		if(xx.indexOf(sub) < 2){
			xx = xx.substring(xx.indexOf(sub) + 1);
		}
		return xx;
	}
	this.formatText = function (){
		var textarea = this.textarea.get(0);
		var text = textarea.value;
		var tms = this.format(text);
		this.tmcount = tms.length;
		textarea.value = "";
		for(var i in tms){
			var tm = tms[i];
			if(i > 0){
				textarea.value += "\n";
			}
			textarea.value += tm.title;
			textarea.value += "\n";
			textarea.value += tm.config;
			textarea.value += "\n";
			var a = "A".charCodeAt(0);
			var tmlx = this.getConfig("题目类型", tm.config);
			for(var j in tm.xx){
				var xx = tm.xx[j];
				if(tmlx != "网格题"){
					textarea.value += String.fromCharCode(a++) + ".";
				}
				textarea.value += xx;
				textarea.value += "\n";
			}
		}
	}
	this.addContent = function(str){
		var textarea = this.textarea.get(0);
		textarea.focus();
		if(document.selection){
			document.selection.createRange().text=str; 
		}else{
			textarea.value=textarea.value.substr(0,textarea.selectionStart)+str+textarea.value.substring(textarea.selectionStart);
		}
		this.preview(this.div, textarea.value);
	}
	this.addConfig = function (type){
		var config = "[题目类型：" + type + "]";
		switch(type){
		case "网格题":
			config+="[题目选项：]";
			break;
		default:
			config+="[正确答案：]";
		}
		this.addContent(config);
	}
}