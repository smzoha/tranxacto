package com.zedapps.txuser.controller;

import com.zedapps.common.dto.LoginResponseDto;
import com.zedapps.txuser.dto.LoginRegistrationDto;
import com.zedapps.txuser.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shamah M Zoha
 * @since 07-May-23
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/save")
    public LoginResponseDto saveLogin(@RequestBody LoginRegistrationDto registrationDto) {
        return loginService.save(registrationDto);
    }
}
