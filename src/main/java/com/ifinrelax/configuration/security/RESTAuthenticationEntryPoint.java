package com.ifinrelax.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifinrelax.dto.message.ResponseMessageDTO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * @author Timur Berezhnoi
 */
@Component
public class RESTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws
            IOException, ServletException {

        String contentTypeKey = "Content-Type";
        String contentTypeValue = "application/json";

        response.setHeader(contentTypeKey, contentTypeValue);
        response.getWriter().append(new ObjectMapper().writeValueAsString(new ResponseMessageDTO("Unauthorized")));
        response.setStatus(SC_UNAUTHORIZED);
        response.getWriter().flush();
    }
}