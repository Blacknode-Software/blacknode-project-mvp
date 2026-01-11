import { useOrganizationsApiService } from '@/api-services';
import type { Organization } from '@/shared-types/organization';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useCurrentOrganizationStore = defineStore('currentOrganization', () => {
    const organizationsApiService = useOrganizationsApiService();

    const organization = ref<Organization>();

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

    return { requestOrganization, organization };
});
