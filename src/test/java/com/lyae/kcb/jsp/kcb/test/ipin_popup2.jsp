<%@ page import="kcb.org.json.*" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%
    //**************************************************************************
	// ���ϸ� : ipin_popup2.jsp
	// - �˾�������
	// ������ ���� ���������� ȣ�� ȭ��
    //
    // ������
    // 	���� ��ÿ��� ȭ�鿡 �������� �����͸� �����Ͽ� �ֽñ� �ٶ��ϴ�.
    // 	�湮�ڿ��� ����Ʈ�����Ͱ� ����� �� �ֽ��ϴ�.
    //**************************************************************************
	
	//' UTF-8 ȯ���� ��� �ּ� ���� + ��ü ������ ��� charset, pageEncoding �� �������ڵ� ���� �ʿ�
	//request.setCharacterEncoding("UTF-8");
	
	/**************************************************************************
	 * OkCert3 ������ ���� �Ķ����
	 **************************************************************************/
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	//' ȸ���� ����Ʈ��, URL
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String SITE_NAME = "����Ʈ��"; 		// ��û����Ʈ�� 
	String SITE_URL = "www.test.co.kr";

	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' KCB�κ��� �ο����� ȸ�����ڵ�(���̵�) ���� (12�ڸ�) 
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String CP_CD = "V06880000000";	// ȸ�����ڵ�
	//session.setAttribute("IPIN_CP_CD", CP_CD);

	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' ���� URL ���� : �ִ� 1000 ����Ʈ.
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	//' opener(popup1)�� �����ΰ� ��ġ�ϵ��� �����ؾ� ��. 
	//' (http://www.test.co.kr�� http://test.co.kr�� �ٸ� ���������� �ν��ϸ�, http �� https�� ��ġ�ؾ� ��)
	// ���� �Ϸ� �� ���ϵ� URL (������ ���� full path)
	String RTN_URL = "http://"+request.getServerName()+":8080/ipin_popup/ipin_popup3.jsp";

	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' ������û�����ڵ�
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String RQST_CAUS_CD = "00";
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' ���ϸ޽��� (���鰡��. returnUrl���� ���� ���޹ް��� �ϴ� ���� �ִٸ� ����.)
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String RETURN_MSG = "";
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' Ÿ�� �� �˾�URL : �/�׽�Ʈ ��ȯ�� ���� �ʿ�
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String target = "PROD"; // �׽�Ʈ="TEST", �="PROD"
	//String popupUrl = "https://tmpin.ok-name.co.kr:5443/CommonSvl";// �׽�Ʈ URL
	String popupUrl = "https://ipin.ok-name.co.kr/CommonSvl";// � URL
	
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' ���̼��� ����
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String license = "C:\\okcert3_license\\" + CP_CD + "_TIS_01_" + target + "_AES_license.dat";
		
	//'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
    //' ���񽺸� (������)
    //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
	String svcName = "TIS_IPIN_POPUP_START";
	
	/**************************************************************************
	okcert3 ��û ����
	**************************************************************************/
	JSONObject reqJson = new JSONObject();
	reqJson.put("RTN_URL", RTN_URL);
	reqJson.put("SITE_NAME", SITE_NAME);
	reqJson.put("SITE_URL", SITE_URL);
	reqJson.put("RQST_CAUS_CD", RQST_CAUS_CD);
	
	reqJson.put("RETURN_MSG", RETURN_MSG);
	
	//' �ŷ��Ϸù�ȣ�� �⺻������ ��� ������ �ڵ� ä���ǰ� ä���� ���� ��������.
	//'	ȸ���簡 ���� ä���ϱ� ���ϴ� ��쿡�� �Ʒ� �ڵ带 �ּ� ���� �� ���.
	//' �� �ŷ����� �ߺ� ���� String �� �����Ͽ� �Է�. �ִ����:20
	//reqJson.put("TX_SEQ_NO", "123456789012345"); 
	
	String reqStr = reqJson.toString();
	
	/**************************************************************************
	okcert3 ����
	**************************************************************************/
	kcb.module.v3.OkCert okcert = new kcb.module.v3.OkCert();
	
	// '************ IBM JDK ��� ��, �ּ� �����Ͽ� ȣ�� ************
	// okcert.setProtocol2type("22");
	// '��ü �� license�� ���ε��ؾ� �� ��쿡�� �ּ� �����Ͽ� ȣ��. (v1.1.7 ���� ���̼����� ������ġ�� key�� �Ͽ� static HashMap���� ����)
	// okcert.delLicense(license);
	
	//' callOkCert �޼ҵ�ȣ�� : String license ���� path�� ���̼��� �ε�
	String resultStr = okcert.callOkCert(target, CP_CD, svcName, license,  reqStr);
	/*
	// 'OkCert3 ���ο��� String license ���� path�� ���̼����� �� �о�� ���(Executable Jar ȯ�� ��� �߻�),
	// '�޼ҵ� ������ �Ķ���Ϳ� InputStream�� ����Ͽ� ���̼��� �ε�
	String resultStr = null;
	if ( ! okcert.containsLicense(license) ) {			// �ε�� ���̼��� ������ HashMap�� ���� ���
		java.io.InputStream is = new java.io.FileInputStream(license);	// ȯ�濡 �°� InputStream �ε�
		resultStr = okcert.callOkCert(target, CP_CD, svcName, license,  reqStr, is);
	} else {											// �ε�� ���̼��� ������ HashMap�� �ִ� ���
		resultStr = okcert.callOkCert(target, CP_CD, svcName, license,  reqStr);
	}
	*/
	
	JSONObject resJson = new JSONObject(resultStr);

    String RSLT_CD =  resJson.getString("RSLT_CD");
    String RSLT_MSG = resJson.getString("RSLT_MSG");
    //if(resJson.has("TX_SEQ_NO")) String TX_SEQ_NO = resJson.getString("TX_SEQ_NO"); // �ʿ� �� �ŷ� �Ϸ� ��ȣ �� ���Ͽ� DB���� ���� ó��
    String MDL_TKN = "";
    
	boolean succ = false;
	
    if ("T300".equals(RSLT_CD) && resJson.has("MDL_TKN") ) {
            MDL_TKN = resJson.getString("MDL_TKN");
			succ = true;
    }
	
%>

<html>
<title>KCB ������ ���� ���� 2</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script type="text/javascript">
	function request(){
		document.form1.action = "<%=popupUrl%>";
		document.form1.method = "post";
	
		document.form1.submit();
	}
</script>
</head>

<body>
	<form name="form1">
	<!-- ���� ��û ���� -->
	<!--// �ʼ� �׸� -->
	<input type="hidden" name="tc" value="kcb.tis.ti.cmd.LoginRPCert3Cmd"/>	<!-- ����Ұ�-->
	<input type="hidden" name="cpCd" value="<%=CP_CD%>">	<!-- ȸ�����ڵ� -->
	<input type="hidden" name="mdlTkn" value="<%=MDL_TKN%>">	<!-- ��ū --> 
	<!-- �ʼ� �׸� //-->	
	</form>
</body>
<%
	if (succ) {
		//������û
		out.println("<script>request();</script>");
	} else {
		//��û ���� �������� ����
		out.println("<script>alert('" + RSLT_CD + " : " + RSLT_MSG + "'); self.close(); </script>");
	}
%>
</html>


