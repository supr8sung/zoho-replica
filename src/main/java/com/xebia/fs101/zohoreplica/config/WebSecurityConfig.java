package com.xebia.fs101.zohoreplica.config;

import com.xebia.fs101.zohoreplica.exception.CustomAuthenticationFailureHandler;
import com.xebia.fs101.zohoreplica.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.concurrent.TimeUnit;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    //@formatter:off

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .authorizeRequests()
                .antMatchers("/zoho","/reaources/**","/signup","/index","/css/*","/js/*").permitAll()
                .antMatchers("/zoho/user/add").permitAll()
                .antMatchers("/zoho/valid/**").permitAll()
                .antMatchers("/kafka/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .failureHandler(customAuthenticationFailureHandler())
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/",true)
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("somethingsecured")
                .and()
                .logout().logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login")
                    .and()
                    .httpBasic();
    }
    //@formatter:on


    @Bean
    @Override
    protected UserDetailsService userDetailsService() {

        return new CustomUserDetailService();
    }



    @Bean
    public RoleHierarchyImpl roleHierarchy() {

        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER\n"
                + "ROLE_ADMIN > ROLE_EMPLOYEE");
        return roleHierarchy;
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}
