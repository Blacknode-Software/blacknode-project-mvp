package software.blacknode.backend.domain.member.role.repository;

import java.util.List;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.role.MemberRoleAssociation;

public interface MemberRoleRepository {

	public List<MemberRoleAssociation> getByMemberId(HUID memberId);
	
	public MemberRoleAssociation getByMemberIdAndScopeId(HUID memberId, HUID scopeId);
	
	public void save(MemberRoleAssociation association);
}
