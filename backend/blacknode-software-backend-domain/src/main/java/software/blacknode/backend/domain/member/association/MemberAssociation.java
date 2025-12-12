package software.blacknode.backend.domain.member.association;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.association.meta.MemberAssociationMeta;
import software.blacknode.backend.domain.member.association.meta.create.MemberChannelAssociationCreationMeta;
import software.blacknode.backend.domain.member.association.meta.create.MemberOrganizationAssociationCreationMeta;
import software.blacknode.backend.domain.member.association.meta.create.MemberProjectAssociationCreationMeta;
import software.blacknode.backend.domain.member.association.meta.delete.MemberAssociationDeletionMeta;
import software.blacknode.backend.domain.modifier.create.Creatable;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.delete.Deletable;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;

public class MemberAssociation implements Creatable, Deletable {

	@Getter private HUID id;
	
	@Getter private MemberAssociationMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
	@Getter private HUID memberId;
	@Getter private HUID scopeId;
	@Getter private HUID roleId;
	
	@Getter private HUID organizationId;
	
	public MemberAssociation(HUID organizationId) {
		this.organizationId = organizationId;
	}
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		if(meta0.isEmpty()) throw new BlacknodeException("Creation meta must be provided when creating an member association.");
		
		this.id = HUID.random();
		this.meta = MemberAssociationMeta.builder().build();
		
		var meta = meta0.get();
		
		if(meta instanceof MemberOrganizationAssociationCreationMeta _meta) {
			this.memberId = _meta.getMemberId();
			this.roleId = _meta.getRoleId();
			this.scopeId = _meta.getOrganizationId();
			
			this.meta = this.meta.withScope(MemberAssociationMeta.Scope.ORGANIZATION);
		}
		
		else if(meta instanceof MemberProjectAssociationCreationMeta _meta) {
			this.memberId = _meta.getMemberId();
			this.roleId = _meta.getRoleId();
			this.scopeId = _meta.getProjectId();
			
			this.meta = this.meta.withScope(MemberAssociationMeta.Scope.PROJECT);
		}
		
		else if(meta instanceof MemberChannelAssociationCreationMeta _meta) {
			this.memberId = _meta.getMemberId();
			this.roleId = _meta.getRoleId();
			this.scopeId = _meta.getChannelId();
			
			this.meta = this.meta.withScope(MemberAssociationMeta.Scope.CHANNEL);
		}
		else {
			throw new BlacknodeException("Unsupported CreationMeta type for MemberAssociation creation");
		}
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		if(meta0.isEmpty()) throw new BlacknodeException("Deletion meta must be provided when deleting an member association.");

		var meta = meta0.get();
		
		if(meta instanceof MemberAssociationDeletionMeta _meta) {
			// Currently no specific deletion logic
		}
		else {
			throw new BlacknodeException("Unsupported DeletionMeta type for MemberAssociation deletion");
		}
		
		deletationTimestamp = Timestamp.now();
		
	}
}
