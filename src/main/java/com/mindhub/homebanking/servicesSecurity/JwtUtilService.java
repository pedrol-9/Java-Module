package com.mindhub.homebanking.servicesSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtilService {

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    private static final long EXPIRATION_TOKEN = 1000 * 60 * 60;

    // este metodo se utiliza en el JwtRequestFilter para extraer la payloadde la peticion
    public Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
    }

    // este metodo se utiliza en el JwtRequestFilter para extraer claims específicos del payload
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    // estos métodos se utilizan en JwtRequestFiler para extraer la info a la que apunta y poder autenticar
    public String extractUserName(String token) { return extractClaim(token, Claims::getSubject); }

    public Date extractExpiration(String token) { return extractClaim(token, Claims::getExpiration); }

    public Boolean isTokenExpired(String token) { return extractExpiration(token).before(new Date()); }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        claims.put("role", role);
        return createToken(claims, userDetails.getUsername());
    }
}
