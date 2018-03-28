package com.bumu.arya.admin.model.dao;

import com.bumu.arya.Utils;
import com.bumu.arya.common.OperateConstants;
import com.bumu.arya.model.SysJournalRepository;
import com.bumu.arya.model.document.SysJournalDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

//            /WEB-INF/ac_arya_admin.xml,
//                    /WEB-INF/ac_arya_admin_shiro.xml,
//                    /WEB-INF/ac_trx.xml,
//                    /WEB-INF/ac_security.xml,
//                    /WEB-INF/ac_bran_security.xml,
//                    /WEB-INF/ac_mybatis.xml
//                    classpath*:ac_mail.xml
//                    classpath*:ac_common_schedule.xml,
//                    classpath*:ac_common_sms.xml
//                    classpath*:ac_datasource.xml,
//                    classpath*:ac_mongo.xml,
//                    classpath*:ac_captcha.xml,
//                    classpath*:ac_arya_shiro.xml
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/ac_mongo.xml"})
public class SysJournalRepositoryTest {

    @Autowired
    SysJournalRepository sysLogDao;

    @Test
    public void test() {
        SysJournalDocument sysj = new SysJournalDocument();
        sysj.setId(Utils.makeUUID());
        sysj.setOperatorId(Utils.makeUUID());
        sysj.setOpLoginName("admin");
        sysj.setOpRealName("Admin");
        sysj.setOpSuccess(1);
        sysj.setOpType(OperateConstants.CORP_UPDATE);
        sysj.setCreateTime(new Date());
        sysLogDao.log(sysj);
    }
}
