package com.zedapps.txuser.service;

import com.zedapps.common.dto.LoginResponseDto;
import com.zedapps.common.util.ResponseUtils;
import com.zedapps.txuser.dto.LoginRegistrationDto;
import com.zedapps.txuser.entity.Login;
import com.zedapps.txuser.entity.enums.Role;
import com.zedapps.txuser.entity.enums.Status;
import com.zedapps.txuser.repository.LoginRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Shamah M Zoha
 * @since 01-May-23
 */

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<LoginResponseDto> getAllLogins() {
        return loginRepository.findAll()
                .stream()
                .map(LoginService::generateLoginResponse)
                .collect(Collectors.toList());
    }

    public List<LoginResponseDto> getActiveLogins() {
        return loginRepository.getActiveLogins()
                .stream()
                .map(LoginService::generateLoginResponse)
                .collect(Collectors.toList());
    }

    public Optional<Login> getLogin(String username) {
        return loginRepository.findByUsername(username);
    }

    public LoginResponseDto getLoginResponse(String username) {
        Optional<Login> login = getLogin(username);

        return login.map(LoginService::generateLoginResponse).orElse(null);
    }

    @Transactional
    public LoginResponseDto save(LoginRegistrationDto registrationDto) {
        Login login = new Login(registrationDto);

        if (StringUtils.isNotBlank(registrationDto.getPassword())) {
            login.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        }

        loginRepository.save(login);

        return generateLoginResponse(login);
    }

    @Transactional
    public LoginResponseDto update(LoginRegistrationDto registrationDto, Login originalLogin) {
        updateLoginProperties(registrationDto, originalLogin);

        loginRepository.save(originalLogin);

        return generateLoginResponse(originalLogin);
    }

    @Transactional
    public LoginResponseDto updateStatus(Login login, Status status) {
        login.setStatus(status);

        loginRepository.save(login);

        return generateLoginResponse(login);
    }

    private static LoginResponseDto generateLoginResponse(Login login) {
        return ResponseUtils.getLoginResponse(login.getUsername(), login.getEmail(), login.getFirstName(),
                login.getLastName(), login.getStatus().name(),
                login.getRoles().stream().map(Role::name).collect(Collectors.toSet()));
    }

    private void updateLoginProperties(LoginRegistrationDto registrationDto, Login login) {
        if (!StringUtils.equals(login.getEmail(), registrationDto.getEmail())) {
            login.setEmail(registrationDto.getEmail());
        }

        if (StringUtils.isNotBlank(registrationDto.getPassword())
                && !passwordEncoder.matches(registrationDto.getPassword(), login.getPassword())) {
            login.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        }

        if (!StringUtils.equals(login.getFirstName(), registrationDto.getFirstName())) {
            login.setFirstName(registrationDto.getFirstName());
        }

        if (!StringUtils.equals(login.getLastName(), registrationDto.getLastName())) {
            login.setLastName(registrationDto.getLastName());
        }

        login.setRoles(registrationDto.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet()));
    }
}
