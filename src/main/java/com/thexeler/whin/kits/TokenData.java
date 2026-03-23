package com.thexeler.whin.kits;

import com.thexeler.whin.config.SecurityConfig;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

import java.util.Date;

@Getter
public class TokenData {
    private final String username;
    private final Date expiration;
    private final String token;

    public TokenData(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SecurityConfig.secretKey)).build();
        this.username = parser.parseClaimsJws(token).getBody().getSubject();
        this.expiration = parser.parseClaimsJws(token).getBody().getExpiration();
        this.token = token;
    }
}
