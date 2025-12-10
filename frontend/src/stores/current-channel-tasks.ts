import { defineStore } from 'pinia';

export const useCurrentChannelTasksStore = defineStore('currentChannelTasks', () => {
    function refreshTasks(/* channelId: string */) {
        (async () => {
            // example:
            //
            // const res = await channelsApiService.requestAllChannelsForProject({
            //     organizationId: channelId,
            //     projectId: channelId,
            // });
        })();
    }

    return { refreshTasks };
});
