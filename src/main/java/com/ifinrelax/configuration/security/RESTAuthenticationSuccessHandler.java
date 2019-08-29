package com.ifinrelax.configuration.security;

import com.ifinrelax.entity.currency.Currency;
import com.ifinrelax.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * @author Timur Berezhnoi
 */
@Component
public class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        clearAuthenticationAttributes(request);
        response.addCookie(initLoginCookies(authentication));
        response.setStatus(SC_OK);
        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
        response.getWriter().flush();
    }

    private Cookie initLoginCookies(Authentication authentication) {
        Currency currency = userService.getUser(authentication.getName()).getCurrency();
        Cookie cookie = new Cookie("userCurrency", currency.getCode());
        cookie.setMaxAge(86400 * 7);
        return cookie;
    }
}