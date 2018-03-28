package com.bumu.bran.admin;

import com.bumu.common.third.umengpush.Demo;
import org.junit.Test;

import static com.bumu.arya.common.Constants.*;

/**
 * iOS-不木钱包推送测试
 *
 * @author majun
 * @date 2018/3/2
 * @email 351264830@qq.com
 */
public class IOSBumuWalletPushTest {

    /**
     * 自定义播
     */
    @Test
    public void customizedCastTest() throws Exception {
        Demo demo = new Demo(UMENG_APP_KEY_IOS_BUMU_WALLET, UMENG_APP_MASTER_SECRET_IOS_BUMU_WALLET);
        demo.sendIOSCustomizedcast("61d7dfe85eb44d5ebc42ecd3843bbb58", UMENG_PUSH_ALIAS_TYPE_IOS_V3);
    }

    /**
     * 广播
     */
    @Test
    public void broadCastTest() throws Exception {
        Demo demo = new Demo(UMENG_APP_KEY_IOS_BUMU_WALLET, UMENG_APP_MASTER_SECRET_IOS_BUMU_WALLET);
        demo.sendIOSBroadcast();
    }
}
