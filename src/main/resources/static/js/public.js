// --------------------------------------------------------------------
// 공용 라이브러리
// 일시 : 2017-02-01 
// 작성 : 박용서
// 설명 : 모든 페이지에서 보이는 공용라이브러리입니다.
// 주의 : 모든 페이지에서 로드되기 때문에 공통적인 모듈만 넣으시기 바랍니다.
// --------------------------------------------------------------------

// 각종 input 스캔
$(function() 
{
	// 모든 달력 스캔
	// 일반사용 : <input type="text" mode="date" />
	// 포멧설정 : <input type="text" mode="date" format="yymmdd" />
	$('input[type="text"][mode="date"]').each(function(idx, e){
		var node = $(e);
		var opt = {};
		var gap = 1;
		
		node.attr('readonly', 'readonly'); // 임의로 쓸 수 없게 만듬
		
		if (node.is('[format]')) {
			opt.dateFormat = node.attr('format');
		}
		if (node.is('[icon]')) { 
			util.ext(opt, {showOn: "both", buttonImage: "/images/icon/icon02.png", buttonImageOnly: true, buttonText: "날짜선택"}); 
		}
		if (node.is('[date-gap]')) {
			gap = Number(node.attr('date-gap'));
		}
		if (node.is('[date-under-id]')) {
			var target = $('#'+node.attr('date-under-id'));
			target.on('change', (function() {
				this.node.datepicker('option', 'maxDate', this.target.datepicker('getDate').addTime(this.gap * -86400000));
	        }).bind({node : node, target : target, gap : gap}));
		}
		if (node.is('[date-over-id]')) {
			var target = $('#'+node.attr('date-over-id'));
			target.on('change', (function() {
				this.node.datepicker('option', 'minDate', this.target.datepicker('getDate').addTime(this.gap * 86400000) );
	        }).bind({node : node, target : target, gap : gap}));
		}
		// 시간나면 레인지를 포함한 민 맥스 도 만들어야함.
		node.datepicker(opt);
	});
	
	// 엔터 인식
	// <input type="text" mode="enter" mode-func="login"/> -> login(); 실행
	$('input[type="text"][mode="enter"]').each(function(idx, e){
		e.onkeyup=(function(e){ if (e.keyCode == 13) { var fn = $(e.target).attr("mode-func"); (eval('('+ fn +')'))(); } });
	});
	
	// a태그에 href가 # 인경우 해당 주소이동은 금지시킨다.
	document.addEventListener('click', function (event) {
		var el = event.target;
		if (el.tagName.toLowerCase() == 'a' && el.getAttribute('href') == '#') {
			try { event.preventDefault(); } catch (e) {  }
			return false;
		}
	});
});

