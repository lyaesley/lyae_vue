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
<title></title>
    <script></script>
<script>
<!--
    if(document.domain == "localhost") {
        document.domain == "localhost"
    }else if("${domain}" == null || "${domain}" == ""){
        document.domain = "benepia.co.kr"
    }else{
        document.domain = "${domain}";
    }

    var kcb = {

        //************************************************************************
        // 하단 자바스크립트는 팝업창을 띄우는 자바스크립트이다.
        // height (팝업창세로크기) 의 경우, 712 가 컨텐츠가 한번에 모두 노출되는 값이며,
        // PC 화면 세로 해상도가 낮은 고객의 경우, 팝업창 하단이 잘려서 보이는 경우가 있으므로
        // 회원사별로 테스트 및 판단 하에 712 보다 낮은 수치로 조절 요망.
        //************************************************************************
        goCard : function(){
            var form1 = document.form1;

            if(kcb.isTest) {
                window.open("", "kcb_auth_popup", "width=430,height=712,scrollbar=yes");
                form1.target = "kcb_auth_popup";
            }

            form1.action = "/frnt/callKcbModuleStart.do";
            form1.kcbAuthType.value = "CARD";
            form1.submit();
        },

        goPhone : function(){
            var form1 = document.form1;

            if(kcb.isTest) {
                window.open("", "kcb_auth_popup", "width=430,height=712,scrollbar=yes");
                form1.target = "kcb_auth_popup";
            }

            form1.action = "/frnt/callKcbModuleStart.do";
            form1.kcbAuthType.value = "PHONE";
            form1.submit();
        },

        goIpin : function() {
            var form1 = document.form1;

            if(kcb.isTest) {
                window.open("", "kcb_auth_popup", "width=430,height=712,scrollbar=yes");
                form1.target = "kcb_auth_popup";
            }

            form1.action = "/frnt/callKcbModuleStart.do";
            form1.kcbAuthType.value = "IPIN";
            form1.submit();
        },

        isEmpty : function(value){
            if(typeof value == "undefined" || value == null || value == "") { return false; }
            else { return true; }
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
            if(kcb.isTest) {
                document.body.style.display="block";
                document.form1.isTest.value = "true";
                document.form1.custCoCd.value = "test";
                document.form1.mbrId.value = "test";
                document.form1.reqUrl.value = kcb.resParam.reqUrl;
                document.form1.mbrNm.value = "test";
                document.form1.birthDy.value = "test";
                document.form1.empNo.value = "test";
                document.form1.ci.value = kcb.resParam.ci;
                return;
            } else {
                document.form1.custCoCd.value = kcb.resParam.custCoCd;
                document.form1.mbrId.value = kcb.resParam.mbrId;
                document.form1.reqUrl.value = kcb.resParam.reqUrl;
                document.form1.mbrNm.value = kcb.resParam.mbrNm;
                document.form1.birthDy.value = kcb.resParam.birthDy;
                document.form1.empNo.value = kcb.resParam.empNo;
                document.form1.ci.value = kcb.resParam.ci;

                if(kcb.resParam.authType == 'PHONE'){
                    document.form1.phoneName.value = (typeof kcb.resParam.phoneName == "undefined") ? "" : kcb.resParam.phoneName;
                    document.form1.phoneNumber.value = (typeof kcb.resParam.phoneNumber == "undefined") ? "" : kcb.resParam.phoneNumber;
                    document.form1.phoneBirthdy.value = (typeof kcb.resParam.phoneBirthdy == "undefined") ? "" : kcb.resParam.phoneBirthdy;
                    document.form1.phoneSex.value = (typeof kcb.resParam.phoneSex == "undefined") ? "" : kcb.resParam.phoneSex;
                    document.form1.phoneKoreanflag.value = (typeof kcb.resParam.phoneKoreanflag == "undefined") ? "" : kcb.resParam.phoneKoreanflag;
                }

            }

            kcb.checkAuthType();

        }
    };
    kcb.isTest = kcb.isEmpty(${isTest});
    kcb.resParam = ${resParamJson};
