<%@ page import="kcb.org.json.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<title></title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">

	if(document.domain == "localhost") {
		document.domain == "localhost"
	}else if("${domain}" == null || "${domain}" == ""){
		document.domain = "benepia.co.kr"
	}else{
		document.domain = "${domain}";
	}

	var kcb = {

		goCard : function() {
			document.CARD.action = kcb.resParam.popupUrl;
			document.CARD.method = "post";

			document.CARD.cpCd.value	= kcb.resParam.CP_CD;
			document.CARD.mdlTkn.value	= kcb.resParam.MDL_TKN;

			document.CARD.submit();
		},

		goIpin : function() {
			document.IPIN.action = kcb.resParam.popupUrl;
			document.IPIN.method = "post";

			document.IPIN.cpCd.value	= kcb.resParam.CP_CD;
			document.IPIN.mdlTkn.value	= kcb.resParam.MDL_TKN;
			document.IPIN.tc.value		= kcb.resParam.tc;

			document.IPIN.submit();
		},

		goPhone : function() {
			document.PHONE.action = kcb.resParam.popupUrl;
			document.PHONE.method = "post";

			document.PHONE.cp_cd.value		= kcb.resParam.CP_CD;
			document.PHONE.mdl_tkn.value	= kcb.resParam.MDL_TKN;
			document.PHONE.tc.value			= kcb.resParam.tc;
			document.PHONE.target_id.value	= "";

			document.PHONE.submit();
		},

		checkAuthType : function() {
			if("CARD" == kcb.resParam.authType) {
				kcb.goCard();
			}else if("IPIN" == kcb.resParam.authType) {
				kcb.goIpin();
			}else if("PHONE" == kcb.resParam.authType) {
				kcb.goPhone();
			}
		},

		init : function() {

			if(kcb.resParam.succ) {
				//인증요청
				kcb.checkAuthType();
			} else {
				alert( kcb.resParam.RSLT_CD + " : " + kcb.resParam.RSLT_MSG); self.close();
			}

		}

	};

	kcb.resParam = ${resParamJson};

</script>
</head>

<body onload="kcb.init();">
	<form name="CARD">
	<!-- 인증 요청 정보 -->
	<!--// 필수 항목 -->
		<input type="hidden" name="cpCd" value="">	<!-- 회원사코드 -->
		<input type="hidden" name="mdlTkn" value="">	<!-- 토큰 -->
		<!-- 필수 항목 //-->
	</form>
	<form name="IPIN">
	<!-- 인증 요청 정보 -->
	<!--// 필수 항목 -->
		<input type="hidden" name="cpCd" value="">	<!-- 회원사코드 -->
		<input type="hidden" name="mdlTkn" value="">	<!-- 토큰 -->
		<input type="hidden" name="tc" value=""/>	<!-- 변경불가-->
		<!-- 필수 항목 //-->
	</form>
	<form name="PHONE">
	<!-- 인증 요청 정보 -->
	<!--// 필수 항목 -->
		<input type="hidden" name="cp_cd" value="">	<!-- 회원사코드 -->
		<input type="hidden" name="mdl_tkn" value="">	<!-- 토큰 -->
		<input type="hidden" name="tc" value=""/>	<!-- 변경불가-->
		<input type="hidden" name="target_id" value="">
		<!-- 필수 항목 //-->
	</form>

</body>
</html>


