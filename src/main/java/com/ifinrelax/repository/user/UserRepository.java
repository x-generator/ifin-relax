package com.ifinrelax.repository.user;

import com.ifinrelax.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Timur Berezhnoi
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findOneByEmail(String email);
}