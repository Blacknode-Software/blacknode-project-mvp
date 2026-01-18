package software.blacknode.backend.api.config;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import software.blacknode.backend.domain.exception.BlacknodeException;

@Configuration
public class JwtConfiguration {

	@Bean
	public SecretKey jwtSigningKey(@Value("${jwt.secret}") String base64Secret, @Value("${jwt.development-auto-generate:false}") boolean developmentAutoGenerate) {
		if(developmentAutoGenerate) {
			base64Secret = Base64.getEncoder().encodeToString(
							Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
		}
		
		if(base64Secret == null || base64Secret.isBlank()) {
			throw new BlacknodeException("JWT secret key is not configured");
		}
		
		if(base64Secret.length() < 43) {
			throw new BlacknodeException("JWT secret key is too short. It must be at least 256 bits (43 base64 characters)");
		}
		
		if("this_is_not_a_safe_secret_change_it".equals(base64Secret)) {
			throw new BlacknodeException("JWT secret key is using the default unsafe value. Change it to a secure random value by setting the 'jwt.secret' environment variable.");
		}
		
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
