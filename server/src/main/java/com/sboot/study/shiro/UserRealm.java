package com.sboot.study.shiro;

import com.sboot.study.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author faraway
 * @date 2019/3/12 11:21
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 执行认证逻辑
     *
     * @param authenticationToken token信息
     * @return AuthenticationInfo
     * @throws AuthenticationException 异常信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("执行认证逻辑");

        //1.获取用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        //2.到数据库中匹配
        User user = userService.selectUserByUsername(username);

        //3.判断用户
        if (user == null) {
            //用户不存在 shiro会自动抛出UnknownAccountException
            return null;
        }
        //4.判断密码：第一个参数为user对象；第二个参数为用户密码；第三个参数为当前realm对象的真实名字
        //return new SimpleAuthenticationInfo("", user.getPassword(), "");
        String realName = getName();
        return new SimpleAuthenticationInfo(user, user.getPassword(), realName);

    }

    /**
     * 执行授权逻辑
     *
     * @param principalCollection 执行授权逻辑
     * @return null
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("执行授权逻辑");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        //这里getPrincipal就是第48行的第一个参数，49行不存该参数，这里就get不到
        User user = (User)subject.getPrincipal();
        //获取当前登陆用户的权限
        info.addStringPermission(user.getAuth());
        return info;

    }
}
