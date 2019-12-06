package com.lyae.kcb.dao;

import com.benepia.frnt.kcb.vo.KcbLogVo;

public interface KcbDao {
    String selectKcbCertLogSeq();

    void insertLogKcb(KcbLogVo kcbLogVo);

    KcbLogVo selectKcbLogSeqGrpByTxSeqNo(String TX_SEQ_NO);
}
