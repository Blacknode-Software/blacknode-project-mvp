package software.blacknode.backend.infrastructure.entity.impl;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.infrastructure.entity.InfrastructureEntity;
import software.blacknode.backend.infrastructure.entity.state.EntityState;

@SuperBuilder
@Getter
@MappedSuperclass
public class BaseEntity implements InfrastructureEntity {
	
	@Id
	@Column(name = "id")
    private UUID id;
	
	@Column(name = "state")
	@Builder.Default
	private EntityState state = EntityState.NOT_DEFINED;

}
