import { useTasksApiService } from '@/api-services/tasks';
import type { Task, TaskStatus } from '@/shared-types';
import { UnixTimestamp } from '@/utils';
import { defineStore } from 'pinia';
import { ref } from 'vue';
import { useAuthUserStore } from './auth-user';

export const useCurrentChannelTasksStore = defineStore('currentChannelTasks', () => {
    const authStore = useAuthUserStore();

    const tasksApiSerive = useTasksApiService();

    const tasks = ref<Task[]>([]);
    const statuses = ref<TaskStatus[]>([]);

    async function requestTasksForChannel(channelId: string, organizationId: string) {
        await requestTaskStatuses(organizationId);

        const tasksForChannelRes = await tasksApiSerive.requestTasksForChannel({
            channelId,
            organizationId,
            authToken: authStore.accessToken!,
        });

        if (tasksForChannelRes.isErr()) {
            console.error(
                'currentChannelTasks: requestTasksForChannel error',
                tasksForChannelRes.value,
            );
            return;
        }

        const ids = tasksForChannelRes.unwrap().ids;

        const tasksBatchRes = await tasksApiSerive.requestTasksBatch({
            ids,
            organizationId,
            authToken: authStore.accessToken!,
        });

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
        const statusesRes = await tasksApiSerive.requestStatusesBatch({
            organizationId,
            authToken: authStore.accessToken!,
            ids: [
                '03946528-a4b7-44f6-8dfb-000000000001',
                '03946528-a4b7-44f6-8dfb-000000000002',
                '03946528-a4b7-44f6-8dfb-000000000003',
                '03946528-a4b7-44f6-8dfb-000000000004',
            ],
        });

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

    function updateTask(organizationId: string, projectId: string, task: Task) {
        tasksApiSerive.updateTask({
            authToken: authStore.accessToken!,
            organizationId,
            projectId,
            updates: {
                beginAt: task.beginAt.unixTimestamp,
                endAt: task.endAt.unixTimestamp,
                description: task.description,
                priority: task.priority,
                statusId: task.statusId,
                title: task.title,
            },
        });
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
        updateTask,
        tasks,
        statuses,
    };
});
