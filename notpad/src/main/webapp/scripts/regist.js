$(document).ready(
	function() {
		$('#email').focus();
//		$('#regist_button').click(regist);
		
		document.onkeydown = function(event) {
			event = event ? event : (window.event ? window.event : null);
			if (event.keyCode == 13) {
				regist();
			}
		};
	}
);
function regist() {
	$('#registForm').submit();
}
/*
 * 改变图片链接，如果自己构造url，需要在页面添加base属性，动态添加前缀
 * 用下面的方式可以自动保存前缀
 * */
function reloadVerifyCode(){
	var imgSrc = $("#visualCode");
	var src = imgSrc.attr("src");
	imgSrc.attr("src",changeUrl(src));
}
function changeUrl(url){
	var timeNow = new Date().getTime();
	if((url.indexOf("?")) >= 0){
		url = url.substring(0,url.indexOf("=") + 1);
		url = url + timeNow;
	} else {
		url = url + "?time=" + timeNow;
	}
	return url;
}