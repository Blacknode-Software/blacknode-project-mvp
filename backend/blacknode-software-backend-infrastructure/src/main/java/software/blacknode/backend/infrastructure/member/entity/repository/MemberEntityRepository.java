package software.blacknode.backend.infrastructure.member.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.member.entity.MemberEntity;

public interface MemberEntityRepository extends JpaRepository<MemberEntity, UUID> {

	@Query("SELECT m FROM MemberEntity m WHERE m.organizationId = :organizationId AND m.id = :id AND m.state = :state")
	Optional<MemberEntity> queryByIdAndOrganizationIdAndState(@Param("organizationId") UUID organizationId, @Param("id") UUID id, @Param("state") EntityState state);

	@Query("SELECT m FROM MemberEntity m WHERE m.organizationId = :organizationId AND m.state = :state")
	List<MemberEntity> queryByOrganizationIdAndState(@Param("organizationId") UUID organizationId, @Param("state") EntityState state);
	
	@Query("SELECT m FROM MemberEntity m WHERE m.organizationId = :organizationId AND m.id IN :ids AND m.state = :state")
	List<MemberEntity> queryAllByIdInOrganizationIdAndState(@Param("organizationId") UUID organizationId, @Param("ids") List<UUID> ids, @Param("state") EntityState state);
	
	@Query("SELECT m FROM MemberEntity m WHERE m.organizationId = :organizationId AND m.accountId = :accountId AND m.state = :state")
	Optional<MemberEntity> queryByOrganizationIdAndAccountIdAndState(@Param("organizationId") UUID organizationId, @Param("accountId") UUID accountId, @Param("state") EntityState state);
}
