package software.blacknode.backend.domain.auth.type;

import me.hinsinger.projects.hinz.common.huid.HUID;

public interface AuthType {

	public static final AuthType PASSWORD_AUTHENTICATION = AuthType.of(
			HUID.fromString("00000000-0000-0000-0000-000000000001"),
			"Password Authentication",
			"Authentication using a password."
	);
	
	public static AuthType of(HUID id, String name, String description) {
		return new AuthType() {
			@Override
			public HUID getId() {
				return id;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getDescription() {
				return description;
			}
			
			@Override
			public boolean equals(Object obj) {
			    if (this == obj)
			        return true;
			    if (obj == null || !(obj instanceof AuthType))
			        return false;
			    
			    AuthType other = (AuthType) obj;
			    return this.getId().equals(other.getId());
			}

			@Override
	        public int hashCode() {
	            return getId().hashCode();
	        }
		};
	}
	
	HUID getId();
	String getName();
	String getDescription();
	
}
