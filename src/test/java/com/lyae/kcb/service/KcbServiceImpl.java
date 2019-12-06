package com.lyae.kcb.service;

import com.benepia.admin.company.dto.CustCompanySearchTO;
import com.benepia.admin.company.service.CustCompanyService;
import com.benepia.admin.company.vo.CustCompanyVO;
import com.benepia.admin.member.dto.MemberPrivateVO;
import com.benepia.admin.member.dto.MemberSearchTO;
import com.benepia.admin.member.dto.MemberVO;
import com.benepia.admin.member.service.MbrSmsConfHstService;
import com.benepia.admin.member.service.MemberService;
import com.benepia.admin.point.dto.PointSearchTO;
import com.benepia.admin.point.dto.PointSearchVO;
import com.benepia.admin.point.service.PointSearchIF;
import com.benepia.frnt.comm.service.CodeConstantService;
import com.benepia.frnt.comm.util.CommonUtil;
import com.benepia.frnt.comm.util.DateUtils;
import com.benepia.frnt.comm.util.EncryptionSha256;
import com.benepia.frnt.comm.util.SessionUtil;
import com.benepia.frnt.kcb.dao.KcbDao;
import com.benepia.frnt.kcb.util.KcbUtil;
import com.benepia.frnt.kcb.util.KcbUtil.KcbReqVo;
import com.benepia.frnt.kcb.util.KcbUtil.KcbResVo;
import com.benepia.frnt.kcb.vo.KcbLogVo;
import com.benepia.frnt.login.model.factory.JoinModelFactoryIF;
import com.benepia.frnt.login.service.LoginService;
import com.benepia.frnt.membership.service.CuMembershipService;
import com.benepia.frnt.seed.SeedService;
import com.benepia.frnt.seed.err.SeedException;
import com.benepia.standard.common.constant.ConstantCode4Application;
import com.benepia.standard.common.korcham.KorChamUtil;
import com.benepia.standard.common.korcham.vo.MbrInfoResponse;
import com.benepia.standard.common.swing.SwingUtil;
import com.benepia.standard.common.swing.vo.SUKEY00093;
import com.benepia.standard.common.util.SkmnsMapperUtil;
import com.benepia.utils.PasswordUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class KcbServiceImpl implements KcbService {

    @Autowired
    KcbUtil kcbUtil;

    @Autowired
    KcbDao kcbDao;

    @Autowired
    private CodeConstantService codeConstantService;

    @Autowired
    private SeedService seedService;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    private String getSubDomNm(final StringBuffer requestURL) {
        String url = requestURL.toString();
        String subDomNm = "";

        if (url.indexOf("localhost") != -1) {
            subDomNm = this.codeConstantService.getStringValue("LOCAL_SUB_DOM_NM");
        } else { // 개발 stg, 운영 new
            subDomNm = url.substring(url.indexOf("://") + 3);
            subDomNm = subDomNm.substring(0, subDomNm.indexOf("."));
        }
        this.logger.info("subDomNm ---> " + subDomNm);
        return subDomNm;
    }

    private String getIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) { ip = request.getHeader("Proxy-Client-IP"); }
        if (ip == null) { ip = request.getHeader("WL-Proxy-Client-IP"); } // 웹로직
        if (ip == null) { ip = request.getHeader("HTTP_CLIENT_IP"); }
        if (ip == null) { ip = request.getHeader("HTTP_X_FORWARDED_FOR"); }
        if (ip == null) { ip = request.getRemoteAddr(); }

        return ip;
    }

    private void insertLogKcb(HttpServletRequest request, KcbReqVo kcbReqVo) {

        String regrIp = this.getIp(request);

        String SndRcvFg = "9000".equals(kcbReqVo.getRSLT_CD()) ? "C" : "S";

        String logSeq = ""; //인증 시퀀스

        logSeq = "C".equals(SndRcvFg) ? kcbReqVo.getLogSeqGrp() : kcbDao.selectKcbCertLogSeq();

        //개인정보 암호화
        String key = this.seedService.getSeedKey("2418");
        String mbrNm = kcbReqVo.getMbrNm();
        String birthDy = kcbReqVo.getBirthdy();
        String empNo = kcbReqVo.getEmpNo();

        try {
            mbrNm = !"".equals(mbrNm) ? seedService.encrypt(key, kcbReqVo.getMbrNm()) : "";
            birthDy = !"".equals(birthDy) ? seedService.encrypt(key, kcbReqVo.getBirthdy()) : "";
            empNo = !"".equals(empNo) ? seedService.encrypt(key, kcbReqVo.getEmpNo()) : "";
        } catch (SeedException e) {
//            e.printStackTrace();
        }

        KcbLogVo KcbLogVo = new KcbLogVo();
        KcbLogVo.setLogSeq(Integer.parseInt(logSeq));
        KcbLogVo.setLogSeqGrp(Integer.parseInt(kcbReqVo.getLogSeqGrp()));
        KcbLogVo.setSndRcvFg(SndRcvFg);
        KcbLogVo.setAuthTyp(kcbReqVo.getAuthType());
        KcbLogVo.setReqServer(kcbReqVo.getReqUrl());
        KcbLogVo.setCustCoCd(kcbReqVo.getCustCoCd());
        KcbLogVo.setMbrId(kcbReqVo.getMbrId());
        KcbLogVo.setRegrIp(regrIp);
        KcbLogVo.setMdlTkn(kcbReqVo.getMDL_TKN());
        KcbLogVo.setTxSeqNo(kcbReqVo.getTX_SEQ_NO());
        KcbLogVo.setMbrNm(mbrNm);
        KcbLogVo.setBirthdy(birthDy);
        KcbLogVo.setEmpNo(empNo);
        KcbLogVo.setCi(kcbReqVo.getCi());
        KcbLogVo.setRsltCd(kcbReqVo.getRSLT_CD());
        KcbLogVo.setRsltMsg(kcbReqVo.getRSLT_MSG());

        kcbDao.insertLogKcb(KcbLogVo);

    }

    private void insertLogKcb(HttpServletRequest request, KcbResVo kcbResVo) {


        String reqUrl = "";
        String logSeqGrp = "";
        String custCoCd = "";
        String mbrId = "";
        String empNo = "";
        String regrIp = "";

        String key = "";
        String mbrNm = "";
        String birthDy = "";

        String authType = "";

        JSONObject jsonObject = JSONObject.fromObject(kcbResVo.getRETURN_MSG());

//        if(!jsonObject.isNullObject()) {

            authType = kcbResVo.getAuthType();

            reqUrl = jsonObject.optString("reqUrl", "");
            logSeqGrp = jsonObject.optString("logSeqGrp", "");
            custCoCd = jsonObject.optString("custCoCd", "");
            mbrId = jsonObject.optString("mbrId", "");
            empNo = jsonObject.optString("empNo", "");
            regrIp = this.getIp(request);

            //개인정보 암호화
            key = this.seedService.getSeedKey("2418");
            mbrNm = kcbResVo.getRSLT_NAME();
            birthDy = kcbResVo.getRSLT_BIRTHDAY();

            try {
                mbrNm = !"".equals(mbrNm) ? seedService.encrypt(key, kcbResVo.getRSLT_NAME()) : "";
                birthDy = !"".equals(birthDy) ? seedService.encrypt(key, kcbResVo.getRSLT_BIRTHDAY()) : "";
                empNo = !"".equals(empNo) ? seedService.encrypt(key, empNo) : "";
            } catch (SeedException e) {
//            e.printStackTrace();
            }
//        } else {
            /* 사용하지 않으나 추후에 사용될 수도 있으므로 주석처리
            //본인인증 팝업창에서 본인인증을 하지않고 닫을 경우 kcbResVo.getRETURN_MSG() 값이 넘어 오지 않아 별도로 처리
            KcbLogVo KcbLogVoGrp = this.kcbDao.selectKcbLogSeqGrpByTxSeqNo(kcbResVo.getTX_SEQ_NO());
            logSeqGrp = String.valueOf(KcbLogVoGrp.getLogSeqGrp());
            authType = KcbLogVoGrp.getAuthTyp();
            */
//        }



        KcbLogVo KcbLogVo = new KcbLogVo();
        KcbLogVo.setLogSeq(Integer.parseInt(kcbDao.selectKcbCertLogSeq()));
        KcbLogVo.setLogSeqGrp(Integer.parseInt(logSeqGrp));
        KcbLogVo.setSndRcvFg("R");
        KcbLogVo.setAuthTyp(authType);
        KcbLogVo.setReqServer(reqUrl);
        KcbLogVo.setCustCoCd(custCoCd);
        KcbLogVo.setMbrId(mbrId);
        KcbLogVo.setRegrIp(regrIp);
        KcbLogVo.setMdlTkn(kcbResVo.getMDL_TKN());
        KcbLogVo.setTxSeqNo(kcbResVo.getTX_SEQ_NO());
        KcbLogVo.setMbrNm(mbrNm);
        KcbLogVo.setBirthdy(birthDy);
        KcbLogVo.setEmpNo(empNo);
        KcbLogVo.setCi(kcbResVo.getCI());
        KcbLogVo.setRsltCd(kcbResVo.getRSLT_CD());
        KcbLogVo.setRsltMsg(kcbResVo.getRSLT_MSG());

        kcbDao.insertLogKcb(KcbLogVo);

    }


    @Override
    public Map<String, Object> kcbStart(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {

        SessionUtil sessionUtil = new SessionUtil();
        sessionUtil.setSession(request);

        String authType = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "kcbAuthType", ""));
        String custCoCd = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "custCoCd", ""));
        String mbrId = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "mbrId", ""));
        String mbrNm = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "mbrNm", ""));
        String birthDy = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "birthDy", ""));
        birthDy = birthDy.replace(".", "");
        String empNo = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "empNo", ""));
        String ci = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "ci", ""));
        String reqUrl = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "callUrl", ""));
        reqUrl = "".equals(reqUrl) ? request.getHeader("Referer") : reqUrl;

        mbrId = CommonUtil.isNullOrBlank(mbrId) ? sessionUtil.getValue("mbrId") : mbrId;
        custCoCd = CommonUtil.isNullOrBlank(custCoCd) ? sessionUtil.getValue("custCoCd") : custCoCd;

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String phoneName, phoneNumber, phoneBirthdy, phoneSex, phoneKoreanflag;

        if("PHONE".equals(authType) && !"휴면".equals(mbrNm) && !"휴면".equals(sessionUtil.getAttribute("mbrNm", ""))) {
            phoneName = sessionUtil.getAttribute("mbrNm", CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "NAME", mbrNm)));
            //phoneNumber = sessionUtil.getAttribute("mobTelNo", CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "PHONENO", "")));
            phoneBirthdy = sessionUtil.getAttribute("brthdy", CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "BIRTHDAY", birthDy)));
            //phoneSex = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "SEX", ""));
            //phoneKoreanflag = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "KOREANFLAG", ""));

            resultMap.put("phoneName", phoneName);
            //resultMap.put("phoneNumber", phoneNumber);
            resultMap.put("phoneBirthdy", phoneBirthdy);
            //resultMap.put("phoneSex", phoneSex);
            //resultMap.put("phoneKoreanflag", phoneKoreanflag);
        }

        resultMap.put("authType", authType);
        resultMap.put("custCoCd", custCoCd);
        resultMap.put("mbrId", mbrId);
        resultMap.put("reqUrl", reqUrl);        //요청 URL
        resultMap.put("mbrNm", mbrNm);
        resultMap.put("birthDy", birthDy);
        resultMap.put("empNo", empNo);
        resultMap.put("ci", ci);

        return resultMap;
    }

    @Override
    public KcbReqVo callKcbModuleStart(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {

        String authType = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "kcbAuthType"));

        KcbReqVo kcbReqVo = null;

        if("CARD".equals(authType)) {
            kcbReqVo = this.callKcbModuleStartCard(request, response);
        } else if("IPIN".equals(authType)) {
            kcbReqVo = this.callKcbModuleStartIpin(request, response);
        } else if("PHONE".equals(authType)) {
            kcbReqVo = this.callKcbModuleStartPhone(request, response);
        }

        return kcbReqVo;
    }

    @Override
    public KcbResVo callKcbModuleResult(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {

        String authType = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "kcbAuthType"));

        KcbResVo kcbResVo = null;

        if("CARD".equals(authType)) {
            kcbResVo = this.callKcbModuleResultCard(request, response);
        } else if("IPIN".equals(authType)) {
            kcbResVo = this.callKcbModuleResultIpin(request, response);
        } else if("PHONE".equals(authType)) {
            kcbResVo = this.callKcbModuleResultPhone(request, response);
        }

        return kcbResVo;
    }

    @Override
    public KcbReqVo callKcbModuleStartCard(HttpServletRequest request, HttpServletResponse response) {

        KcbReqVo kcbReqVo = new KcbReqVo.Builder(null, null)
                .target("PROD")
                .authType("CARD")
                .RQST_CAUS_CD("00")
                .build();

        return this.callKcbModuleStartTemplate(kcbReqVo, request, response);
    }

    @Override
    public KcbReqVo callKcbModuleStartIpin(HttpServletRequest request, HttpServletResponse response) {

        KcbReqVo kcbReqVo = new KcbReqVo.Builder(null, null)
                .target("PROD")
                .authType("IPIN")
                .RQST_CAUS_CD("00")
                .SITE_URL("")
                .build();

        return this.callKcbModuleStartTemplate(kcbReqVo, request, response);
    }

    @Override
    public KcbReqVo callKcbModuleStartPhone(HttpServletRequest request, HttpServletResponse response) {

        String phoneName = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "phoneName", ""));
        String phoneNumber = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "phoneNumber", ""));
        String phoneBirthdy = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "phoneBirthdy", ""));
        String phoneSex = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "phoneSex", ""));
        String phoneKoreanflag = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "phoneKoreanflag", ""));

        KcbReqVo kcbReqVo = new KcbReqVo.Builder(null, null)
                .target("PROD")
                .authType("PHONE")
                .RQST_CAUS_CD("00")
                .SITE_URL("")
                .phoneName(phoneName)
                .phoneNumber(phoneNumber)
                .phoneBirthdy(phoneBirthdy)
                .phoneSex(phoneSex)
                .phoneKoreanflag(phoneKoreanflag)
                .build();

        return this.callKcbModuleStartTemplate(kcbReqVo, request, response);
    }

    @Override
    public KcbResVo callKcbModuleResultCard(HttpServletRequest request, HttpServletResponse response) {

        KcbReqVo kcbReqVo = new KcbReqVo.Builder(null, null)
                .authType("CARD")
                .target("PROD")
                .build();

        return this.callKcbModuleResultTemplate(kcbReqVo, request, response);
    }

    @Override
    public KcbResVo callKcbModuleResultIpin(HttpServletRequest request, HttpServletResponse response) {


        KcbReqVo kcbReqVo = new KcbReqVo.Builder(null, null)
                .authType("IPIN")
                .target("PROD")
                .RQST_CAUS_CD("00")
                .build();

        return this.callKcbModuleResultTemplate(kcbReqVo, request, response);
    }

    @Override
    public KcbResVo callKcbModuleResultPhone(HttpServletRequest request, HttpServletResponse response) {


        KcbReqVo kcbReqVo = new KcbReqVo.Builder(null, null)
                .authType("PHONE")
                .target("PROD")
                .RQST_CAUS_CD("00")
                .build();

        return this.callKcbModuleResultTemplate(kcbReqVo, request, response);
    }

    private KcbReqVo callKcbModuleStartTemplate(KcbReqVo reqKcbReqVo, HttpServletRequest request, HttpServletResponse response) {

        SessionUtil sessionUtil = new SessionUtil();
        sessionUtil.setSession(request);

        /**************************************************************************
         * OkCert3 카드 본인확인 서비스 파라미터
         **************************************************************************/

        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //' 회원사 사이트명 : 최대 50 바이트. KCB DB 저장 외에도 간편인증 시 일부 앱카드 어플에 표시되는 정보.
        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
//        String REQ_SITE_NM = request.getParameter("REQ_SITE_NM");

        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //' KCB로부터 부여받은 회원사코드(아이디) 설정 (12자리)
        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        String CP_CD = (String) this.codeConstantService.getValue("KCB_CP_CD"); // 회원사코드

        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //' 리턴 URL 설정 : 최대 1000 바이트.
        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //' opener(popup1)의 도메인과 일치하도록 설정해야 함.
        //' (http://www.test.co.kr과 http://test.co.kr는 다른 도메인으로 인식하며, http 및 https도 일치해야 함)
        //String RTN_URL = "http://"+request.getServerName()+":8080/test/kcb/result.do";		// 본인인증 완료 후 리턴될 URL (도메인 포함 full path)
        String RTN_URL = String.format("%s%s:%d", request.isSecure() ? "https://" : "http://", request.getServerName() , request.getServerPort());		// 본인인증 완료 후 리턴될 URL (도메인 포함 full path)
//        String RTN_URL22 = String.format("%s%s.benepia.co.kr", request.isSecure() ? "https://" : "http://", this.getSubDomNm(request.getRequestURL()));
//        logger.info("############ KCB AUTH RTN_URL 1: " + request.isSecure());
//        logger.info("############ KCB AUTH RTN_URL 2: " + request.getServerName());
//        logger.info("############ KCB AUTH RTN_URL 3: " + request.getServerPort());
        RTN_URL += (String) this.codeConstantService.getValue("KCB_RETURN_URL");		// 본인인증 완료 후 리턴될 URL (도메인 포함 full path)
//        logger.info("############ KCB AUTH RTN_URL 6: " + RTN_URL);
        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //' 라이센스 파일
        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //String license = "C:\\okcert3_license\\" + CP_CD + "_CID_01_" + target +"_AES_license.dat";
        String realPath = request.getSession().getServletContext().getRealPath("");
        String license = (String) this.codeConstantService.getValue("KCB_KEY_PATH");

        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //' 서비스명 (고정값)
        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        String svcName = "";

        /**
        인증요청사유코드
        00 : 회원가입
        01 : 성인인증
        02 : 회원정보수정
        03 : 비밀번호찾기
        04 : 상품구매
        99 : 기타
        */


        String authType = reqKcbReqVo.getAuthType();
        String popupUrl = "";

        if("CARD".equals(authType)) {
            license = license.replace("#authCode#", "CID_01");
            license = license.replace("#target#", reqKcbReqVo.getTarget());
            svcName = "CID_CARD_POPUP_START";
            popupUrl = (String) this.codeConstantService.getValue("KCB_AUTH_CARD_POPURL_"+ reqKcbReqVo.getTarget());

        } else if("IPIN".equals(authType)) {
            license = license.replace("#authCode#", "TIS_01");
            license = license.replace("#target#", reqKcbReqVo.getTarget());
            svcName = "TIS_IPIN_POPUP_START";
            popupUrl = (String) this.codeConstantService.getValue("KCB_AUTH_IPIN_POPURL_"+ reqKcbReqVo.getTarget());

        } else if("PHONE".equals(authType)) {
            license = license.replace("#authCode#", "IDS_01");
            license = license.replace("#target#", reqKcbReqVo.getTarget());
            svcName = "IDS_HS_POPUP_START";
            popupUrl = (String) this.codeConstantService.getValue("KCB_AUTH_PHONE_POPURL_"+ reqKcbReqVo.getTarget());

        }

        RTN_URL = RTN_URL + "?kcbAuthType="+authType; //**중요** 인증타입을 kcbModuleResult.do 에서 받기 위함. *필수세팅* 지우지마세요.
        license = realPath + license;

        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //' 서비스명 (고정값)
        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //String svcName = "CID_CARD_POPUP_START";



        KcbReqVo resultKcbReqVo = null;    // return 변수
        try {
            String REQ_SITE_NM = (String) this.codeConstantService.getValue("KCB_REQ_SITE_NM");

            // 로그 저장하기 위한 변수 세팅
            String custCoCd = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "custCoCd"));
            String mbrId = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "mbrId"));
            String reqUrl = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "reqUrl"));       //요청 URL
            String mbrNm = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "mbrNm"));
            String birthdy = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "birthDy"));
            String empNo = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "empNo"));
            String ci = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "ci"));


            //테스트여부 S
            String strIsTest = CommonUtil.getRemoveCode2(ServletRequestUtils.getStringParameter(request, "isTest"));
            Boolean isTest = false;
            if("true".equals(strIsTest)) {
                isTest = true;
                RTN_URL = String.format("%s%s:%d", request.isSecure() ? "https://" : "http://", request.getServerName() , request.getServerPort());		// 본인인증 완료 후 리턴될 URL (도메인 포함 full path)
                RTN_URL += (String) this.codeConstantService.getValue("KCB_RETURN_URL_TEST") + "?kcbAuthType="+authType;		// 본인인증 완료 후 리턴될 URL (도메인 포함 full path)
            }
            //테스트여부 E

            String logSeq = kcbDao.selectKcbCertLogSeq(); //인증 시퀀스 가져오기 : TX_SEQ_NO 파라미터로 넘기기 위함.

            KcbReqVo kcbReqVo = new KcbReqVo.Builder(CP_CD, license)
                    //공통
                    .REQ_SITE_NM(REQ_SITE_NM)
                    .RTN_URL(RTN_URL)
                    .svcName(svcName)
                    //선택사항(아이핀,휴대폰,카드)
                    //이 함수를 호출하는 곳에서 하기 선택사항 정보를 파라미터(KcbReqVo) 로 전달해줘야함
                    .popupUrl(popupUrl)
                    .target(reqKcbReqVo.getTarget())
                    .RQST_CAUS_CD(reqKcbReqVo.getRQST_CAUS_CD())
                    .SITE_URL(reqKcbReqVo.getSITE_URL())
                    .authType(authType)
                    .isTest(isTest) // isTest = true 일 경우 리턴URL 이 다르다.
                    .custCoCd(custCoCd)
                    .mbrId(mbrId)
                    .logSeqGrp(logSeq)
                    .reqUrl(reqUrl)
                    .mbrNm(mbrNm)
                    .birthdy(birthdy)
                    .empNo(empNo)
                    .ci(ci)
                    .phoneName(reqKcbReqVo.getPhoneName())
                    .phoneNumber(reqKcbReqVo.getPhoneNumber())
                    .phoneBirthdy(reqKcbReqVo.getPhoneBirthdy())
                    .phoneSex(reqKcbReqVo.getPhoneSex())
                    .phoneKoreanflag(reqKcbReqVo.getPhoneKoreanflag())
                    .build();

            this.insertLogKcb(request, kcbReqVo); // 인증시작 로그

            resultKcbReqVo = kcbUtil.getMapCallKcbModuleStart(kcbReqVo);

            this.insertLogKcb(request, resultKcbReqVo); // 인증시작 결과 로그

        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }

        return resultKcbReqVo;
    }

    private KcbResVo callKcbModuleResultTemplate(KcbReqVo reqKcbReqVo, HttpServletRequest request, HttpServletResponse response) {

        SessionUtil sessionUtil = new SessionUtil();
        sessionUtil.setSession(request);

        //' 처리결과 모듈 토큰 정보
        String MDL_TKN = "";
        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //' KCB로부터 부여받은 회원사코드(아이디) 설정 (12자리)
        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        String CP_CD = (String) this.codeConstantService.getValue("KCB_CP_CD"); // 회원사코드


        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //' 라이센스 파일
        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //String license = "C:\\okcert3_license\\" + CP_CD + "_CID_01_" + target + "_AES_license.dat";
        String realPath = request.getSession().getServletContext().getRealPath("");
        String license = (String) this.codeConstantService.getValue("KCB_KEY_PATH");

        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        //' 서비스명 (고정값)
        //'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        String svcName = "";
        String authType = reqKcbReqVo.getAuthType();


        String TX_SEQ_NO = ""; // IPIN 인증일 경우 TX_SEQ_NO 값이 모듈통신 결과 resJson 가 아닌 request에 담겨있다.

        if("CARD".equals(authType)) {
            MDL_TKN = request.getParameter("MDL_TKN");
            license = license.replace("#authCode#", "CID_01");
            license = license.replace("#target#", reqKcbReqVo.getTarget());
            svcName = "CID_CARD_POPUP_RESULT";

        } else if("IPIN".equals(authType)) {
            MDL_TKN = request.getParameter("MDL_TKN");
            TX_SEQ_NO = request.getParameter("TX_SEQ_NO");
            license = license.replace("#authCode#", "TIS_01");
            license = license.replace("#target#", reqKcbReqVo.getTarget());
            svcName = "TIS_IPIN_POPUP_RESULT";

        } else if("PHONE".equals(authType)) {
            MDL_TKN = request.getParameter("mdl_tkn");
            license = license.replace("#authCode#", "IDS_01");
            license = license.replace("#target#", reqKcbReqVo.getTarget());
            svcName = "IDS_HS_POPUP_RESULT";

        }

        license = realPath + license;

        KcbReqVo kcbReqVo = new KcbReqVo.Builder(CP_CD, license)
                .authType(reqKcbReqVo.getAuthType())
                .target("PROD") // 테스트="TEST", 운영="PROD"
                .svcName(svcName) //' 서비스명 (고정값)
                .MDL_TKN(MDL_TKN)
                .RQST_CAUS_CD(reqKcbReqVo.getRQST_CAUS_CD())
                .TX_SEQ_NO(TX_SEQ_NO)
                .build();

        KcbResVo kcbResVo; // return 변수
        kcbResVo = kcbUtil.getMapCallKcbModuleResult(kcbReqVo);

        this.insertLogKcb(request, kcbResVo); //인증결과 로그

        return kcbResVo;
    }
}
