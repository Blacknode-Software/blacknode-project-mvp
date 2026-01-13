import {
    useChannelsApiService,
    useOrganizationsApiService,
    useProjectsApiService,
} from '@/api-services';
import type { Channel, Organization } from '@/shared-types';
import type { Project } from '@/shared-types/project';
import { defineStore } from 'pinia';
import { ref } from 'vue';
import { useAuthUserStore } from './auth-user';

export const useCurrentOrganizationStore = defineStore('currentOrganization', () => {
    const authStore = useAuthUserStore();

    const organizationsApiService = useOrganizationsApiService();
    const projectsApiService = useProjectsApiService();
    const channelsApiService = useChannelsApiService();

    const organization = ref<Organization>(); // e63c7895-6d65-41cb-9400-000000000001
    const projects = ref<Project[]>();
    const channels = ref<Record<string, Channel[]>>({});

    async function requestWholeOrganization(organizationId: string) {
        organization.value = undefined;
        projects.value = undefined;
        channels.value = {};

        await requestOrganization(organizationId);
        await requestAllProjectsForOrganization(organizationId);

        if (projects.value) {
            await Promise.all(
                (projects.value as Project[]).map(async (project) =>
                    requestAllChannelsForProject(organizationId, (project as Project).id),
                ),
            );
        }
    }

    async function requestOrganization(organizationId: string) {
        const res = await organizationsApiService.requestOrganization({
            organizationId,
            authToken: authStore.accessToken!,
        });

        if (res.isErr()) {
            console.error('currentOrganization: requestOrganization error', res.value);
            return;
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
            authToken: authStore.accessToken!,
        });

        if (allChannelsRes.isErr()) {
            console.error(
                'currentOrganization: requestAllChannelsForProject error',
                allChannelsRes.value,
            );
            return;
        }

        const ids = allChannelsRes.unwrap().ids;

        const channelsBatchRes = await channelsApiService.requestChannelsBatch({
            organizationId,
            ids,
            authToken: authStore.accessToken!,
        });

        if (channelsBatchRes.isErr()) {
            console.error(
                'currentOrganization: requestChannelsBatch error',
                channelsBatchRes.value,
            );
            return;
        }

        channels.value[projectId] = channelsBatchRes.unwrap().items.map(
            (apiChannel) =>
                ({
                    id: apiChannel.id,
                    name: apiChannel.name,
                    description: apiChannel.description,
                    color: apiChannel.color,
                }) satisfies Channel,
        );
    }

    async function requestAllProjectsForOrganization(organizationId: string) {
        const allProjectsRes = await projectsApiService.requestAllProjectsForOrganization({
            organizationId,
            authToken: authStore.accessToken!,
        });

        if (allProjectsRes.isErr()) {
            console.error(
                'currentOrganization: requestAllProjectsForOrganization error',
                allProjectsRes.value,
            );
            return;
        }

        const ids = allProjectsRes.unwrap().ids;

        const projectsBatchRes = await projectsApiService.requestAllProjectsBatch({
            organizationId,
            ids,
            authToken: authStore.accessToken!,
        });

        if (projectsBatchRes.isErr()) {
            console.error(
                'currentOrganization: requestAllProjectsBatch error',
                projectsBatchRes.value,
            );
            return;
        }

        projects.value = projectsBatchRes.unwrap().items.map(
            (apiProjects) =>
                ({
                    color: apiProjects.color,
                    description: apiProjects.description,
                    id: apiProjects.id,
                    name: apiProjects.name,
                }) satisfies Project,
        );
    }

    return {
        requestOrganization,
        requestAllChannelsForProject,
        requestAllProjectsForOrganization,
        requestWholeOrganization,
        organization,
        projects,
        channels,
    };
});
