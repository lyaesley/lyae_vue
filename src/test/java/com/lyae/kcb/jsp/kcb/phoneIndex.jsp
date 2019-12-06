<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="xss" uri="/WEB-INF/tld/xssDecoderTag.tld" %>
<%@ page import ="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=960" />
<title>BENEPIA | 본인인증</title>
<link rel="stylesheet" type="text/css" href="../../com/css/import.css" />
<link rel="stylesheet" type="text/css" href="../../com/css/jquery.ui.datepicker.css" />
<script type="text/javascript" src="../../com/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../../com/js/design.js"></script>
<script type="text/javascript" src="../../com/js/select.js"></script>
<script type="text/javascript" src="../../com/js/slides.jquery.js"></script>
<script type="text/javascript" src="../../com/js/jquery.ui.core.js"></script>
<script type="text/javascript" src="../../com/js/jquery.ui.datepicker.js"></script>
<!--[if IE 6]>
<script type="text/javascript" src="../../com/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script type="text/javascript">DD_belatedPNG.fix('img, .png');</script>
<![endif]-->
<script type="text/javascript">

	if(document.domain == "localhost") {
		document.domain == "localhost"
	}else if("${domain}" == null || "${domain}" == ""){
		document.domain = "benepia.co.kr"
	}else{
		document.domain = "${domain}";
	}

	$(document).ready(function(){
		var birthDy = opener.document.getElementById("birthDy").value;
		var mbrNm = opener.document.getElementById("mbrNm").value;
		var mbrId = opener.document.getElementById("mbrId").value;
		var mode = opener.document.getElementById("mode").value;
		
		if(birthDy != null && mbrNm != null ){
			$("#birth").val(birthDy);
			$("#NAME").val(mbrNm);
			$("#CPDATA").val(mbrId);
		}
	});
 function chkReadOnly(){
	 var mode = opener.document.getElementById("mode").value;
	 if(mode == 'pwdUpdate'){
		//$(this).prop("readonly",true);
		alert("이름은 수정이 불가합니다.");
		document.getElementById("NAME").readOnly = true;
		document.getElementById("phoneNo1").focus();
	 } 
 }
 function chkBirthDyReadOnly(){
	 var mode = opener.document.getElementById("mode").value;
	 if (mode == 'searchIdPwd') {
		alert("생년월일은 수정이 불가합니다.");
		document.getElementById("birth").readOnly = true;
		document.getElementById("phoneNo1").focus();
	 }
 }
 function goNextPg(){

    if(document.getElementById("NAME").value == ""){
        alert("이름을 입력해 주세요");
        document.getElementById("NAME").focus();
        return;
    }
    /*if(document.getElementById("TELECOM").value == ""){
        alert("통신사를 선택해 주세요");
        document.getElementById("TELECOM").focus();
        return;
    }*/
    if(document.getElementById("phoneNo2").value == ""){
        alert("휴대폰 번호 중간자리를 입력해 주세요");
        document.getElementById("phoneNo2").focus();
        return;
    }
    if(document.getElementById("phoneNo3").value == ""){
        alert("휴대폰 번호 뒷자리를 입력해 주세요");
        document.getElementById("phoneNo3").focus();
        return;
    }
    if(document.getElementById("birth").value == ""){
        alert("생년월일을 입력해 주세요");
        return;
    }

	document.getElementById("PHONENO").value= document.getElementById("phoneNo1").value + document.getElementById("phoneNo2").value + document.getElementById("phoneNo3").value;
	var birth = document.getElementById("birth");
	document.getElementById("BIRTHDAY").value=birth.value.replace(/[\.]/g, "");

	//호출 페이지 크기로 인한 사이즈조절
	this.resizeTo(430, 712);

	document.frm.callUrl.value = opener.location.href;

	document.frm.method = "POST";
	document.frm.action = "/frnt/kcb/authStart.do";
	document.frm.submit();

}

</script>
</head>

