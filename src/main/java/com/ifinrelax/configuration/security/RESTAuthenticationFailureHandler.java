package com.ifinrelax.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifinrelax.dto.message.ResponseMessageDTO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

/**
 * @author Timur Berezhnoi
 */
@Component
public class RESTAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String contentTypeKey = "Content-Type";
        String contentTypeValue = "application/json";

        response.setHeader(contentTypeKey, contentTypeValue);
        response.setStatus(SC_BAD_REQUEST);
        response.getWriter().print(new ObjectMapper().writeValueAsString(new ResponseMessageDTO("Bad cridentianls")));
        response.getWriter().flush();
    }
}