//-->
</script>
</head>
<body onload="kcb.init();" style="display: none;">
<H1>Encoding Information</H1>
<TABLE border="0" cellpadding="3" width="100%">
    <TR><TD class="e" width="30%">Locale Default Encoding</TD><TD class="v"><%= java.util.Locale.getDefault() %></TD></TR>
    <TR><TD class="e" width="30%">File Encoding</TD><TD class="v"><%= System.getProperty("file.encoding") %></TD></TR>
    <TR><TD class="e" width="30%">OutputStreamWriter Encoding</TD><TD class="v"><%= new java.io.OutputStreamWriter(System.out).getEncoding() %></TD></TR>
</TABLE>
    <div>
    <form name="form1" method="post">

        <input type="text" name="isTest" id="isTest" value="fasle"/>
        <input type="text" name="kcbAuthType" id="kcbAuthType" value=""/>
        <input type="text" name="custCoCd" id="custCoCd" value=""/>
        <input type="text" name="mbrId" id="mbrId" value=""/>
        <input type="text" name="reqUrl" id="reqUrl" value=""/>
        <input type="text" name="mbrNm" id="mbrNm" value=""/>
        <input type="text" name="birthDy" id="birthDy" value=""/>
        <input type="text" name="empNo" id="empNo" value=""/>
        <input type="text" name="ci" id="ci" value=""/>

        <%--휴대폰 인증 사전 정보 입력--%>
        <input type="hidden" name="phoneName" id="phoneName" value=""/>
        <input type="hidden" name="phoneNumber" id="phoneNumber" value=""/>
        <input type="hidden" name="phoneBirthdy" id="phoneBirthdy" value=""/>
        <input type="hidden" name="phoneSex" id="phoneSex" value=""/>
        <input type="hidden" name="phoneKoreanflag" id="phoneKoreanflag" value=""/>
        <%--휴대폰 인증 사전 정보 입력--%>

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
					<input type="text" name="REQ_SITE_NM" maxlength="20" size="24" value="베네피아">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" value="카드인증" onClick="kcb.goCard();"></td>
			</tr>
            <tr>
                <td colspan="2" align="center"><input type="button" value="휴대폰 본인확인" onClick="kcb.goPhone();"></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="button" value="아이핀 본인확인" onClick="kcb.goIpin();"></td>
            </tr>
		</table>
	</form>
	</div>

<!-- 인증팝업 처리결과 정보 = popup3 에서 값 입력 -->
<form name="kcbResultForm" method="post" >
        <%--공통--%>
        <input type="hidden" name="CP_CD"       value="" />
        <input type="hidden" name="TX_SEQ_NO"   value="" />
        <input type="hidden" name="RSLT_CD"     value="" />
        <input type="hidden" name="RSLT_MSG"    value="" />

        <input type="hidden" name="RSLT_NAME"   value="" />
        <input type="hidden" name="RSLT_BIRTHDAY"	value="" />
        <input type="hidden" name="RSLT_SEX_CD" 	value="" />
        <input type="hidden" name="RSLT_NTV_FRNR_CD"	value="" />
		
        <input type="hidden" name="DI"			value="" />
        <input type="hidden" name="CI"    		value="" />
		<input type="hidden" name="CI_UPDATE"   value="" />
		
		<input type="hidden" name="RETURN_MSG"   value="" />
        <%--공통--%>

        <%--CARD 전용--%>
        <input type="hidden" name="CRD_CD"   	value="" />
        <input type="hidden" name="CI_RQST_DT_TM"   value="" />
        <%--CARD 전용--%>

        <%--IPIN 전용--%>
        <input type="hidden" name="CI2"   	value="" />
        <input type="hidden" name="VSSN"   value="" />
        <%--IPIN 전용--%>

        <%-- 휴대폰 전용 --%>
        <input type="hidden" name="TEL_COM_CD" />
        <input type="hidden" name="TEL_NO" />
        <%-- 휴대폰 전용 --%>
</form>
</body>
</html>
