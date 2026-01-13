<script lang="ts" setup>
import { useInvitesApiService } from '@/api-services';
import type { Invite } from '@/shared-types';
import { useAuthUserStore } from '@/stores/auth-user';
import { TimestampDate } from '@/ui-toolkit';
import { UnixTimestamp } from '@/utils';
import { computed, onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';

const authStore = useAuthUserStore();
const invitesApiService = useInvitesApiService();
const invites = ref<Invite[]>([]);

const dateFormatter = computed(
    () =>
        new Intl.DateTimeFormat('en-US', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
        }),
);

const route = useRoute();

onMounted(() => {
    invitesApiService.requestOrganizationInvites({
        authToken: authStore.accessToken!,
        organizationId: route.params.organization_id as string,
    });
});

watch(() => [route.params.organization_id], handleInvitesLoading, { immediate: true });

async function handleInvitesLoading() {
    const organizationId = route.params.organization_id as string;

    const invitesIdsRes = await invitesApiService.requestOrganizationInvites({
        authToken: authStore.accessToken!,
        organizationId,
    });

    if (invitesIdsRes.isErr()) {
        console.error('Invites loading error', invitesIdsRes.value);
        return;
    }

    const _invites = await invitesApiService.batchFeatchInvites({
        authToken: authStore.accessToken!,
        ids: invitesIdsRes.unwrap().ids,
        organizationId,
    });

    if (invitesIdsRes.isErr()) {
        console.error('Invites loading error 2', invitesIdsRes.value);
        return;
    }

    invites.value = _invites.unwrap().items.map(
        (apiInvite) =>
            ({
                claimed: apiInvite.claimed,
                claimedAt: new UnixTimestamp(apiInvite.claimedAt),
                claimedBy: apiInvite.claimedBy,
                email: apiInvite.email,
                expired: apiInvite.expired,
                expiresAt: new UnixTimestamp(apiInvite.expiresAt),
                id: apiInvite.id,
                message: apiInvite.message,
                revoked: apiInvite.revoked,
                status: apiInvite.status,
                token: apiInvite.token,
            }) satisfies Invite,
    );
}
</script>

<template>
    <div class="invites-wrapper">
        <div class="invites-heading">Invites</div>
        <div class="invites-desc">A list of new users</div>
        <table class="invites-table">
            <tbody>
                <tr class="invites-table-header">
                    <th>Full name</th>
                    <th>Email</th>
                    <th>Invite link</th>
                    <th>Status</th>
                    <th>Expires at</th>
                </tr>
                <tr v-for="invite in invites" :key="invite.id" class="invite-row">
                    <td>{{ invite.claimedBy }}</td>
                    <td>{{ invite.email }}</td>
                    <td>{{ invite.token }}</td>
                    <td>{{ invite.status }}</td>
                    <td>
                        <TimestampDate :formatter="dateFormatter" :timestmap="invite.expiresAt" />
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<style scoped>
.invites-wrapper {
    padding: 24px 16px;
    width: 100%;
}

.invites-heading {
    font-size: 16px;
    color: white;
}

.invites-desc {
    font-size: 14px;
    color: var(--color-second-gray);
}

.invites-table {
    width: 100%;
    font-size: 14px;
    color: white;
    border-collapse: collapse;
}

.invites-table-header {
    color: var(--color-second-gray);
    font-size: 12px;
}

.invites-table th {
    padding: 10px 12px;
    text-align: left;
}

.invites-table td {
    padding: 14px 12px;
}

.invites-table tr {
    border-bottom: 1px solid #7a7a9075;
}

.invite-row {
    cursor: pointer;
}

.invite-row:hover {
    background-color: #00000021;
}
</style>
