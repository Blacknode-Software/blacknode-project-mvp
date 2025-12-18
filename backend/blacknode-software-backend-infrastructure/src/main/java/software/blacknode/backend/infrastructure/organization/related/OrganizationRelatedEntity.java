package software.blacknode.backend.infrastructure.organization.related;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.infrastructure.entity.impl.BaseEntity;

@SuperBuilder
@Getter
@MappedSuperclass
public class OrganizationRelatedEntity extends BaseEntity {

	@Id
	@Column(name = "organization_id")
    private UUID organizationId;
	
}
