package store.auth;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import store.account.AccountOut;

@Service
public class JwtService {

    @Value("${store.jwt.secretKey}")
    private String secretKey;

    public String generate(AccountOut account) {

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        // JwtParser parser = Jwts.parser().verifyWith(key).build();

        Date now = new Date();

        String jwt = Jwts.builder()
            .header()
            .and()
            .id(account.id())
            .issuer("Insper::PMA")
            .claims(Map.of(
                "email", account.email()
            ))
            .signWith(key)
            .subject(account.name())
            .notBefore(now)
            .expiration(new Date(now.getTime() + 1000 * 60 * 120)) // em milisegundos
            .compact();
        return jwt;

    }
    
}
