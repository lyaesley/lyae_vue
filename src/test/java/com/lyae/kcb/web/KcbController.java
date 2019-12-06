package com.lyae.kcb.web;

import com.benepia.frnt.kcb.service.KcbService;
import com.benepia.frnt.kcb.util.KcbUtil;
import com.benepia.standard.common.util.SkmnsMapperUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class KcbController {

    @Autowired
    KcbService kcbService;

    @RequestMapping(value = "/frnt/kcb/authStart.do")
    public ModelAndView authStart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();

        //kcbService.authStart(request, response);
        mav = this.kcbStep1(request, response);

        return mav;
    }

    @RequestMapping(value = "/frnt/kcb/authResult.do")
    public ModelAndView authResult(final HttpServletRequest request, final HttpServletResponse response, final HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();

        KcbUtil.KcbResVo kcbResVo = null;
        kcbResVo = kcbService.callKcbModuleResult(request, response);

        if(kcbResVo.getSucc()){
            JSONObject jsonObject = JSONObject.fromObject(kcbResVo.getRETURN_MSG());
            String reqUrl = jsonObject.getString("reqUrl");

            if(reqUrl.indexOf("/travel/refund/travelRefund.do") != -1){
                mav.addObject("refund", "refund");
            }
        }

        mav.addObject("resParam", kcbResVo);
        mav.addObject("resParamJson", SkmnsMapperUtil.writeObjectAsString(kcbResVo));

        mav.setViewName("/auth/kcb/resultKcb");

        return mav;
    }

    //ID/PW, 휴면인증 찾기 페이지 본인인증 후 검증 로직
    @RequestMapping(value = "/auth/resultKcbOfSearch.do")
    public void resultKcbOfSearch(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletRequestBindingException {
        kcbService.resultKcbOfSearch(request, response);
    }

    //IMPAY, IPIN 인증 후 개인정보 변경 / CI, 생년월일 확인 후 업데이트
    @RequestMapping(value = "/auth/resultKcbOfPwdUpdate.do")
    public ModelAndView resultKcbOfPwdUpdate(final HttpServletRequest request, final HttpServletResponse response, final HttpSession session) throws Exception {
        return kcbService.resultKcbOfPwdUpdate(request, response, session);
    }

    // 팝업 최초인증, 개인정보 활용 동의
    @RequestMapping(value = "/auth/resultKcbOfFrntCertify.do")
    public void resultKcbOfFrntCertify(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletRequestBindingException {
        kcbService.resultKcbOfFrntCertify(request, response);
    }

    @RequestMapping(value = "/auth/resultKcbOfNewYearCertify.do")
    public void resultKcbOfNewYearCertify(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletRequestBindingException {
        kcbService.resultKcbOfNewYearCertify(request, response);
    }

    //회원가입
    @RequestMapping(value = "/auth/resultKcbOfFrntJoin.do")
    public void resultKcbOfFrntJoin(final HttpServletRequest request, final HttpServletResponse response, final HttpSession session) throws ServletRequestBindingException, IOException {
        kcbService.resultKcbOfFrntJoin(request, response, session);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "/frnt/kcb/phoneIndex.do")
    public ModelAndView phoneIndex(final HttpServletRequest request, final HttpServletResponse response) throws ServletRequestBindingException {
        ModelAndView mav = new ModelAndView();

        if (request.getRequestURL().indexOf("benepia.co.kr") < 0) {
            mav.addObject("domain", "visitkorea.or.kr");
        } else {
            mav.addObject("domain", "benepia.co.kr");
        }
        mav.setViewName("/auth/kcb/phoneIndex");
        return mav;
    }

    @RequestMapping(value = "/frnt/kcbStep1.do")
    public ModelAndView kcbStep1(final HttpServletRequest request, final HttpServletResponse response) throws ServletRequestBindingException {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap = kcbService.kcbStart(request, response);

        mav.addObject("resParam", resultMap);
        mav.addObject("resParamJson", SkmnsMapperUtil.writeObjectAsString(resultMap));



        mav.setViewName("/auth/kcb/common_popup1");
        return mav;
    }

    @RequestMapping(value = "/frnt/callKcbModuleStart.do")
    public ModelAndView callKcbModuleStart(final HttpServletRequest request, final HttpServletResponse response) throws ServletRequestBindingException {
        ModelAndView mav = new ModelAndView();
        KcbUtil.KcbReqVo kcbReqVo = null;

        kcbReqVo = kcbService.callKcbModuleStart(request, response);

        mav.addObject("resParam", kcbReqVo);
        mav.addObject("resParamJson", SkmnsMapperUtil.writeObjectAsString(kcbReqVo));

        mav.setViewName("/auth/kcb/common_popup2");
        return mav;
    }

    @RequestMapping(value = "/frnt/callKcbModuleResult.do")
    public ModelAndView callKcbModuleResult(final HttpServletRequest request, final HttpServletResponse response) throws ServletRequestBindingException {
        ModelAndView mav = new ModelAndView();

        KcbUtil.KcbResVo kcbResVo = null;

        kcbResVo = kcbService.callKcbModuleResult(request, response);

        mav.addObject("resParam", kcbResVo);
        mav.addObject("resParamJson", SkmnsMapperUtil.writeObjectAsString(kcbResVo));

        mav.setViewName("/auth/kcb/common_popup3");
        return mav;
    }

    @RequestMapping(value = "/frnt/kcbStep4.do")
    public ModelAndView kcbStep4(final HttpServletRequest request, final HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/auth/kcb/common_popup4");
        return mav;
    }



}
