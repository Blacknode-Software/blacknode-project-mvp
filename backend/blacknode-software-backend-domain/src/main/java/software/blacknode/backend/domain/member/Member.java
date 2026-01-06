package software.blacknode.backend.domain.member;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.Modifiable;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.meta.MemberMeta;
import software.blacknode.backend.domain.member.meta.create.MemberCreationMeta;
import software.blacknode.backend.domain.member.meta.delete.MemberDeletionMeta;
import software.blacknode.backend.domain.member.meta.modify.MemberModificationMeta;

@Builder
@AllArgsConstructor(onConstructor = @__({ @Deprecated }))
public class Member implements DomainEntity, Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	
	//@Getter private MemberSettings settings;
	@Getter private MemberMeta meta;

	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Getter private HUID accountId;
	@Getter private HUID organizationId;
	
	public Member(HUID organizationId) {
		this.organizationId = organizationId;
	}
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);
		
		var meta = meta0.get();
		
		this.id = HUID.random();
		//this.settings = new MemberSettings();
		
		if(meta instanceof MemberCreationMeta _meta) {
			
			@NonNull var accountId = _meta.getAccountId();
			
			this.accountId = accountId;
			
			this.meta = MemberMeta.builder()
					.build();
			
		} else throwUnsupportedCreationMeta(meta);
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureModificationMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof MemberModificationMeta _meta) {
			// Apply modifications here
		} else throwUnsupportedModificationMeta(meta);
		
		modificationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureDeletionMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof MemberDeletionMeta _meta) {
			// Apply deletion logic here
		} else throwUnsupportedDeletionMeta(meta);
		
		deletionTimestamp = Timestamp.now();
	}
	
	public boolean belongsToOrganization(HUID organizationId) {
        if (organizationId == null) return false;
        return organizationId.equals(this.organizationId);
    }

    public void ensureBelongsToOrganization(HUID organizationId) {
        if (!belongsToOrganization(organizationId)) {
			throw new BlacknodeException("Member with ID " + id + " does not belong to Organization with ID " + organizationId + ".");
        }
    }
	
}
