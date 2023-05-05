package com.zedapps.txuser.config;

import com.zedapps.txuser.filter.JwtUsernamePasswordAuthFilter;
import com.zedapps.txuser.service.LoginDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Shamah M Zoha
 * @since 05-May-23
 */

@Configuration
public class SecurityConfig {

    @Autowired
    private LoginDetailService loginDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static String API_SECRET = "4E645267556B58703273357638792F413F4428472B4B6250655368566D597133";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/auth/**", "/user/login/save", "/user/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtUsernamePasswordAuthFilter(authProvider()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean("authProvider")
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(loginDetailService);

        return authProvider;
    }
}
