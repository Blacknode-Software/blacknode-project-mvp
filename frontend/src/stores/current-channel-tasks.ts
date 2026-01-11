import { useTasksApiService } from '@/api-services/tasks';
import type { Task, TaskStatus } from '@/shared-types';
import { UnixTimestamp } from '@/utils';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useCurrentChannelTasksStore = defineStore('currentChannelTasks', () => {
    const tasksApiSerive = useTasksApiService();

    const tasks = ref<Task[]>([]);
    const statuses = ref<TaskStatus[]>([]);

    async function requestTasksForChannel(channelId: string, organizationId: string) {
        if (statuses.value.length <= 0) {
            await requestTaskStatuses(organizationId);
        }

        const tasksForChannelRes = await tasksApiSerive.requestTasksForChannel({
            channelId,
            organizationId,
        });

        if (tasksForChannelRes.isErr()) {
            console.error(
                'currentChannelTasks: requestTasksForChannel error',
                tasksForChannelRes.value,
            );
            return;
        }

        const ids = tasksForChannelRes.unwrap().ids;

        const tasksBatchRes = await tasksApiSerive.requestTasksBatch({ ids, organizationId });

        if (tasksBatchRes.isErr()) {
            console.error('currentChannelTasks: requestTasksBatch error', tasksBatchRes.value);
            return;
        }

        tasks.value = tasksBatchRes.unwrap().items.map(
            (apiTask) =>
                ({
                    id: apiTask.id,
                    description: apiTask.description,
                    priority: apiTask.priority,
                    title: apiTask.title,
                    beginAt: new UnixTimestamp(apiTask.beginAt),
                    endAt: new UnixTimestamp(apiTask.endAt),
                    statusId: apiTask.statusId,
                }) satisfies Task,
        );
    }

    async function requestTaskStatuses(organizationId: string) {
        const statusesRes = await tasksApiSerive.requestStatusesBatch({ organizationId });

        if (statusesRes.isErr()) {
            console.error('currentChannelTasks: requestStatusesBatch error', statusesRes.value);
            return;
        }

        statuses.value = statusesRes.unwrap().items.map(
            (apiTaskSatus) =>
                ({
                    color: apiTaskSatus.color,
                    description: apiTaskSatus.description,
                    id: apiTaskSatus.id,
                    name: apiTaskSatus.name,
                }) satisfies TaskStatus,
        );
    }

    function getStatusWithId(id: string) {
        return statuses.value.find((status) => status.id === id);
    }

    function getTasksWithStatus(status: TaskStatus) {
        return tasks.value.filter((task) => task.statusId === status.id);
    }

    return {
        requestTaskStatuses,
        requestTasksForChannel,
        getStatusWithId,
        getTasksWithStatus,
        tasks,
        statuses,
    };
});
