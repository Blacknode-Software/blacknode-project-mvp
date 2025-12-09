package software.blacknode.backend.domain.member.role;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.role.meta.MemberRoleAssociationMeta;
import software.blacknode.backend.domain.member.role.meta.create.MemberRoleOrganizationAssociationCreationMeta;
import software.blacknode.backend.domain.member.role.meta.delete.MemberRoleAssociationDeletionMeta;
import software.blacknode.backend.domain.modifier.create.Creatable;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.delete.Deletable;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;

@Getter
public class MemberRoleAssociation implements Creatable, Deletable {

	private HUID id;
	
	private MemberRoleAssociationMeta meta;
	
	private Timestamp creationTimestamp;
	private Timestamp deletationTimestamp;
	
	private HUID memberId;
	private HUID scopeId;
	private HUID roleId;
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		if(meta0.isEmpty()) BlacknodeException.throwWith("Creation meta must be provided when creating an member-role association.");
		
		this.id = HUID.random();
		this.meta = MemberRoleAssociationMeta.builder().build();
		
		var meta = meta0.get();
		
		if(meta instanceof MemberRoleOrganizationAssociationCreationMeta orgCreMeta) {
			this.memberId = orgCreMeta.getMemberId();
			this.roleId = orgCreMeta.getRoleId();
			this.scopeId = orgCreMeta.getOrganizationId();
			
			this.meta = this.meta.withScope(MemberRoleAssociationMeta.Scope.ORGANIZATION);
		}
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		if(meta0.isEmpty()) BlacknodeException.throwWith("Deletion meta must be provided when deleting an member-role association.");

		var meta = meta0.get();
		
		if(meta instanceof MemberRoleAssociationDeletionMeta deletionMeta) {
			// Currently no specific deletion logic
		}
		
		deletationTimestamp = Timestamp.now();
		
	}
}
