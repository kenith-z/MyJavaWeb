//BrowserName
function browserName(){
	var ua = window.navigator.userAgent;
	if(ua.indexOf("MSIE")>-1) return "MSIE";
	else if(ua.indexOf("Firefox")>-1) return "Firefox";
	else if(ua.indexOf("Chrome")>-1) return "Chrome";
	else if(ua.indexOf("Safari")>-1) return "Safari";
	else if(ua.indexOf("Opera")>-1) return "Opera";
	else if(ua.indexOf("QQBrowser")>-1) return "QQBrowser";
	else return "other";
}

//Site Root
var _ROOT = null;
function getRoot(){
	var path = document.location.pathname;
	var pos = path.indexOf("/",1);
	var root = path.substring(0,pos);
	if(root.indexOf("/")==-1) root="/"+root;
	return root;
}

//goPage
function goPage(xForm, page){
	disableAllButton(xForm);
	if(typeof(xForm.option)!="undefined") xForm.option.value="LIST";
	xForm.page.value = page;
	xForm.submit();
}

//doAction
function doAction(xForm, option, info){
	if(option=="DEL"){
		if(!confirm("'"+info+"' 将被删除，确定吗?")) return;
	}
	var url;
	try{url=xForm.tagName;}catch(e){;}finally{;}
	if(typeof(xForm)=="string"){
		location = xForm.replace("/DipoJson.cgi", "/DipoView.cgi");
	}else if(url=="FORM"){
		if(typeof(xForm.option)!="undefined") xForm.option.value=option;
		disableAllButton(xForm);
		xForm.action = xForm.action.replace("/DipoJson.cgi", "/DipoView.cgi");
		xForm.submit();
	}
}

//show a modal dialog window
function popWin(uri, value){
	var obj = new Object();
	if(typeof(value)=="string") obj.key=value;
	var returnValue = window.showModalDialog(uri,obj,"dialogWidth:800px;dialogHeight:600px;status:no;");
}

//trim left space
function trimLeft(strSrc){
	if(typeof(strSrc)!="string") return strSrc;
	var len = strSrc.length;
	for(var i=0; i<len; i++)
		if(strSrc.charAt(i)!=" ")
			break;
	strSrc = strSrc.substring(i,len);
	return strSrc;
}

//trim right space
function trimRight(strSrc){
	if(typeof(strSrc)!="string") return strSrc;
	var len = strSrc.length;
	for(var i=len-1; i>=0; i--)
		if(strSrc.charAt(i)!=" ")
			break;
	strSrc = strSrc.substring(0,i+1);
	return strSrc;
}

//trim both side space
function trimBoth(strSrc){
	strSrc = trimLeft(strSrc);
	strSrc = trimRight(strSrc);
	return strSrc;
}

//trim all text'value
function trimAllText(xForm){
	var obj;
	for(var i=0; i<xForm.elements.length; i++){
		obj = xForm.elements[i];
		if(obj.type=="text"){
			obj.value = trimBoth(obj.value);
		}
	}
}

