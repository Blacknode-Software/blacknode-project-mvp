import type { Organization } from '@/shared-types/organization';
import { defineStore } from 'pinia';

export const useCurrentOrganizationStore = defineStore('currentChannelTasks', () => {
    const organization: Organization = {
        uuid: '5b898dae-5509-4743-aa24-ab9711d83719',
        name: 'test',
    };

    async function changeOrganization(/* organizationId: string */) {}

    return { changeOrganization, organization };
});
