import { type Result } from '@/utils';
import { defineApiService } from './utils/DefineApiService';
import { parseResponse, passResult, type ApiError, type ApiStatus } from './utils';

type Project = {
    id: string;
    name: string;
    description: string;
    color: string;
};

type AllProjectsIds = {
    ids: string[];
};

type NewProject = {
    projectId: string;
};

export const useProjectsApiService = defineApiService('dummy url', {
    async requestAllProjectsIds(
        baseUrl,
        payload: {
            organizationId: string;
        },
    ): Promise<Result<AllProjectsIds & ApiStatus, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/projects`, {
                method: 'GET',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                },
            }),
        );
    },

    async requestAllProjectsBatch(
        baseUrl,
        payload: {
            organizationId: string;
            ids: string[];
        },
    ): Promise<Result<AllProjectsIds & ApiStatus, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/projects/batch-fetch`, {
                method: 'GET',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                },
                body: JSON.stringify({
                    identifiers: payload.ids,
                }),
            }),
        );
    },

    async createNewProject(
        baseUrl,
        payload: { organizationId: string; name: string; description: string; color: string },
    ): Promise<Result<NewProject & ApiStatus, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/projects`, {
                method: 'POST',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                },
                body: JSON.stringify({
                    name: payload.name,
                    description: payload.description,
                    color: payload.color,
                }),
            }),
        );
    },

    async requestProject(
        baseUrl,
        payload: { organizationId: string; projectId: string },
    ): Promise<Result<Project & ApiStatus, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/projects/${payload.projectId}`, {
                method: 'GET',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                },
            }),
        );
    },

    async deleteProject(baseUrl, payload: { organizationId: string; projectId: string }) {
        return passResult(
            fetch(`${baseUrl}/projects/${payload.projectId}`, {
                method: 'DELETE',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                },
            }),
        );
    },

    async updateProject(
        baseUrl,
        payload: { organizationId: string; projectId: string; updates: Partial<Project> },
    ) {
        return passResult(
            fetch(`${baseUrl}/projects/${payload.projectId}`, {
                method: 'PATCH',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                },
                body: JSON.stringify({
                    operations: Object.keys(payload.updates),
                    ...payload.updates,
                }),
            }),
        );
    },
});
