import { API_URL } from '@/config/env';
import { defineApiService, parseResponse, passResult, type ApiError } from './utils';
import type { Result } from '@/utils';

interface RequestTasksBatchSuccess {
    message: 'Operation completed successfully.';
    status: 'success';
    items: {
        id: string;
        title: string;
        description: string;
        priority: number;
        beginAt: number;
        endAt: number;
        statusId: string;
    }[];
}

interface RequestTasksForChannelSuccess {
    message: 'Operation completed successfully.';
    status: 'success';
    ids: string[];
}

interface CreateNewTaskSuccess {
    message: 'Operation completed successfully.';
    status: 'success';
    taskId: string;
}

interface RequestTaskSuccess {
    id: string;
    title: string;
    description: string;
    priority: number;
    beginAt: number;
    endAt: number;
    statusId: string;
    status: 'success';
    message: string;
}

interface DeleteTaskSuccess {
    message: 'Operation completed successfully.';
    status: 'success';
}

interface Task {
    title: string;
    description: string;
    priority: number;
    beginAt: number;
    endAt: number;
    statusId: string;
}

interface RequestTaskStatusesSuccess {
    message: 'Operation completed successfully.';
    status: 'success';
    items: [
        {
            id: string;
            name: string;
            description: string;
            color: string;
        },
    ];
}

export const useTasksApiService = defineApiService(API_URL, {
    async requestTasksBatch(
        baseUrl,
        payload: { ids: string[]; organizationId: string; authToken: string },
    ): Promise<Result<RequestTasksBatchSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/tasks/batch-fetch`, {
                method: 'POST',
                body: JSON.stringify({
                    ids: payload.ids,
                }),
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async requestTasksForChannel(
        baseUrl,
        payload: { channelId: string; organizationId: string; authToken: string },
    ): Promise<Result<RequestTasksForChannelSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/channels/${payload.channelId}/tasks`, {
                method: 'GET',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async createNewTask(
        baseUrl,
        payload: { channelId: string; organizationId: string; authToken: string },
    ): Promise<Result<CreateNewTaskSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/channels/${payload.channelId}/tasks`, {
                method: 'POST',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async requestTask(
        baseUrl,
        payload: { taskId: string; organizationId: string; authToken: string },
    ): Promise<Result<RequestTaskSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/tasks/${payload.taskId}/tasks`, {
                method: 'GET',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async deleteTask(
        baseUrl,
        payload: { taskId: string; organizationId: string; authToken: string },
    ): Promise<Result<DeleteTaskSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/tasks/${payload.taskId}/tasks`, {
                method: 'DELETE',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async updateTask(
        baseUrl,
        payload: {
            organizationId: string;
            projectId: string;
            updates: Partial<Task>;
            authToken: string;
        },
    ) {
        return passResult(
            fetch(`${baseUrl}/projects/${payload.projectId}`, {
                method: 'PATCH',
                body: JSON.stringify({
                    operations: Object.keys(payload.updates),
                    ...payload.updates,
                }),
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async requestStatusesBatch(
        baseUrl,
        payload: { organizationId: string; authToken: string },
    ): Promise<Result<RequestTaskStatusesSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/statuses/batch-fetch`, {
                method: 'POST',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },
});
