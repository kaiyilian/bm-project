package com.bumu.arya.admin;

import com.bumu.arya.admin.misc.RealShowHttpHandler;
import com.bumu.arya.admin.misc.controller.command.RealShowCommand;
import com.bumu.arya.admin.misc.result.RealShowResult;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author majun
 * @date 2017/3/10
 */
public class RealShowTest {

    private static Logger logger = LoggerFactory.getLogger(RealShowTest.class);


    RealShowHttpHandler poster = new RealShowHttpHandler();

    @Test
    public void realShowRequest() throws IOException {
//		张琦,500221199001137650
        poster.setUrl("http://www.jztest.cc/JZSystem/jz/common/speedQuery.action");
        RealShowCommand param = new RealShowCommand("马俊", "320501198904095052");
        poster.post(param);
    }

    @Test
    public void realShowResponse() throws IOException {
        String result = "{\"body\":{\"orderNumber\":\"CN11489129883556210635\",\"data\":[{\"transactionType\":150,\"entity\":{\"id\":638,\"orderId\":1755,\"resCode\":0,\"resMsg\":\"查询无记录\",\"caseTime\":null,\"des\":null,\"createBy\":null,\"createTime\":1489129883815,\"updateBy\":null,\"updateTime\":1489129883815}}],\"paymentTradeNo\":\"6d439afa9ff74d0a873315c6aea6b598\",\"orderId\":1755},\"responseCode\":200}";
        RealShowResult realShowResult = RealShowResult.createByJson(result);
        logger.info("realShowResult: " + realShowResult.toString());
    }

    @Test
    public void signTest() {
        RealShowCommand param = new RealShowCommand("马俊", "320501198904095052");
        param.setPaymentTradeNo("61d8f17f6f3f4766ab26adb619ed6b99");

        poster.sign("companyName=苏州不木科技有限公司&hrEmail=27568294@qq.com&hrMobile=13776012606&hrUserName=王宇星&identityCard=320501198904095052&mobile=13776012606&paymentTradeNo=61d8f17f6f3f4766ab26adb619ed6bfb&transactionType=150&userName=马俊&secret=78a6a2098943502142660925219bcebc");
//		a8a5301f9d9a1e027e0ec2f12a3d11c2
//		logger.info("sign: " + sign);
    }
}
