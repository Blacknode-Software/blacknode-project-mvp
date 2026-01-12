package software.blacknode.backend.domain.task.assign.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.task.assign.TaskAssign;

public interface TaskAssignRepository {

	Optional<TaskAssign> findById(HUID organizationId, HUID id);
	
	List<TaskAssign> findAll(HUID organizationId);
	
	List<TaskAssign> findByTaskId(HUID organizationId, HUID taskId);
	
	List<TaskAssign> findByMemberId(HUID organizationId, HUID memberId);
	
	List<TaskAssign> findByTaskIds(HUID organizationId, Set<HUID> taskIds);
	
	List<TaskAssign> findByMemberIds(HUID organizationId, Set<HUID> memberIds);
	
	void save(HUID organizationId, TaskAssign taskAssign);
	
}
