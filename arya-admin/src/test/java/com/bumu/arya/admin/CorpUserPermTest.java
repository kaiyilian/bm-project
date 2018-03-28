package com.bumu.arya.admin;

import com.bumu.arya.admin.corporation.result.CorpUserPermResult;
import com.bumu.arya.admin.corporation.service.impl.CorpUserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author majun
 * @date 2018/1/3
 * @email 351264830@qq.com
 */
public class CorpUserPermTest {

    private static Logger logger = LoggerFactory.getLogger(CorpUserPermTest.class);

    private CorpUserServiceImpl corpUserService = new CorpUserServiceImpl();

    @Test
    public void genTree() {
        List<CorpUserPermResult> list = new ArrayList<>();
        // 父节点
        CorpUserPermResult prospective = new CorpUserPermResult("1", "prospective:*");
        // 子节点
        CorpUserPermResult prospectiveC = new CorpUserPermResult("101", "prospective:create:*");
        CorpUserPermResult prospectiveR = new CorpUserPermResult("102", "prospective:query:*");
        CorpUserPermResult prospectiveU = new CorpUserPermResult("103", "prospective:update:*");
        CorpUserPermResult prospectiveD = new CorpUserPermResult("104", "prospective:delete:*");

        list.add(prospective);
        list.add(prospectiveC);
        list.add(prospectiveR);
        list.add(prospectiveU);
        list.add(prospectiveD);
        list = corpUserService.filter(list);
        CorpUserPermResult remove = list.remove(0);
        list.forEach(one -> Assert.assertEquals(remove.getId(), one.getParentId()));
    }
}
