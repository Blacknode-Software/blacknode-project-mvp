import { type Result } from '@/utils';
import { defineApiService } from '../utils/DefineApiService';
import { parseResponse, passResult, type ApiError } from '../utils';

interface Project {
    any: number; // todo
}

export const useProjectsApiService = defineApiService('dummy url', {
    async requestAllProjects(
        baseUrl,
        payload: { organizationId: string },
    ): Promise<Result<Project[], ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/organization/${payload.organizationId}/projects`, {
                method: 'GET',
            }),
        );
    },

    async createNewProject(
        baseUrl,
        payload: { organizationId: string; name: string; description: string },
    ) {
        return passResult(
            fetch(`${baseUrl}/organization/${payload.organizationId}/projects`, {
                method: 'POST',
                body: JSON.stringify({ name: payload.name, description: payload.description }),
            }),
        );
    },

    async requestProject(
        baseUrl,
        payload: { projectId: string },
    ): Promise<Result<Project, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/projects/${payload.projectId}`, {
                method: 'GET',
            }),
        );
    },

    async deleteProject(baseUrl, payload: { projectId: string }) {
        return passResult(
            fetch(`${baseUrl}/projects/${payload.projectId}`, {
                method: 'DELETE',
            }),
        );
    },

    async updateProject(baseUrl, payload: { projectId: string; updates: Partial<Project> }) {
        return passResult(
            fetch(`${baseUrl}/projects/${payload.projectId}`, {
                method: 'PATCH',
                body: JSON.stringify({
                    operations: Object.keys(payload.updates),
                    ...payload.updates,
                }),
            }),
        );
    },
});
