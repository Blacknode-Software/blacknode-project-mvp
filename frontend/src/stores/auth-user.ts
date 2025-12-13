import { defineStore } from 'pinia';

export const useAuthUserStore = defineStore('authUser', () => {
    async function refreshTasks(/* channelId: string */) {
        // example:
        //
        // const res = await channelsApiService.requestAllChannelsForProject({
        //     organizationId: channelId,
        //     projectId: channelId,
        // });
    }

    return { refreshTasks };
});
