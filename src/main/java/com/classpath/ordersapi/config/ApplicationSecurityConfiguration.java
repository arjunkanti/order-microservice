package com.classpath.ordersapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login/**", "/register/**", "/logout/**", "/contact-us/**", "/h2-console/**")
                    .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/orders/**")
                    .hasAnyAuthority("ROLE_Everyone", "ROLE_super_admins", "ROLE_admins")
                .antMatchers(HttpMethod.POST, "/api/v1/orders/**")
                    .hasAnyAuthority("ROLE_super_admins", "ROLE_admins")
                .antMatchers(HttpMethod.DELETE, "/api/v1/orders/**")
                    .hasAuthority("ROLE_super_admins")
                .anyRequest()
                    .authenticated()
                .and()
                    .oauth2ResourceServer()
                .jwt();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        final JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("groups");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}