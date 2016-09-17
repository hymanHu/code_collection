/*
 * tools
 */
$(function() {
	//Array contains tools
	Array.prototype.contains = function (element) {
	    for (var i = 0; i < this.length; i++) {
	        if (this[i] == element) {
	            return true;
	        }
	    }
	    return false;
	};
	
	//String end with tools
	String.prototype.endswith = function(c) {
		if (!c) {
			return this.charAt(this.length - 1);
		} else {
			if (typeof c == "string") {
				c = RegExp(c + "$");
			}
			return c.test(this);
		}
	};
});

//替换字符串
function replaceAll(str, sptr, sptr1) {
	while (str.indexOf(sptr) >= 0) {
		str = str.replace(sptr, sptr1);
	}
	return str;
}

//check number
function checkNum(number) {
	var i, j, strTemp;
	strTemp = "0123456789";
	if (number.length == 0) {
		return false;
	}
	for (i = 0; i < number.length; i++) {
		j = strTemp.indexOf(number.charAt(i));
		if (j == -1) {
			return false;
		}
	}
	return true;
}