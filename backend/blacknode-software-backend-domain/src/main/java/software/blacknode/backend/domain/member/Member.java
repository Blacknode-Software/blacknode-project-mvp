package software.blacknode.backend.domain.member;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.meta.MemberMeta;
import software.blacknode.backend.domain.member.meta.create.MemberAdminCreationMeta;
import software.blacknode.backend.domain.member.settings.MemberSettings;
import software.blacknode.backend.domain.modifier.create.Creatable;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.delete.Deletable;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.modifier.modify.Modifiable;
import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;

public class Member implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	
	@Getter private MemberSettings settings;
	@Getter private MemberMeta meta;

	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
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
		this.settings = new MemberSettings();
		this.meta = new MemberMeta();
		
		if(meta instanceof MemberAdminCreationMeta adminMeta) {
			this.accountId = adminMeta.getAccountId();
		} else {
			throwUnsupportedCreationMeta(meta);
		}
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureModificationMetaProvided(meta0);
		
		modificationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureDeletionMetaProvided(meta0);
		
		deletationTimestamp = Timestamp.now();
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
