package com.zedapps.txuser.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zedapps.common.dto.LoginRequestDto;
import com.zedapps.common.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author Shamah M Zoha
 * @since 05-May-23
 */

public class JwtUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationProvider authProvider;
    private final ObjectMapper objectMapper;
    private final String secret;

    public JwtUsernamePasswordAuthFilter(AuthenticationProvider authProvider, ObjectMapper objectMapper, String secret) {
        this.authProvider = authProvider;
        this.objectMapper = objectMapper;
        this.secret = secret;

        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/authenticate", HttpMethod.POST));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            LoginRequestDto requestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(requestDto.getUsername(),
                    requestDto.getPlainPassword(), Collections.emptyList());

            return authProvider.authenticate(token);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String username = auth.getName();
        List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        String token = JwtUtils.getToken(username, roles, secret);

        response.addHeader("Authorization", "Bearer " + token);

        setResponseBodyOnSuccess(response, username, token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
    }

    private void setResponseBodyOnSuccess(HttpServletResponse response, String username, String token) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        objectMapper.writeValue(response.getWriter(), new TokenResponse(username, token, System.currentTimeMillis()));
    }

    record TokenResponse(String username, String token, long timestamp) {
    }
}
