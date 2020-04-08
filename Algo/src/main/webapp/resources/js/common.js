var g_contextPath = true;
function getContextPath(){
	if(g_contextPath){
		let idx = location.href.indexOf(location.host)+location.host.length;
		return location.href.substring(idx,location.href.indexOf('/',idx+1));
	}else{
		return "";
	}
}