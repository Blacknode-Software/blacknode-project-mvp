package software.blacknode.backend.domain.auth.method.type;

import me.hinsinger.hinz.common.huid.HUID;

public interface AuthMethodType {

	public static AuthMethodType of(HUID id, String name, String description) {
		return new AuthMethodType() {
			
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
				if(this == obj) return true;
				if(obj == null || !(obj instanceof AuthMethodType)) return false;
				AuthMethodType other = (AuthMethodType) obj;
				return id.equals(other.getId());
			}
			
			@Override
			public int hashCode() {
				return id.hashCode();
			}
		};
	}
	
	HUID getId();
	String getName();
	String getDescription();
	
}
