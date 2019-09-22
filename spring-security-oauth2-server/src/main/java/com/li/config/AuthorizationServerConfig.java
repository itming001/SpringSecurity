package com.li.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

//授权服务器
@Configuration
//开启springSecurity授权
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 替换datasource的数据源
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置token数据源
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //将tocken放入数据库
        endpoints.tokenStore(tokenStore());
    }

    /**
     * 从数据库读取客户端配置
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailService(){
        return new JdbcClientDetailsService(dataSource());
    }
    /**
     * 配置客户端
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //基于内存配置
        /*clients.inMemory()
                //客户端的名称
                .withClient("client")
                //相当于密码，必须加密
                .secret(passwordEncoder.encode("secret"))
                //设置授权类型为授权码
                .authorizedGrantTypes("authorization_code")
                //设置授权范围
                .scopes("app")
                //生成code的回调地址
                .redirectUris("https://www.baidu.com");*/
        //基于数据库配置
        clients.withClientDetails(clientDetailService());
    }
}
