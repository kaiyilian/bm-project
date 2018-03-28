package com.bumu.bran.admin.system.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Allen 2018-01-09
 **/
public class BumuShiroUserPermissionTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        Session session = SecurityUtils.getSubject().getSession();
        if (session == null) {
            System.out.println("WARN: 会话不存在");
            return super.doStartTag();
        }
        String permissions = (String) session.getAttribute("user_permissions");
        System.out.printf("用户权限集合：%s%n", permissions);
        return EVAL_BODY_INCLUDE;
    }
}
