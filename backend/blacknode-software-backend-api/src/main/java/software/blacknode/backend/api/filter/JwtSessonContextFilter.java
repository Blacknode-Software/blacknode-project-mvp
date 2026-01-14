package software.blacknode.backend.api.filter;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.security.JwtAuthenticationToken;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.domain.session.context.SessionContext;
import software.blacknode.backend.domain.session.context.holder.SessionContextHolder;

@Component
@RequiredArgsConstructor
public class JwtSessonContextFilter extends OncePerRequestFilter {
	
	private static final Logger log = LoggerFactory.getLogger(JwtSessonContextFilter.class);
	
	private static final String AUTH_HEADER = "Authorization";
    private static final String ORG_HEADER = "X-Organization-Id";

	private final JwtParser jwtParser;
	
	private final MemberService memberService;
	
	private final SessionContextHolder sessionContextHolder;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain
		) throws ServletException, IOException {
		
		SessionContext context = SessionContext.empty();

        String authHeader = request.getHeader(AUTH_HEADER);
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
        	try {
	            String token = authHeader.substring(7);
	            Claims claims = jwtParser.parseClaimsJws(token).getBody();
	
	            var accountId = HUID.fromString(claims.getSubject());
	            
	            context = context.withAccountId(Optional.ofNullable(accountId));
	        
	            String orgHeader = request.getHeader(ORG_HEADER);
	            HUID organizationId = null;
	            HUID memberId = null;
	            
	            if (orgHeader != null && !orgHeader.isBlank()) {
	            	organizationId = HUID.fromString(orgHeader);
	            	
	            	var member = memberService.getByAccountIdOrThrow(accountId, organizationId);
	            	memberId = member.getId();
	            	
	                context = context.withOrganizationId(Optional.ofNullable(organizationId))
	                			 .withMemberId(Optional.ofNullable(memberId));
	                
	            }
	            
	            JwtAuthenticationToken authentication = new JwtAuthenticationToken(
	            	accountId, organizationId, memberId, token
	            );
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	            
        	} catch (ExpiredJwtException ex) {
        		log.debug("Expired JWT token provided: {}", ex.getMessage());
        	} catch (JwtException ex) {
        		log.debug("Invalid JWT token provided: {}", ex.getMessage());
        	} catch (IllegalArgumentException ex) {
        		log.debug("Malformed JWT token or HUID: {}", ex.getMessage());
        	} catch (Exception ex) {
        		log.warn("Unexpected error processing JWT token: {}", ex.getMessage());
        	}
        }

        sessionContextHolder.initialize(context);
        
        filterChain.doFilter(request, response);
    }

}