package com.ifinrelax.service.user;

import com.ifinrelax.dto.user.UserRegistrationDTO;
import com.ifinrelax.entity.role.Role;
import com.ifinrelax.entity.user.User;
import com.ifinrelax.exception.UserAlreadyExistException;
import com.ifinrelax.repository.user.UserRepository;
import com.ifinrelax.service.currency.CurrencyService;
import com.ifinrelax.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static com.ifinrelax.constant.ResponseMessage.EMAIL_IN_USE;
import static com.ifinrelax.constant.ResponseMessage.USER_NOT_FOUND;

/**
 * @author Timur Berezhnoi
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CurrencyService currencyService;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, CurrencyService currencyService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.currencyService = currencyService;
    }

    public User createUser(UserRegistrationDTO userDTO) throws IllegalArgumentException, UserAlreadyExistException {
        checkIfUserExist(userDTO.getEmail());
        return userRepository.save(new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), new BCryptPasswordEncoder().encode(userDTO.getPassword()),
                new HashSet<Role>() {{add(roleService.findByRoleName("ROLE_USER"));}}, currencyService.findCurrencyById(userDTO.getCurrency().getId())));
    }

    public User getUser(String email) {
        User user = userRepository.findOneByEmail(email);
        if(user == null) {
            throw new IllegalStateException(USER_NOT_FOUND.getMessage());
        } else {
            return user;
        }
    }

    private void checkIfUserExist(String email) throws UserAlreadyExistException {
        if(userRepository.findOneByEmail(email) != null) {
            throw new UserAlreadyExistException(EMAIL_IN_USE.getMessage());
        }
    }
}