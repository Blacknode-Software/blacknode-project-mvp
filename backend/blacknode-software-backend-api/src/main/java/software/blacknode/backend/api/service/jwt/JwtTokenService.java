package software.blacknode.backend.api.service.jwt;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import me.hinsinger.hinz.common.huid.HUID;

@Service
public class JwtTokenService {

	private final long expiration;
	
	private final String issuer;
    private final SecretKey key;

    public JwtTokenService(
            @Value("${jwt.secret}") String base64Secret,
            @Value("${jwt.expiration}") long expiration,
            @Value("${jwt.issuer}") String issuer
    )
    {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
        this.expiration = expiration;
        this.issuer = issuer;
    }
	
	public AccessToken generateToken(@NonNull HUID accountId, @NonNull String email) {
		var issuedAt = new Date();
		var expiration = new Date(issuedAt.getTime() + this.expiration);
		
        var token = Jwts.builder()
        		.setSubject(accountId.toUUID().toString())
        		.setIssuer(issuer)
        		.setAudience("api")
                .claim("email", email)
                .claim("typ", "access")
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
        
		return AccessToken.builder()
				.token(token)
				.expiresAt(expiration.toInstant())
				.build();
    }
	
	@Getter
	@Builder
	public static class AccessToken {
		private final String token;
		private final Instant expiresAt;
	}
}
