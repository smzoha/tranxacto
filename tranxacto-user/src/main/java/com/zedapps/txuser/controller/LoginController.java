package com.zedapps.txuser.controller;

import com.zedapps.common.dto.LoginRequestDto;
import com.zedapps.common.dto.LoginResponseDto;
import com.zedapps.txuser.entity.Login;
import com.zedapps.txuser.entity.enums.Status;
import com.zedapps.txuser.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Shamah M Zoha
 * @since 01-May-23
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/all")
    public List<LoginResponseDto> getAllLogins() {
        return loginService.getAllLogins();
    }

    @GetMapping("/active")
    public List<LoginResponseDto> getActiveLogins() {
        return loginService.getActiveLogins();
    }

    @GetMapping("/{username}")
    public LoginResponseDto getLoginByUsername(@PathVariable String username) {
        LoginResponseDto loginResponse = loginService.getLoginResponse(username);

        if (Objects.isNull(loginResponse)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No login was found for username!");
        }

        return loginResponse;
    }

    @PostMapping("/update/{username}")
    public LoginResponseDto updateLogin(@RequestBody LoginRequestDto requestDto, @PathVariable String username) {
        return loginService.update(requestDto, getLogin(username));
    }

    @PostMapping("/updateStatus/{username}/{status}")
    public LoginResponseDto updateLoginStatus(@PathVariable String username, @PathVariable Status status) {
        return loginService.updateStatus(getLogin(username), status);
    }

    private Login getLogin(String username) {
        Optional<Login> login = loginService.getLogin(username);

        if (login.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No login was found for username!");
        }

        return login.get();
    }
}
