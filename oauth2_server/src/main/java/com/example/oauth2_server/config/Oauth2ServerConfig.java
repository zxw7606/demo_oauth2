package com.example.oauth2_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {


    /**
     * 声明服务储存客户信息的位置 ClientDetailsService
     * 如果没有配置 ClientDetailsService 那么就无法启动
     */


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        ClientDetailsServiceBuilder<InMemoryClientDetailsServiceBuilder>.ClientBuilder builder = clients.inMemory()
                .withClient("client1");

        builder.resourceIds("resources1")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("product", "rw")
                .authorities("ROLE_CLIENT")
                .secret("secret")
                .redirectUris("http://localhost:9998/9999/redirect")  //返回codke
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(3600);
    }


    @Bean
    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
//         需要使用 redis 的话，放开这里
        return new RedisTokenStore(redisConnectionFactory);
    }

    //    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
//            throws Exception {
//        if (this.tokenConverter != null) {
//            endpoints.accessTokenConverter(this.tokenConverter);
//        }
//        if (this.tokenStore != null) {
//            endpoints.tokenStore(this.tokenStore);
//        }
//        if (this.details.getAuthorizedGrantTypes().contains("password")) {
//            endpoints.authenticationManager(this.authenticationManager);
//        }
//    }


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);

//        endpoints.pathMapping("/oauth/confirm_access","/oauth/confirm_access");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        //允许表单认证

        oauthServer.allowFormAuthenticationForClients();
    }
}
