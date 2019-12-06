package com.lyae.kcb.dao;

import com.benepia.admin.adminSessionDao.AdminSqlSessionDAO;
import com.benepia.frnt.kcb.vo.KcbLogVo;
import org.springframework.stereotype.Repository;

@Repository
public class KcbDaoImpl extends AdminSqlSessionDAO implements KcbDao {

    @Override
    public String selectKcbCertLogSeq() {
        return (String) this.selectOne("selectKcbCertLogSeq");
    }

    @Override
    public void insertLogKcb(KcbLogVo kcbLogVo) {
        this.insert("insertLogKcb", kcbLogVo);
    }

    @Override
    public KcbLogVo selectKcbLogSeqGrpByTxSeqNo(String TX_SEQ_NO) {
        return (KcbLogVo) this.selectOne("selectKcbLogSeqGrpByTxSeqNo", TX_SEQ_NO);
    }

}