//disableAllButton
function disableAllButton(xForm){
	var obj;
	for(var i=0; i<xForm.elements.length; i++){
		obj = xForm.elements[i];
		if(obj.type=="button" || obj.type=="submit" || obj.type=="reset"){
			obj.disabled = true;
		}else if(obj.type=="text" || obj.type=="password"){
			obj.value = trimBoth(obj.value);
		}
	}
	prepareSyncSubmit(xForm);
}
function prepareSyncSubmit(xForm){
	if(xForm.enctype=="multipart/form-data"){
		//remove _uploadid before add
		var action = xForm.action;
		if(action.indexOf("_uploadid=")!=-1){
			xForm.action = action.substring(0, action.indexOf("_uploadid=")-1);
		}
		var uploadId = Math.random().toString();
		uploadId = uploadId.substr(uploadId.indexOf(".")+1);
		if(xForm.action.indexOf("?")==-1){
			xForm.action += "?_uploadid="+uploadId;
		}else{
			xForm.action += "&_uploadid="+uploadId;
		}
		//remove _filename before add
		if(typeof(xForm._filename)!="undefined"){xForm.removeChild(xForm._filename);}
		var objs = xForm.getElementsByTagName("input");
		for(var i=0; i<objs.length; i++){
			if(objs[i].type=="file"){
				var fileName = objs[i].value.substr(objs[i].value.lastIndexOf("\\")+1);
				var newobj = document.createElement("input");
				newobj.setAttribute("type", "hidden");
				newobj.setAttribute("id",   "_filename");
				newobj.setAttribute("name", "_filename");
				newobj.setAttribute("value", fileName);
				xForm.insertBefore(newobj, xForm.firstChild);
				break;
			}
		}
	}
	//add _charset
	if(typeof(xForm._charset)=="undefined"){
		var charsetName = "utf-8";
		if(typeof(document.charset)!="undefined") charsetName=document.charset;
		var newobj = document.createElement("input");
		newobj.setAttribute("type", "hidden");
		newobj.setAttribute("id",   "_charset");
		newobj.setAttribute("name", "_charset");
		newobj.setAttribute("value", charsetName);
		xForm.insertBefore(newobj, xForm.firstChild);
	}
}

//enableAllButton
function enableAllButton(xForm){
	var obj;
	for(var i=0; i<xForm.elements.length; i++){
		obj = xForm.elements[i];
		if(obj.type=="button" || obj.type=="submit" || obj.type=="reset"){
			obj.disabled = false;
		}
	}
}

/***********************************************************************
 *Function: clear all control's content(text, textarea, password, checkbox, radio, droplistbox)
 *Athor: YXK
 *Add Date: 2008-01-22
 *Modify: 
 **********************************************************************/
function clearAllContent(xForm){
	var i;
	for(i=0; i<xForm.elements.length; i++){
		if(xForm.elements[i].type=="text"
			|| xForm.elements[i].type=="textarea"
			|| xForm.elements[i].type=="password"){
			xForm.elements[i].value="";
		}else if(xForm.elements[i].tagName=="SELECT"){
			xForm.elements[i].selectedIndex=0;
		}else if(xForm.elements[i].type=="checkbox"
			|| xForm.elements[i].type=="radio"){
			xForm.elements[i].checked=false;
		}
	}
}

function download(page){
	var img = document.getElementById("loading");
	img.style.display="";
	disableAllButton(theform);
	open(page, 'footframe', 'titlebar=no,status=no,toolbar=no,scrollbars=no,menubar=no,location=no', true);
	setTimeout(function(){
		enableAllButton(theform);
		img.style.display="none";
	},3000);
	/****************
	var obj = document.getElementById("footframe");
	obj.onreadystatechange = function(){
		alert(obj.readyState);
		if(obj.readyState=="interactive" || obj.readyState=="complete"){
			enableAllButton(theform);
			img.style.display="none";
		}
	}
	****************/
}

function downloadReport(xForm){
	var img = document.getElementById("loading");
	img.style.display = "";
	disableAllButton(xForm);
	var target = xForm.target;
	xForm.target = "footframe";
	var option = xForm.option.value;
	xForm.option.value = "DOWNLOAD";
	xForm.submit();
	setTimeout(function(){
		enableAllButton(xForm);
		img.style.display = "none";
		xForm.option.value = option;
		xForm.target = target;
	},3000);
}

//retrun current date yyyy-MM-dd
function getNowDate(){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	month = month.toString().length==1?"0"+month:month;
	day = day.toString().length==1?"0"+day:day;
	return year+"-"+month+"-"+day;
}

//retrun current time HH:mm:ss
function getNowTime(){
	var date = new Date();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();
	hour = hour.toString().length==1?"0"+hour:hour;
	minute = minute.toString().length==1?"0"+minute:minute;
	second = second.toString().length==1?"0"+second:second;
	return hour+":"+minute+":"+second;
}

