package software.blacknode.backend.api.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;

@Getter
public class JwtAuthenticationToken implements Authentication {

	private final HUID accountId;
	private final HUID organizationId;
	private final HUID memberId;
	private final String token;
	private boolean authenticated;

	public JwtAuthenticationToken(HUID accountId, HUID organizationId, HUID memberId, String token) {
		this.accountId = accountId;
		this.organizationId = organizationId;
		this.memberId = memberId;
		this.token = token;
		this.authenticated = true;
	}

	@Override
	public String getName() {
		return accountId != null ? accountId.toString() : null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return accountId;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}

	public boolean isOrganizationScoped() {
		return organizationId != null && memberId != null;
	}
}
