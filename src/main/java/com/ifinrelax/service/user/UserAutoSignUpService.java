package com.ifinrelax.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * The service is supposed for autologin after succes registration.
 *
 * @author Timur Berezhnoi
 */
@Service
public class UserAutoSignUpService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserAutoSignUpService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public void autologin(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(authenticationToken);

        if(authenticationToken.isAuthenticated()) {
            getContext().setAuthentication(authenticationToken);
        }
    }
}