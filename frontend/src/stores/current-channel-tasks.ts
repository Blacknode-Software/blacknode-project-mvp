import { defineStore } from 'pinia';

export const useCurrentChannelTasksStore = defineStore('currentChannelTasks', () => {
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
