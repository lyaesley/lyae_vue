<%@ page import="kcb.org.json.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    //**************************************************************************
	// 파일명 : card_popup3.jsp
	// - 팝업페이지
	// 카드 본인확인 서비스 결과 화면(return url)
	//**************************************************************************
	
	//' 처리결과 모듈 토큰 정보
    String MDL_TKN = request.getParameter("MDL_TKN");

	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	//' KCB로부터 부여받은 회원사코드(아이디) 설정 (12자리)
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
//	String CP_CD = "T00000000001";							//회원사코드(아이디)
	String CP_CD = (String)session.getAttribute("CARD_CP_CD");
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' 타겟 : 운영/테스트 전환시 변경 필요
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String target = "PROD"; // 테스트="TEST", 운영="PROD"
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' 라이센스 파일
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	//String license = "C:\\okcert3_license\\" + CP_CD + "_CID_01_" + target + "_AES_license.dat";
	String license = application.getRealPath("/kcb/" + CP_CD + "_CID_01_" + target +"_AES_license.dat");
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' 서비스명 (고정값)
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String svcName = "CID_CARD_POPUP_RESULT";
	
	JSONObject reqJson = new JSONObject();
	reqJson.put("MDL_TKN", MDL_TKN);
	
	String reqStr = reqJson.toString();
	
	/**************************************************************************
	okcert3 실행
	**************************************************************************/
	kcb.module.v3.OkCert okcert = new kcb.module.v3.OkCert();
	
	// '************ IBM JDK 사용 시, 주석 해제하여 호출 ************
	// okcert.setProtocol2type("22");
	// '객체 내 license를 리로드해야 될 경우에만 주석 해제하여 호출. (v1.1.7 이후 라이센스는 파일위치를 key로 하여 static HashMap으로 사용됨)
	// okcert.delLicense(license);
	
	//' callOkCert 메소드호출 : String license 파일 path로 라이센스 로드
	String resultStr = okcert.callOkCert(target, CP_CD, svcName, license,  reqStr);
	/*
	// 'OkCert3 내부에서 String license 파일 path로 라이센스를 못 읽어올 경우(Executable Jar 환경 등에서 발생),
	// '메소드 마지막 파라미터에 InputStream를 사용하여 라이센스 로드
	String resultStr = null;
	if ( ! okcert.containsLicense(license) ) {			// 로드된 라이센스 정보가 HashMap에 없는 경우
		java.io.InputStream is = new java.io.FileInputStream(license);	// 환경에 맞게 InputStream 로드
		resultStr = okcert.callOkCert(target, CP_CD, svcName, license,  reqStr, is);
	} else {											// 로드된 라이센스 정보가 HashMap에 있는 경우
		resultStr = okcert.callOkCert(target, CP_CD, svcName, license,  reqStr);
	}
	*/

	JSONObject resJson = new JSONObject(resultStr);
	
	String TX_SEQ_NO = resJson.getString("TX_SEQ_NO");
    String RSLT_CD =  resJson.getString("RSLT_CD");
    String RSLT_MSG =  resJson.getString("RSLT_MSG");
	
	String CRD_CD = "";
	String CI_RQST_DT_TM = "";
    String RSLT_NAME = "";
    String RSLT_BIRTHDAY = "";
    String RSLT_SEX_CD = "";
    String RSLT_NTV_FRNR_CD = "";
    String DI = "";
	String CI = "";
	String CI_UPDATE = "";
	
	String RETURN_MSG = "";
	
	if ( resJson.has("CRD_CD") && !resJson.isNull("CRD_CD"))	CRD_CD = resJson.getString("CRD_CD");
	if ( resJson.has("RETURN_MSG") && !resJson.isNull("RETURN_MSG"))	RETURN_MSG = resJson.getString("RETURN_MSG");
	//***************************************************************************************
	// RSLT_CD (결과코드)가 T000 인 경우, 인증에 성공한 것이므로 그에 맞는 비즈니스 처리 요망. 그외 결과코드는 설명서 참고.
	//***************************************************************************************
if ("T000".equals(RSLT_CD)) {
	CI_RQST_DT_TM = resJson.getString("CI_RQST_DT_TM");
    RSLT_NAME =  resJson.getString("RSLT_NAME");
    RSLT_BIRTHDAY =  resJson.getString("RSLT_BIRTHDAY");
    RSLT_SEX_CD =  resJson.getString("RSLT_SEX_CD");
    RSLT_NTV_FRNR_CD =  resJson.getString("RSLT_NTV_FRNR_CD");
    DI =  resJson.getString("DI");
	CI =  resJson.getString("CI");
	CI_UPDATE =  resJson.getString("CI_UPDATE");
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>KCB 카드 본인확인 서비스 샘플 3</title>
<script language="javascript" type="text/javascript" >
	//***************************************************************************************
	// 팝업을 호출한 card_popup1 의 kcbResultForm 에 값을 넣어주고, card_popup4를 호출하는 자바스크립트
	//***************************************************************************************
	function fncOpenerSubmit() {
		opener.document.kcbResultForm.CP_CD.value = "<%=CP_CD%>";
		opener.document.kcbResultForm.TX_SEQ_NO.value = "<%=TX_SEQ_NO%>";
		opener.document.kcbResultForm.RSLT_CD.value = "<%=RSLT_CD%>";
		opener.document.kcbResultForm.RSLT_MSG.value = "<%=RSLT_MSG%>";

		opener.document.kcbResultForm.CRD_CD.value = "<%=CRD_CD%>";
		opener.document.kcbResultForm.CI_RQST_DT_TM.value = "<%=CI_RQST_DT_TM%>";
		
		opener.document.kcbResultForm.RSLT_NAME.value = "<%=RSLT_NAME%>";
		opener.document.kcbResultForm.RSLT_BIRTHDAY.value = "<%=RSLT_BIRTHDAY%>";
		opener.document.kcbResultForm.RSLT_SEX_CD.value = "<%=RSLT_SEX_CD%>";
		opener.document.kcbResultForm.RSLT_NTV_FRNR_CD.value = "<%=RSLT_NTV_FRNR_CD%>";
		
		opener.document.kcbResultForm.DI.value = "<%=DI%>";
		opener.document.kcbResultForm.CI.value = "<%=CI%>";
		opener.document.kcbResultForm.CI_UPDATE.value = "<%=CI_UPDATE%>";
		
		opener.document.kcbResultForm.RETURN_MSG.value = "<%=RETURN_MSG%>";

		opener.document.kcbResultForm.action = "/test/kcb/popup4.do";
		opener.document.kcbResultForm.submit();
		
		self.close();
	}	
</script>
</head>
<body>
</body>
<%
	//인증결과 복호화 성공
	// 인증결과를 확인하여 페이지분기등의 처리를 수행해야한다.
	if ( "T000".equals(RSLT_CD) ) {
		out.println("<script>alert('본인인증성공'); fncOpenerSubmit();</script>");
	}
	else {
		out.println("<script>alert('본인인증실패 : "+RSLT_CD + " : " + RSLT_MSG + "'); fncOpenerSubmit();</script>");
	}
%>
</html>
