package software.blacknode.backend.api.controller.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionContextInterceptor implements HandlerInterceptor {

//    @Autowired
//    private SessionContext sessionContext;
//
//    @Autowired
//    private JWTService jwtService;
//
//    @Autowired
//    private MemberService memberService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        clearSessionContext();
//        String jwtToken = extractJwtToken(request);
//        if (jwtToken == null || !jwtService.isTokenValid(jwtToken)) {
//            return true;
//        }
//        HUID accountId = extractAccountId(jwtToken);
//        if (accountId == null) {
//            return true;
//        }
//        sessionContext.setAccountId(accountId);
//        handleOrganizationAndMember(request, accountId);
//        return true;
//    }
//
//    private void clearSessionContext() {
//        sessionContext.setAccountId(null);
//        sessionContext.setMemberId(null);
//        sessionContext.setOrganizationId(null);
//    }
//
//    private String extractJwtToken(HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            return authHeader.substring(7);
//        }
//        return null;
//    }
//
//    private HUID extractAccountId(String jwtToken) {
//        String accountIdStr = jwtService.extractAccountId(jwtToken);
//        if (accountIdStr != null && !accountIdStr.isBlank()) {
//            return HUID.fromString(accountIdStr);
//        }
//        return null;
//    }
//
//    private void handleOrganizationAndMember(HttpServletRequest request, HUID accountId) {
//        String organizationHeader = request.getHeader("X-Organization-Id");
//        if (organizationHeader == null || organizationHeader.isBlank()) {
//            return;
//        }
//        HUID organizationId = HUID.fromString(organizationHeader);
//        var member = memberService.getByAccountId(accountId, organizationId);
//        if (member != null) {
//            sessionContext.setOrganizationId(organizationId);
//            sessionContext.setMemberId(member.getId());
//        }
//    }
}
