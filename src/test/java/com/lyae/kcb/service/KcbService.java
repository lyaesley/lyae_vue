package com.lyae.kcb.service;

import com.benepia.frnt.kcb.util.KcbUtil;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public interface KcbService {

    KcbUtil.KcbReqVo callKcbModuleStartCard(HttpServletRequest request, HttpServletResponse response);

    KcbUtil.KcbReqVo callKcbModuleStartIpin(HttpServletRequest request, HttpServletResponse response);

    KcbUtil.KcbReqVo callKcbModuleStartPhone(HttpServletRequest request, HttpServletResponse response);

    KcbUtil.KcbResVo callKcbModuleResultCard(HttpServletRequest request, HttpServletResponse response);

    KcbUtil.KcbResVo callKcbModuleResultIpin(HttpServletRequest request, HttpServletResponse response);

    KcbUtil.KcbResVo callKcbModuleResultPhone(HttpServletRequest request, HttpServletResponse response);



    KcbUtil.KcbReqVo callKcbModuleStart(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException;

    KcbUtil.KcbResVo callKcbModuleResult(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException;

    Map<String, Object> kcbStart(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException;

}