//retrun current full yyyy-MM-dd HH:mm:ss
function getNowFull(){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();
	month = month.toString().length==1?"0"+month:month;
	day = day.toString().length==1?"0"+day:day;
	hour = hour.toString().length==1?"0"+hour:hour;
	minute = minute.toString().length==1?"0"+minute:minute;
	second = second.toString().length==1?"0"+second:second;
	return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
}

//retrun current unformated date yyyyMMddHHmmss
function getNowUFDate(){
	var str = getNowFull();
	str = str.replace(/-/g, "");
	str = str.replace(/:/g, "");
	str = str.replace(/\s/g, "");
	return str;
}

//return week day in CN type
function getNowWeek(){
	var week = {'0':'星期天','1':'星期一','2':'星期二','3':'星期三','4':'星期四','5':'星期五','6':'星期六'};
	var day = new Date().getUTCDay();
	return week[day];
}

//replace the str's oldSubStr with newSubStr
function replace(str, oldSubStr, newSubStr){
	var re = new RegExp(oldSubStr, "g");
	str = str.replace(re, newSubStr);
	return str;
}

function text2html(str){
	var reg1 = new RegExp("&lt;", "g");
	var reg2 = new RegExp("&gt;", "g");
	str = str.replace(reg1, "<");
	str = str.replace(reg2, ">");
	return str;
}

function html2text(str){
	var reg1 = new RegExp("<", "g");
	var reg2 = new RegExp(">", "g");
	str = str.replace(reg1, "&lt;");
	str = str.replace(reg2, "&gt;");
	return str;
}

function removeHtmlTag(str){
	str = str.replace(/<(!--)[\s\S]*?>/g, "");//去掉注解体
	str = str.replace(/<(!\[[a-zA-A]+\])[\s\S]*?-->/g, "");//去掉注解体
	str = str.replace(/(<!\[CDATA\[)|(\]\]>)/ig, "");//去掉文本段标记
	str = str.replace(/<(\?)?xml[^>]*>[\s\S]*?<\/xml>/ig, "");//去掉xml体
	str = str.replace(/<script[^>]*>[\s\S]*?<\/script>/ig, "");//去掉script体
	str = str.replace(/<style>[\s\S]*?<\/style>/ig, "");//去掉style体
	str = str.replace(/<[^>][\s\S]*?>/g, "");//去掉html标签
	str = str.replace(/(&nbsp;)/g, " ");//转换html空格
	str = str.replace(/^\s+/g, "");//去掉前面空格
	str = str.replace(/\/$/g, "");//去掉后面的"/"
	str = str.replace(/<$/g, "");//去掉后面的"<"
	str = str.replace(/\s+$/g, "");//去掉后面空格
	return str;
}

function setOptions(dropCtrl, s){
	var i;
	for(i=0; i<dropCtrl.length; i++){
		if(s.search(dropCtrl.options[i].value) != -1)
			dropCtrl.options[i].selected = true;
		else
			dropCtrl.options[i].selected = false;
	}
}

function getOptions(dropCtrl){
	var i;
	var s = "";
	for(i=0; i<dropCtrl.length; i++){
		if(dropCtrl.options[i].selected){
			s += dropCtrl.options[i].value + ",";
		}
	}
	s = s.substr(0, s.length-1);
	return s;
}

//清空下拉框中的所有项
function clearOptions(selectObj){
	for(var i=selectObj.options.length-1; i>=0; i--){
		var opt = selectObj.options[i];
		selectObj.removeChild(opt);
	}
}

//为下拉框插入一条记录
function appendOption(selectObj, value, text){
	var opt = document.createElement("option");
	opt.value = value;
	opt.text = text;
	if(browserName()=="MSIE"){
		selectObj.add(opt);
	}else{
		selectObj.appendChild(opt);
	}
}