// 각종유틸
var util = {
	// 현재 시간
	time : function() {
		return new Date().getTime();
	},
	// 0 ~ max
	rand : function(max) {
		return Math.floor(Math.random() * max + 1);
	},
	// 오브젝트 덮어쓰기 [기존 오브젝트에 적용]
	ext : function (dest, src) {
		for (var attr in src) {
			if (src.hasOwnProperty(attr)) {
				dest[attr] = src[attr];
			}
		}
		return dest;
	},
	nvl : function() {
		for (var i = 0 ; i < arguments.length ; i++) {
			var val = arguments[i];
			if (val != undefined && val != null) {
				return val;
			}
		}
		return null;
	},
	getParam : function(name) {
		
		// 선언
		var src = {};
		
		// 기존정보
		var lsrcp = location.search;
		if (lsrcp != null && lsrcp.length > 1) {
			var nodes = location.search.sub(1).split('&');
			for (var i = 0 ; i < nodes.length ; i++) {
				var node = nodes[i].split('=');
				if (node.length == 2) {
					src[node[0]] = decodeURIComponent(node[1]);
				}
			}
		}
		
		if (name) {
			return src[name] != undefined ? src[name] : null;
		}
		return src;
	},
	// 해당 주소로 이동
	moveParam : function(params, clear) {
		
		// 선언
		var src = {}, srcp = '';
		
		// 기존정보
		if (clear !== true) {
			var lsrcp = location.search;
			if (lsrcp != null && lsrcp.length > 1) {
				var nodes = location.search.sub(1).split('&');
				for (var i = 0 ; i < nodes.length ; i++) {
					var node = nodes[i].split('=');
					if (node.length == 2) {
						src[node[0]] = decodeURIComponent(node[1]);
					}
				}
			}
		}
		
		// 덮어쓰기
		for (var param in params) {
			if (params.hasOwnProperty(param)) {
				var val = params[param];
				if (val != null) {
					src[param] = (val + "").trim();
				} else {
					src[param] = undefined;
				}
			}
		}
		
		// 이동
		for (var name in src) {
			if (src.hasOwnProperty(name)) {
				var val = src[name];
				if (val == undefined) {
					continue;
				}
				if (srcp.length == 0) { srcp += '?'; } else { srcp += '&'; }
				srcp += name + "=" + encodeURIComponent(src[name]);
			}
		}
		
		location.href = srcp;
	},
	
	// 해더는 'CUST_INFO=회원정보,CUST_NM=회원이름' 과 같이 넣습니다.
	listDataToTable : function(listData, header) {
		var table = $('<table></table>');
		var cols = [];
		
		if (!header) {
			header = "";
			
			for (var name in listData) {
				if (listData.hasOwnProperty(name)) {
					header += ","+name+"="+name;
				}
			}
			
			header = header.substring(1);
		}
		
		var colsv = header.split(/\,/);
		var tr = $('<tr></tr>');
		
		for (var i = 0 ; i < colsv.length ; i++) {
			var cc = colsv[i].split(/\=/);
			cols.push(cc[0]);
			tr.append($('<th></th>').text(cc[1]));
		}
		
		table.append(tr);
		
		for (var i = 0 ; i < listData.length ; i++) {
			var tr = $('<tr></tr>');
			for (var j = 0 ; j < cols.length ; j++) {
				tr.append($('<td></td>').text(listData[i][cols[j]]));
			}
			table.append(tr);
		}
		
		return table;
	}
};

// 프로토 타입 유틸
util.ext(String.prototype, {
	isJumin : function() {
		if (this.test(/^[\d]{13}$/) && ((('1256'.iof(this.sub(6, 7)) != -1 ? '19' : '20') + this.sub(0, 6)).isDate('yyyyMMdd'))) {
			var sum = 0, key = [2,3,4,5,6,7,8,9,2,3,4,5];
			for (var i = 0 ; i < 12 ; i++) {
				sum += this.charAt(i).toNum() * key[i];
			}
			return ((11 - (sum % 11)) % 10) == this.charAt(12).toNum(); 
		}
		return false;
	},
	range : function(min, max) {
		return this.length >= min && this.length <= max;
	},
	isInt : function() {
		return this.test(/^[\d]+$/); 
	},
	test : function(regex) {
		return regex.test(this);
	},
	toNum : function() {
		return Number(this);
	},
	isDate : function(format) {
		return Date.isDate(this, format);
	},
	eval : function() {
		return eval('(' + this + ')');
	},
	//empty to null
	etn : function() {
		return this.trim().length > 0 ? this : null;
	},
	deHtml : function() {
		var el = document.createElement('div');
		el.innerText = this;
		return el.innerHTML
	},
	iof : String.prototype.indexOf,
	lof : String.prototype.lastIndexOf,
	sub : String.prototype.substring,
	re : String.prototype.replace,
	toKoAtom : function() {
		if (this.length == 0) { return ''; }
		var 가 = '가'.charCodeAt(0), 힣 = '힣'.charCodeAt(0), ㄱ = 'ㄱ'.charCodeAt(0), ㅣ = 'ㅣ'.charCodeAt(0);
		var a = '';
		for (var i = 0 ; i < this.length ;) {
			var ch = this.charCodeAt(i++);
			if (ch >= 가 && ch <= 힣) {
				var rv = '';
				// 한글의 시작부분을 구함
				var ce = ch - 가;
				// 초성을 구함
				rv +=  utilv.KO_ATOM_S[(ce / (588)).floor()];
				// 중성을 구함
				rv +=  utilv.KO_ATOM_M[((ce = ce % (588)) / 28).floor()]; // 21 * 28
				// 종성을 구함
				if ((ce = ce % 28) != 0)
				{
					rv +=  utilv.KO_ATOM_E[ce];
				}
				a += rv;
				continue;
			} else if (ch >= ㄱ && ch <= ㅣ) {
				a += utilv.KO_ATOM_P[ch - ㄱ];
				continue;
			}
			a += ch.toChar();
		}
		return a;
	}
});

