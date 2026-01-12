package software.blacknode.backend.infrastructure.task.assign.entity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import software.blacknode.backend.infrastructure.entity.state.EntityState;
import software.blacknode.backend.infrastructure.task.assign.entity.TaskAssignEntity;

@Repository
public interface TaskAssignEntityRepository extends JpaRepository<TaskAssignEntity, UUID> {

	@Query("SELECT t FROM TaskAssignEntity t WHERE t.id = :id AND t.organizationId = :organizationId AND t.state = :state")
	Optional<TaskAssignEntity> queryByIdAndOrganizationIdAndState(
			@Param("id") UUID id, 
			@Param("organizationId") UUID organizationId,
			@Param("state") EntityState state
	);
	
	@Query("SELECT t FROM TaskAssignEntity t WHERE t.taskId = :taskId AND t.memberId = :memberId AND t.organizationId = :organizationId AND t.state = :state")
	Optional<TaskAssignEntity> queryByOrganizationIdAndTaskIdAndMemberIdAndState(
			@Param("organizationId") UUID organizationId,
			@Param("taskId") UUID taskId,
			@Param("memberId") UUID memberId,
			@Param("state") EntityState state
	);
	
	@Query("SELECT t FROM TaskAssignEntity t WHERE t.id IN :ids AND t.organizationId = :organizationId AND t.state = :state")
	List<TaskAssignEntity> queryByOrganizationIdAndIdInAndState(
			@Param("organizationId") UUID organizationId,
			@Param("ids") List<UUID> ids,
			@Param("state") EntityState state
	);
	
	@Query("SELECT t FROM TaskAssignEntity t WHERE t.taskId = :taskId AND t.organizationId = :organizationId AND t.state = :state")
	List<TaskAssignEntity> queryByOrganizationIdAndTaskIdAndState(
			@Param("organizationId") UUID organizationId,
			@Param("taskId") UUID taskId,
			@Param("state") EntityState state
	);
	
	@Query("SELECT t FROM TaskAssignEntity t WHERE t.memberId = :memberId AND t.organizationId = :organizationId AND t.state = :state")
	List<TaskAssignEntity> queryByOrganizationIdAndMemberIdAndState(
			@Param("organizationId") UUID organizationId,
			@Param("memberId") UUID memberId,
			@Param("state") EntityState state
	);
	
	@Query("SELECT t FROM TaskAssignEntity t WHERE t.taskId IN :tasksIds AND t.organizationId = :organizationId AND t.state = :state")
	List<TaskAssignEntity> queryByOrganizationIdAndTaskIdInAndState(
			@Param("organizationId") UUID organizationId,
			@Param("tasksIds") List<UUID> taskIds,
			@Param("state") EntityState state
	);
	
	@Query("SELECT t FROM TaskAssignEntity t WHERE t.memberId IN :membersIds AND t.organizationId = :organizationId AND t.state = :state")
	List<TaskAssignEntity> queryByOrganizationIdAndMemberIdInAndState(
			@Param("organizationId") UUID organizationId,
			@Param("membersIds") List<UUID> memberIds,
			@Param("state") EntityState state
	);
	
	@Query("SELECT t FROM TaskAssignEntity t WHERE t.organizationId = :organizationId AND t.state = :state")
	List<TaskAssignEntity> queryAllByOrganizationIdAndState(
			@Param("organizationId") UUID organizationId,
			@Param("state") EntityState state
	);

}
