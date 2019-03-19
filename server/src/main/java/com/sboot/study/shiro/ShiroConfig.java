package com.sboot.study.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author faraway
 * @date 2019/3/12 11:19
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilter.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilter.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilter.setSuccessUrl("/index");

        shiroFilter.setUnauthorizedUrl("/401");

        /*
         ************添加shiro内置过滤器*************
         *
         * 常用的过滤器：
         *      anon：   无需认证(登录)即可访问
         *      authc：  必须认证(登录)才能访问
         *      user：   如果使用rememberMe的功能可以直接访问
         *      perms：  该资源必须得到资源权限才可以访问
         *      role：   该资源必须得到角色权限才可以访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();

        // 配置不会被拦截的链接 顺序判断
        //filterMap.put("/css/**", "anon");
        //filterMap.put("/js/**", "anon");

        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterMap.put("/logout", "logout");

        // "/**"必须放在最下边
        filterMap.put("/**", "authc");

        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;

    }

    /**
     * 创建SecurtyManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /* 开启shiro aop注解支持----开始*/
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /* 开启shiro aop注解支持----结束*/

    /**
     * ShiroDialect，用于在thymeleaf中使用shiro标签
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
