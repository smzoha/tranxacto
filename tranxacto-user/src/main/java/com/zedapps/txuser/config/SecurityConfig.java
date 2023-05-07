package com.zedapps.txuser.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zedapps.txuser.filter.JwtUsernamePasswordAuthFilter;
import com.zedapps.txuser.service.LoginDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:secret.properties")
public class SecurityConfig {

    @Autowired
    private LoginDetailService loginDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${api.auth.secret}")
    private String API_SECRET;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/auth/**", "/user/login/save", "/user/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtUsernamePasswordAuthFilter(authProvider(), objectMapper, API_SECRET))
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