util.ext(Number.prototype, {
	toStr : function() {
		return this + '';
	},
	toDate : function()
	{
		return new Date(this);
	},
	toStrBin : function()
	{
		return this.toStr(2);
	},
	toStrOct : function()
	{
		return this.toStr(8);
	},
	toStrHex : function()
	{
		return this.toStr(16);
	},
	toStrZf : function(lenZeroFill)
	{
		var sign = this < 0;
		var str = (sign ? (this * -1) : this).toStr();
		var diff = lenZeroFill - str.length;
		var zero = '';
		
		for (var i = 0 ; i < diff ; i++) { zero += '0'; }
		
		return (sign ? '-' : '') + zero + str;
	},
	toChar : function()
	{
		return String.fromCharCode(this);
	},
	floor : function() {
		return Math.floor(this);
	}
});

util.ext(Date.prototype, {
	addTime : function(time) {
		this.setTime(this.getTime() + time);
		return this;
	},
	toStr : function(format) {
		return Date.toStr(this, format);
	},
});

util.ext(Date, {
	isDate : function(dateText, format) {
		return Date.toDate(dateText, format, true) != null;
	},
	toStr : function(date, format) {
		return format
			.re('yyyy', date.getFullYear()+'')
			.re('yy', date.getFullYear().toStr().sub(2).toNum().toStrZf(2))
			.re('MM', (date.getMonth() + 1).toStrZf(2))
			.re('dd', date.getDate().toStrZf(2))
			.re('HH', date.getHours().toStrZf(2))
			.re('mm', date.getMinutes().toStrZf(2))
			.re('SSS', date.getMilliseconds().toStrZf(3))
			.re('ss', date.getSeconds().toStrZf(2));
	},
	toDate : function(dateText, format, isSafe) {
		if (isSafe == undefined) { isSafe = true; }
		var i, d = dateText, f = format;
		var rv = new Date (
			(i = f.iof('yyyy')) != -1 ? (d.sub(i, i+4).toNum()) : 2000
		,	(i = f.iof('MM')) != -1 ? (d.sub(i, i+2).toNum() - 1) : 0
		,	(i = f.iof('dd')) != -1 ? (d.sub(i, i+2).toNum()) : 1
		,	(i = f.iof('HH')) != -1 ? (d.sub(i, i+2).toNum()) : 0
		,	(i = f.iof('mm')) != -1 ? (d.sub(i, i+2).toNum()) : 0
		,	(i = f.iof('ss')) != -1 ? (d.sub(i, i+2).toNum()) : 0
		,	(i = f.iof('SSS')) != -1 ? (d.sub(i, i+3).toNum()) : 0
		);
		
		if (isSafe && rv.toStr(format) != dateText) { return null; }
		
		return rv;
	},
});

