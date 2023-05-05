package com.zedapps.txuser.service;

import com.zedapps.txuser.entity.Login;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Shamah M Zoha
 * @since 05-May-23
 */

@Service
public class LoginDetailService implements UserDetailsService {

    @Autowired
    private LoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Login> optionalLogin = loginService.getLogin(username);

        if (optionalLogin.isEmpty()) {
            throw new NoResultException("Invalid credentials passed to service!");
        }

        Login login = optionalLogin.get();

        return new User(login.getUsername(), login.getPassword(), login.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList());
    }
}
