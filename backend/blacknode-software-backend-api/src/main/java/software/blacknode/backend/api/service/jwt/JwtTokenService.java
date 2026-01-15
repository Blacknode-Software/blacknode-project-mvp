package software.blacknode.backend.api.service.jwt;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import me.hinsinger.hinz.common.huid.HUID;

@Service
public class JwtTokenService {

    private final long expiration;
    private final String issuer;
    private final SecretKey key;

    public JwtTokenService(SecretKey jwtSigningKey, 
    		@Value("${jwt.expiration}") long expiration,
            @Value("${jwt.issuer}") String issuer) {
        this.key = jwtSigningKey;
        this.expiration = expiration;
        this.issuer = issuer;
    }

    public AccessToken generateToken(@NonNull HUID accountId, @NonNull String email) {

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusMillis(expiration);

        String token = Jwts.builder()
                .setSubject(accountId.toString())
                .setIssuer(issuer)
                .setAudience("api")
                .claim("email", email)
                .claim("typ", "access")
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiresAt))
                .signWith(key)
                .compact();

        return AccessToken.builder()
                .token(token)
                .expiresAt(expiresAt)
                .build();
    }

    @Getter
    @Builder
    public static class AccessToken {
        private final String token;
        private final Instant expiresAt;
    }
}
