package com.lyae.kcb.util;

import kcb.module.v3.OkCert;
import kcb.module.v3.exception.OkCertException;
import kcb.org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KcbUtil {

    public KcbReqVo getMapCallKcbModuleStart(KcbReqVo kcbReqVo) {

        String authType = kcbReqVo.getAuthType();

        if("CARD".equals(authType)) {
            kcbReqVo = this.getMapCallKcbModuleStartByCard(kcbReqVo);
        } else if("IPIN".equals(authType)) {
            kcbReqVo = this.getMapCallKcbModuleStartByIpin(kcbReqVo);
        } else if("PHONE".equals(authType)) {
            kcbReqVo = this.getMapCallKcbModuleStartByPhone(kcbReqVo);
        }

        return kcbReqVo;
    }

    public KcbResVo getMapCallKcbModuleResult(KcbReqVo kcbReqVo) {

        KcbResVo kcbResVo = new KcbResVo(); // return 변수
        String authType = kcbReqVo.getAuthType();

        if("CARD".equals(authType)) {
            kcbResVo = this.getMapCallKcbModuleResultByCard(kcbReqVo);
        } else if("IPIN".equals(authType)) {
            kcbResVo = this.getMapCallKcbModuleResultByIpin(kcbReqVo);
        } else if("PHONE".equals(authType)) {
            kcbResVo = this.getMapCallKcbModuleResultByPhone(kcbReqVo);
        }

        return kcbResVo;
    }

    private KcbReqVo getMapCallKcbModuleStartByCard(final KcbReqVo kcbReqVo) {

        //카드,아이핀,휴대폰 인증별 필요 파라미터 세팅
        String REQ_SITE_NM  = kcbReqVo.getREQ_SITE_NM();
        String RTN_URL      = kcbReqVo.getRTN_URL();

        JSONObject reqJson = new JSONObject();
        reqJson.put("RTN_URL", RTN_URL);
        reqJson.put("REQ_SITE_NM", REQ_SITE_NM);
        //카드,아이핀,휴대폰 인증별 필요 파라미터 세팅

        CheckStartCallback callback = new CheckStartCallback() {
            @Override
            public KcbReqVo checkStartCallback(String cd, JSONObject resJson) {
                if ("T300".equals(cd) && resJson.has("MDL_TKN") ) { // 정상적으로 모듈 호출 성공한 경우
                    kcbReqVo.setMDL_TKN(resJson.getString("MDL_TKN"));
                    kcbReqVo.setSucc(true);
                }
                return kcbReqVo;
            }
        };
        return this.callOkCertStartTemplate(kcbReqVo, reqJson, callback);
    }

    private KcbReqVo getMapCallKcbModuleStartByIpin(final KcbReqVo kcbReqVo) {

        //카드,아이핀,휴대폰 인증별 필요 파라미터 세팅
        JSONObject reqJson = new JSONObject();
        reqJson.put("RTN_URL", kcbReqVo.getRTN_URL());
        reqJson.put("SITE_NAME", kcbReqVo.getREQ_SITE_NM());
        reqJson.put("SITE_URL", kcbReqVo.getSITE_URL());
        reqJson.put("RQST_CAUS_CD", kcbReqVo.getRQST_CAUS_CD());
        //카드,아이핀,휴대폰 인증별 필요 파라미터 세팅

        // 선택가능
        //reqJson.put("RETURN_MSG", RETURN_MSG);

        CheckStartCallback callback = new CheckStartCallback() {
            @Override
            public KcbReqVo checkStartCallback(String cd, JSONObject resJson) {
                if ("T300".equals(cd) && resJson.has("MDL_TKN") ) { // 정상적으로 모듈 호출 성공한 경우
                    kcbReqVo.setMDL_TKN(resJson.getString("MDL_TKN"));
                    kcbReqVo.setSucc(true);
                    kcbReqVo.setTc("kcb.tis.ti.cmd.LoginRPCert3Cmd");
                }
                return kcbReqVo;
            }
        };
        return this.callOkCertStartTemplate(kcbReqVo, reqJson, callback);
    }

    private KcbReqVo getMapCallKcbModuleStartByPhone(final KcbReqVo kcbReqVo) {

        //카드,아이핀,휴대폰 인증별 필요 파라미터 세팅
        JSONObject reqJson = new JSONObject();
        reqJson.put("RETURN_URL", kcbReqVo.getRTN_URL());
        reqJson.put("SITE_NAME", kcbReqVo.getREQ_SITE_NM());
        reqJson.put("SITE_URL", kcbReqVo.getSITE_URL());
        reqJson.put("RQST_CAUS_CD", kcbReqVo.getRQST_CAUS_CD());
        //카드,아이핀,휴대폰 인증별 필요 파라미터 세팅

        //reqJson.put("CHNL_CD", CHNL_CD);
        //reqJson.put("RETURN_MSG", RETURN_MSG);

        // 사전에 입력받은 정보로 팝업창 개인정보를 고정할 경우 사용 (가이드 참고)
        //reqJson.put("IN_TP_BIT", "15");
        //reqJson.put("NAME", "홍길동");
        //reqJson.put("BIRTHDAY", "19870725");
        //reqJson.put("TEL_NO", "01012345678");
        //reqJson.put("NTV_FRNR_CD", "F");	// 내국인 L 외국인 F
        //reqJson.put("SEX_CD", "F");		// 남성 M 여성 F

        if(!"".equals(kcbReqVo.getPhoneName()) && !"".equals(kcbReqVo.getPhoneBirthdy())) {
            reqJson.put("IN_TP_BIT", "3"); // 고정값 성명, 생년월일
            /* 2019.12.05 휴대폰 번호는 고정값에서 제외함. 휴대폰 번호 고정할경우 아래 코드 사용
            if(!"".equals(kcbReqVo.getPhoneNumber())){
                reqJson.put("IN_TP_BIT", "11"); // 고정값 성명, 생년월일, 휴대폰번호
                reqJson.put("TEL_NO", kcbReqVo.getPhoneNumber());
            }
            */
            reqJson.put("NAME", kcbReqVo.getPhoneName());
            reqJson.put("BIRTHDAY", kcbReqVo.getPhoneBirthdy());
//            reqJson.put("NTV_FRNR_CD", kcbReqVo.getPhoneKoreanflag());    // 내국인 L 외국인 F
//            reqJson.put("SEX_CD", kcbReqVo.getPhoneSex());        // 남성 M 여성 F
        }
        CheckStartCallback callback = new CheckStartCallback() {
            @Override
            public KcbReqVo checkStartCallback(String cd, JSONObject resJson) {
                if ("B000".equals(cd) && resJson.has("MDL_TKN") ) { // 정상적으로 모듈 호출 성공한 경우
                    kcbReqVo.setMDL_TKN(resJson.getString("MDL_TKN"));
                    kcbReqVo.setSucc(true);
                    kcbReqVo.setTc("kcb.oknm.online.safehscert.popup.cmd.P931_CertChoiceCmd");
                }
                return kcbReqVo;
            }
        };
        return this.callOkCertStartTemplate(kcbReqVo, reqJson, callback);
    }

    private KcbResVo getMapCallKcbModuleResultByCard(KcbReqVo kcbReqVo) {

        CheckResultCallback callback = new CheckResultCallback() {
            @Override
            public KcbResVo checkResultCallback(String RSLT_CD, JSONObject resJson) {
                KcbResVo kcbResVo = new KcbResVo();

                kcbResVo.setCRD_CD(resJson.optString("CRD_CD",""));

                if ("T000".equals(RSLT_CD)) {
                    kcbResVo.setCI_RQST_DT_TM(resJson.getString("CI_RQST_DT_TM"));
                    kcbResVo.setSucc(true);
                }
                return kcbResVo;
            }
        };

        return this.callOkCertResultTemplate(kcbReqVo, callback);
    }

    private KcbResVo getMapCallKcbModuleResultByIpin(KcbReqVo kcbReqVo) {

        CheckResultCallback callback = new CheckResultCallback() {
            @Override
            public KcbResVo checkResultCallback(String RSLT_CD, JSONObject resJson) {
                KcbResVo kcbResVo = new KcbResVo();
                if ("T000".equals(RSLT_CD)) {
                    kcbResVo.setCI2(resJson.getString("CI2"));
                    kcbResVo.setVSSN(resJson.getString("VSSN"));
                    kcbResVo.setSucc(true);
                }
                return kcbResVo;
            }
        };

        return this.callOkCertResultTemplate(kcbReqVo, callback);
    }

    private KcbResVo getMapCallKcbModuleResultByPhone(KcbReqVo kcbReqVo) {

        CheckResultCallback callback = new CheckResultCallback() {
            @Override
            public KcbResVo checkResultCallback(String RSLT_CD, JSONObject resJson) {
                KcbResVo kcbResVo = new KcbResVo();
                if ("B000".equals(RSLT_CD)) {
                    kcbResVo.setTEL_COM_CD(resJson.getString("TEL_COM_CD"));
                    kcbResVo.setTEL_NO(resJson.getString("TEL_NO"));
                    kcbResVo.setSucc(true);
                }
                return kcbResVo;
            }
        };
        return this.callOkCertResultTemplate(kcbReqVo, callback);
    }


    private KcbReqVo callOkCertStartTemplate(KcbReqVo kcbReqVo, JSONObject reqJson, CheckStartCallback callback) {
        //공통
        String target       = kcbReqVo.getTarget();
        String CP_CD        = kcbReqVo.getCP_CD();
        String svcName      = kcbReqVo.getSvcName();
        String license      = kcbReqVo.getLicense();
//        String authType     = kcbReqVo.getAuthType();

        //리턴메시지 설정
        JSONObject returnMsgJson = new JSONObject();
        returnMsgJson.put("reqUrl", kcbReqVo.getReqUrl());
        returnMsgJson.put("logSeqGrp", kcbReqVo.getLogSeqGrp());
        returnMsgJson.put("custCoCd", kcbReqVo.getCustCoCd());
        returnMsgJson.put("mbrId", kcbReqVo.getMbrId());
        returnMsgJson.put("empNo", kcbReqVo.getEmpNo());

        //' 리턴메시지 (공백가능. returnUrl에서 같이 전달받고자 하는 값이 있다면 설정.)
        reqJson.put("RETURN_MSG", returnMsgJson.toString());

        //' 거래일련번호는 기본적으로 모듈 내에서 자동 채번되고 채번된 값을 리턴해줌.
        //'	회원사가 직접 채번하길 원하는 경우에만 아래 코드를 주석 해제 후 사용.
        //' 각 거래마다 중복 없는 String 을 생성하여 입력. 최대길이:20

        // DEV, STG, REAL DB 별로 중복키가 발생해서 TX_SEQ_NO 를 보내지 않고, RETURN_MSG 에 logSeq 를 통해 사용도록 변경경
       //reqJson.put("TX_SEQ_NO", kcbReqVo.getTX_SEQ_NO());

        String reqStr = reqJson.toString();

        OkCert okcert = new OkCert();

//        return okcert.callOkCert(target, CP_CD, svcName, license,  reqStr);

        Map<String, Object> resultMap = new HashMap<String, Object>(); // return 변수

        String resultStr;

        String RSLT_CD = "";    //모듈 통신 결과코드
        String RSLT_MSG = "";   //모듈 통신 결과메시지
        try {
            resultStr = okcert.callOkCert(target, CP_CD, svcName, license,  reqStr);

            JSONObject resJson = new JSONObject(resultStr);

            String TX_SEQ_NO =  resJson.getString("TX_SEQ_NO");
            RSLT_CD =  resJson.getString("RSLT_CD");
            RSLT_MSG = resJson.getString("RSLT_MSG");


            String MDL_TKN = "";
            boolean succ = false;

            kcbReqVo.setRSLT_CD(RSLT_CD);
            kcbReqVo.setRSLT_MSG(RSLT_MSG);
            kcbReqVo.setTX_SEQ_NO(TX_SEQ_NO);

            kcbReqVo.setMDL_TKN(MDL_TKN);
            kcbReqVo.setSucc(succ);

            //콜백 함수. 이 함수를 호출하는 곳에서 익명내부클래스로 작성한다.
            //카드,아이핀,휴대폰 인증시 공통이외 개별 파라미터 세팅시 처리
            callback.checkStartCallback(RSLT_CD, resJson);

            /**
             *  아래 항목은 파라미터로 받은 kcbReqVo에 담겨있다.
             *  CP_CD
             *  popupUrl
             *  authType
             */


        } catch (OkCertException e) {
            kcbReqVo.setRSLT_CD(e.getCode());
            kcbReqVo.setRSLT_MSG(RSLT_MSG);
        } catch (Exception e) {
            kcbReqVo.setRSLT_CD(RSLT_CD);
            kcbReqVo.setRSLT_MSG(RSLT_MSG);
        }

        return kcbReqVo;
    }

    private KcbResVo callOkCertResultTemplate(KcbReqVo kcbReqVo, CheckResultCallback callback) {
        //공통
        String target = kcbReqVo.getTarget();
        String CP_CD = kcbReqVo.getCP_CD();
        String svcName = kcbReqVo.getSvcName();
        String license = kcbReqVo.getLicense();

        //선택
        String MDL_TKN = kcbReqVo.getMDL_TKN();

        JSONObject reqJson = new JSONObject();
        reqJson.put("MDL_TKN", MDL_TKN);

        String reqStr = reqJson.toString();

        OkCert okcert = new OkCert();

        KcbResVo kcbResVo = new KcbResVo();
        String resultStr;

        String RSLT_CD = "";
        String RSLT_MSG = "";
        try {
            resultStr = okcert.callOkCert(target, CP_CD, svcName, license,  reqStr);
            JSONObject resJson = new JSONObject(resultStr);

            // IPIN 인증일 경우 TX_SEQ_NO 값이 모듈통신 결과 resJson 가 아닌 request에 담겨있다.
            String TX_SEQ_NO =  "IPIN".equals(kcbReqVo.getAuthType()) ?  kcbReqVo.getTX_SEQ_NO() : resJson.getString("TX_SEQ_NO");
            RSLT_CD =  resJson.getString("RSLT_CD");
            RSLT_MSG =  resJson.getString("RSLT_MSG");

            kcbResVo = callback.checkResultCallback(RSLT_CD, resJson); // 순서 변경 금지!!

            kcbResVo.setTX_SEQ_NO(TX_SEQ_NO);
            kcbResVo.setRSLT_CD(RSLT_CD);
            kcbResVo.setRSLT_MSG(RSLT_MSG);


            kcbResVo.setCP_CD(kcbReqVo.getCP_CD());
            kcbResVo.setRSLT_CD(resJson.optString("RSLT_CD", ""));
            kcbResVo.setRSLT_MSG(resJson.optString("RSLT_MSG", ""));

            kcbResVo.setRSLT_NAME(resJson.optString("RSLT_NAME", ""));
            kcbResVo.setRSLT_BIRTHDAY(resJson.optString("RSLT_BIRTHDAY", ""));
            kcbResVo.setRSLT_SEX_CD(resJson.optString("RSLT_SEX_CD", ""));
            kcbResVo.setRSLT_NTV_FRNR_CD(resJson.optString("RSLT_NTV_FRNR_CD", ""));

            kcbResVo.setDI(resJson.optString("DI", ""));
            kcbResVo.setCI(resJson.optString("CI", ""));
            kcbResVo.setCI_UPDATE(resJson.optString("CI_UPDATE", ""));

            kcbResVo.setRETURN_MSG(resJson.optString("RETURN_MSG", ""));

            kcbResVo.setMDL_TKN(MDL_TKN);

            kcbResVo.setAuthType(kcbReqVo.getAuthType());
            //kcbResVo.setSucc(true); //인증별 함수에서 설정

        } catch (OkCertException e) {
            kcbResVo.setRSLT_CD(e.getCode());
            kcbResVo.setRSLT_MSG(RSLT_MSG);
        } catch (Exception e) {
            kcbResVo.setRSLT_CD(RSLT_CD);
            kcbResVo.setRSLT_MSG(RSLT_MSG);
        }

        return kcbResVo;
    }

    public static class KcbReqVo {
        private final String CP_CD;
        private final String license;
        private final String REQ_SITE_NM;
        private final String RTN_URL;
        private final String target;
        private final String popupUrl;
        private final String svcName;
        private String MDL_TKN;
        private final String SITE_URL;
        private final String RQST_CAUS_CD;
        private String TX_SEQ_NO;           // 거래번호
        private String RSLT_CD;             // 결과코드
        private String RSLT_MSG;            // 결과메세지
        private Boolean succ;               // 모듈통신 성공 여부
        private String tc;

        //사용자 정의 값
        private final String authType;
        private Boolean isTest;
        private String custCoCd;
        private String mbrId;
        private String reqUrl;
        private String mbrNm;
        private String birthdy;
        private String empNo;
        private String ci;
        private String logSeqGrp;

        //휴대폰 인증 전용 사전 입력 변수
        private String phoneName;
        private String phoneNumber;
        private String phoneBirthdy;
        private String phoneSex;
        private String phoneKoreanflag;

        public KcbReqVo(Builder builder) {
            CP_CD          = builder.CP_CD;
            license        = builder.license;
            REQ_SITE_NM    = builder.REQ_SITE_NM;
            RTN_URL        = builder.RTN_URL;
            target         = builder.target;
            popupUrl       = builder.popupUrl;
            svcName        = builder.svcName;
            MDL_TKN        = builder.MDL_TKN;
            SITE_URL       = builder.SITE_URL;
            RQST_CAUS_CD   = builder.RQST_CAUS_CD;
            RSLT_CD        = builder.RSLT_CD;
            RSLT_MSG       = builder.RSLT_MSG;
            succ           = builder.succ;
            authType       = builder.authType;
            isTest         = builder.isTest;
            custCoCd       = builder.custCoCd;
            mbrId          = builder.mbrId;
            TX_SEQ_NO      = builder.TX_SEQ_NO;
            reqUrl         = builder.reqUrl;
            mbrNm          = builder.mbrNm;
            birthdy        = builder.birthdy;
            empNo          = builder.empNo;
            ci             = builder.ci;
            phoneName       = builder.phoneName;
            phoneNumber     = builder.phoneNumber;
            phoneBirthdy    = builder.phoneBirthdy;
            phoneSex        = builder.phoneSex;
            phoneKoreanflag = builder.phoneKoreanflag;
            logSeqGrp       = builder.logSeqGrp;
        }

        public String getCP_CD() {return CP_CD;}

        public String getLicense() {return license;}

        public String getREQ_SITE_NM() {return REQ_SITE_NM;}

        public String getRTN_URL() {return RTN_URL;}

        public String getTarget() { return target; }

        public String getPopupUrl() { return popupUrl; }

        public String getSvcName() { return svcName; }

        public String getMDL_TKN() { return MDL_TKN; }

        public String getSITE_URL() { return SITE_URL; }

        public String getRQST_CAUS_CD() { return RQST_CAUS_CD; }

        public String getAuthType() { return authType; }

        public String getTX_SEQ_NO() { return TX_SEQ_NO; }

        public String getRSLT_CD() { return RSLT_CD; }

        public String getRSLT_MSG() { return RSLT_MSG; }

        public Boolean getSucc() { return succ; }

        public String getTc() { return tc; }

        public Boolean getTest() { return isTest; }

        public Boolean getIsTest(Boolean isTest) { return isTest; }

        public void setIsTest(Boolean isTest) { this.isTest = isTest; }

        public void setMDL_TKN(String MDL_TKN) { this.MDL_TKN = MDL_TKN; }

        public void setRSLT_CD(String RSLT_CD) { this.RSLT_CD = RSLT_CD; }

        public void setRSLT_MSG(String RSLT_MSG) { this.RSLT_MSG = RSLT_MSG; }

        public void setTc(String tc) { this.tc = tc; }

        public void setSucc(Boolean succ) { this.succ = succ; }

        public void setTX_SEQ_NO(String TX_SEQ_NO) { this.TX_SEQ_NO = TX_SEQ_NO; }

        public String getCustCoCd() { return custCoCd; }

        public String getMbrId() { return mbrId; }

        public String getReqUrl() { return reqUrl; }

        public String getMbrNm() { return mbrNm; }

        public void setMbrNm(String mbrNm) { this.mbrNm = mbrNm; }

        public String getBirthdy() { return birthdy; }

        public void setBirthdy(String birthdy) { this.birthdy = birthdy; }

        public String getEmpNo() { return empNo; }

        public void setEmpNo(String empNo) { this.empNo = empNo; }

        public String getCi() { return ci; }

        public void setCi(String ci) { this.ci = ci; }

        public String getPhoneName() { return phoneName; }

        public String getPhoneNumber() { return phoneNumber; }

        public String getPhoneBirthdy() { return phoneBirthdy; }

        public String getPhoneSex() { return phoneSex; }

        public String getPhoneKoreanflag() { return phoneKoreanflag; }

        public String getLogSeqGrp() { return logSeqGrp; }

        public void setLogSeqGrp(String logSeqGrp) { this.logSeqGrp = logSeqGrp; }

        public static class Builder {
            //필수
            private final String CP_CD;
            private final String license;
            //선택
            private String REQ_SITE_NM = "";
            private String RTN_URL = "";
            private String target = "";
            private String popupUrl = "";
            private String svcName = "";
            private String MDL_TKN = "";
            private String SITE_URL = "";
            private String RQST_CAUS_CD = "";
            private String RSLT_CD = "9000";
            private String RSLT_MSG = "인증시작";
            private Boolean succ = false;
            private String authType = "";
            private Boolean isTest = false;
            private String custCoCd = "";
            private String mbrId = "";
            private String TX_SEQ_NO = "";
            private String reqUrl = "";
            private String mbrNm = "";
            private String birthdy = "";
            private String empNo = "";
            private String ci = "";
            private String logSeqGrp = "";

            //휴대폰 인증 전용 사전 입력 변수
            private String phoneName = "";
            private String phoneNumber = "";
            private String phoneBirthdy = "";
            private String phoneSex = "";
            private String phoneKoreanflag = "";

            public Builder(String CP_CD, String license) {
                this.CP_CD = CP_CD;
                this.license = license;
            }

            public Builder REQ_SITE_NM(String REQ_SITE_NM) {
                this.REQ_SITE_NM = REQ_SITE_NM;
                return this;
            }

            public Builder RTN_URL(String RTN_URL) {
                this.RTN_URL = RTN_URL;
                return this;
            }

            public Builder target(String target) {
                this.target = target;
                return this;
            }

            public Builder popupUrl(String popupUrl) {
                this.popupUrl = popupUrl;
                return this;
            }

            public Builder svcName(String svcName) {
                this.svcName = svcName;
                return this;
            }
            public Builder MDL_TKN(String MDL_TKN) {
                this.MDL_TKN = MDL_TKN;
                return this;
            }
            public Builder SITE_URL(String SITE_URL) {
                this.SITE_URL = SITE_URL;
                return this;
            }
            public Builder RQST_CAUS_CD(String RQST_CAUS_CD) {
                this.RQST_CAUS_CD = RQST_CAUS_CD;
                return this;

            }
            public Builder RSLT_CD(String RSLT_CD) {
                this.RSLT_CD = RSLT_CD;
                return this;
            }
            public Builder RSLT_MSG(String RSLT_MSG) {
                this.RSLT_MSG = RSLT_MSG;
                return this;
            }
            public Builder succ(Boolean succ) {
                this.succ = succ;
                return this;
            }
            public Builder authType(String authType) {
                this.authType = authType;
                return this;
            }
            public Builder isTest(Boolean isTest) {
                this.isTest = isTest;
                return this;
            }
            public Builder custCoCd(String custCoCd) {
                this.custCoCd = custCoCd;
                return this;
            }
            public Builder mbrId(String mbrId) {
                this.mbrId = mbrId;
                return this;
            }
            public Builder TX_SEQ_NO(String TX_SEQ_NO) {
                this.TX_SEQ_NO = TX_SEQ_NO;
                return this;
            }
            public Builder reqUrl(String reqUrl) {
                this.reqUrl = reqUrl;
                return this;
            }

            public Builder mbrNm(String mbrNm) {
                this.mbrNm = mbrNm;
                return this;
            }

            public Builder birthdy(String birthdy) {
                this.birthdy = birthdy;
                return this;
            }

            public Builder empNo(String empNo) {
                this.empNo = empNo;
                return this;
            }

            public Builder ci(String ci) {
                this.ci = ci;
                return this;
            }

            public Builder phoneName(String phoneName) {
                this.phoneName = phoneName;
                return this;
            }

            public Builder phoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
                return this;
            }

            public Builder phoneBirthdy(String phoneBirthdy) {
                this.phoneBirthdy = phoneBirthdy;
                return this;
            }

            public Builder phoneSex(String phoneSex) {
                this.phoneSex = phoneSex;
                return this;
            }

            public Builder phoneKoreanflag(String phoneKoreanflag) {
                this.phoneKoreanflag = phoneKoreanflag;
                return this;
            }

            public Builder logSeqGrp(String logSeqGrp) {
                this.logSeqGrp = logSeqGrp;
                return this;
            }

            public KcbReqVo build() {
                return new KcbReqVo(this);
            }
        }

    }

    public static class KcbResVo {
        private String TX_SEQ_NO;           // 거래번호
        private String CP_CD;               // 고객사코드
        private String RSLT_CD;             // 결과코드
        private String RSLT_MSG;            // 결과메세지

        private String RSLT_NAME;           // 성명
        private String RSLT_BIRTHDAY;       // 생년월일
        private String RSLT_SEX_CD;         // 성별 (M : 남성, F : 여성)
        private String RSLT_NTV_FRNR_CD;    // 내외국인

        private String DI;                  // DI
        private String CI;                  // CI
        private String CI_UPDATE;           // CI 업데이트 횟수 (현재 ‘1’ 로 고정임)

        private String RETURN_MSG;          // 리턴메시지

        // 카드 인증 전용
        private String CRD_CD;              // 카드사
        private String CI_RQST_DT_TM;       // 요청 일시

        // 아이핀 인증 전용
        private String CI2;                 // CI 값 (CI_UPDATE가 짝수일 경우 사용)
        private String VSSN;                // 가상주민번호

        // 휴대폰 인증 전용
        private String TEL_COM_CD;          // 통신사코드
        private String TEL_NO;              // 휴대폰번호

        // 사용자 정의값
        private String authType;            // 인증타입 (card, ipin, phone)
        private Boolean succ = false;       // 결과 여부 true or false
        private String MDL_TKN;

        public String getTX_SEQ_NO() {
            return TX_SEQ_NO;
        }

        public void setTX_SEQ_NO(String TX_SEQ_NO) {
            this.TX_SEQ_NO = TX_SEQ_NO;
        }

        public String getCP_CD() {
            return CP_CD;
        }

        public void setCP_CD(String CP_CD) {
            this.CP_CD = CP_CD;
        }

        public String getRSLT_CD() {
            return RSLT_CD;
        }

        public void setRSLT_CD(String RSLT_CD) {
            this.RSLT_CD = RSLT_CD;
        }

        public String getRSLT_MSG() {
            return RSLT_MSG;
        }

        public void setRSLT_MSG(String RSLT_MSG) {
            this.RSLT_MSG = RSLT_MSG;
        }

        public String getRSLT_NAME() {
            return RSLT_NAME;
        }

        public void setRSLT_NAME(String RSLT_NAME) {
            this.RSLT_NAME = RSLT_NAME;
        }

        public String getRSLT_BIRTHDAY() { return RSLT_BIRTHDAY; }

        public void setRSLT_BIRTHDAY(String RSLT_BIRTHDAY) {
            this.RSLT_BIRTHDAY = RSLT_BIRTHDAY;
        }

        public String getRSLT_SEX_CD() { return RSLT_SEX_CD; }

        public void setRSLT_SEX_CD(String RSLT_SEX_CD) {
            this.RSLT_SEX_CD = RSLT_SEX_CD;
        }

        public String getRSLT_NTV_FRNR_CD() {
            return RSLT_NTV_FRNR_CD;
        }

        public void setRSLT_NTV_FRNR_CD(String RSLT_NTV_FRNR_CD) { this.RSLT_NTV_FRNR_CD = RSLT_NTV_FRNR_CD; }

        public String getDI() {
            return DI;
        }

        public void setDI(String DI) {
            this.DI = DI;
        }

        public String getCI() {
            return CI;
        }

        public void setCI(String CI) {
            this.CI = CI;
        }

        public String getCI_UPDATE() {
            return CI_UPDATE;
        }

        public void setCI_UPDATE(String CI_UPDATE) {
            this.CI_UPDATE = CI_UPDATE;
        }

        public String getRETURN_MSG() {
            return RETURN_MSG;
        }

        public void setRETURN_MSG(String RETURN_MSG) {
            this.RETURN_MSG = RETURN_MSG;
        }

        public String getCRD_CD() {
            return CRD_CD;
        }

        public void setCRD_CD(String CRD_CD) {
            this.CRD_CD = CRD_CD;
        }

        public String getCI_RQST_DT_TM() {
            return CI_RQST_DT_TM;
        }

        public void setCI_RQST_DT_TM(String CI_RQST_DT_TM) { this.CI_RQST_DT_TM = CI_RQST_DT_TM; }

        public String getCI2() {
            return CI2;
        }

        public void setCI2(String CI2) {
            this.CI2 = CI2;
        }

        public String getVSSN() {
            return VSSN;
        }

        public void setVSSN(String VSSN) {
            this.VSSN = VSSN;
        }

        public String getTEL_COM_CD() {
            return TEL_COM_CD;
        }

        public void setTEL_COM_CD(String TEL_COM_CD) {
            this.TEL_COM_CD = TEL_COM_CD;
        }

        public String getTEL_NO() {
            return TEL_NO;
        }

        public void setTEL_NO(String TEL_NO) {
            this.TEL_NO = TEL_NO;
        }

        public String getAuthType() {
            return authType;
        }

        public void setAuthType(String authType) {
            this.authType = authType;
        }

        public Boolean getSucc() { return succ; }

        public void setSucc(Boolean succ) { this.succ = succ; }

        public String getMDL_TKN() { return MDL_TKN; }

        public void setMDL_TKN(String MDL_TKN) { this.MDL_TKN = MDL_TKN; }

    }

    public interface CheckStartCallback {
        KcbReqVo checkStartCallback(String cd, JSONObject json);
    }
    public interface CheckResultCallback {
        KcbResVo checkResultCallback(String cd, JSONObject json);
    }
}