<body>
<form name="frm" id="frm">
	<input type="hidden" id="PHONENO" 		name="PHONENO" 		value="" maxlength="11"/>
	<input type="hidden" id="BIRTHDAY" 		name="BIRTHDAY" 	value="" maxlength="8"/>
	<%--KCB 인증타입 --%>
	<input type="hidden" id="kcbAuthType" name="kcbAuthType" value="PHONE" />
	<input type="hidden" id="kcbResVoJson" name="kcbResVoJson" value="" />
	<input type="hidden" id="callUrl" name="callUrl" value="" />
	<%--KCB 인증타입 --%>

<!-- 팝업사이즈 450*300 -->
<!-- popup s -->
<div class="popup" style="width:460px">
	<!-- pop_tit_area s -->
	<div class="pop_tit_area">
		<h1><img src="../../img/tit/pop_tit_namecheck.gif" alt="본인인증" /></h1>
	</div>
	<!--// pop_tit_area e -->

	<div class="tbl_type02 marT01">
		<table>
			<caption>가족회원 정보입력</caption>
			<colgroup>
			<col style="width:25%" />
			<col style="width:75%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">이름</th>
					<td><input name="NAME" id="NAME" type="text" title="이름" style="width:96%;" onkeydown="javascript:chkReadOnly();" onclick="javascript:chkReadOnly();"/></td>
				</tr>
				<tr>
					<th scope="row">휴대폰번호</th>
					<td><%--<select name="TELECOM" id="TELECOM" title="통신사" class="not_apply_fakeselect">
							<option value="">선택</option>
							<option value="SKT">SKT</option>
							<option value="SKM">SK 알뜰폰</option>
							<option value="KTF">KT</option>
							<option value="KTM">KT 알뜰폰</option>
							<option value="KTM">CJ헬로모바일</option>
							<option value="LGT">LG U+</option>
							<option value="LGM">LG U+ 알뜰폰</option>
						</select>--%>

						<select name="phoneNo1" id="phoneNo1" title="휴대폰번호 앞자리" style="width:50px;" class="not_apply_fakeselect">
							<option value="010">010</option>
							<option value="011">011</option>
							<option value="016">016</option>
							<option value="017">017</option>
							<option value="018">018</option>
							<option value="019">019</option>
						</select>
						-
						<input name="phoneNo2" id="phoneNo2" type="text" style="width:40px;" title="휴대폰 번호 중간자리" maxlength="4" />
						-
						<input name="phoneNo3" id="phoneNo3" type="text" style="width:40px;" title="휴대폰 번호 뒷자리" maxlength="4" /></td>
				</tr>
				<tr>
					<th scope="row">생년월일</th>
					<td class="datepicker"><input name="birth" title="생년월일" id="birth" type="text" style="width:120px;" class="datepicker" /></td> <!-- 130425 수정 -->
				  </tr>
				<tr>
					<th scope="row">성별</th>
					<td>
						<input name="SEX" type="radio" value="M" id="SEX" class="check" checked="checked" />
					    <label for="man">남</label>
					    <input name="SEX" type="radio" value="F" id="SEX" class="check" />
					    <label for="woman">여</label>
					</td>
				</tr>
				<tr>
					<th scope="row">내외국인</th>
					<td><select title="내외국인 선택" name="KOREANFLAG" id="KOREANFLAG" class="not_apply_fakeselect">
							<option value="L">내국인</option>
							<option value="F">외국인</option>
					  </select></td>
				  </tr>
			</tbody>
		</table>

	<!-- button s -->
	<div class="btn_area">
	          <button type="button" onclick="goNextPg();" class="btn_m_set btn_check"><span class="hide">인증확인</span></button>
	          <button type="button" onclick="window.close();" class="btn_m_set btn_cancel02"><span class="hide">본인인증 취소</span></button>
	   </div>
	<!--// button e -->
</div>
</div>
</form>
<!--// popup e -->
</body>

</html>
