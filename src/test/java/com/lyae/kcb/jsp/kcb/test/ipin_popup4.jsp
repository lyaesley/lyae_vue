<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<%
    //**************************************************************************
	// ���ϸ� : ipin_popup4.jsp
	// - �ٴ�������
	// ������ ���� ��� �Ϸ� ȭ��
	//**************************************************************************

	//request.setCharacterEncoding("UTF-8"); // UTF-8 ȯ���� ��� �ּ� ����
	
	/* ���� ���� �׸� */
	String CP_CD		= request.getParameter("CP_CD");					// �����ڵ�
	String TX_SEQ_NO	= request.getParameter("TX_SEQ_NO");				// �ŷ���ȣ
	String RSLT_CD		= request.getParameter("RSLT_CD");					// ����ڵ�
	//String RSLT_MSG	= request.getParameter("RSLT_MSG");					// ����޼���(UTF-8)
	String RSLT_MSG	= new String(request.getParameter("RSLT_MSG").getBytes("8859_1"));	// ����޼���(EUC-KR)
	
	//String RSLT_NAME		= request.getParameter("RSLT_NAME");			// ����(UTF-8)
	String RSLT_NAME		= new String(request.getParameter("RSLT_NAME").getBytes("8859_1"));	// ����(EUC-KR)
	String RSLT_BIRTHDAY	= request.getParameter("RSLT_BIRTHDAY");		// �������
	String RSLT_SEX_CD 		= request.getParameter("RSLT_SEX_CD");			// �����ڵ�
	String RSLT_NTV_FRNR_CD = request.getParameter("RSLT_NTV_FRNR_CD");		// ���ܱ��� ����
	
	String DI = request.getParameter("DI");							// DI ��
	String CI = request.getParameter("CI");							// CI �� (CI_UPDATE�� Ȧ���� ��� ���)
	String CI2	= request.getParameter("CI2");						// CI �� (CI_UPDATE�� ¦���� ��� ���)
	String CI_UPDATE	= request.getParameter("CI_UPDATE");		// CI ������Ʈ Ƚ�� (���� ��1�� �� ������)
	String VSSN = request.getParameter("VSSN");						// �����ֹι�ȣ
	
	String RETURN_MSG = request.getParameter("RETURN_MSG");			// ���ϸ޽���
%>
<title>KCB ������ ���� ���� 4</title>
</head>
<body>
<h3>�������</h3>
<ul>
  <li>�����ڵ�		: <%=CP_CD%> </li>
  <li>�ŷ���ȣ			: <%=TX_SEQ_NO%> </li>
  <li>����ڵ�			: <%=RSLT_CD%></li>
  <li>����޼���		: <%=RSLT_MSG%></li>

  <li>����				: <%=RSLT_NAME%></li>
  <li>�������			: <%=RSLT_BIRTHDAY%></li>
  <li>����				: <%=RSLT_SEX_CD%></li>
  <li>���ܱ��� ����		: <%=RSLT_NTV_FRNR_CD%></li>
  
  <li>DI				: <%=DI%></li>
  <li>CI				: <%=CI%></li>
  <li>CI2				: <%=CI2%></li>
  <li>CI������Ʈ Ƚ��	: <%=CI_UPDATE%></li>
  <li>�����ֹι�ȣ		: <%=VSSN%></li>
  
  <li>���ϸ޽���		: <%=RETURN_MSG%></li>
</ul>
</body>
</html>
