package software.blacknode.backend.infrastructure.entity.impl;

import java.util.UUID;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.infrastructure.entity.InfrastructureEntity;
import software.blacknode.backend.infrastructure.entity.state.EntityState;

@SuperBuilder
@Getter
@MappedSuperclass
@Access(AccessType.FIELD)
@NoArgsConstructor
public class BaseInfrastructureEntity implements InfrastructureEntity {
	
	@Id
	@Column(name = "id")
	protected UUID id;
	
	@Column(name = "state")
	@Builder.Default
	protected EntityState state = EntityState.NOT_DEFINED;

}
