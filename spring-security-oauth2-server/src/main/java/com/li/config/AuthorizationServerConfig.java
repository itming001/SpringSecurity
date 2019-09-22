package com.li.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import javax.annotation.Resource;

//授权服务器
@Configuration
//开启springSecurity授权
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    /**
     * 配置客户端
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //客户端的名称
                .withClient("client")
                //相当于密码，必须加密
                .secret(passwordEncoder.encode("secret"))
                //设置授权类型为授权码
                .authorizedGrantTypes("authorization_code")
                //设置授权范围
                .scopes("app")
                //生成code的回调地址
                .redirectUris("https://www.baidu.com");
    }
}
