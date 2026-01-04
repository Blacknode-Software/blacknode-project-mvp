package software.blacknode.backend.api.config;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtConfiguration {

    @Bean
    public SecretKey jwtSigningKey(@Value("${jwt.secret}") String base64Secret) {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
    }

    @Bean
    public JwtParser jwtParser(SecretKey jwtSigningKey, @Value("${jwt.issuer}") String issuer) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSigningKey)
                .requireIssuer(issuer)
                .requireAudience("api")
                .build();
    }
}
