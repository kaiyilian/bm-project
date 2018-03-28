package com.bumu.bran.admin.system.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * Shiro 权限标签，控制标签内的内容在用户获得标签指定的权限或者其子权限的情况下都能显示。
 *
 * @author Allen 2018-01-03
 **/
public class BumuShiroHasPermissionTag extends PermissionTag {

    @Override
    protected boolean isPermitted(String p) {
        Session session = getSubject().getSession();
        if (session == null) {
            System.out.println("WARN: 会话不存在");
            return false;
        }
        String permissions = (String) session.getAttribute("user_permissions");
        System.out.printf("菜单权限：%s%n", p);
        String[] split = StringUtils.split(permissions, ',');
        if (split == null) {
            return false;
        }
        String menuPermPrefix = StringUtils.substringBefore(p.trim(), ":*");
        for (String perm : split) {
            // 用户的权限属于菜单要求权限的子权限
            if (perm.trim().startsWith(menuPermPrefix)) {
                System.out.printf("  可以访问权限 %s 的菜单%n", menuPermPrefix);
                return true;
            }
            String userPermPrefix = StringUtils.substringBefore(perm.trim(), ":*");
            // 用户的权限包含菜单要求的权限
            if (p.trim().startsWith(userPermPrefix)) {
                System.out.printf("  可以访问权限 %s 的菜单%n", menuPermPrefix);
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean showTagBody(String p) {
        return isPermitted(p);
    }
}
