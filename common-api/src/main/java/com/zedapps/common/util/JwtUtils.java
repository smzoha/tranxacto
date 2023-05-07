package com.zedapps.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * @author Shamah M Zoha
 * @since 05-May-23
 */

public class JwtUtils {

    public static String getToken(String username, List<String> roles, String secret) {
        return Jwts.builder()
                .setIssuer("com.zedapps.tranxacto")
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDate())
                .claim("username", username)
                .claim("roles", roles)
                .signWith(getKey(secret), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getUsername(String token, String secret) {
        return getClaims(token, secret).get("username", String.class);
    }

    @SuppressWarnings("unchecked")
    public static List<String> getRoles(String token, String secret) {
        return (List<String>) getClaims(token, secret).get("roles", List.class);
    }

    private static Key getKey(String secret) {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private static Date getExpirationDate() {
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(60);
        return Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private static Claims getClaims(String token, String secret) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
