<%@ page import="kcb.org.json.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script language="javascript" type="text/javascript" >

    if(document.domain == "localhost") {
        document.domain == "localhost"
    }else if("${domain}" == null || "${domain}" == ""){
        document.domain = "benepia.co.kr"
    }else{
        document.domain = "${domain}";
    }

	var kcb = {

		//***************************************************************************************
		// 팝업을 호출한 card_popup1 의 kcbResultForm 에 값을 넣어주고, card_popup4를 호출하는 자바스크립트
		//***************************************************************************************
		fncOpenerSubmit : function() {

		    //공통
			opener.document.kcbResultForm.CP_CD.value = "${resParam.CP_CD}";
			opener.document.kcbResultForm.TX_SEQ_NO.value = "${resParam.TX_SEQ_NO}";
			opener.document.kcbResultForm.RSLT_CD.value = "${resParam.RSLT_CD}";
			opener.document.kcbResultForm.RSLT_MSG.value = "${resParam.RSLT_MSG}";

			opener.document.kcbResultForm.RSLT_NAME.value = "${resParam.RSLT_NAME}";
            opener.document.kcbResultForm.RSLT_BIRTHDAY.value = "${resParam.RSLT_BIRTHDAY}";
            opener.document.kcbResultForm.RSLT_SEX_CD.value = "${resParam.RSLT_SEX_CD}";
            opener.document.kcbResultForm.RSLT_NTV_FRNR_CD.value = "${resParam.RSLT_NTV_FRNR_CD}";

			opener.document.kcbResultForm.DI.value = "${resParam.DI}";
            opener.document.kcbResultForm.CI.value = "${resParam.CI}";
            opener.document.kcbResultForm.CI_UPDATE.value = "${resParam.CI_UPDATE}";
            //공통

            //CARD 전용
            opener.document.kcbResultForm.CRD_CD.value = "${resParam.CRD_CD}";
            opener.document.kcbResultForm.CI_RQST_DT_TM.value = "${resParam.CI_RQST_DT_TM}";
            //CARD 전용

            //IPIN 전용
            opener.document.kcbResultForm.CI2.value = "${resParam.CI2}";
            opener.document.kcbResultForm.VSSN.value = "${resParam.VSSN}";
            //IPIN 전용

            //PHONE 전용
            opener.document.kcbResultForm.TEL_COM_CD.value = "${resParam.TEL_COM_CD}";
            opener.document.kcbResultForm.TEL_NO.value = "${resParam.TEL_NO}";
            //PHONE 전용


			opener.document.kcbResultForm.RETURN_MSG.value = JSON.stringify(${resParam.RETURN_MSG});

			opener.document.kcbResultForm.action = "/frnt/kcbStep4.do"; // 테스트용
            opener.document.kcbResultForm.submit();

			self.close();
		},

        checkAuthType : function() {

		    if("CARD" == kcb.resParam.authType) {
                if ( "T000" == "${resParam.RSLT_CD}") {return true;}
            }else if("IPIN" == kcb.resParam.authType) {
                if ( "T000" == "${resParam.RSLT_CD}") {return true;}
            }else if("PHONE" == kcb.resParam.authType) {
                if ( "B000" == "${resParam.RSLT_CD}") {return true;}
            }
		    return false;
        },

		init : function() {

			//인증결과 복호화 성공
			// 인증결과를 확인하여 페이지분기등의 처리를 수행해야한다.
			if (kcb.checkAuthType()) {
				alert('본인인증성공'); kcb.fncOpenerSubmit();
			}
			else {
				alert('본인인증실패 : [${resParam.RSLT_CD}]  ,  [${resParam.RSLT_MSG}] '); kcb.fncOpenerSubmit();
			}

		}

	};

	kcb.resParam = ${resParamJson};

</script>
</head>
<body onload="kcb.init();">
</body>
</html>