var utilv = {
	KO_ATOM_S : [
		[ 'ㄱ' ], [ 'ㄱㄱ' ], [ 'ㄴ' ], [ 'ㄷ' ], [ 'ㄷㄷ' ], [ 'ㄹ' ], [ 'ㅁ' ],
		[ 'ㅂ' ], [ 'ㅂㅂ' ], [ 'ㅅ' ], [ 'ㅅㅅ' ], [ 'ㅇ' ], [ 'ㅈ' ], [ 'ㅈㅈ' ], [ 'ㅊ' ], [ 'ㅋ' ], [ 'ㅌ' ],
		[ 'ㅍ' ], [ 'ㅎ' ]
	],
	KO_ATOM_M : [
		[ 'ㅏ' ], [ 'ㅐ' ], [ 'ㅑ' ], [ 'ㅒ' ], [ 'ㅓ' ], [ 'ㅔ' ], [ 'ㅕ' ], [ 'ㅖ' ],
		[ 'ㅗ' ], [ 'ㅗㅏ' ], [ 'ㅗㅐ' ], [ 'ㅗㅣ' ], [ 'ㅛ' ], [ 'ㅜ' ], [ 'ㅜㅓ' ], [ 'ㅜㅔ' ],
		[ 'ㅜㅣ' ], [ 'ㅠ' ], [ 'ㅡ' ], [ 'ㅡㅣ' ], [ 'ㅣ' ] 
	],
	KO_ATOM_E : [
		[], [ 'ㄱ' ], [ 'ㄱㄱ' ], [ 'ㄱㅅ' ], [ 'ㄴ' ], [ 'ㄴㅈ' ],
		[ 'ㄴㅎ' ], [ 'ㄷ' ], [ 'ㄹ' ], [ 'ㄹㄱ' ], [ 'ㄹㅁ' ], [ 'ㄹㅂ' ], [ 'ㄹㅅ' ], [ 'ㄹㅌ' ],
		[ 'ㄹㅍ' ], [ 'ㄹㅎ' ], [ 'ㅁ' ], [ 'ㅂ' ], [ 'ㅂㅅ' ], [ 'ㅅ' ], [ 'ㅅㅅ' ], [ 'ㅇ' ], [ 'ㅈ' ],
		[ 'ㅊ' ], [ 'ㅋ' ], [ 'ㅌ' ], [ 'ㅍ' ], [ 'ㅎ' ] 
	],
	KO_ATOM_P : [
		[ 'ㄱ' ], [ 'ㄱㄱ' ], [ 'ㄱㅅ' ], [ 'ㄴ' ], [ 'ㄴㅈ' ],
		[ 'ㄴㅎ' ], [ 'ㄷ' ], [ 'ㄸ' ], [ 'ㄹ' ], [ 'ㄹㄱ' ], [ 'ㄹㅁ' ], [ 'ㄹㅂ' ], [ 'ㄹㅅ' ],
		[ 'ㄹㄷ' ], [ 'ㄹㅍ' ], [ 'ㄹㅎ' ], [ 'ㅁ' ], [ 'ㅂ' ], [ 'ㅂㅂ' ], [ 'ㅂㅅ' ], [ 'ㅅ' ],
		[ 'ㅅㅅ' ], [ 'ㅇ' ], [ 'ㅈ' ], [ 'ㅈㅈ' ], [ 'ㅊ' ], [ 'ㅋ' ], [ 'ㅌ' ], [ 'ㅍ' ], [ 'ㅎ' ], [ 'ㅏ' ], [ 'ㅐ' ],
		[ 'ㅑ' ], [ 'ㅒ' ], [ 'ㅓ' ], [ 'ㅔ' ], [ 'ㅕ' ], [ 'ㅖ' ], [ 'ㅗ' ], [ 'ㅗㅏ' ], [ 'ㅗㅐ' ], [ 'ㅗㅣ' ],
		[ 'ㅛ' ], [ 'ㅜ' ], [ 'ㅜㅓ' ], [ 'ㅜㅔ' ], [ 'ㅜㅣ' ], [ 'ㅠ' ], [ 'ㅡ' ], [ 'ㅡㅣ' ], [ 'ㅣ' ]
	]
};
/*
// 제이쿼리 달력 한글 설정
$.datepicker.setDefaults({
	dateFormat : 'yy-mm-dd',
	changeMonth: true,
	changeYear: true,
	showButtonPanel: true,
	prevText : '이전 달',
	nextText : '다음 달',
	monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
	monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
	dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
	dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
	dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
	showMonthAfterYear : true,
	yearSuffix : '년'
});
*/
// 브라우저 호환성 영역
if (window.ActiveXObject) {
	if (!document.addEventListener) {
		Function.prototype.bind = function(obj) {
			var func = this;
			var rv = function() {
				return func.apply(obj, arguments);
			};
			return rv;
		};
	}
	if (typeof String.prototype.trim !== 'function') {
		String.prototype.trim = function() {
			return this.replace(/^\s+|\s+$/g, '');
		};
	}
}
if (!String.prototype.startsWith) {
	String.prototype.startsWith = function(s) {
		return this.slice(0, s.length) == s;
	};
	String.prototype.endsWith = function(s) {
		return this.slice(-(s.length)) == s;
	};
}
