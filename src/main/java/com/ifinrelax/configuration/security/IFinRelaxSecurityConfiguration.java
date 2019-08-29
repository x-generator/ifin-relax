package com.ifinrelax.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.ifinrelax.constant.Route.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.BeanIds.AUTHENTICATION_MANAGER;

/**
 * @author Timur Berezhnoi
 */
@Configuration
@EnableWebSecurity
public class IFinRelaxSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RESTAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private RESTAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private RESTAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private RESTLogoutSuccessHandler restLogoutSuccessHandler;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder(11));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Set remember me expiration date to a week.
        final int rememberMeExpiration = 86400 * 7;

        http.csrf()
                .disable()
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
            .formLogin()
                .loginPage(SIGN_IN)
                .usernameParameter("email")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
            .logout()
                .logoutUrl(SIGN_OUT)
                .logoutSuccessHandler(restLogoutSuccessHandler)
                .deleteCookies("userCurrency")
                .and()
            .authorizeRequests()
                .antMatchers(GET, USER_EXPENSES).authenticated()
                .antMatchers(GET, EXPENSE_STATISTIC).authenticated()
                .antMatchers(GET, EXPENSE_STATISTIC_YEARS).authenticated()
                .antMatchers(POST, EXPENSE).authenticated()
                .antMatchers(DELETE, EXPENSE + ID_PATH_VARIABLE).authenticated()
            .anyRequest()
                .permitAll()
            .and()
                .rememberMe()
                .key("rem-me-key")
                .rememberMeParameter("rememberMe")
                .rememberMeCookieName("remember_me_token")
                .tokenValiditySeconds(rememberMeExpiration)
                .alwaysRemember(true);
    }

    @Bean(name = AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}