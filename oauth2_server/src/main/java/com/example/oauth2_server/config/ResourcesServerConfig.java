package com.example.oauth2_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


@EnableResourceServer
@Configuration
@Order(6)
public class ResourcesServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("resource1").stateless(true);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                // Since we want the protected resources to be accessible in the UI as well we need
                // session creation to be allowed (it's disabled by default in 2.0.6)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/product/**").access("#oauth2.hasScope('product') and #oauth2.hasScope('rw') and hasRole('ROLE_USER')")
                .antMatchers("/order/**").authenticated();//配置order访问控制，必须认证过后才可以访问
        // @formatter:on
    }


//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/order/**").authenticated();//配置order访问控制，必须认证过后才可以访问
//    }

}
