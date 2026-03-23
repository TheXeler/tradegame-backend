package com.thexeler.whin.kits;

import com.thexeler.whin.config.SecurityConfig;
import com.thexeler.whin.entity.userdata.User;
import com.thexeler.whin.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public enum TokenKits {
    INSTANCE;

    public static String generateToken(UserRepository repo, User user) {
        Date now = new Date();

        String token = Jwts.builder().setClaims(Jwts.claims().setSubject(user.getUsername())).setIssuedAt(now).setExpiration(new Date(now.getTime() + 1800000)) // 30分钟有效期
                .signWith(Keys.hmacShaKeyFor(SecurityConfig.secretKey), SignatureAlgorithm.HS256).compact();

        user.setLastToken(token);
        repo.save(user);

        return token;
    }

    public static boolean verifyToken(TokenData token) {
        try {
            return token.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean verifyToken(TokenData token, String username) {
        try {
            if (verifyToken(token)) {
                return username.equals(token.getUsername());
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
