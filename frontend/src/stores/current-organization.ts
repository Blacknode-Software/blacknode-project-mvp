import { useChannelsApiService, useOrganizationsApiService } from '@/api-services';
import type { Channel, Organization } from '@/shared-types';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useCurrentOrganizationStore = defineStore('currentOrganization', () => {
    const organizationsApiService = useOrganizationsApiService();
    const channelsApiService = useChannelsApiService();

    const organization = ref<Organization>(); // e63c7895-6d65-41cb-9400-000000000001
    const channels = ref<Channel[]>();

    async function requestOrganization(organizationId: string) {
        const res = await organizationsApiService.requestOrganization({ organizationId });

        if (res.isErr()) {
            console.log('currentOrganization: requestOrganization error', res.value);
        }

        const { id, name } = res.unwrap();

        organization.value = {
            id,
            name,
        } satisfies Organization;
    }

    async function requestAllChannelsForProject(organizationId: string, projectId: string) {
        const allChannelsRes = await channelsApiService.requestAllChannelsForProject({
            organizationId,
            projectId,
        });

        if (allChannelsRes.isErr()) {
            console.log(
                'currentOrganization: requestAllChannelsForProject error',
                allChannelsRes.value,
            );
        }

        const ids = allChannelsRes.unwrap().ids;

        const channelsBatchRes = await channelsApiService.requestChannelsBatch({
            organizationId,
            ids,
        });

        if (channelsBatchRes.isErr()) {
            console.log('currentOrganization: requestChannelsBatch error', channelsBatchRes.value);
        }

        channels.value = channelsBatchRes.unwrap().items.map(
            (apiChannel) =>
                ({
                    id: apiChannel.id,
                    name: apiChannel.name,
                    description: apiChannel.description,
                    color: apiChannel.color,
                }) satisfies Channel,
        );
    }

    return { requestOrganization, requestAllChannelsForProject, organization, channels };
});
