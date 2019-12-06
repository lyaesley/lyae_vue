package com.lyae.kcb.vo;

import java.util.Date;

public class KcbLogVo {
    private int logSeq;            // '로그순번';
    private int logSeqGrp;            // '로그순번';
    private String sndRcvFg;       // '송수신구분 S:송신,R:수신';
//    private String rqstCausCd;     // '인증타입 00:회원가입,01:성인인증,02:회원정보수정,03:비밀번호찾기,04:상품구매,99:기타';
    private String authTyp;        // '인증타입 CARD:카드,IPIN:아이핀,PHONE:핸드폰';
    private String reqServer;      // '요청 서버 도메인';
    private String custCoCd;       // '요청 고객사 코드';
    private String mbrId;          // '요청회원ID';
    private String regrIp;         // '인증요청자 IP';
    private String mdlTkn;         // '거래요청 수신 토큰';
    private String txSeqNo;        // '거래 일련 번호';
    private String mbrNm;          // '인증요청 성명';
    private String birthdy;        // '인증요청 생년월일';
    private String empNo;          // '인증요청 사번';
    private String ci;             // '거래 일련 번호';
    private String rsltCd;         // '응답코드';
    private String rsltMsg;        // '응답메세지';
    private Date regDt;            // '등록일';

    public int getLogSeq() { return logSeq; }

    public void setLogSeq(int logSeq) { this.logSeq = logSeq; }

    public int getLogSeqGrp() { return logSeqGrp; }

    public KcbLogVo setLogSeqGrp(int logSeqGrp) { this.logSeqGrp = logSeqGrp;return this;
    }

    public String getSndRcvFg() { return sndRcvFg; }

    public void setSndRcvFg(String sndRcvFg) { this.sndRcvFg = sndRcvFg; }

    public String getAuthTyp() { return authTyp; }

    public void setAuthTyp(String authTyp) { this.authTyp = authTyp; }

    public String getReqServer() { return reqServer; }

    public void setReqServer(String reqServer) { this.reqServer = reqServer; }

    public String getCustCoCd() { return custCoCd; }

    public void setCustCoCd(String custCoCd) { this.custCoCd = custCoCd; }

    public String getMbrId() { return mbrId; }

    public void setMbrId(String mbrId) { this.mbrId = mbrId; }

    public String getRegrIp() { return regrIp; }

    public void setRegrIp(String regrIp) { this.regrIp = regrIp; }

    public String getMdlTkn() { return mdlTkn; }

    public void setMdlTkn(String mdlTkn) { this.mdlTkn = mdlTkn; }

    public String getTxSeqNo() { return txSeqNo; }

    public void setTxSeqNo(String txSeqNo) { this.txSeqNo = txSeqNo; }

    public String getMbrNm() { return mbrNm; }

    public void setMbrNm(String mbrNm) { this.mbrNm = mbrNm; }

    public String getBirthdy() { return birthdy; }

    public void setBirthdy(String birthdy) { this.birthdy = birthdy; }

    public String getEmpNo() { return empNo; }

    public void setEmpNo(String empNo) { this.empNo = empNo; }

    public String getCi() { return ci; }

    public void setCi(String ci) { this.ci = ci; }

    public String getRsltCd() { return rsltCd; }

    public void setRsltCd(String rsltCd) { this.rsltCd = rsltCd; }

    public String getRsltMsg() { return rsltMsg; }

    public void setRsltMsg(String rsltMsg) { this.rsltMsg = rsltMsg; }

    public Date getRegDt() { return regDt; }

    public void setRegDt(Date regDt) { this.regDt = regDt; }
}
