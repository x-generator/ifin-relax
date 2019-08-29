package com.ifinrelax.controller.user;

import com.ifinrelax.dto.user.UserRegistrationDTO;
import com.ifinrelax.entity.user.User;
import com.ifinrelax.service.user.UserAutoSignUpService;
import com.ifinrelax.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

import static com.ifinrelax.constant.Route.SIGN_UP;
import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Timur Berezhnoi
 */
@RestController
public class UserRegistrationController {

    private final UserService userService;
    private final UserAutoSignUpService userAutoSignUpService;

    @Autowired
    public UserRegistrationController(UserService userService, UserAutoSignUpService userAutoSignUpService) {
        this.userService = userService;
        this.userAutoSignUpService = userAutoSignUpService;
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<User> signUp(@Valid @RequestBody UserRegistrationDTO user, HttpServletResponse httpServletResponse) {
        User createdUser = userService.createUser(user);
        ResponseEntity<User> responseEntity = ResponseEntity.status(CREATED).body(createdUser);

        Cookie cookie = new Cookie("userCurrency", createdUser.getCurrency().getCode());
        cookie.setMaxAge(86400 * 7);
        httpServletResponse.addCookie(cookie);

        userAutoSignUpService.autologin(user.getEmail(), user.getPassword());
        return responseEntity;
    }

    // TODO: this method is temporary sollution, so it needs to be reomved after TOKEN authentication (OAuth 1/2) will be provided.
    @GetMapping("/isUserAuthenticated")
    public ResponseEntity<Map<String, Boolean>> isUserAuthenticated(Principal principal) {
        return ResponseEntity.status(OK).body(singletonMap("isAuthenticated", principal != null));
    }
}