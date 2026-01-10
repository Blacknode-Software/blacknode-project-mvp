package software.blacknode.backend.application.access.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.exception.AccessDeniedException;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.task.TaskService;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.task.Task;

@Service
@RequiredArgsConstructor
public class TaskAccessControl {

	private final ChannelAccessControl channelAccessControl;
	private final MemberService memberService;
	private final TaskService taskService;
	
	public void ensureMemberHasTaskAccess(HUID organizationId, HUID memberId, HUID taskId, AccessLevel level) {
		var member = memberService.getOrThrow(organizationId, memberId);
		var task = taskService.getOrThrow(organizationId, taskId);
		
		ensureMemberHasTaskAccess(member, task, level);
	}
	
	public void ensureMemberHasTaskAccess(Member member, HUID taskId, AccessLevel level) {
		var task = taskService.getOrThrow(member.getOrganizationId(), taskId);
		
		ensureMemberHasTaskAccess(member, task, level);
	}
	
	public void ensureMemberHasTaskAccess(HUID memberId, Task task, AccessLevel level) {
		var member = memberService.getOrThrow(task.getOrganizationId(), memberId);
		
		ensureMemberHasTaskAccess(member, task, level);
	}
	
	public void ensureMemberHasTaskAccess(Member member, Task task, AccessLevel level) {
		var hasAccess = hasAccessToTask(member, task, level);
		
		var memberId = member.getId();
		var taskId = task.getId();
		
		if(!hasAccess) {
			throw new AccessDeniedException("Member with ID " + memberId + " does not have " + level + " access to task with ID " + taskId + ".");		
		}
	}
	
	public AccessLevel getRoleAccessInTask(HUID organizationId, HUID memberId, HUID taskId) {
		var task = taskService.getOrThrow(organizationId, taskId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return getRoleAccessInTask(member, task);
	}
	
	public AccessLevel getRoleAccessInTask(Member member, HUID taskId) {
		var task = taskService.getOrThrow(member.getOrganizationId(), taskId);
		
		return getRoleAccessInTask(member, task);
	}
	
	public AccessLevel getRoleAccessInTask(HUID memberId, Task task) {
		var member = memberService.getOrThrow(task.getOrganizationId(), memberId);
		
		return getRoleAccessInTask(member, task);
	}
	
	public AccessLevel getRoleAccessInTask(Member member, Task task) {
		var organizationId = member.getOrganizationId();
		
		task.ensureBelongsToOrganization(organizationId);
		
		var channelId = task.getChannelId();
		
		var access = channelAccessControl.getRoleAccessInChannel(member, channelId);
		
		/* If the member is the owner of the task, grant MANAGE access regardless of their role access in the channel. */
		if(!access.atLeast(AccessLevel.MANAGE) && task.isOwnedByMember(member.getId())) {
			return AccessLevel.MANAGE;
		}
		
		return access;
	}
	
	public boolean hasAccessToTask(HUID organizationId, HUID memberId, HUID taskId, AccessLevel level) {
		var task = taskService.getOrThrow(organizationId, taskId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return hasAccessToTask(member, task, level);
	}
	
	public boolean hasAccessToTask(Member member, HUID taskId, AccessLevel level) {
		var task = taskService.getOrThrow(member.getOrganizationId(), taskId);
		
		return hasAccessToTask(member, task, level);
	}
	
	public boolean hasAccessToTask(HUID memberId, Task task, AccessLevel level) {
		var member = memberService.getOrThrow(task.getOrganizationId(), memberId);
		
		return hasAccessToTask(member, task, level);
	}
	
	public boolean hasAccessToTask(Member member, Task task, AccessLevel level) {
		var access = getRoleAccessInTask(member, task);
		
		return access.atLeast(level);
	}
}