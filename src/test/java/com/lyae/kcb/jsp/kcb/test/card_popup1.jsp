<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    //**************************************************************************
	// 파일명 : card_popup1.jsp
	// - 바닥페이지
	// 카드 본인확인 서비스 요청 정보 입력 화면
    //**************************************************************************
%>
<html>
<head>
<title>KCB 카드 본인확인 서비스 샘플 1</title>
<script>
<!--
	//************************************************************************
	// 하단 자바스크립트는 팝업창을 띄우는 자바스크립트이다.
	// height (팝업창세로크기) 의 경우, 712 가 컨텐츠가 한번에 모두 노출되는 값이며,
	// PC 화면 세로 해상도가 낮은 고객의 경우, 팝업창 하단이 잘려서 보이는 경우가 있으므로
	// 회원사별로 테스트 및 판단 하에 712 보다 낮은 수치로 조절 요망.
	//************************************************************************
	function jsSubmit(){
			window.open("", "auth_popup", "width=430,height=712,scrollbar=yes");
			var form1 = document.form1;
			form1.target = "auth_popup";
			form1.action = "/test/kcb/popup2.do";
			form1.submit();
	}

	function jsSubmitPhone(){
			window.open("", "auth_popup", "width=430,height=712,scrollbar=yes");
			var form1 = document.form1;
        form1.action = "/test/kcb/phone/popup2.do";
			form1.target = "auth_popup";
			form1.submit();
	}
//-->
</script>
</head>
<body>
<H1>Encoding Information</H1>
<TABLE border="0" cellpadding="3" width="100%">
    <TR><TD class="e" width="30%">Locale Default Encoding</TD><TD class="v"><%= java.util.Locale.getDefault() %></TD></TR>
    <TR><TD class="e" width="30%">File Encoding</TD><TD class="v"><%= System.getProperty("file.encoding") %></TD></TR>
    <TR><TD class="e" width="30%">OutputStreamWriter Encoding</TD><TD class="v"><%= new java.io.OutputStreamWriter(System.out).getEncoding() %></TD></TR>
</TABLE>
    <div>
    <form name="form1" method="post">
        <table>
            <tr>
                <td colspan="2"><strong> - KCB 인증정보 입력용</strong></td>
            </tr>
            <tr>
                <td>회원사코드</td>
				<td>
					<input type="text" name="CP_CD" maxlength="12" size="14" value="V42670000000">
				</td>
			</tr>
			<tr>
				<td>요청사이트명</td>
				<td>
					<input type="text" name="REQ_SITE_NM" maxlength="20" size="24" value="test">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" value="카드인증" onClick="jsSubmit();"></td>
			</tr>
            <tr>
                <td colspan="2" align="center"><input type="button" value="휴대폰 본인확인" onClick="jsSubmitPhone();"></td>
            </tr>
		</table>
	</form>
	</div>

<!-- 인증팝업 처리결과 정보 = popup3 에서 값 입력 -->
<form name="kcbResultForm" method="post" >
        <input type="hidden" name="CP_CD"       value="" />
        <input type="hidden" name="TX_SEQ_NO"   value="" />
        <input type="hidden" name="RSLT_CD"     value="" />
        <input type="hidden" name="RSLT_MSG"    value="" />
		
		<input type="hidden" name="CRD_CD"   	value="" />
		<input type="hidden" name="CI_RQST_DT_TM"   value="" />
		
        <input type="hidden" name="RSLT_NAME"   value="" />
        <input type="hidden" name="RSLT_BIRTHDAY"	value="" />
        <input type="hidden" name="RSLT_SEX_CD" 	value="" />
        <input type="hidden" name="RSLT_NTV_FRNR_CD"	value="" />
		
        <input type="hidden" name="DI"			value="" />
        <input type="hidden" name="CI"    		value="" />
		<input type="hidden" name="CI_UPDATE"   value="" />
		
		<input type="hidden" name="RETURN_MSG"   value="" />

        <%-- 휴대폰 인증 필요 값 --%>
        <input type="hidden" name="TEL_COM_CD" />
        <input type="hidden" name="TEL_NO" />
</form>
</body>
</html>
