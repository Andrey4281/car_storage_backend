package ru.job4j.cars_storage.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

/**
 * Service for working with jwt tokken
 */
@Service
public class JwtTokenUtil implements Serializable {
    private final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);
    @Value("${token.accessTokenValiditySeconds}")
    private Long accessTokenValiditySeconds;

    @Value("${token.signingKey}")
    private String signingKey;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private  <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        log.debug("All claims will be");
        final Claims claims = getAllClaimsFromToken(token);
        log.debug("All claims {}:", claims);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String doGenerateToken(UserDetails user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("scopes", user.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("asemenov")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValiditySeconds *1000))
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }

}
