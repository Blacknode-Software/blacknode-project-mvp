import { type Result } from '@/utils';
import { defineApiService } from './utils/DefineApiService';
import { parseResponse, passResult, type ApiError, type ApiStatus } from './utils';
import { API_URL } from '@/config/env';

type Project = {
    id: string;
    name: string;
    description: string;
    color: string;
};

type NewProject = {
    projectId: string;
};

interface RequestAllProjectsForOrganizationSuccess {
    message: 'Operation completed successfully.';
    status: 'success';
    ids: string[];
}

interface RequestAllProjectsBatchSuccess {
    message: 'Operation completed successfully.';
    status: 'success';
    items: {
        id: string;
        name: string;
        description: string;
        color: string;
    }[];
}

export const useProjectsApiService = defineApiService(API_URL, {
    async requestAllProjectsForOrganization(
        baseUrl,
        payload: {
            organizationId: string;
            authToken: string;
        },
    ): Promise<Result<RequestAllProjectsForOrganizationSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/projects`, {
                method: 'GET',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async requestAllProjectsBatch(
        baseUrl,
        payload: {
            organizationId: string;
            ids: string[];
            authToken: string;
        },
    ): Promise<Result<RequestAllProjectsBatchSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/projects/batch-fetch`, {
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

    async createNewProject(
        baseUrl,
        payload: {
            organizationId: string;
            name: string;
            description: string;
            color: string;
            authToken: string;
        },
    ): Promise<Result<NewProject & ApiStatus, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/projects`, {
                method: 'POST',
                body: JSON.stringify({
                    name: payload.name,
                    description: payload.description,
                    color: payload.color,
                }),
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async requestProject(
        baseUrl,
        payload: { organizationId: string; projectId: string; authToken: string },
    ): Promise<Result<Project & ApiStatus, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/projects/${payload.projectId}`, {
                method: 'GET',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async deleteProject(
        baseUrl,
        payload: { organizationId: string; projectId: string; authToken: string },
    ) {
        return passResult(
            fetch(`${baseUrl}/projects/${payload.projectId}`, {
                method: 'DELETE',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async updateProject(
        baseUrl,
        payload: {
            organizationId: string;
            projectId: string;
            updates: Partial<Project>;
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
});
