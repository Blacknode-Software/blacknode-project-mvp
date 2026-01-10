package software.blacknode.backend.api.controller.invite.response.content.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Mappings({
	@Mapping(target = "id", source = "invite.id"),
	
	@Mapping(target = "token", source = "invite.token"),
	
	@Mapping(target = "email", source = "invite.meta.email"),
	@Mapping(target = "expiresAt", source = "invite.meta.expiresAt"),
	
	@Mapping(target = "revoked", source = "invite.meta.revoked"),
	
	@Mapping(target = "claimedAt", source = "invite.claimedAt"),
	@Mapping(target = "claimedBy", source = "invite.claimedBy"),
})
public @interface InviteResponseContentMapping {

}
