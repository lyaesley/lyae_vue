<script type="text/javascript">

if(document.domain == "localhost") {
    document.domain == "localhost"
}else if("${domain}" == null || "${domain}" == ""){
    document.domain = "benepia.co.kr"
}else{
    document.domain = "${domain}";
}

 var kcb = {
  checkOpenUrl : function() {
   if(window.opener && opener.location) {
    var url = opener.location.pathname;
    //var url = "/login/joinStep02.do";

    if("${refund}" == "refund"){
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));
    }else if(url.indexOf("login/joinStep02.do") >= 0 || url.indexOf("login/joinCertify.do") >= 0 || url.indexOf("login/selfCertJoin.do") >= 0){
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));
    }else if(url.indexOf("frnt/mypage/refundAccChgCertPop.do") >= 0  || url.indexOf("frnt/mypage/refundAccChg.do")>=0 ){
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));
    }else if(url.indexOf("frnt/mypage/memberPwdUpdate.do") >= 0){ // 마이페이지 개인정보/패스워드 변경
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));
    }else if(url.indexOf("certify/checkCertifyForNewYear.do") >= 0){ //새해 로그인.
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));
    }else if(url.indexOf("auth/searchId.do") >= 0 || url.indexOf("auth/searchPwdNo.do") >= 0) { // 로그인전 아이디/패스워드 찾기
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));
    }else if(url.indexOf("frnt/firstlogin/firstLogin.do") >= 0){ //제도 최초 로그인 자동배정유/무
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));
    }else if(url.indexOf("login/popupFirstLoginSelfCert.do") >= 0){
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));  //마이페이지(아이디/패스워드 찾기) 최초 인증 팝업
    }else if(url.indexOf("frnt/mypage/searchId.do") >= 0){
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));  // 마이패이지 아이디 찾기
    }else if(url.indexOf("frnt/mypage/searchPwdNo.do") >= 0){  // 마이페이지 패스워드 찾기
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));
    }else if(url.indexOf("login/dormantRestoreAuth.do") >= 0){ // 휴면회원 로그인 시
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));
    }else if(url.indexOf("frnt/childCare/childCareApplyForm.do") >= 0){
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));
    }else if(url.indexOf("login/getPwCheckUrl.do") >= 0){
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));
    }else if(url.indexOf("frnt/authOcbIntegrate.do") >= 0){
     opener.kcb.successKcb(JSON.stringify(kcb.resParamJson));
    }
    self.close();
   }
  },

  init : function() {
   kcb.checkOpenUrl();
  }
 };

 kcb.resParamJson = ${resParamJson};

</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import = "java.util.*" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
</head>
<body onload="kcb.init();">
</body>
</html>