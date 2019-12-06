<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%
    //**************************************************************************
	// 파일명 : card_popup4.jsp
	// - 바닥페이지
	// 카드 본인확인 서비스 결과 완료 화면
	//**************************************************************************
	
	//request.setCharacterEncoding("UTF-8"); // UTF-8 환경의 경우 주석 제거
	
	/* 공통 리턴 항목 */
	String CP_CD		= request.getParameter("CP_CD");		// 고객사코드
	String TX_SEQ_NO	= request.getParameter("TX_SEQ_NO");	// 거래번호
	String RSLT_CD		= request.getParameter("RSLT_CD");		// 결과코드
	String RSLT_MSG	= request.getParameter("RSLT_MSG");		// 결과메세지(UTF-8)
//	String RSLT_MSG		= new String(request.getParameter("RSLT_MSG").getBytes("8859_1"));	// 결과메세지(EUC-KR)
	
	String CRD_CD		= request.getParameter("CRD_CD");		// 카드사
	String CI_RQST_DT_TM= request.getParameter("CI_RQST_DT_TM");// 요청 일시
	
	String RSLT_NAME	= request.getParameter("RSLT_NAME");	// 성명(UTF-8)
	//String RSLT_NAME	= new String(request.getParameter("RSLT_NAME").getBytes("8859_1"));	// 성명(EUC-KR)
	String RSLT_BIRTHDAY= request.getParameter("RSLT_BIRTHDAY");// 생년월일
	String RSLT_SEX_CD	= request.getParameter("RSLT_SEX_CD");	// 성별
	String RSLT_NTV_FRNR_CD	= request.getParameter("RSLT_NTV_FRNR_CD");		// 내외국인
	
	String DI			= request.getParameter("DI");			// DI
	String CI			= request.getParameter("CI");			// CI
	String CI_UPDATE	= request.getParameter("CI_UPDATE");	// CI_UPDATE
	
	String RETURN_MSG	= request.getParameter("RETURN_MSG");	// RETURN_MSG
%>
<title>KCB 카드 본인확인 서비스 샘플 4</title>
</head>
<body>
<h3>인증결과</h3>
<ul>
	<li>고객사코드	: <%=CP_CD%> </li>
	<li>거래번호	: <%=TX_SEQ_NO%> </li>
	<li>결과코드	: <%=RSLT_CD%></li>
	<li>결과메세지	: <%=RSLT_MSG%></li>
	
	<li>카드사		: <%=CRD_CD%></li>
	<li>요청일시	: <%=CI_RQST_DT_TM%></li>
	
	<li>성명		: <%=RSLT_NAME%></li>
	<li>생년월일	: <%=RSLT_BIRTHDAY%></li>
	<li>성별		: <%=RSLT_SEX_CD%></li>
	<li>내외국인	: <%=RSLT_NTV_FRNR_CD%></li>
	
	<li>DI			: <%=DI%></li>
	<li>CI			: <%=CI%></li>
	<li>CI_UPDATE	: <%=CI_UPDATE%></li>
	
	<li>RETURN_MSG	: <%=RETURN_MSG%></li>
</ul>
</body>
</html>
