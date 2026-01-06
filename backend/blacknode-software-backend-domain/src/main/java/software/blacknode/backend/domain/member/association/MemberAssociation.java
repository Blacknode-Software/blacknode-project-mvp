package software.blacknode.backend.domain.member.association;

import java.util.Optional;

import lombok.Getter;
import lombok.NonNull;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.association.meta.MemberAssociationMeta;
import software.blacknode.backend.domain.member.association.meta.create.MemberAssociationCreationMeta;
import software.blacknode.backend.domain.member.association.meta.delete.MemberAssociationDeletionMeta;

public class MemberAssociation implements DomainEntity, Creatable, Deletable {

	@Getter private HUID id;
	
	@Getter private MemberAssociationMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Getter private HUID memberId;
	@Getter private HUID entityId;
	@Getter private HUID roleId;
	
	@Getter private Scope scope;
	
	@Getter private HUID organizationId;
	
	public MemberAssociation(HUID organizationId) {
		this.organizationId = organizationId;
	}
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureCreationMetaProvided(meta0);
		
		this.id = HUID.random();
		
		var meta = meta0.get();
		
		if(meta instanceof MemberAssociationCreationMeta _meta) {
			@NonNull var memberId = _meta.getMemberId();
			@NonNull var entityId = _meta.getEntityId();
			@NonNull var roleId = _meta.getRoleId();
			@NonNull var scope = _meta.getScope();
			
			this.memberId = memberId;
			this.entityId = entityId;
			this.roleId = roleId;
			this.scope = scope;
			
			this.meta = MemberAssociationMeta.builder().build();
		} else throwUnsupportedCreationMeta(meta);
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof MemberAssociationDeletionMeta _meta) {
			// Currently no specific deletion logic
		} else throwUnsupportedDeletionMeta(meta);
		
		deletionTimestamp = Timestamp.now();
		
	}
	
	public boolean belognsToOrganization(HUID organizationId) {
		return this.organizationId.equals(organizationId);
	}
	
	public void ensureBelongsToOrganization(HUID organizationId) {
		if(!belognsToOrganization(organizationId)) {
			throw new BlacknodeException("Association does not belong to organization with ID: " + organizationId);
		}
	}
	
	public enum Scope {
		ORGANIZATION,
		PROJECT,
		CHANNEL,
		UNKNOWN
	}
	
}
