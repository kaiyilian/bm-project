package com.bumu.bran.admin;

import com.bumu.SysUtils;
import org.junit.Assert;
import org.junit.Test;

public class UserPasswordTest {

    @Test
    public void md5Test(){
        Assert.assertEquals("1f1bba2ba172d5c4502684e87f4fa648",SysUtils.encryptPassword("201802"));
    }
}
