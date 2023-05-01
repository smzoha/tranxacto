package com.zedapps.txuser.service;

import com.zedapps.common.dto.LoginRequestDto;
import com.zedapps.common.dto.LoginResponseDto;
import com.zedapps.common.util.ResponseUtils;
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
    public LoginResponseDto save(LoginRequestDto loginRequestDto) {
        Login login = new Login(loginRequestDto);

        if (StringUtils.isNotBlank(loginRequestDto.getPlainPassword())) {
            login.setPassword(passwordEncoder.encode(loginRequestDto.getPlainPassword()));
        }

        loginRepository.save(login);

        return generateLoginResponse(login);
    }

    @Transactional
    public LoginResponseDto update(LoginRequestDto requestDto, Login originalLogin) {
        updateLoginProperties(requestDto, originalLogin);

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

    private void updateLoginProperties(LoginRequestDto requestDto, Login login) {
        if (!StringUtils.equals(login.getEmail(), requestDto.getEmail())) {
            login.setEmail(requestDto.getEmail());
        }

        if (StringUtils.isNotBlank(requestDto.getPlainPassword())
                && !passwordEncoder.matches(requestDto.getPlainPassword(), login.getPassword())) {
            login.setPassword(passwordEncoder.encode(requestDto.getPlainPassword()));
        }

        if (!StringUtils.equals(login.getFirstName(), requestDto.getFirstName())) {
            login.setFirstName(requestDto.getFirstName());
        }

        if (!StringUtils.equals(login.getLastName(), requestDto.getLastName())) {
            login.setLastName(requestDto.getLastName());
        }

        login.setRoles(requestDto.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet()));
    }
}
