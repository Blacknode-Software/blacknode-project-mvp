package software.blacknode.backend.infrastructure.organization.related;

import java.util.UUID;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.infrastructure.entity.impl.BaseInfrastructureEntity;

@SuperBuilder
@Getter
@MappedSuperclass
@Access(AccessType.FIELD)
@NoArgsConstructor
public class OrganizationRelatedEntity extends BaseInfrastructureEntity {

	@Column(name = "organization_id")
	protected UUID organizationId;
	
}
