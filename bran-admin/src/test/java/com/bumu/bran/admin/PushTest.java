package com.bumu.bran.admin;

import com.bumu.common.third.umengpush.Demo;
import org.junit.Test;

import static com.bumu.arya.common.Constants.*;

/**
 * @author majun
 * @date 2018/1/31
 * @email 351264830@qq.com
 */
public class PushTest {

    @Test
    public void pushAndroidTest() throws Exception {
        Demo demo = new Demo(UMENG_APP_KEY_ANDROID_PROD, UMENG_APP_MASTER_SECRET_ANDROID_PROD);
        demo.sendAndroidCustomizedcast();
    }


    @Test
    public void pushIOSTest() throws Exception {
        Demo demo = new Demo(UMENG_APP_KEY_IOS_PROD, UMENG_APP_MASTER_SECRET_IOS_PROD);
        demo.sendIOSCustomizedcast();
    }
}