function isFile(str){
	if(trimBoth(str).length==0){
		return false;
	}else{
		return true;
	}
	if(str==null){
		return false;
	}else{
		var strlen = str.length;
		var i = 0;
		for(i=0; i<strlen; i++){
			var char = str.substring(i,i+1);
			if(char >= 'a' && char <= 'z') continue;
			if(char >= 'A' && char <= 'Z') continue;
			if(char >= '0' && char <= '9') continue;
			if(char == '_' ) continue;
			if(char == '.' ) continue;
			if(char == ':' ) continue;
			if(char == '/' ) continue;
			if(char == '\\') continue;
			return false;
		}
	}
	return true;
}

function setMainHeight(height){
	var MIN_HEIGHT = 515;
	var mainHeight = MIN_HEIGHT;
	var lastHeight = 40;
	var browserToolBarHeight = 139;
	var byBodyHeight = true;
	if(typeof(height) != "number"){
		var firstObj = document.body.children[0];
		if(typeof(firstObj) == "object"){
			var maxWinHeight = window.screen.availHeight;
			if(byBodyHeight) maxWinHeight=document.body.clientHeight;//取网页可见区域高
			var firstHeight = firstObj.offsetHeight;
			if(typeof(maxWinHeight)=="number" && typeof(firstHeight)=="number"){
				mainHeight = maxWinHeight - (byBodyHeight?0:browserToolBarHeight) - firstHeight - lastHeight;
			}
		}
	}else{
		mainHeight = height;
	}
	var view = document.getElementById("detailWin");
	if(mainHeight < MIN_HEIGHT){
		view.style.height = MIN_HEIGHT+"px";
	}else{
		view.style.height = mainHeight + "px";
	}
}

function coverWinHead(width){
	var bodyWidth = document.body.clientWidth;
	var bodyHeight = document.body.clientHeight;
	var blockWidth = typeof(width)=="number"?width:360;
	var blockLeft = (bodyWidth-blockWidth)/2;
	if(bodyHeight<450) bodyHeight=450;
	document.write("<div id='coverwin' style='display:none;position:absolute;top:0px;left:0px;width:"+bodyWidth+"px;height:"+bodyHeight+"px;z-index:50;background-color:#BEBEBE;filter:;'>");
	document.write("<table style='position:absolute;top:40px;left:"+blockLeft+"px;border-collapse:collapse;z-index:9999;' width='"+blockWidth+"' border='1' bgcolor='#FFFFFF' bordercolor='#008080'>");
	document.write("<tr><td align='right' valign='middle' style='height:10px;line-height:10px;'><img src='"+getRoot()+"/images/close01.gif' onclick='fadeCoverWin()'/></td></tr>");
	document.write("<tr>");
	document.write("<td align='center' valign='top'>");
}
function coverWinFoot(){
	document.write("</td>");
	document.write("</tr>");
	document.write("</table>");
	document.write("</div>");
}
function fadeCoverWin(){
	var obj = document.getElementById("coverwin");
	if(obj.style.display=="none"){
		obj.style.display="";
	}else{
		obj.style.display="none"
	}
}

//switch button background
function enterBt(e){
	if(_ROOT==null) _ROOT=getRoot();
	var obj = e ? e.target : event.srcElement;
	obj.style.color = "#000000";
	obj.style.background = "transparent url("+_ROOT+"/images/button09.gif) no-repeat center top";
}
function clickBt(e){
	if(_ROOT==null) _ROOT=getRoot();
	var obj = e ? e.target : event.srcElement;
	obj.style.color = "#2F4F4F";
	obj.style.background = "transparent url("+_ROOT+"/images/button05.gif) no-repeat center top";
}
function leaveBt(e){
	if(_ROOT==null) _ROOT=getRoot();
	var obj = e ? e.target : event.srcElement;
	obj.style.color = "#FFFFFF";
	obj.style.background = "transparent url("+_ROOT+"/images/button07.gif) no-repeat center top";
}
