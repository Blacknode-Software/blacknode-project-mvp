package software.blacknode.backend.infrastructure.member.association.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.member.association.entity.MemberAssociationEntity;

@Repository
public interface MemberAssociationEntityRepository extends JpaRepository<MemberAssociationEntity, UUID> {

	@Query("SELECT m FROM MemberAssociationEntity m WHERE m.id = :id AND m.organizationId = :organizationId AND m.state = :state")
	Optional<MemberAssociationEntity> queryByIdAndOrganizationIdAndState(
		@Param("id") UUID id, 
		@Param("organizationId") UUID organizationId, 
		@Param("state") EntityState state
	);

	@Query("SELECT m FROM MemberAssociationEntity m WHERE m.organizationId = :organizationId AND m.state = :state")
	List<MemberAssociationEntity> queryAllByOrganizationIdAndState(
		@Param("organizationId") UUID organizationId, 
		@Param("state") EntityState state
	);

	@Query("SELECT m FROM MemberAssociationEntity m WHERE m.organizationId = :organizationId AND m.memberId IN :memberIds AND m.state = :state")
	List<MemberAssociationEntity> queryByOrganizationIdAndMemberIdsAndState(
		@Param("organizationId") UUID organizationId, 
		@Param("memberIds") List<UUID> memberIds, 
		@Param("state") EntityState state
	);

	@Query("SELECT m FROM MemberAssociationEntity m WHERE m.organizationId = :organizationId AND m.memberId = :memberId AND m.state = :state")
	List<MemberAssociationEntity> queryByOrganizationIdAndMemberIdAndState(
		@Param("organizationId") UUID organizationId, 
		@Param("memberId") UUID memberId, 
		@Param("state") EntityState state
	);

	@Query("SELECT m FROM MemberAssociationEntity m WHERE m.organizationId = :organizationId AND m.memberId = :memberId AND m.scope = :scope AND m.state = :state")
	List<MemberAssociationEntity> queryByOrganizationIdAndMemberIdAndScopeAndState(
		@Param("organizationId") UUID organizationId, 
		@Param("memberId") UUID memberId, 
		@Param("scope") String scope, 
		@Param("state") EntityState state
	);

	@Query("SELECT m FROM MemberAssociationEntity m WHERE m.organizationId = :organizationId AND m.memberId = :memberId AND m.entityId = :entityId AND m.scope = :scope AND m.state = :state")
	Optional<MemberAssociationEntity> queryByOrganizationIdAndMemberIdAndScopeIdAndScopeAndState(
		@Param("organizationId") UUID organizationId, 
		@Param("memberId") UUID memberId, 
		@Param("entityId") UUID entityId, 
		@Param("scope") String scope, 
		@Param("state") EntityState state
	);
	
	@Query("SELECT m FROM MemberAssociationEntity m WHERE m.organizationId = :organizationId AND m.roleId = :roleId AND m.state = :state")
	List<MemberAssociationEntity> queryByOrganizationIdRoleIdAndState(
		@Param("organizationId") UUID organizationId,
		@Param("roleId") UUID roleId,
		@Param("state") EntityState state
	);
	
}
