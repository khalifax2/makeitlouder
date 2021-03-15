package com.makeitlouder.shared;

import com.makeitlouder.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Utils {

    public static String generateVerificationToken(String email) {
        return generateToken(email, new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME));
    }

    public static String generatePasswordResetToken(UUID userId)  {
        return generateToken(userId.toString(), new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME));
}

    public static String generateToken(String userId, Date expiration) {
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();

        return  token;
    }

    public static boolean hasTokenExpired(String token) {
        boolean hasExpired = false;

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstants.getTokenSecret())
                    .parseClaimsJws(token).getBody();

            Date tokenExpirationDate = claims.getExpiration();

            hasExpired = tokenExpirationDate.before(new Date());

        } catch (ExpiredJwtException e) {
            hasExpired = true;
        }

        return hasExpired;
    }

}
